(ns jorgen.aoc25.dec01
  (:require
    [jorgen.util :as util]))

(defn rotations
  "Parse lines in to [direction amount] pairs"
  [lines]
  (->> lines
       (map (fn [s] [(first s) (apply str (rest s))]))
       (map (fn [[a b]] [(keyword (str a)) (util/parse-int b)]))))


(defn rotate [[dial zeros] [direction amount]]
  (let [actual_ammount (mod amount 100)
        v (case direction
            :R (+ dial actual_ammount)
            :L (- dial actual_ammount))
        new_val (cond
                  (neg? v) (+ 100 v)
                  (>= v 100) (mod v 100)
                  :else v)]
    (if (= 0 new_val)
      [new_val (inc zeros)]
      [new_val zeros])))


(defn rotate2 [[dial zeros] [direction amount]]
  (let [full_turns (int (/ amount 100))
        actual_ammount (mod amount 100)
        v (case direction
            :R (+ dial actual_ammount)
            :L (- dial actual_ammount))
        [new_val passed_zero] (cond
                                (neg? v) [(+ 100 v) (if (zero? dial) 0 1)]
                                (= v 0) [0 1]
                                (>= v 100) [(mod v 100) 1]
                                :else [v 0])
        new_zeros (+ zeros full_turns passed_zero)]
    [new_val new_zeros]))


(defn dialer [lines rotate-fn]
  (let [r (rotations lines)
        start 50]
    (second (reduce rotate-fn [start 0] r))))


(defn part1 [lines]
  (dialer lines rotate))


(defn part2 [lines]
  (dialer lines rotate2))


(comment
  (time (part1 (util/file->lines "dec01_sample.txt")))
  (time (part1 (util/file->lines "dec01_input.txt")))
  (time (part2 (util/file->lines "dec01_sample.txt")))
  (time (part2 (util/file->lines "dec01_input.txt"))))


