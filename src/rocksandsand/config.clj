(ns rocksandsand.config
  "Loads and parses the config file"
  (:import java.net.URL)
  (:require [clj-json.core :as json]))

(defn str-keys->keyword-keys [smap]
  "Converts a nested map to have only keyword keys.
   In the case of conflicts will overwrite existing
   keyword keys with string key value"
  (reduce
    (fn [m [k v]]
      (assoc m
             (keyword k)
             (cond (map? v) (str-keys->keyword-keys v)
                   :else    v)))
    {}
    smap))

(defn load-config [filename]
  "Loads a JSON config file from a given resource filename
   parses it into a nested map"
  (-> filename
      clojure.java.io/resource
      .openStream
      slurp
      json/parse-string
      str-keys->keyword-keys))

(def opts (load-config "config.json"))

(defmacro opt [& path-keys]
  "Take a seq of symbols to get the path to an option"
  `(-> opts ~@path-keys))

(defmacro let-opts
  [bindings & body]
  "Executes body with bindings destructured from the opts map
  Ex: (let-opts {foo :foo} (println foo))"
  `(let [~bindings opts]
     ~@body))
