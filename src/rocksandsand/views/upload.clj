(ns rocksandsand.views.upload
  (:import java.io.File
           java.io.FileOutputStream)
  (:require [rocksandsand.views.common :as common]
            [noir.util.s3 :as s3]
            [noir.content.getting-started]
            [clojure.data.codec.base64 :as base64])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))

(defpage [:post "/upload"] {:keys [garden]}
        (println "BODY!")
        (let [b64      (slurp (:tempfile garden))
              outf     (File. "outf2.png")
              outfs    (FileOutputStream. outf)
              decoded  (base64/decode (.getBytes b64 "UTF-8"))]
              (spit  "6nt.png" b64)
              (.write outfs decoded)
              (.close outfs)
              (.close outf))
         "OK")
