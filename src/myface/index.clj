(ns myface.index
  (:require [myface.css :as css]
            [hiccup.core :as html]
            [myface.properties :as p]))

(def title "IMB Generosity: Give Cheerfully")

(def app-js-path
  (if (= :dev (p/property :mode))
    "/development/index.js"
    "/release/index.js"))

(def scripts
  [app-js-path])

(def index
  (html/html
    {:mode :html}
    [:html
     [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
      [:title title]
      (map css/css css/csses)
      [:style css/styles]]
     [:body
      [:div#app
       "<!-- https://www.youtube.com/watch?v=E-P2qL3qkzk -->"]
      (for [s scripts]
        [:script {:src s}])]]))