(ns myface.routes.azure-face
  (:require [org.httpkit.client :as c]
            [myface.http :as http]
            [myface.properties :as p]
            [cheshire.core :refer :all]))

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

(defn detect-faces [{:keys [body] {:strs [content-type]} :headers {:keys [s-id]} :route-params :as req}]
  (let [binary? (= content-type "application/octet-stream")
        face-response (detect-faces-request binary? body)
        k (keyword s-id)
        m (first (parse-string (:body @face-response) true))]
    (when s-id (swap! face-db assoc k (if-not (k @face-db)
                                        [m]
                                        (conj (k @face-db) m))))
    (http/ok (:body @face-response))))

(defn get-faces [{{:keys [s-id]} :route-params}]
  (http/ok (into [] ((keyword s-id) @face-db))))
