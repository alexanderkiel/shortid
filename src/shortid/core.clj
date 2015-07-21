(ns shortid.core
  (:import [java.security SecureRandom]))

(set! *warn-on-reflection* true)

(def ^String alphabet
  "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn int-to-base62
  "Converts a positive integer to a base62 string."
  ([n] (int-to-base62 (quot n 62) (str (nth alphabet (rem n 62)))))
  ([n accum]
   (if (zero? n)
     accum
     (recur (quot n 62) (str (nth alphabet (rem n 62)) accum)))))

(defn base62-to-int
  "Converts a base62 string to a positive integer."
  ([s]
   (first
     (reduce
       (fn [[n factor] c]
         [(+ n (* factor (.indexOf alphabet (str c)))) (* 62 factor)])
       [0 1]
       (reverse s)))))

(def ^SecureRandom r (SecureRandom.))

(defn- max-int "The maximum integer encodable with n chars." [n]
  (apply + (take n (iterate #(* 62 %) 61))))

(defn generate
  "Generates an up to n character long base62 secure random string."
  [n]
  {:pre [(< 0 n 6)]}
  (int-to-base62 (.nextInt r (inc (max-int n)))))
