(ns jorgen.aoc25.dec03-test
  (:require
    [clojure.test :refer :all]
    [jorgen.util :as util]
    [jorgen.aoc25.dec03 :refer :all]))


(deftest testing-
  (testing "It should use two batteries"
    (is (= 357 (part1 (util/file->lines "dec03_sample.txt"))))
    (is (= 17034 (part1 (util/file->lines "dec03_input.txt")))))


  (testing "It should use twelve batteries"
    (is (= 3121910778619 (part2 (util/file->lines "dec03_sample.txt"))))
    (is (= 168798209663590 (part2 (util/file->lines "dec03_input.txt"))))))
