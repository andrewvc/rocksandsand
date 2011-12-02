(ns rocksandsand.views.index
  (:require [rocksandsand.views.common :as common]
            [rocksandsand.helpers :as helpers]
            [rocksandsand.models.garden :as garden])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))

(defpage "/" []
  (common/layout
    [:p "A garden of rocks and sand"]
    [:div {:id "create-new"}
      (link-to "/create" "☗ Create Your Own")]
    [:h2 "&#9673; Recent Gardens"]
    [:ul {:id "recent"}
      (map (fn [uuid] [:li (link-to (str "/gardens/" uuid)  uuid)])
           (garden/recent))]
    [:div {:id "about"}
      [:h2 "◪ About"]
      [:p
       " &lt;&gt; "
        "Created By: "
        (link-to "http://www.andrewvc.com" "Andrew Cholakian")]
      [:p
        " &lt;&gt; Source: "
        (link-to "https://github.com/andrewvc/rocksandsand" "github/rocksandsand")]]))
