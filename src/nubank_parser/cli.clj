(ns nubank-parser.cli
  (:gen-class)
  (:require
   [clojure.tools.cli :refer [parse-opts]]
   [clojure.string :refer [join]]
   [nubank-parser.core :as core]))

(def cli-options [["-w" "--where CONDITION" "A Clojure function that filters the result"
                   :parse-fn #(load-string (str "#" %))
                   :default (constantly true)
                   :default-desc ""]
                  ["-a" "--aggregate METHOD" "A aggregate method"
                   :parse-fn keyword
                   :validate [(fn [x] (some #(= x %) [:sum :count :mean])) "Must be one of the valid aggregation methods"]]
                  ["-g" "--group-by COLUMN" "Group by keys"
                   :parse-fn keyword
                   :validate [(fn [x] (some #(= x %) [:category :title :amount :year :date])) "Must be one of the valid columns"]]
                  ["-l" "--limit NUMBER" "Limit of rows to return"
                    :parse-fn #(Integer/parseInt %)
                    :validate [#(> % 0) "Limit value must bigger than 0"]
                    :default 0]
                  ["-o" "--order-by COLUMN" "Sort by a column"
                    :parse-fn keyword
                    :validate [(fn [x] (some #(= x %) [:category :title :amount :year :date])) "Must be one of the valid columns"]
                    :default :date]
                  ["-h" "--help"]])

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
       (join \newline)))

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
          (core/execute directory-path options))
        :else (println (usage summary))))))
