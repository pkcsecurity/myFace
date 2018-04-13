(ns myface.auth
  (:require [buddy.auth :as auth]
            [buddy.auth.accessrules :as authz]
            [buddy.auth.backends.token :as token]
            [buddy.auth.middleware :as mw]
            [caesium.crypto.generichash :as crypto]
            [myface.utils :as u]))

(def allow-all (constantly true))
(def deny-all (constantly false))

(def rules
  [{:uris ["/"]
    :handler allow-all}
   {:pattern #"^/static/.*$"
    :handler allow-all}
   {:pattern #"^/.*$"
    :handler deny-all}])

(defn wrap-security [app]
  (-> app
    (authz/wrap-access-rules {:rules rules})))

