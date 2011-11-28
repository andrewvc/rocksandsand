(ns rocksandsand.views.upload
  (:require [rocksandsand.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))

(defpage "/upload" [& body]
        (println "BODY!")
        (println body)
         "OK")
