(ns nubank-parser.presenters.simple-table
  (:gen-class)
  (:require
    [clojure.pprint :as pprint]
    ))

(defn print-output [header entries]
    (do
        (println (clojure.string/join " - " header))
        (pprint/print-table entries)
    )
)
