(ns rocksandsand.helpers
  (:require [rocksandsand.config :as config]))

(defn url-for-garden [uuid]
  (format "http://%s.s3.amazonaws.com/%s.png" (config/opts :s3-bucket) uuid))
