(ns rocksandsand.views.upload
  (:import java.io.File
           java.io.FileOutputStream)
  (:require [rocksandsand.views.common :as common]
            [rocksandsand.models.garden :as garden]
            [rocksandsand.config :as config]
            [clojure.data.codec.base64 :as base64])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))

(defn process-upload [file]
  "Decodes a base64 encoded uploaded file from an uploaded canvas"
    (let [b64 (slurp (:tempfile file))
          decoded  (base64/decode (.getBytes b64 "UTF-8"))
          uuid     (str (java.util.UUID/randomUUID))
          out-path (str "tmp/" uuid ".png")
          out-file (File. out-path)]
            (with-open [out-stream (FileOutputStream. out-file)]
              (.write out-stream decoded))
      {:decoded-file out-file :uuid uuid}))

(defpage [:post "/upload"] {:keys [garden-file]}
  (let [{:keys [decoded-file uuid]} (process-upload garden-file)]
    (try
      (garden/create uuid decoded-file)
      (finally
        (.delete decoded-file)))
    uuid))
