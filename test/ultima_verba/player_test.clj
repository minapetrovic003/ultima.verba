(ns ultima-verba.player-test
  (:require [clojure.test :refer :all]
            [ultima-verba.player :refer :all]))

(deftest test-create-player
  (is (= {:name "Ana" :score 0}
         (create-player "Ana")))
  (is (nil? (create-player "")))
  (is (nil? (create-player nil))))

(deftest test-add-points
  (let [player (create-player "Ana")]
    (is (= 10 (get-score (add-points player 10))))
    (is (= 0 (get-score player)))))

(deftest test-remove-points
  (let [player {:name "Ana" :score 20}]
    (is (= 15 (get-score (remove-points player 5))))))

(deftest test-reset-score
  (let [player {:name "Ana" :score 30}]
    (is (= 0 (get-score (reset-score player))))))
