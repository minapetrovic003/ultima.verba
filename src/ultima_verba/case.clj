
(ns ultima-verba.case)

(def cases
  [{:id 1
    :title "The Missing Necklace"
    :description "A necklace disappeared during a family dinner."
    :clues ["The window was open"
            "A guest left early"
            "The jewelry box was unlocked"]}

   {:id 2
    :title "The Library Mystery"
    :description "A rare book disappeared from the city library."
    :clues ["The security camera was turned off"
            "A library card was found"
            "The door was not damaged"]}

   {:id 3
    :title "The Last Letter"
    :description "A mysterious letter was found in an abandoned house."
    :clues ["The letter had no signature"
            "A broken clock was found"
            "The front door was locked"]}])

(defn get-all-cases []
  cases)

(defn find-case [case-id]
  (first (filter #(= (:id %) case-id) cases)))

(defn get-case-titles []
  (map :title cases))

(defn get-clues [detective-case]
  (:clues detective-case))

(defn count-clues [detective-case]
  (count (:clues detective-case)))
