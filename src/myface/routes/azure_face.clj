(ns myface.routes.azure-face
  (:require [org.httpkit.client :as c]
            [myface.http :as http]
            [myface.properties :as p]
            [cheshire.core :refer :all]
            [thumbnailz.core :as thumb]
            [image-resizer.core :refer :all]
            [image-resizer.format :as format])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream File FileOutputStream BufferedOutputStream]
           [java.awt.image BufferedImage]
           [javax.imageio ImageIO]
           [java.util Base64]
           [org.apache.commons.io IOUtils]
           [javax.xml.bind DatatypeConverter]
           [net.coobird.thumbnailator Thumbnails]))

(def azure-face-key (p/property :azure :api))
(def azure-face-url (p/property :azure :url))

(def face-db (atom {}))

(defn detect-faces-request [binary? body]
  (c/post (str azure-face-url "/detect?returnFaceLandmarks=false&returnFaceAttributes=age,gender,smile,facialHair,emotion,hair,makeup,accessories,blur,exposure,noise")
           {:headers {"Content-Type" (if binary? "application/octet-stream" "application/json")
                      "Ocp-Apim-Subscription-Key" azure-face-key}
            :body (if binary?
                    body
                    (generate-string body))}))

(defn detect-faces [{:keys [body query-string] {:strs [content-type]} :headers {:keys [s-id]} :route-params :as req}]
  (let [binary? (= content-type "application/octet-stream")
        b-arr (IOUtils/toByteArray body)
        face-response (detect-faces-request binary? (ByteArrayInputStream. b-arr))
        k (keyword s-id)
        m (first (parse-string (:body @face-response) true))]
    (if binary?
      (let [ext (last (clojure.string/split query-string #"="))
            baos (ByteArrayOutputStream.)
            buf-img (resize-to-width (clojure.java.io/file (str "./image." ext)) 200)
            write (ImageIO/write buf-img ext baos)
            b64 (DatatypeConverter/printBase64Binary (.toByteArray baos))
            m (assoc m :b64 b64 :ext ext)]
        (when s-id (swap! face-db assoc k (if-not (k @face-db)
                                        [m]
                                        (conj (k @face-db) m))))
        (when s-id (swap! face-db assoc k (if-not (k @face-db)
                                        [m]
                                        (conj (k @face-db) m))))))
    (http/ok (:body @face-response))))

(defn get-faces [{{:keys [s-id]} :route-params}]
  (http/ok (into [] ((keyword s-id) @face-db))))

(defn img->thumbnail [img])

(defprotocol Analyzer
  (analyze [x1 x2]))

(extend-protocol Analyzer
  String
  (analyze [x1 x2]
    (if (coll? x1)
      (conj x1 x2)
      (into [] [x1 x2])))
  clojure.lang.PersistentVector
  (analyze [x1 x2]
    (if (map? (first x1))
      (mapv (partial merge-with analyze) x1 x2)
      (conj x1 x2)))
  java.lang.Double
  (analyze [x1 x2]
    (- x2 x1))
  clojure.lang.PersistentArrayMap
  (analyze [x1 x2]
    (merge-with analyze x1 x2))
  clojure.lang.PersistentHashMap
  (analyze [x1 x2]
    (merge-with analyze x1 x2))
  java.lang.Boolean
  (analyze [x1 x2]
    (if (coll? x1)
      (conj x1 x2)
      (into [] [x1 x2]))))

(defn analyze-face-maps [fm1 fm2]
  (merge-with analyze
              (select-keys fm1 [:faceAttributes])
              (select-keys fm2 [:faceAttributes])))

(defn face-diff-steps [k]
  (let [partitioned-steps (partition 2 1 (k @face-db))]
    (mapv (fn [[x1 x2]] (analyze-face-maps x1 x2)) partitioned-steps)))

(defn face-steps [{{:keys [s-id]} :route-params}]
  (http/ok (face-diff-steps (keyword s-id))))

(defn face-overall-changes [{{:keys [s-id]} :route-params}]
  (let [photos ((keyword s-id) @face-db)]
    (if (> (count photos) 1)
      (http/ok (merge-with analyze
                           (select-keys (first photos) [:faceAttributes])
                           (select-keys (last photos) [:faceAttributes])))
      (http/ok "Not enough data. Need at least 2 photos."))))
