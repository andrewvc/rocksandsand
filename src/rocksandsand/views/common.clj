(ns rocksandsand.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5 include-js javascript-tag link-to]]))

(defpartial layout [& content]
  (html5
    [:html {:class "no-js" :lang "en"}
    [:head [:meta {:charset "utf-8"}]
     [:title "Rocks &#9648; Sand"]
     [:meta {:name "description", :content "A garden of rocks and sand"}]
     [:meta {:name "author", :content "Andrew Cholakian"}]
     (include-css
       "http://fonts.googleapis.com/css?family=Inconsolata"
       "/css/style.css"
       "/css/main.css")
     (include-js
       "js/libs/modernizr-2.0.min.js"
       "js/libs/respond.min.js"
       "js/libs/script.js")
     (javascript-tag "try{Typekit.load();}catch(e){};")]
    [:body [:div {:id "container"}
             [:header
               [:h1
                 (link-to "/" "Rocks &#9648; Sand")]]
             [:div {:id "main" :role "main"}
               content]
             [:footer
               (link-to "/about" "About Rocks and Sand")
               "&lt;&gt;"
               (link-to "https://github.com/andrewvc/rocksandsand" "Source Code")]
           ]
     (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js")
     (javascript-tag "window.jQuery || document.write('<script src=\"/js/libs/jquery-1.6.2.min.js\"><\\/script>');")
     (javascript-tag "
       $script(['/js/libs/jquery-ui.min.js','/js/libs/underscore.min.js'], function () {
         $script(['/js/main.js']);
       });
     ")
     ]]))
