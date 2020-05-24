(ns nubank-parser.cli
  (:gen-class)
  (:require
    [clojure.tools.cli :refer [parse-opts]]
    [clojure.string :refer [join]]
    [nubank-parser.core :as core]
    ))

(def cli-options [
  ["-g" "--group-by FIELD" "Group by keys"
   :parse-fn keyword
   :validate [(fn [x] (some #(= x %) [:category :title :amount])) "Must be one of the valid columns"]
   ]
  ["-h" "--help"]
  ])

(defn usage [options-summary]
  (->> ["This is the nubank parser. It helps you to extract data from your nubank credit card bill csvs"
        ""
        "Usage: nubank-parser [options] (directory-path)"
        ""
        "Options:"
        options-summary
        ""
        ""
        "Please refer to the manual page for more information."]
       (join \newline))       
)

(defn -main
  "Reads a list of csv files and prints the corresponding maps"
  [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args  cli-options)]
    (do
      (println options)
      (cond
        (not-empty errors) (println (first errors))
        (:help options) (println (usage summary))
        (not-empty arguments) 
        (let [directory-path (first arguments)]
          (core/execute directory-path options)
          )
        :else (println (usage summary))
        )
      )
  )
)