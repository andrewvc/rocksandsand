(ns rocksandsand.views.garden
  (:require [rocksandsand.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/garden" []
         (common/layout
           [:canvas {:id "garden" :width "760" :height "520"}]
           [:div {:id "rock-caddy"}]))
