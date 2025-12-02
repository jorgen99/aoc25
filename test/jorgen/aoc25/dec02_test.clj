(ns jorgen.aoc25.dec02-test
  (:require
    [clojure.test :refer :all]
    [jorgen.util :as util]
    [jorgen.aoc25.dec02 :refer :all]))


(deftest testing-
  (testing "It should find the sum of all IDs that repeats twice"
    (is (= 1227775554 (part1 (util/file->lines "dec02_sample.txt"))))
    (is (= 40214376723 (part1 (util/file->lines "dec02_input.txt")))))


  (testing "It should find the sum of all invalid IDs that repeat"
    (is (= 4174379265 (part2 (util/file->lines "dec02_sample.txt"))))
    (is (= 50793864718 (part2 (util/file->lines "dec02_input.txt"))))))
