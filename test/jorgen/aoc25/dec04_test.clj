(ns jorgen.aoc25.dec04-test
  (:require
    [clojure.test :refer :all]
    [jorgen.util :as util]
    [jorgen.aoc25.dec04 :refer :all]))


(deftest testing-
  (testing "It should count accessible rolls"
    (is (= 13 (part1 (util/file->lines "dec04_sample.txt"))))
    (is (= 1457 (part1 (util/file->lines "dec04_input.txt")))))


  (testing "It should remove rolls"
    (is (= 43 (part2 (util/file->lines "dec04_sample.txt"))))
    (is (= 8310 (part2 (util/file->lines "dec04_input.txt"))))))
