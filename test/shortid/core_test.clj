(ns shortid.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [shortid.core :refer :all]))

(defspec base62-prop-pos-int 100000
  (prop/for-all [n gen/pos-int]
    (= n (base62-to-int (int-to-base62 n)))))

(deftest base62-long-max-value
  (= Long/MAX_VALUE (base62-to-int (int-to-base62 Long/MAX_VALUE))))

(defspec generate-prop 1000
  (prop/for-all [n (gen/choose 1 5)]
    (<= (count (generate n)) n)))
