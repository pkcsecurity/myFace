(ns myface.cljs.view.upload-url
  (:require [reagent.core :as r]
            [myface.cljs.components.button :as button]
            [myface.cljs.utils :as utils]
            [myface.cljs.model.upload :as upload]))

(defn body []
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
         [button/button :pri "Upload" #(upload/post-image {:url @data-atom})]]]])))