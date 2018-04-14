(ns myface.cljs.model.global
  (:require [reagent.core :as r]))

(def state (r/atom :analyze-one))

(def files (r/atom []))

(def error (r/atom nil))

