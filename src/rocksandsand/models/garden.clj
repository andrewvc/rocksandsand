(ns rocksandsand.models.garden
  (:import java.util.UUID
           java.io.File
           (org.jets3t.service.security AWSCredentials)
           (org.jets3t.service.acl AccessControlList)
           (org.jets3t.service.impl.rest.httpclient RestS3Service)
           (org.jets3t.service.model S3Object))
  (:require [rocksandsand.config :as config]
            [noir.util.s3 :as s3]))

(def recent (ref []))

(def s3-service
  (let [{:keys [access-key secret-key]} config/s3-credentials]
    (new RestS3Service (new AWSCredentials access-key secret-key))))

(defn put!
  "Put the given file on S3 where bucket is the string name of the S3 bucket to use."
  [bucket file]
  (let [obj (new S3Object file)]
    (. obj setAcl (. AccessControlList REST_CANNED_PUBLIC_READ))
    (. s3-service putObject bucket obj)))

(defn create [uuid file]
  (dosync
    (alter recent conj uuid)
    (ref-set recent (take-last 20 @recent)))
  (println @recent)
  (put! config/s3-bucket file))