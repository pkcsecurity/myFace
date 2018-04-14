(ns myface.cljs.components.button
  (:require [reagent.core :as r]
            [myface.cljs.utils :as utils]))


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