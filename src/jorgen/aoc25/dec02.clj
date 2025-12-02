(ns jorgen.aoc25.dec02
  (:require
    [clojure.string :as str]
    [jorgen.util :as util]))


(defn split-middle [s]
  (let [middle (/ (count s) 2)]
    [(subs s 0 middle) (subs s middle)]))


(defn find-dual-repeating [[s e]]
  (reduce (fn [acc x]
            (if (apply = (split-middle (str x)))
              (conj acc x)
              acc))
          []
          (range s (inc e))))


(defn find-any-repeating [s]
  (let [length (count s)
        half (inc (int (/ length 2)))]
    (->> (range 1 half)
         (filter #(zero? (mod length %)))
         (map #(partition % s))
         (map #(apply = %))
         (some true?)
         boolean)))


(defn find-patterns [[s e]]
  (reduce (fn [acc i]
            (if (find-any-repeating (str i))
              (conj acc i)
              acc))
          []
          (range s (inc e))))


(defn sum-invalids [lines repeating-fn]
  (let [ranges (str/split (first lines) #"\,")]
    (->> ranges
         (map #(str/split % #"\-"))
         (map #(mapv parse-long %))
         (map repeating-fn)
         (remove empty?)
         (flatten)
         (reduce +))))


(defn part1 [lines]
  (sum-invalids lines find-dual-repeating))


(defn part2 [lines]
  (sum-invalids lines find-patterns))


(comment
  (time (part1 (util/file->lines "dec02_sample.txt")))
  (time (part1 (util/file->lines "dec02_input.txt")))
  (time (part2 (util/file->lines "dec02_sample.txt")))
  (time (part2 (util/file->lines "dec02_input.txt"))))
