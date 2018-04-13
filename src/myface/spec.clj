(ns myface.spec
  (:require [clojure.spec.alpha :as s]
            [myface.utils :as utils]))

(defn explain [spec obj]
  (when-let [probs (s/explain-data spec obj)]
    (with-out-str
      (s/explain-out probs))))

(defmacro string-max-n [n]
  `(s/and string? utils/not-blank? #(< (count %) ~n)))

(defmacro string-of-length [n]
  `(s/and string? utils/not-blank? #(= (count %) ~n)))

(def string (string-max-n 256))
(def long-string (string-max-n 1024))

(def email (s/and string utils/valid-email?))

(def strong-password (s/and string #(>= (count %) 8)))

(def numeric
  (s/and
    string?
    utils/not-blank?
    (partial re-matches #"[0-9]+")))

(def uuid
  (s/and
    (string-of-length 36)
    #(try
       #_(utils/uuid %)
       (catch Exception _))))



