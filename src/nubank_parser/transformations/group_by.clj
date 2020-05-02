(ns nubank-parser.transformations.group-by
  [:require [clj-time.core :as time]])

(defn- year-month-hash [entry]
  (let [date (:date entry)]
    (time/date-time (time/year date) (time/month date))))

(defn by-month [entries]
  (group-by year-month-hash entries))

(defn by-category [entries]
  (group-by :category entries))

(defn by-title [entries]
  (group-by :title entries))


(defn compose-group-by-key [entry functions]
  (reduce (fn [a x] (str a "+" (apply x [entry]))) "" functions))

(defn multi-key-group-by [entries & functions]
  (group-by #(compose-group-by-key % functions) entries)
  )

