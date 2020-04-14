(ns nubank-parser.data-source.reader)
(require '[clojure.java.io :as io])

(def file-format ".csv")
(def resources-directory (io/file "resources/"))

(defn- get-data-files []
  (filter #(clojure.string/ends-with? % file-format) (file-seq resources-directory)))

(defn- read-file [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce conj [] (line-seq rdr))))

(defn read-entries []
  (flatten (map read-file (get-data-files))))

