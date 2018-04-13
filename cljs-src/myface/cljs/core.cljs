(ns myface.cljs.core
  (:require [reagent.core :as r]
            [goog.dom :as dom]))

(enable-console-print!)

(defn body []
  [:div.fixed.top-0.left-0.right-0.bottom-0
   [:h1 "Brevity is the soul of wit"]
   [:h3 "myface"]
   [:p "your children will be placed in the custody of carls jr"]])

(defn -main []
  (r/render-component [body]
                      (dom/getElement "app")))

(-main)
