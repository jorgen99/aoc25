(ns jorgen.aoc25.dec04
  (:require
    [clojure.string :as str]
    [jorgen.util :as util]))


(defn neigbor-values [neighbor-positions grid]
  (reduce (fn [acc p]
            (conj acc (util/value-in-grid grid p)))
          []
          neighbor-positions))


(defn safe? [pos grid]
  (if (= "." (util/value-in-grid grid pos))
    false
    (let [ns (util/neighbor-positions pos grid util/all-directions)
          nv (neigbor-values ns grid)
          no-rolls-in-pos (count (remove #(= "." %) nv))]
      (< no-rolls-in-pos 4))))


(defn safe-positions [grid]
  (let [coords (util/coordinates grid)]
    (loop [c coords
           acc []]
      (if (empty? c)
        acc
        (let [pos (first c)]
          (if (safe? pos grid)
            (recur (rest c) (conj acc pos))
            (recur (rest c) acc)))))))


(defn remove-unsafe [grid]
  (loop [sps (safe-positions grid)
         new-grid grid
         result (count sps)]
    (if (empty? sps)
      result
      (let [safe-grid (reduce #(util/set-value-in-grid %1 %2 ".") new-grid sps)
            new-sps (safe-positions safe-grid)
            new-result (+ (count new-sps) result)]
        (recur new-sps safe-grid new-result)))))


(defn part1 [lines]
  (let [grid (util/parse-grid-of-chars lines)]
    (count (safe-positions grid))))


(defn part2 [lines]
  (let [grid (util/parse-grid-of-chars lines)]
    (remove-unsafe grid)))


(comment
  (time (part1 (util/file->lines "dec04_sample.txt")))
  (time (part1 (util/file->lines "dec04_input.txt")))
  (time (part2 (util/file->lines "dec04_sample.txt")))
  (time (part2 (util/file->lines "dec04_input.txt"))))


