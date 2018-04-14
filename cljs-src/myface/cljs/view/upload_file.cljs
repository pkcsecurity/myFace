(ns myface.cljs.view.upload-file
  (:require [reagent.core :as r]
            [myface.cljs.utils :as utils]
            [myface.cljs.model.upload :as upload]))

(defn body []
  (let [hover? (r/atom false)
        drag-count (r/atom 0)]
    (fn []
      [:label
       [:input
        {:type "file"
         :multiple "multiple"
         :on-change (fn [event]
                      (when-not (utils/blank? (-> event .-target .-value))
                        (let [files (utils/filelist->clj (.-files (.-target event)))]
                          (doseq [file files]
                            (upload/post-image
                              {:file file
                               :ext (nth
                                      (clojure.string/split
                                        (first
                                          (filter
                                            not-empty
                                            [(.-type file) "application/octet-stream"]))
                                        #"/") 1)})))))
         :style {:display "none"}}]
       [:div.flex.justify-center.items-center.pointer
        {:on-mouse-over (utils/on hover?)
         :on-mouse-leave (utils/off hover?)
         :on-drag-leave (fn [e]
                          (reset! drag-count (dec @drag-count))
                          (.stopPropagation e)
                          (.preventDefault e))
         :on-drag-exit (fn [e]
                         (.stopPropagation e)
                         (.preventDefault e))
         :on-drag-over (fn [e]
                         (.stopPropagation e)
                         (.preventDefault e))
         :on-drag-enter (fn [e]
                          (reset! drag-count (inc @drag-count))
                          (.stopPropagation e)
                          (.preventDefault e))
         :onDrop (fn [e]
                   (reset! drag-count 0)
                   (.stopPropagation e)
                   (.preventDefault e)
                   (js/console.log "Trying to upload files")
                   (let [files (js->clj (.-files (.-dataTransfer e)))]
                     (doseq [file files]
                       (upload/post-image
                         {:file file
                          :ext (nth
                                 (clojure.string/split
                                   (first
                                     (filter
                                       not-empty
                                       [(.-type file) "application/octet-stream"]))
                                   #"/") 1)}))))
         :class (cond
                  (not= 0 @drag-count) :bg-pri-light
                  @hover? :bg-gray
                  :else :bg-white)
         :style {:height "250px"
                 :width "250px"}}
        [:div.flex.justify-center.items-center.flex-column
         [:div.flex
          {:style {:font-size "3rem"}}
          [:i.fas.pri {:class :fa-cloud-upload-alt}]]
         [:h6.upper.my1 (if (not= 0 @drag-count) "Drop anywhere to upload!" "Upload Image")]
         [:p.light (when (= 0 @drag-count) "(Drag-n-Drop or Click to upload)")]]]])))