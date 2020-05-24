(ns nubank-parser.core
  (:gen-class)
  (:require 
      [nubank-parser.data-source.parser :as parser]
      [nubank-parser.data-source.reader :as reader]
      [nubank-parser.transformations.group-by :as group-by]
      [clojure.pprint :as pprint]))

(defn get-credit-card-entries [directory-path]
  (flatten (map parser/parse (reader/read-entries directory-path))))

(defn execute
  "Reads a list of csv files and prints the corresponding maps"
  [directory-path]
  (let [credit-card-entries (get-credit-card-entries directory-path)]
    (doseq [[month entries] (group-by (group-by/comp-funcs :category group-by/month-year-str) credit-card-entries)]
      (do
        (println month)
        (pprint/print-table entries)))))

