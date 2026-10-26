(ns ultima-verba.core
  (:gen-class))

(defn create-player [name]
  {:name name
   :score 0})

(defn -main [& args]
  (def player (create-player "Alex"))
  (println player))
