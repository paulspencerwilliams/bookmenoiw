(ns bookmenow.routes.events
  (:require [compojure.core :refer :all]
            [environ.core :refer [env]]
            [clj-http.client :as client]
            [bookmenow.models.bookings :as bookings]))

(defn profile [access-token user-id]
  (get-in
    (client/get
      (str "https://slack.com/api/users.info?token=" access-token "&user=" user-id)
      {:as :json})
    [:body :user :profile]))

(defn handle-callback [body]
  (println "handling callback")
  (let [event (:event body)
        {text :text channel :channel user-id :user} event
        access-token (env :slack-access-token)
        real-name (:real_name (profile access-token user-id))]
    (when (not (= real-name "bookmenowdev"))
      (println "posting")
      (client/post "https://slack.com/api/chat.postMessage"
                   {:form-params {:token   access-token
                                  :channel channel
                                  :text    (.toString (bookings/progress real-name text))
                                  :as_user true}}))))

(defn progress-booking [request]
  (let [body (:body request)]
    (println body)
    (case (:type body)
      "url_verification" {:body {:challenge (:challenge body)}}
      "event_callback" (handle-callback body))))

(defroutes event-routes
           (POST "/events" request (progress-booking request)))
