(ns jorgen.util
  (:require [clojure.string :as str]))

(def nesw [:n :e :s :w])
(def eswn [:e :s :w :n])
(def clockwise (zipmap nesw eswn))
(def all-directions [:e :se :s :sw :w :nw :n :ne])


(defn file->lines [filename]
  (->> filename
       (str "resources/")
       slurp
       str/split-lines))


(defn char->int [^Character c]
  (Character/digit c 10))


(defn find-first [f col]
  (first (filter f col)))


(defn digit? [c]
  (->> c
       char->int
       neg-int?
       not))


(defn first-digit [col]
  (find-first digit? col))


(defn first-and-last [col]
  ((juxt first last) col))


(defn parse-number-str [line]
  (->> line
       (re-seq #"-?\d+")))


(defn parse-longs [line]
  (->> (parse-number-str line)
       (mapv parse-long)))


(defn gcd
  "Calculate the greatest common divisor with the Euclidean algoritm.
   https://en.wikipedia.org/wiki/Greatest_common_divisor#Euclidean_algorithm
  "
  [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))


(defn lcm
  "Calculate the least common multiple using the greatest common divisor.
   https://en.wikipedia.org/wiki/Least_common_multiple#Using_the_greatest_common_divisor
  "
  [a b]
  (* (abs a) (/ (abs b) (gcd a b))))


(defn lcm-multiple
  "The least common multiple for more than two numbers."
  [numbers]
  (reduce lcm (first numbers) (rest numbers)))


(defn parse-grid-of-chars [lines]
  (mapv #(mapv str %) lines))


(defn value-in-grid [grid pos]
  (get-in grid (reverse pos)))


(defn set-value-in-grid [grid pos value]
  (assoc-in grid (reverse pos) value))


(defn take-step
  "Given a direction and a position get the new position.
   Ignoring 'going out of the grid' resulting in -1
  Ex.
       :n  [1 2] => [1 1]
       :e  [3 8] => [4 8]
       :sw [3 8] => [2 9]
       :n  [1 0] => [1 -1]
  "
  [[x y] direction]
  (case direction
    :n  [x (dec y)]
    :e  [(inc x) y]
    :s  [x (inc y)]
    :w  [(dec x) y]
    :ne [(inc x) (dec y)]
    :se [(inc x) (inc y)]
    :sw [(dec x) (inc y)]
    :nw [(dec x) (dec y)]))


(defn indicies-of-char
  "Find the indicies of the supplied char/string
  Ex.
      [\\*, '.234.*.#.321*.']  => (5, 12)
      ['#', ['.' '.' '#' '.' '#' '.'] => (2, 4)
  "
  [char col]
  (reduce (fn [acc [i s]]
            (if (= s char)
              (conj acc i)
              acc))
          []
          (map-indexed vector col)))


(defn positions-of-char-in-grid [char grid]
  (->> grid
       (map-indexed vector)
       (mapcat (fn [[i line]]
                 (map (fn [j] [i j]) (indicies-of-char char line))))
       (remove empty?)
       (map reverse)))


(defn transpose [grid]
  (apply mapv vector grid))


(defn parse-blocks [lines]
  (loop [blocks []
         remaining lines]
    (if (empty? remaining)
      blocks
      (recur (conj blocks (take-while not-empty remaining))
             (rest (drop-while not-empty remaining))))))


(defn grid-of-longs [lines]
  (->> lines
       (map #(str/split % #"\W"))
       (map #(map parse-long %))))


(defn zip [xs ys]
  (map vector xs ys))


(defn coordinates [grid]
  (for [y (range 0 (count grid))
        x (range 0 (count (first grid)))]
    [x y]))


(defn index-of [col i]
  (first
    (keep-indexed #(when (= %2 i) %1) col)))
