(ns rocksandsand.views.about
  (:require [rocksandsand.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))

(defpage "/about" []
  (common/layout
    [:h2 "About"]
    [:p
     " &lt;&gt; "
      "Created By: "
      (link-to "http://www.andrewvc.com" "Andrew Cholakian")]
    [:p
      " &lt;&gt; Source: "
      (link-to "https://github.com/andrewvc/rocksandsand" "github/rocksandsand")]))
