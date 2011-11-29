(defproject rocksandsand "0.1.0-SNAPSHOT"
            :description "A garden made of rocks and sand"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [org.clojure/clojure-contrib "1.2.0"]
                           [org.clojure/data.codec "0.1.0"]
                           [org.clojars.tavisrudd/redis-clojure "1.3.0"]
                           [net.java.dev.jets3t/jets3t "0.8.0"]
                           [noir "1.2.2-SNAPSHOT"]]
            :main rocksandsand.server)

