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
  (let [data-files (get-data-files directory-path)]
    (if (not-empty data-files)
      (map read-file data-files)
      (println "No csv files found input directory" directory-path)
      )
   )
  )
