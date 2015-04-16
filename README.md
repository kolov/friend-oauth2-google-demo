# friend-oauth2-google-demo

Demo of Google OpenID Connect (OAuth2) authentication with 
[ddellacosta/friend-oauth2](https://github.com/ddellacosta/friend-oauth2) 
and
[cemerick/friend](https://github.com/cemerick/friend).

They do the heavy lifting, this is just a small app that lets you log in with google, retrieves the identity 
information provided by google and shows it on screen, something like:

    identity {:gender "male", 
              :kind "plus#person", 
              :etag "\"RqKWnRU4WW46-6W3rWhLR9iFZQM/oXobESu4b9W1k-_frJhkhk75tQI\"", 
              :name 
            {:familyName "xxx", :givenName "xxx"}, 
            :placesLived [{:value "xxx", :primary true}], 
            :image {:url "https://...", 
            :isDefault false}, :urls [{:value "https://profiles.google.com/...", 
            :type "contributor", :label "Buzz"}], 
            :language "en_GB", 
            :displayName "...", 
            :emails [{:value "...", :type "account"}], 
            :url "https://plus.google.com...", 
            :isPlusUser true, :verified false, :id "..", :objectType "person"}, 
            :roles #{:friend-oauth2-google-demo.core/user}}

## Usage

First, you need to register an application in the [Google Developers Console](https://console.developers.google.com).
Enable both OAuth and  Google+ API.
Then, export the needed keys and run the app:

    export CLIENT_ID=[your id here]
    export CLIENT_SECRET=[your secret here]
    lein ring server-headless
    
## License

Copyright © 2015

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
