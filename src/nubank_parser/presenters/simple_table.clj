(ns nubank-parser.presenters.simple-table
  (:gen-class)
  (:require
   [clojure.pprint :as pprint]))

(defn print-header [header]
  (println (clojure.string/join " - " header)))

(defn print-table [entries]
  (pprint/print-table entries))
