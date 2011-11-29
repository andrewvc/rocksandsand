(ns rocksandsand.models.garden
  (:import java.util.UUID
           java.io.File
           (org.jets3t.service.security AWSCredentials)
           (org.jets3t.service.acl AccessControlList)
           (org.jets3t.service.impl.rest.httpclient RestS3Service)
           (org.jets3t.service.model S3Object))
  (:require [rocksandsand.config :as conf]
            [redis.core :as redis]))

(def garden-uuids-key "garden-uuids")
(def recent-gardens-key "garden-uuids:recent")

(def s3-service
  (let [credentials (conf/option :s3-credentials)
        access-key  (get credentials "access-key")
        secret-key  (get credentials "secret-key")]
    (new RestS3Service (new AWSCredentials access-key secret-key))))

(defn put!
  "Put the given file on S3 where bucket is the string name of the S3 bucket to use."
  [bucket file]
  (let [obj (new S3Object file)]
    (. obj setAcl (. AccessControlList REST_CANNED_PUBLIC_READ))
    (. s3-service putObject bucket obj)))

(defn recent []
  "Returns a short list of recent garden uuids"
  (redis/with-server conf/redis-opts
    (redis/lrange recent-gardens-key  0 -1)))

(defn create [uuid file]
  (let [score (double (/ (System/currentTimeMillis) 1000))]
    (redis/with-server conf/redis-opts
      (redis/zadd garden-uuids-key score uuid)
      (redis/lpush recent-gardens-key uuid)
      (redis/ltrim recent-gardens-key 0 19))
  (put! (conf/option :s3-bucket) file)))
