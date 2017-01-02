(ns bookmenow.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [bookmenow.routes.auth :refer [auth-routes]]
            [bookmenow.routes.events :refer [event-routes]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(defn init []
  (println "bookmenow is starting"))

(defn destroy []
  (println "bookmenow is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes auth-routes event-routes app-routes)
      (handler/site)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-json-response)))
