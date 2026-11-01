(ns ultima-verba.case-test
  (:require [midje.sweet :refer [fact =>]]
            [ultima-verba.case :as detective-case]))

(fact "All detective cases are available"
      (count (detective-case/get-all-cases)) => 3)

(fact "A case can be found by its ID"
      (:title (detective-case/find-case 1)) => "The Missing Necklace"
      (:title (detective-case/find-case 2)) => "The Library Mystery"
      (:title (detective-case/find-case 3)) => "The Last Letter")

(fact "An unknown case cannot be found"
      (detective-case/find-case 99) => nil)

(fact "Case titles can be retrieved"
      (count (detective-case/get-case-titles)) => 3
      (first (detective-case/get-case-titles)) => "The Missing Necklace")

(fact "Clues can be retrieved from a case"
      (let [selected-case (detective-case/find-case 1)]
        (detective-case/count-clues selected-case) => 3
        (first (detective-case/get-clues selected-case))
        => "The window was open"))
