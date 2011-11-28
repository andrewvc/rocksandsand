(ns rocksandsand.views.index
  (:require [rocksandsand.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))

(defpage "/" []
         (common/layout
          [:p "A garden of rocks and sand"]
          [:div {:id "create-new"}
            (link-to "/garden" "☗ Create Your Own")]))
