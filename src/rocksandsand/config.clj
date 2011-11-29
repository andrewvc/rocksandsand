(ns rocksandsand.config
  "Loads and parses the config file"
  (:import java.net.URL)
  (:require [clj-json.core :as json]))

(defn load-json [filename]
  (json/parse-string (slurp (.openStream (clojure.java.io/resource filename)))))

(def raw (load-json "config.json"))

(defn option
  ([k] (option k raw))
  ([k config-data]
    (cond (keyword? k) (get config-data (name k))
          :else        (get config-data k))))
