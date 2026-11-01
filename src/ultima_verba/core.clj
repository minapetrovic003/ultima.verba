(ns ultima-verba.core
  (:require [ultima-verba.player :as player]
            [ultima-verba.room :as room]
            [ultima-verba.case :as detective-case])
  (:gen-class))

(defn -main [& args]
  (let [first-player (player/create-player "Alex")
        second-player (player/create-player "Ana")
        new-room (room/create-room "ABC123" 1)
        room-with-players (-> new-room
                              (room/add-player first-player)
                              (room/add-player second-player))
        started-room (room/start-game room-with-players)
        updated-room (room/add-statement started-room
                                         "Alex"
                                         "I saw someone near the window.")
        finished-room (room/finish-game updated-room)]
    (println "Available cases:" (detective-case/get-case-titles))
    (println "Room:" (room/get-room-info finished-room))
    (println "Statements:" (:statements finished-room))))