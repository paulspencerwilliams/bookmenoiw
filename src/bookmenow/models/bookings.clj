(ns bookmenow.models.bookings
  (:require [clojure.spec :as s]
            [clojure.string :as string])
  (:import (org.ocpsoft.prettytime.nlp PrettyTimeParser)))

(def all-bookings (atom {}))

(defn user-booking [real-name]
  (if (contains? @all-bookings real-name)
    (get @all-bookings real-name)
    {}))

(defn parse-dates [booking text]
  (let [dates (.parse (PrettyTimeParser.) text)]
    (cond
      (= 1 (count dates)) (-> (assoc booking :from (first dates))
                              (dissoc :to))
      (= 2 (count dates)) (-> (assoc booking :from (first dates))
                              (assoc :to (second dates)))
      :else booking)))

(defn add-some-details [real-name text]
  (get
    (swap!
      all-bookings
      assoc
      real-name
      (-> (user-booking real-name)
          (parse-dates text))) real-name))

(defn progress [real-name text]
  (if (= (string/upper-case text) "FORGET IT")
    (get
      (swap!
        all-bookings
        assoc
        real-name
        {}) real-name)
    (add-some-details real-name text)))


(def complete? (s/keys :req [::from ::to ::organiser] :opt [::meeting-name]))
(clojure.pprint/pprint (s/explain-data complete? {}))
(clojure.pprint/pprint (s/explain-data complete? {::from "wat"}))
