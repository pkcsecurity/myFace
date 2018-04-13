(ns myface.cljs.utils
  (:require [clojure.string :as str]
            [goog.text.LoremIpsum]
            [goog.dom :as dom]
            [goog.format.EmailAddress :as addr]))

(defn remove-nils [coll]
  (keep some? coll))

(def path (.-pathname js/location))

(defn set-path [path]
  (set! (.-pathname js/location) path))

(defn id [] (str (gensym)))

(defn id->element [uid]
  (dom/getElement uid))

(defn focus-id [uid]
  (when-let [elem (id->element uid)]
    (.focus elem)))

(defn off [a]
  (fn [] (reset! a false)))

(defn on [a]
  (fn [] (reset! a true)))

(defn toggle [a]
  (fn [] (swap! a not)))

(def not-blank? (complement str/blank?))

(def blank? str/blank?)

(defn nil-as-blank [s]
  (if (nil? s)
    ""
    s))

(defn blank-as-nil [s]
  (if (str/blank? s)
    nil
    s))

(def trim (comp str/trim nil-as-blank))

(def nop (constantly nil))

(defn pp-number [x]
  (.toLocaleString (js/Number. x)))

(defn ipsum-words [n]
  (take n
    (mapcat (comp #(clojure.string/split % #"[ \.\,]+") clojure.string/lower-case)
      (repeatedly #(.generateSentence (goog.text.LoremIpsum.))))))

(defn ipsum [n]
  (clojure.string/join " "
    (repeatedly n
      #(.generateSentence
         (goog.text.LoremIpsum.)))))
(defn truncate [s max-length]
  (if (> (count s) max-length)
    (let [sub (.substring s 0 max-length)
          idx (.lastIndexOf sub " ")]
      (str (.substring s 0 idx) "..."))
    s))

(defn parse-int [s]
  (let [maybe-int (js/parseInt s)]
    (when-not (.isNaN js/Number maybe-int)
      maybe-int)))

(defn email [x] (addr/isValidAddress x))

(defn js-obj->vec [obj]
  (vec
    (map
      (fn [k] {:key k :val (aget obj k)})
      (.keys js/Object obj))))

; removes items in vector a from vector b
(defn subtract-vector [a b]
  (remove #(contains? (apply hash-set a) %) b))

(defn headers->map [s]
  (into (hash-map)
    (mapv #(clojure.string/split % #":\s" 2)
      (clojure.string/split-lines s))))

(defn includes? [string sub ignore-case?]
  (if (or (nil? string) (nil? sub))
    false
    (let [string (if ignore-case? (clojure.string/lower-case string) string)
          sub (if ignore-case? (clojure.string/lower-case sub) sub)]
      (clojure.string/includes? string sub))))

(defn now-date []
  (let [now (js/Date.)
        dd (.getDate now)
        mm (+ (.getMonth now) 1)
        yyyy (.getFullYear now)]
    (str mm "-" dd "-" yyyy)))

(defn filelist->clj [js-col]
  (-> (clj->js [])
    (.-slice)
    (.call js-col)
    (js->clj)))
