(ns tictactoe.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  ::board
  (fn [db]
    (:board db)))

(rf/reg-sub
  ::next-to-move
  (fn [db _]
    (:next-to-move db)))

(rf/reg-sub
  ::history
  (fn [db _]
    (:history db)))

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
