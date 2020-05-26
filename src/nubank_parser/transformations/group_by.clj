(ns nubank-parser.transformations.group-by
  [:require [nubank-parser.transformations.aggregate :refer [aggregate]]])

(defn group-by-column [rows column]
  (group-by #(select-keys % [column]) rows))

(defn group-by-aggregate [rows aggregate-method group-column]
  (reduce-kv (fn [arr key value] (conj arr (assoc key aggregate-method (aggregate aggregate-method value)))) [] (group-by-column rows group-column)))
