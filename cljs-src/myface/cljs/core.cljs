(ns myface.cljs.core
  (:require [reagent.core :as r]
            [goog.dom :as dom]
            [myface.cljs.utils :as utils]))

(enable-console-print!)

(defn upload-body []
  (let [hover? (r/atom false)
        drag-count (r/atom 0)]
    (fn []
      [:label
       [:input
        {:type "file"
         :multiple "multiple"
         :on-change (fn [event]
                      (when-not (utils/blank? (-> event .-target .-value))
                        (js/console.log "Trying to upload!!!!")
                        #_(upload/upload-js-files (.-files (.-target event)) on-change)))
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
                   #_(upload/upload-js-files (.-files (.-dataTransfer e)) on-change))
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

(defn button [color-class text on-click]
  (let [hover? (r/atom false)
        active? (r/atom false)
        color (name color-class)]
    (fn [color-class text on-click]
      [:button.col-12.upper.relative.flex.justify-between.items-center.transition.white.outline-none
       {:on-mouse-enter (utils/on hover?)
        :on-mouse-leave (fn[_]
                          (reset! hover? false)
                          (reset! active? false))
        :on-mouse-down (utils/on active?)
        :on-mouse-up (utils/off active?)
        :on-focus (utils/on hover?)
        :on-blur (utils/off hover?)
        :on-click (fn [e]
                    (.stopPropagation e)
                    (.preventDefault e)
                    (on-click e))
        :class (cond
                 @active? (str "pointer border-none bg-" color "-light " color)
                 @hover? (str "pointer border-none bg-" color "-dark s2")
                 :else (str "pointer border-none bg-" color " s1"))
        :style {:height "2rem" :line-height "2rem" :border-radius ".25rem"}}
       [:div.flex-auto
        [:h6.col-12 text]]])))

(defn upload-uri []
  (let [focus? (r/atom false)
        data-atom (r/atom "")]
    (fn []
      [:div.pt2
       {:style {:height "250px"
                :width "250px"}}
       [:h5.black "Upload with a URL"]
       [:div.flex.justify-center.items-center.flex-column.pt2
        [:div
         [:input.col-12.p1.border.rounded
          {:value @data-atom
           :type :text
           :on-focus (utils/on focus?)
           :on-blur (utils/off focus?)
           :class (str "border pr3 " (if @focus? "border-pri" "border-gray-dark"))
           :on-change #(reset! data-atom (.. % -target -value))
           :placeholder "http://example-url/my-image.png"}]]
        [:div.col-6.pt2
         [button :pri "Upload" #(js/console.log "HE CLICKED ME")]]]])))

(defn body []
  [:div#body.bg-pri.flex.justify-center.center
   [:div.col-8.my4.py4.bg-sec.border.border-black.rounded
    {:style {:border-width ".3rem"}}
    [:h1.black "My Face"]
    [:h3.black "We can find sadness in the biggest of smiles!"]
    [:div.flex.justify-around.pt4.items-center
     [:div.bg-white
      [upload-uri]]
     [:h3 "- OR -"]
     [:div.bg-white
      [upload-body]]]]])

(defn -main []
  (r/render-component [body]
                      (dom/getElement "app")))

(-main)
