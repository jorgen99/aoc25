(ns jorgen.aoc25.dec01
  (:require
    [jorgen.util :as util]))

(defn rotations
  "Parse lines in to +/- amount"
  [lines]
  (->> lines
       (map (fn [s] [(first s) (apply str (rest s))]))
       (map (fn [[a b]] [(keyword (str a)) (parse-long b)]))
       (map (fn [[direction amount]]
              (case direction
                :R amount
                :L (* -1 amount))))))


(defn rotate [[dial zeros] amount]
  (let [actual_ammount (mod amount 100)
        v (+ dial actual_ammount)
        new_val (cond
                  (neg? v) (+ 100 v)
                  (>= v 100) (mod v 100)
                  :else v)]
    (if (= 0 new_val)
      [new_val (inc zeros)]
      [new_val zeros])))


(defn rotate2 [[dial zeros] amount]
  (let [full_turns (int (/ amount 100))
        actual_ammount (- amount (* full_turns 100))
        turns (abs full_turns)
        new_value (+ dial actual_ammount)
        [new_dial passed_zero] (cond
                                 (neg? new_value) [(+ 100 new_value) (if (zero? dial) 0 1)]
                                 (= new_value 0) [0 1]
                                 (>= new_value 100) [(mod new_value 100) 1]
                                 :else [new_value 0])
        new_zeros (+ zeros turns passed_zero)]
    [new_dial new_zeros]))


(defn rotate_dial [lines rotate-fn]
  (second
    (reduce rotate-fn
            [50 0]
            (rotations lines))))


(defn part1 [lines]
  (rotate_dial lines rotate))


(defn part2 [lines]
  (rotate_dial lines rotate2))


(comment
  (time (part1 (util/file->lines "dec01_sample.txt")))
  (time (part1 (util/file->lines "dec01_input.txt")))
  (time (part2 (util/file->lines "dec01_sample.txt")))
  (time (part2 (util/file->lines "dec01_input.txt"))))
