(ns nubank-parser.transformations.aggregate)

(defn aggregate-sum [rows]
  (reduce + (map :amount rows)))

(defn aggregate-count [rows]
  (count rows))

(defn aggregate-mean [rows]
  (/ (aggregate-sum rows) (aggregate-count rows)))

(defn aggregate [method rows]
  (case method
    :sum (aggregate-sum rows)
    :count (aggregate-count rows)
    :mean (aggregate-mean rows)
    0))
