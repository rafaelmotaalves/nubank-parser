(ns nubank-parser.transformations.aggregate)

(defn aggregate-sum [rows]
  (reduce + (map :amount rows)))

(defn aggregate-count [rows]
  (count rows))

(defn aggregate-mean [rows]
  (/ (aggregate-sum rows) (aggregate-count rows)))

(defn aggregate [method rows]
  (case method
    :SUM (aggregate-sum rows)
    :COUNT (aggregate-count rows)
    :MEAN (aggregate-mean rows)
    0))
