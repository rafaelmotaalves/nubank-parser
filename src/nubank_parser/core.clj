(ns nubank-parser.core
  (:gen-class)
  (:require [clj-time.core :as time]
            [nubank-parser.data-source.parser :as parser]
            [nubank-parser.data-source.reader :as reader]
            [nubank-parser.transformations.group-by :as group-by]))

(require '[clojure.pprint :as pprint])

(defn get-credit-card-entries []
  (flatten (map parser/parse (reader/read-entries))))

(defn -main
  "Reads a list of csv files and prints the corresponding maps"
  [& args]
  (let [credit-card-entries (get-credit-card-entries)]
    (doseq [[month entries] (group-by (group-by/comp-funcs :category group-by/month-year-str) credit-card-entries)]
      (do
        (println month)
        (pprint/print-table entries)))))

