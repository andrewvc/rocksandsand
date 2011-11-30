(ns rocksandsand.models.garden
  (:import java.util.UUID
           java.io.File)
  (:require [rocksandsand.config :as conf]
            [rocksandsand.services.s3 :as rs-s3]
            [rocksandsand.services.redis :as rs-redis]
            [redis.core :as redis]))

(def garden-uuids-key "garden-uuids")
(def recent-gardens-key "garden-uuids:recent")

(defn recent []
  "Returns a short list of recent garden uuids"
  (rs-redis/with-server
    (redis/lrange recent-gardens-key  0 -1)))

(defn create [uuid file]
  (let [score (double (/ (System/currentTimeMillis) 1000))]
    (rs-s3/put-public! (conf/opts :s3-bucket) file)
    (rs-redis/with-server
      (redis/atomically
        (redis/zadd garden-uuids-key score uuid)
        (redis/lpush recent-gardens-key uuid)
        (redis/ltrim recent-gardens-key 0 19)))))
