(ns tictactoe.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  ::show-about
  (fn [db _]
    (:show-about db)))

(rf/reg-sub
  ::next-to-move
  (fn [_ _]
    (rf/subscribe [::current-move]))
  (fn [current-move _]
    (if (zero? (mod current-move 2))
      "x"
      "o")))

(rf/reg-sub
  ::current-move
  (fn [db _]
    (:current-move db)))

(rf/reg-sub
  ::board-history
  (fn [db _]
    (:board-history db)))

(rf/reg-sub
  ::in-replay
  (fn [db _]
    (:in-replay db)))

(rf/reg-sub
  ::board
  (fn [_ _]
    [(rf/subscribe [::board-history])
     (rf/subscribe [::current-move])])
  (fn [[board-history current-move] _]
    (get board-history current-move)))

(defn winner [board]
  (let [lines [[0 1 2] [3 4 5] [6 7 8]
               [0 3 6] [1 4 7] [2 5 8]
               [0 4 8] [2 4 6]]]
    (->> (for [l lines]
           (for [i l]
             (get board i)))
         (map #(frequencies %))
         (some #(some (fn [[k v]]
                        (and (= 3 v) k)) %)))))

(rf/reg-sub
  ::winner
  (fn [_ _]
    (rf/subscribe [::board]))
  (fn [board _]
    (winner board)))
