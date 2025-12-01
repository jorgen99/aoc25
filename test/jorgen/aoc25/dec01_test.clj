(ns jorgen.aoc25.dec01-test
  (:require
    [clojure.test :refer :all]
    [jorgen.util :as util]
    [jorgen.aoc25.dec01 :refer :all]))


(deftest testing-dec01
  (testing "It should calculate dials at zero"
    (is (= 3 (part1 (util/file->lines "dec01_sample.txt"))))
    (is (= 969 (part1 (util/file->lines "dec01_input.txt")))))


  (testing "It should calculate dial passing zero"
    (is (= 6 (part2 (util/file->lines "dec01_sample.txt"))))
    (is (= 5887 (part2 (util/file->lines "dec01_input.txt"))))))
