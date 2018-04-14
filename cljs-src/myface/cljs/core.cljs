(ns myface.cljs.core
  (:require [reagent.core :as r]
            [goog.dom :as dom]
            [myface.cljs.model.global :as global]
            [myface.cljs.view.upload-file :as upload-file]
            [myface.cljs.view.upload-url :as upload-url]
            [myface.cljs.view.analyze-one :as analyze-one]
            [myface.cljs.components.button :as button]
            [myface.cljs.model.global :as global]))

(enable-console-print!)

(defn home []
  [:div.col-8.mx-auto
   [:h1.black.caps "My Face"]
   [:h3.black.pt1.upper "Finding sadness in the biggest of smiles"]
   [:div.py2
    (if @global/error
      [:h4.error @global/error])]
   [:div.flex.justify-around.items-center
    [:div.bg-white.border.border-gray
     [upload-url/body]]
    [:h3.black "- OR -"]
    [:div.bg-white.border.border-gray
     [upload-file/body]]]])

(defn analyze-one []
  [analyze-one/body])

(defn analyze-multiple []
  [:div])

(defn body []
  [:div#body.bg-pri.flex.justify-center.center
   [:div.col-8.my4.py2.bg-sec.border.border-black.rounded
    {:style {:border-width ".3rem"}}
    [:div.col-12.pr2.
     [:div.right.col-2
      (if (= :home @global/state)
        [button/button :pri "Statistics" #(reset! global/state :analyze-one)]
        [button/button :pri "Upload" #(reset! global/state :home)])]]
    [:div.pt3
     (case @global/state
       :home [home]
       :analyze-one [analyze-one]
       :analyze-multiple [analyze-multiple])]]])

(defn -main []
  (r/render-component [body]
                      (dom/getElement "app")))

(-main)
