(ns nubank-parser.data-source.parser-test
  (:require [nubank-parser.data-source.parser :as sut]
            [clojure.test :as t]
            [clj-time.core :as time]))

(def csv-file-lines
  ["date,category,title,amount" "2020-01-01,transporte,teste,20.5" "2020-01-02,outros,test,2.4"])
(t/deftest parse-simple-csv
  (let [result (sut/parse csv-file-lines)]
    (t/is (= 2 (count result)))
    (t/is (= {:month 01 :year 2020 :date (time/date-time 2020 01 01) :category "transporte" :title "teste" :amount 20.5} (first result)))
    (t/is (= {:month 01 :year 2020 :date (time/date-time 2020 01 02) :category "outros" :title "test" :amount 2.4 } (second result)))
    )
  )

(t/deftest parse-csv-with-one-field-without-category
  (let [result (sut/parse ["date,category,title,amount" "2020-01-03,,title,4"])]
    (t/is (= 1 (count result)))
    (t/is (= {:month 01 :year 2020 :date (time/date-time 2020 01 03) :category nil :title "title" :amount 4} (first result)))
    )
  )

(t/deftest parse-csv-with-one-negative-amount
  (let [result (sut/parse ["date,category,title,amount" "2020-01-04,outros,title,-50.5"])]
    (t/is (= 1 (count result)))
    (t/is (= {:month 01 :year 2020 :date (time/date-time 2020 01 04) :category "outros" :title "title" :amount -50.5} (first result)))
    )
 )
