(ns nubank-parser.data-source.parser)
(require '[clj-time.coerce :as c])

(defn- split-on-commas [str]
  "Splits a string on the commas"
  (clojure.string/split str #","))

(defn- parse-csv-line [header line]
  "Mounts a map based on the positions of the header keywords and line values"
  (zipmap header (split-on-commas line)))

(defn- convert-data-types [entry]
  "Converts the :amount field to number and the :date field to a clj-time object"
  (update (update (update entry :amount read-string) :date c/from-string) :category #(if (empty? %) nil %)))

(defn- get-header-as-keywords [header]
  "Converts the header string into a list of keywords"
  (map keyword (split-on-commas header)))

(defn parse [lines]
  "Parses a data source, returning a list of structured maps"
  (let [header (get-header-as-keywords (first lines))]
    (map (comp convert-data-types (partial parse-csv-line header)) (drop 1 lines))))
 
