(ns rocksandsand.server
  (:use noir.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers)
  (:require [noir.server :as server]
            [noir.cookies :as cookie]
            [noir.validation :as vali]
            [noir.statuses :as statuses]
            [noir.response :as resp]
            [noir.session :as session]))

(server/load-views "src/rocksandsand/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "3000"))]
    (server/start port {:mode mode
                        :ns 'rocksandsand})))