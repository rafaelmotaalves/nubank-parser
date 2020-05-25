(ns nubank-parser.core
  (:gen-class)
  (:require
   [nubank-parser.data-source.parser :as parser]
   [nubank-parser.data-source.reader :as reader]
   [nubank-parser.transformations.group-by :as group-by]
   [nubank-parser.presenters.simple-table :as simple-table]))

(defn get-credit-card-entries [directory-path]
  (flatten (map parser/parse (reader/read-entries directory-path))))

(defn execute
  "Reads a list of csv files and prints the corresponding maps"
  [directory-path options]
  (let [{group-column :group-by} options]
    (let [credit-card-entries (get-credit-card-entries directory-path)]
      (cond
        (empty? credit-card-entries) (print "No entries or csv files found on input directory")
        (not (nil? group-column))
        (doseq [[group entries] (group-by (group-by/comp-funcs group-column) credit-card-entries)]
          (simple-table/print-header group)
          (simple-table/print-table entries)
          )
        :else (simple-table/print-table credit-card-entries)
        )
    )))

