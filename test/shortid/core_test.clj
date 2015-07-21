(ns shortid.core-test
  (:require [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [shortid.core :refer :all]))

(defspec base62-prop 100000
  (prop/for-all [n gen/pos-int]
    (= n (base62-to-int (int-to-base62 n)))))

(defspec generate-prop 1000
  (prop/for-all [n (gen/choose 1 5)]
    (<= (count (generate n)) n)))
