(ns nubank-parser.transformations.group-by
  [:require [clj-time.core :as time]])

(defn month-year-str [entry]
  (let [date (:date entry)]
    (format "%02d/%04d" (time/month date) (time/year date))))

(defn comp-funcs [& fns]
  "Returns a functions that returns the combines the result of the passed functions"
  (fn [x] (map #(apply % [x]) fns)))
