(ns myface.cljs.view.analyze-one
  (:require [reagent.core :as r]
            [myface.cljs.utils :as utils]
            [myface.cljs.model.global :as global]))

(defn body []
  (let [hover? (r/atom false)
        drag-count (r/atom 0)]
    (fn []
      [:div.mx-auto
       [:h1.black.caps "My Face"]
       [:h3.black.pt1.upper "Finding sadness in the biggest of smiles"]
       [:div.col-10.mx-auto
        [:div.bg-white.border.border-grey.rounded.p2.my2
         [:div.flex.border.rounded
          [:div.absolute.pl2.pt2
           [:h3.black.caps "Trends"]]
          [:div.flex.flex-column.left.pt4.px4.pb2
           [:h5.upper.black "Age:"]
           [:h5.upper.black "Emotions:"]
           [:h5.upper.black "Facial Hair:"]]]
         [:div.flex
          [:div.col-4.m1.border
           [:img {:style {:padding "6em"
                          :background-image "url(/assets/pixlogo.svg)"}}]]
          [:div.col-8.m1.border
           [:div.absolute.pl2.pt2
            [:h4.black.caps "Statistics"]]
           [:div.pt4.px3.flex
            [:div.flex.flex-column
             [:p "one"]
             [:p "two"]
             [:p "three"]]
            [:div.flex.flex-column
             [:p "one"]
             [:p "two"]
             [:p "three"]]
            [:div.flex.flex-column
             [:p "one"]
             [:p "two"]
             [:p "three"]]]]]]]
       (cond
         (>= 0 (count @global/files)) [:h3 "No files have been uploaded"]
         (=  1 (count @global/files)) [:h3 "You have uploaded one file"]
         (<= 1 (count @global/files)) [:h3 "You have uploaded multiple files"])])))


(def messages {:age {:positive #(str "Congratulations! Looks like you have found the fountain of youth and will soon be born on " %)
                     :negative #(str "Uh Oh! Looks like you are ageing and will die on " % " :(")}
               :smile {:positive #(str "Yay! You are getting happier over time :)")
                       :negative #(str "Yikes! Your frown has just gone more down :(")}
               :emotion {}})

      ;
      ;[:label
      ; [:input
      ;  {:type "file"
      ;   :multiple "multiple"
      ;   :on-change (fn [event]
      ;                (when-not (utils/blank? (-> event .-target .-value))
      ;                  (js/console.log "Trying to upload!!!!")
      ;                  (let [files (utils/filelist->clj (.-files (.-target event)))]
      ;                    (doseq [file files]
      ;                      (upload/post-image {:file file})))))
      ;   :style {:display "none"}}]
      ; [:div.flex.justify-center.items-center.pointer
      ;  {:on-mouse-over (utils/on hover?)
      ;   :on-mouse-leave (utils/off hover?)
      ;   :on-drag-leave (fn [e]
      ;                    (reset! drag-count (dec @drag-count))
      ;                    (.stopPropagation e)
      ;                    (.preventDefault e))
      ;   :on-drag-exit (fn [e]
      ;                   (.stopPropagation e)
      ;                   (.preventDefault e))
      ;   :on-drag-over (fn [e]
      ;                   (.stopPropagation e)
      ;                   (.preventDefault e))
      ;   :on-drag-enter (fn [e]
      ;                    (reset! drag-count (inc @drag-count))
      ;                    (.stopPropagation e)
      ;                    (.preventDefault e))
      ;   :onDrop (fn [e]
      ;             (reset! drag-count 0)
      ;             (.stopPropagation e)
      ;             (.preventDefault e)
      ;             (js/console.log "Trying to upload files")
      ;             (let [files (js->clj (.-files (.-dataTransfer e)))]
      ;               (doseq [file files]
      ;                 (upload/post-image {:file file}))))
      ;   :class (cond
      ;            (not= 0 @drag-count) :bg-pri-light
      ;            @hover? :bg-gray
      ;            :else :bg-white)
      ;   :style {:height "250px"
      ;           :width "250px"}}
      ;  [:div.flex.justify-center.items-center.flex-column
      ;   [:div.flex
      ;    {:style {:font-size "3rem"}}
      ;    [:i.fas.pri {:class :fa-cloud-upload-alt}]]
      ;   [:h6.upper.my1 (if (not= 0 @drag-count) "Drop anywhere to upload!" "Upload Image")]
      ;   [:p.light (when (= 0 @drag-count) "(Drag-n-Drop or Click to upload)")]]]])))
