(ns ultima-verba.room-test
  (:require [midje.sweet :refer [fact =>]]
            [ultima-verba.room :as room]
            [ultima-verba.player :as player]))

(fact "A room can be created"
      (let [new-room (room/create-room "ABC123" 1)]
        (:code new-room) => "ABC123"
        (:status new-room) => :waiting
        (room/player-count new-room) => 0))

(fact "A room needs a valid detective case"
      (room/create-room "ABC123" 99) => nil)

(fact "A player can join a room"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            updated-room (room/add-player new-room first-player)]
        (room/player-count updated-room) => 1
        (room/get-player-names updated-room) => '("Alex")
        (room/player-count new-room) => 0))

(fact "A player cannot join twice"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            updated-room (room/add-player new-room first-player)]
        (room/add-player updated-room first-player) => updated-room))

(fact "Invalid players cannot join"
      (let [new-room (room/create-room "ABC123" 1)]
        (room/add-player new-room nil) => new-room))

(fact "A room cannot have more than five players"
      (let [names ["Alex" "Ana" "Mark" "Sara" "John"]
            players (map player/create-player names)
            full-room (reduce room/add-player
                              (room/create-room "ABC123" 1)
                              players)
            extra-player (player/create-player "Emma")]
        (room/player-count full-room) => 5
        (room/room-full? full-room) => true
        (room/add-player full-room extra-player) => full-room))

(fact "A player can leave a waiting room"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            room-with-player (room/add-player new-room first-player)
            updated-room (room/remove-player room-with-player "Alex")]
        (room/player-count updated-room) => 0))

(fact "A game needs at least two players"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            second-player (player/create-player "Ana")
            room-with-one (room/add-player new-room first-player)
            room-with-two (room/add-player room-with-one second-player)]
        (room/can-start? room-with-one) => false
        (room/can-start? room-with-two) => true))

(fact "A game can be started"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            second-player (player/create-player "Ana")
            room-with-one (room/add-player new-room first-player)
            ready-room (room/add-player room-with-one second-player)]
        (:status (room/start-game ready-room)) => :playing
        (:status (room/start-game room-with-one)) => :waiting))

(fact "A new player cannot join after the game starts"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            second-player (player/create-player "Ana")
            third-player (player/create-player "Mark")
            room-with-one (room/add-player new-room first-player)
            ready-room (room/add-player room-with-one second-player)
            started-room (room/start-game ready-room)]
        (room/add-player started-room third-player) => started-room))

(fact "A player can add a statement during the game"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            second-player (player/create-player "Ana")
            room-with-one (room/add-player new-room first-player)
            ready-room (room/add-player room-with-one second-player)
            started-room (room/start-game ready-room)
            updated-room (room/add-statement
                           started-room "Alex" "I saw the suspect.")]
        (count (:statements updated-room)) => 1
        (first (room/get-player-statements updated-room "Alex"))
        => {:player "Alex" :text "I saw the suspect."}))

(fact "Statements cannot be added before the game starts"
      (let [new-room (room/create-room "ABC123" 1)]
        (room/add-statement new-room "Alex" "Hello") => new-room))

(fact "Unknown players cannot add statements"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            second-player (player/create-player "Ana")
            room-with-one (room/add-player new-room first-player)
            ready-room (room/add-player room-with-one second-player)
            started-room (room/start-game ready-room)]
        (room/add-statement started-room "Unknown" "Hello") => started-room))

(fact "A game can be finished"
      (let [new-room (room/create-room "ABC123" 1)
            first-player (player/create-player "Alex")
            second-player (player/create-player "Ana")
            room-with-one (room/add-player new-room first-player)
            ready-room (room/add-player room-with-one second-player)
            started-room (room/start-game ready-room)
            finished-room (room/finish-game started-room)]
        (:status finished-room) => :finished))

(fact "Player scores can be added together"
      (let [first-player (player/add-points (player/create-player "Alex") 10)
            second-player (player/add-points (player/create-player "Ana") 5)
            new-room (room/create-room "ABC123" 1)
            room-with-one (room/add-player new-room first-player)
            room-with-two (room/add-player room-with-one second-player)]
        (room/get-total-score room-with-two) => 15
        (:total-score (room/get-room-info room-with-two)) => 15))
