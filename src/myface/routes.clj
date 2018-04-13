(ns myface.routes
  (:require [ring.middleware.json :as json]
            [ring.middleware.file :as file]
            [ring.middleware.content-type :as ct]
            [myface.auth :as auth]
            [myface.utils :as u]
            [myface.properties :as props]
            [myface.index :as index]
            [compojure.core :as r]
            [compojure.route :as route]
            [hiccup.core :as html]
            [myface.routes.azure-face]))

(defn index-route [_]
  {:status 200
   :body index/index
   :headers {"Content-Type" "text/html"}})

(r/defroutes routes
  (r/GET "/" [] index-route)
  (r/POST "/detect-faces" [] myface.routes.azure-face/detect-faces)
  (r/POST "/detect-faces/:s-id" [s-id] myface.routes.azure-face/detect-faces)
  (r/GET "/get-faces/:s-id" [s-id] myface.routes.azure-face/get-faces)
  (route/not-found nil))

(def app
  (-> routes
      (json/wrap-json-response)
      (json/wrap-json-body {:keywords? true})
      ;(auth/wrap-security)
      (file/wrap-file "static" {:index-files? false})
      (ct/wrap-content-type)))
