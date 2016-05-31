(ns shortid.core-test
  (:require [clojure.spec :as s]
            [clojure.spec.test]
            [clojure.test :refer :all]
            [clojure.test.check]
            [shortid.core :refer :all]))

(s/instrument-ns 'shortid.core)

(defmethod assert-expr 'conform-var? [msg form]
  ;; Test if the var v conforms to it's spec.
  (let [args (rest form)]
    `(let [result# (clojure.spec.test/check-var ~@args)]
       (if (true? (:result result#))
         (do-report {:type :pass :message ~msg
                     :expected '~form :actual result#})
         (do-report {:type :fail :message ~msg
                     :expected '~form :actual result#}))
       result#)))

(deftest int-to-base62-test
  (testing "special values"
    (are [n] (= n (base62-to-int (int-to-base62 n)))
      0
      Long/MAX_VALUE))
  (testing "spec"
    (is (conform-var? #'shortid.core/int-to-base62 :num-tests 1000))))

(deftest base62-to-int-test
  (testing "special values"
    (are [s] (= s (int-to-base62 (base62-to-int s)))
      "0")))

(deftest generate-test
  (is (conform-var? #'shortid.core/generate :num-tests 1000)))
