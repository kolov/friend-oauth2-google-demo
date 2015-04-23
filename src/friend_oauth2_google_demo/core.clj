(ns friend-oauth2-google-demo.core
  (:require [compojure.core :refer :all]
            [net.cgrand.enlive-html :refer :all]
            [compojure.handler :as handler]
            [clj-http.client :as client]
            [cemerick.friend :as friend]
            [friend-oauth2.workflow :as oauth2]
            [friend-oauth2.util :refer [format-config-uri]]
            [clojure.data.json :as json]
            [environ.core :refer [env]]
            )
  )



(def client-config
  {:client-id     (env :client-id)
   :client-secret (env :client-secret)
   :callback      {:domain "http://localhost:3000" :path "/oauth2callback"}})

(defn config-ok? [] (not (or (nil? (:client-id client-config)) (nil? (:client-secret client-config)))))

(deftemplate index-template "index.html" [req current-auth]
             [:div#message]
             (content (if (config-ok?)
                        "client-id & client-secret are set correctly."
                        "Please, define client-id & client-secret first. See the README"))

             [:div#login]
             (if (and (config-ok?) (not current-auth))
               (content (html [:form {:action "/login"} [:input {:type "submit" :value "Login"}]])))

             [:div#logout]
             (if current-auth
               (content (html [:form {:action "/logout"} [:input {:type "submit" :value "Logout"}]]))
               )
             [:div#user]
             (if current-auth
               (content (str "User:" current-auth))
               (content "No user in session"))
             )

(defn index [req]
  {:body    (index-template req (friend/current-authentication req))
   :headers {"Content-type" "text/html"}})

(defn body-as-json [req]
  (-> req :body slurp (json/read-str :key-fn keyword)))

(defn credential-fn
  [token]
  (let [
        resp (client/get (str "https://www.googleapis.com/plus/v1/people/me?access_token=" (:access-token token)))
        data (-> resp :body (json/read-str :key-fn keyword))]

    {:identity (:id data) :roles #{::user}}))

(def uri-config
  {:authentication-uri {:url   "https://accounts.google.com/o/oauth2/auth"
                        :query {:client_id     (:client-id client-config)
                                :response_type "code"
                                :redirect_uri  (format-config-uri client-config)
                                :scope         (str "profile email")}}

   :access-token-uri   {:url   "https://accounts.google.com/o/oauth2/token"
                        :query {:client_id     (:client-id client-config)
                                :client_secret (:client-secret client-config)
                                :grant_type    "authorization_code"
                                :redirect_uri  (format-config-uri client-config)}}})

(defroutes ring-app
           (GET "/" request (index request))
           (friend/logout (ANY "/logout" request (ring.util.response/redirect "/"))))

(def app
  (handler/site
    (friend/authenticate
      ring-app
      {:allow-anon? true
       :workflows   [(oauth2/workflow
                       {:client-config client-config
                        :uri-config    uri-config
                        :credential-fn credential-fn})]})))
