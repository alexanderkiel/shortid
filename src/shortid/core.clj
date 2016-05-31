(ns shortid.core
  (:require [clojure.spec :as s])
  (:import [java.security SecureRandom]))

(set! *warn-on-reflection* true)

(def ^String alphabet
  "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

(s/def ::non-neg-int (s/and integer? #(not (neg? %))))

(declare int-to-base62 base62-to-int)

(s/fdef int-to-base62
  :args (s/cat :n ::non-neg-int :accum (s/? string?))
  :ret string?
  :fn #(or (some? (-> % :args :accum))
           (= (-> % :args :n) (-> % :ret base62-to-int))))

(defn int-to-base62
  "Converts an non-negative integer to a base62 string."
  ([n] (int-to-base62 (quot n 62) (str (nth alphabet (rem n 62)))))
  ([n accum]
   (if (zero? n)
     accum
     (recur (quot n 62) (str (nth alphabet (rem n 62)) accum)))))

(defn base62-to-int
  "Converts a base62 string to an non-negative integer."
  ([s]
   (first
     (reduce
       (fn [[n factor] c]
         [(+ n (* factor (.indexOf alphabet (str c))))
          (unchecked-multiply 62 (long factor))])
       [0 1]
       (reverse s)))))

(def ^SecureRandom r (SecureRandom.))

(defn- int-between [start end]
  (s/with-gen (s/and integer? #(<= start % end))
              #(s/gen (set (range start (inc end))))))

(s/fdef generate
  :args (s/cat :n (int-between 1 5))
  :ret string?
  :fn #(<= (-> % :ret count) (-> % :args :n)))

(defn- max-int "The maximum integer encodable with n chars." [n]
  (apply + (take n (iterate #(* 62 %) 61))))

(defn generate
  "Generates an up to n character long base62 secure random string."
  [n]
  (int-to-base62 (.nextInt r (inc (max-int n)))))
