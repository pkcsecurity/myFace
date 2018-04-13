(ns myface.core
  (:gen-class)
  (:require [myface.routes :as r]
            [myface.properties :as props]
            [immutant.web :as server]))
(defn -main [& args]
  (if (= :prod (props/property :mode))
    (server/run r/app :host (props/property :prod :host) :port (props/property :prod :port))
    (server/run-dmc r/app :host (props/property :dev :host) :port (props/property :dev :port))))
