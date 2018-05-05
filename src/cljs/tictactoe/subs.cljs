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
