(ns rocksandsand.services.redis
  (:require [redis.core :as redis]
            [rocksandsand.config :as conf]))

(defmacro with-server [& body]
  "Equivalent to (with-server conf/redis-opts body)"
  `(redis/with-server (conf/opts :redis) ~@body))
