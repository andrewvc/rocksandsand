(ns rocksandsand.services.s3
  (:import java.util.UUID
    java.io.File
    (org.jets3t.service.security AWSCredentials)
    (org.jets3t.service.acl AccessControlList)
    (org.jets3t.service.impl.rest.httpclient RestS3Service)
    (org.jets3t.service.model S3Object))
  (:require  [rocksandsand.config :as conf]))

(def service
  (conf/let-opts {{:keys [access-key secret-key]} :s3-credentials}
      (new RestS3Service (new AWSCredentials access-key secret-key))))

(defn put-public!
  "Put the given file on S3 where bucket is the string name of the S3 bucket to use."
  [bucket file]
  (let [obj (new S3Object file)]
    (. obj setAcl (. AccessControlList REST_CANNED_PUBLIC_READ))
    (. service putObject bucket obj)))

