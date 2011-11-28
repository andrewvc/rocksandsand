(ns rocksandsand.views.gardens
  (:require [rocksandsand.views.common :as common]
            [rocksandsand.helpers :as helpers])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))

(defpage "/gardens/:uuid" {uuid :uuid}
  (common/layout
    [:img {:src (helpers/url-for-garden uuid) :class "garden"}]))
