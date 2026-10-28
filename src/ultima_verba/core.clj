(ns ultima-verba.core
  (:require [ultima-verba.player :as player])
  (:gen-class))


;;this func is naw in player namespace
#_(defn valid-name? [name]
  (not (empty? name)))

;;this func is naw in player namespace
#_(defn create-player [name]
  (if (valid-name? name)
    {:name name :score 0}
    nil))


(defn -main [& args]
  (def create-player (player/create-player "Alex"))
  (println create-player))
