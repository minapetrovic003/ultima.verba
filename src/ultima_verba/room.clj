
(ns ultima-verba.room
  (:require [ultima-verba.case :as detective-case]))

(defn create-room [code case-id]
  (let [selected-case (detective-case/find-case case-id)]
    (if selected-case
      {:code code
       :case selected-case
       :players []
       :statements []
       :status :waiting}
      nil)))

(defn player-exists? [room name]
  (boolean (some #(= (:name %) name) (:players room))))

(defn room-full? [room]
  (>= (count (:players room)) 5))

(defn add-player [room player]
  (if (and player
           (= (:status room) :waiting)
           (not (room-full? room))
           (not (player-exists? room (:name player))))
    (update room :players conj player)
    room))

(defn remove-player [room name]
  (if (= (:status room) :waiting)
    (update room :players
            (fn [players]
              (vec (filter #(not= (:name %) name) players))))
    room))

(defn get-player-names [room]
  (map :name (:players room)))

(defn player-count [room]
  (count (:players room)))

(defn can-start? [room]
  (and (= (:status room) :waiting)
       (>= (player-count room) 2)))

(defn start-game [room]
  (if (can-start? room)
    (assoc room :status :playing)
    room))

(defn add-statement [room name text]
  (if (and (= (:status room) :playing)
           (player-exists? room name)
           (string? text)
           (not (empty? text)))
    (update room :statements conj
            {:player name
             :text text})
    room))

(defn get-player-statements [room name]
  (filter #(= (:player %) name) (:statements room)))

(defn finish-game [room]
  (if (= (:status room) :playing)
    (assoc room :status :finished)
    room))

(defn get-total-score [room]
  (reduce + 0 (map :score (:players room))))

(defn get-room-info [room]
  {:code (:code room)
   :case (:title (:case room))
   :players (get-player-names room)
   :player-count (player-count room)
   :statement-count (count (:statements room))
   :total-score (get-total-score room)
   :status (:status room)})
