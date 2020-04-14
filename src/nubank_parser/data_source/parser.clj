(ns nubank-parser.data-source.parser)

(defn- split-on-commas [str]
  (clojure.string/split str #","))

(defn- parse-csv-line [header line]
  (zipmap header (split-on-commas line)))

(defn parse [lines]
  (let [header (split-on-commas (first lines))]
    (map (partial parse-csv-line header) (drop 1 lines))))
