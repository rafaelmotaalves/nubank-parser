(ns nubank-parser.data-source.reader)
(require '[clojure.java.io :as io])

(def file-format ".csv")
(defn get-resources-directory [directory-path] (io/file directory-path))

(defn- get-data-files [directory-path]
  (filter #(clojure.string/ends-with? % file-format) (file-seq (get-resources-directory directory-path))))

(defn- read-file [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce conj [] (line-seq rdr))))

(defn read-entries [directory-path]
  (map read-file (get-data-files directory-path)))

