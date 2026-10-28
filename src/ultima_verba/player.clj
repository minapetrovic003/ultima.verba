(ns ultima-verba.player)

(defn valid-name? [name]
  (and (string? name)
       (not (empty? name))))

(defn create-player [name]
  (if (valid-name? name)
    {:name name
     :score 0}
    nil))

(defn add-points [player points]
  (update player :score + points))

(defn remove-points [player points]
  (update player :score - points))

(defn get-score [player]
  (:score player))

(defn reset-score [player]
  (assoc player :score 0))
