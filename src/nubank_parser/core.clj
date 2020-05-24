(ns nubank-parser.core
  (:gen-class)
  (:require 
      [nubank-parser.data-source.parser :as parser]
      [nubank-parser.data-source.reader :as reader]
      [nubank-parser.transformations.group-by :as group-by]
      [nubank-parser.presenters.simple-table :as simple-table]
      ))

(defn get-credit-card-entries [directory-path]
  (flatten (map parser/parse (reader/read-entries directory-path))))

(defn execute
  "Reads a list of csv files and prints the corresponding maps"
  [directory-path]
  (let [credit-card-entries (get-credit-card-entries directory-path)]
    (doseq [[month entries] (group-by (group-by/comp-funcs :category group-by/month-year-str) credit-card-entries)]
      (simple-table/print month entries)
      )))

