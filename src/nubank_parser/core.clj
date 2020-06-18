(ns nubank-parser.core
  (:gen-class)
  (:require
   [nubank-parser.data-source.parser :as data-source.parser]
   [nubank-parser.data-source.reader :as data-source.reader]
   [nubank-parser.transformations.aggregate :as transformations.aggregate]
   [nubank-parser.transformations.group-by :as transformations.group-by]
   [nubank-parser.presenters.simple-table :as simple-table]))

(defn get-credit-card-entries 
  [directory-path]
  (flatten (map data-source.parser/parse (data-source.reader/read-entries directory-path))))

(defn execute
  "Reads a list of csv files and prints the corresponding maps"
  [directory-path options]
  (let [{:keys [group-by aggregate where limit order-by]} options]
    (let [credit-card-entries (get-credit-card-entries directory-path)]
      (-> credit-card-entries
          (#(filter where %))
          (#(cond
              (and aggregate group-by) (transformations.group-by/group-by-aggregate % aggregate group-by)
              aggregate [(assoc {} aggregate (transformations.aggregate/aggregate aggregate %))]
              :else %))
          (#(if (> limit 0) (take limit %) %))
          (#(sort-by order-by %))
          (#(simple-table/print-table %))))))