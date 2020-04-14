(ns nubank-parser.core
  (:gen-class))

(require '[nubank-parser.data-source.parser :as parser])
(require '[nubank-parser.data-source.reader :as reader])

(defn -main
  "Reads a list of csv files and prints the corresponding maps"
  [& args]
  (doseq [entry (parser/parse (reader/read-entries))]
    (println entry)
  )
)
