(ns myface.cljs.model.upload
  (:require [myface.cljs.xhr :as xhr]
            [myface.cljs.model.global :as global]))

(defn error-fn [message]
  (println message)
  (reset! global/error message))

(defn success-fn [resp]
  (reset! global/error nil)
  (swap! global/files conj resp)
  (println resp)
  (reset! global/state :analyze-one))

(defn post-image [{:keys [url file] :as all}]
  (let [endpoint "/detect-faces/abc123"]
    (if url
      (xhr/ajax "POST" endpoint
        :body {:url url}
        :on-success (fn [resp]
                      (if (get-in resp [:error :message])
                        (error-fn (get-in resp [:error :message]))
                        (success-fn resp)))
        :on-error (fn [error]
                    (println (get-in error [:error :message]))
                    (reset! global/error (get-in error [:error :message]))
                    (js/console.log error)))
      (xhr/ajax "POST" (str endpoint "?ext=" (:ext all))
        :body file
        :headers {"Content-Type" "application/octet-stream"}
        :on-success (fn [resp]
                      (if (get-in resp [:error :message])
                        (error-fn (get-in resp [:error :message]))
                        (success-fn resp)))
        :on-error (fn [error]
                    (println (get-in error [:error :message]))
                    (reset! global/error (get-in error [:error :message]))
                    (js/console.log error))))))