(ns rocksandsand.views.common
  (require [rocksandsand.config :as config])
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5 include-js javascript-tag link-to]]))

(defpartial layout [& content]
  (html5
    [:html {:class "no-js" :lang "en"}
    [:head [:meta {:charset "utf-8"}]
     [:title "Rocks &#9648; Sand"]
     [:meta {:name "description", :content "A garden of rocks and sand"}]
     [:meta {:name "author", :content "Andrew Cholakian"}]

     [:link {:rel "icon" :type "image/png" :href "/favicon.png"}]

     (include-css
       "http://fonts.googleapis.com/css?family=Inconsolata"
       "/css/style.css"
       "/css/main.css")
     (include-js
       "/js/libs/modernizr-2.0.min.js"
       "/js/libs/respond.min.js"
       "/js/libs/script.js")
     (javascript-tag "try{Typekit.load();}catch(e){};")]
    [:body [:div {:id "container"}
             [:header
               [:h1
                 (link-to "/" "Rocks &#9648; Sand")]]
             [:div {:id "main" :role "main"}
               content]

           ]
     (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js")
     (javascript-tag "window.jQuery || document.write('<script src=\"/js/libs/jquery-1.6.2.min.js\"><\\/script>');")
     (javascript-tag "
       $script(['/js/libs/jquery-ui.min.js','/js/libs/underscore.min.js'], function () {
         $script(['/js/main.js']);
       });
     ")
     (javascript-tag (str "
       var _gaq = _gaq || [];
       _gaq.push(['_setAccount', '" (config/opt :analytics-code) "']);
       _gaq.push(['_trackPageview']);

       (function() {
         var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
         ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
         var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
       })();
     "))
     ]]))
