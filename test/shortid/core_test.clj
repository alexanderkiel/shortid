(ns shortid.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [shortid.core :refer :all]))

(defspec base62-prop-pos-int 100000
  (prop/for-all [n gen/pos-int]
    (= n (base62-to-int (int-to-base62 n)))))

(deftest int-to-base62-test
  (testing "special values"
    (are [n] (= n (base62-to-int (int-to-base62 n)))
      0
      Long/MAX_VALUE)))

(deftest base62-to-int-test
  (testing "special values"
    (are [s] (= s (int-to-base62 (base62-to-int s)))
      "0")))

(defspec generate-prop 1000
  (prop/for-all [n (gen/choose 1 5)]
    (<= (count (generate n)) n)))
