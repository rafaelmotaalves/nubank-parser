(ns nubank-parser.core
  (:gen-class))

(require '[nubank-parser.data-source.parser :as parser])
(require '[nubank-parser.data-source.reader :as reader])

(defn get-credit-card-entries []
  (flatten (map parser/parse (reader/read-entries)))
)

(defn -main
  "Reads a list of csv files and prints the corresponding maps"
  [& args]
  (let [credit-card-entries (get-credit-card-entries)]
    credit-card-entries
    )
)
