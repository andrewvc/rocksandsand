(ns rocksandsand.views.create
  (:require [rocksandsand.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))

(defpage "/create" []
  (common/layout
    [:canvas {:id "garden" :width "760" :height "520"}]
    [:div {:id "rock-caddy"}]
    [:a {:href "#" :id "complete"} "&#9632; Complete and Save"]))
