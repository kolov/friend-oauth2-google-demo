(defproject friend-oauth-google-demo "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [
                           [org.clojure/clojure "1.6.0"]
                           [enlive "1.1.5"]
                           [org.clojure/clojure "1.5.1"]
                           [compojure "1.1.5" :exclusions [ring/ring-core org.clojure/core.incubator]]
                           [com.cemerick/friend "0.2.0" :exclusions [ring/ring-core]]
                           [friend-oauth2 "0.1.1" :exclusions [org.apache.httpcomponents/httpcore]]
                           [cheshire "5.2.0"]
                           [ring-server "0.3.0" :exclusions [ring]]
                           [org.clojure/data.json "0.2.6"]
                           [environ "0.5.0"]
                           ]
            :plugins [
                      [lein-ring "0.8.5" :exclusions [org.clojure/clojure]]
                      [lein-environ "1.0.0"]
                      ]
            :ring {:handler friend-oauth2-google-demo.core/app}

            )
