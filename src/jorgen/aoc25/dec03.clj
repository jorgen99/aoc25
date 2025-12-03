(ns jorgen.aoc25.dec03
  (:require
    [clojure.string :as str]
    [jorgen.util :as util]))


(defn joltage [x]
  (loop [s x
         acc 0]
    (if (= 0 (count s))
      acc
      (let [i (str (first s))
            r (str (apply str (rest s)))
            s (->> r
                   sort
                   reverse
                   first
                   str)
            sum (parse-long (str/join [i s]))]
        (if (> sum acc)
          (recur r sum)
          (recur r acc))))))


(defn joltage2 [bank no-of-batteries]
  (loop [remaining-batteries bank
         batteries-to-pick no-of-batteries
         acc []]
    (if (zero? batteries-to-pick)
      acc
      (let [window-size (inc (- (count remaining-batteries) batteries-to-pick))
            window (subvec remaining-batteries 0 window-size)
            max-in-window (apply max window)
            i (util/index-of remaining-batteries max-in-window)]
        (recur (subvec remaining-batteries (inc i))
               (dec batteries-to-pick)
               (conj acc max-in-window))))))


(defn part1 [lines]
  (->> lines
       (map joltage)
       (reduce +)))


(defn part2 [lines]
  (->> lines
       (mapv (fn [s] (mapv #(parse-long (str %)) s)))
       (map #(joltage2 % 12))
       (map str/join)
       (map parse-long)
       (reduce +)))

(comment
  (time (part1 (util/file->lines "dec03_sample.txt")))
  (time (part1 (util/file->lines "dec03_input.txt")))
  (time (part2 (util/file->lines "dec03_sample.txt")))
  (time (part2 (util/file->lines "dec03_input.txt"))))


