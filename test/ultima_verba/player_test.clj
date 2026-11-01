(ns ultima-verba.player-test
  (:require [midje.sweet :refer [fact =>]]
            [ultima-verba.player :as player]))

(fact "A player can be created with a valid name"
      (player/create-player "Ana") =>
      {:name "Ana" :score 0})

(fact "Invalid names are not accepted"
      (player/create-player "") => nil
      (player/create-player nil) => nil
      (player/create-player 123) => nil)

(fact "A player starts with zero points"
      (player/get-score (player/create-player "Alex")) => 0)

(fact "Points can be added to a player"
      (let [new-player (player/create-player "Ana")
            updated-player (player/add-points new-player 10)]
        (player/get-score updated-player) => 10
        (player/get-score new-player) => 0))

(fact "Points can be removed from a player"
      (let [new-player {:name "Ana" :score 20}]
        (player/get-score (player/remove-points new-player 5)) => 15))

(fact "A player's score can be reset"
      (let [new-player {:name "Ana" :score 30}]
        (player/get-score (player/reset-score new-player)) => 0))
