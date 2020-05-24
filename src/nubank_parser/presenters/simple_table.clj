(ns nubank-parser.presenters.simple-table
  (:gen-class)
  (:require
    [clojure.pprint :as pprint]
    ))

(defn print [header entries]
    (do
        (println header)
        (pprint/print-table entries)
    )
)