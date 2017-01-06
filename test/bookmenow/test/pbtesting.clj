(ns bookmenow.test.pbtesting)

;; static typing can get in the way
;; optional
;; example sorting elements
;; shrinking
;; generative testing
;; meta programming
;; works in CLJS as well

(require '[clojure.spec :as s])

(defn ranged-rand
  "Returns random int in range start <= rand < end"
  [start end]
  (+ start (long (rand (- end start)))))

(s/fdef ranged-rand
        :args (s/and (s/cat :start int? :end int?)
                     #(< (:start %) (:end %)))
        :ret int?
        :fn (s/and #(>= (:ret %) (-> % :args :start))
                   #(< (:ret %) (-> % :args :end))))

(ranged-rand 8 5)

(defn ranged-rand  ;; BROKEN!
  "Returns random int in range start <= rand < end"
  [start end]
  (+ start (long (rand (- start end)))))

(+ 1 3)
