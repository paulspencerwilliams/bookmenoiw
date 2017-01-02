(ns bookmenow.routes.auth
  (:require [compojure.core :refer :all]
            [bookmenow.views.layout :as layout]
            [environ.core :refer [env]]
            [clj-http.client :as client]))

(defn handle-get []
  (layout/common
    [:a {:href
         (str
           "https://slack.com/oauth/authorize?scope="
           "bot"
           "&client_id="
           (env :slack-client-id)
           "&redirect_uri="
           (env :slack-redirect-url))}
     [:img {:alt    "Add to Slack"
            :height 40
            :width  149
            :src    "https://platform.slack-edge.com/img/add_to_slack.png"}]]))

(defn handle-finish-auth [request]
  (let [request-param (str "https://slack.com/api/oauth.access?client_id="
                           (env :slack-client-id)
                           "&client_secret="
                           (env :slack-client-secret)
                           "&code="
                           (:code (:params request)))]
    (get-in (client/get request-param {:as :json})
            [:body :bot :bot_access_token])))

(defroutes auth-routes
           (GET "/auth" [] (handle-get))
           (GET "/auth/finish" request (handle-finish-auth request)))
