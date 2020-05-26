(ns nubank-parser.core
  (:gen-class)
  (:require
   [nubank-parser.data-source.parser :as parser]
   [nubank-parser.data-source.reader :as reader]
   [nubank-parser.transformations.group-by :refer [group-by-aggregate]]
   [nubank-parser.presenters.simple-table :as simple-table]
   [nubank-parser.transformations.aggregate :refer [aggregate]]))

(defn get-credit-card-entries [directory-path]
  (flatten (map parser/parse (reader/read-entries directory-path))))

(defn execute
  "Reads a list of csv files and prints the corresponding maps"
  [directory-path options]
  (let [{group-column :group-by} options
        {where-fn :where} options
        {aggregate-method :aggregate} options]
    (let [credit-card-entries (filter where-fn (get-credit-card-entries directory-path))]
      (cond
        (empty? credit-card-entries) (println "No entries or csv files found on input director wy")
        (and group-column aggregate-method)
        (simple-table/print-table (group-by-aggregate credit-card-entries aggregate-method group-column))
        (group-column) (println "If you provide the group-by option you must also provide the aggregate")
        (aggregate-method) (println (aggregate aggregate-method credit-card-entries))
        :else (simple-table/print-table credit-card-entries)))))

