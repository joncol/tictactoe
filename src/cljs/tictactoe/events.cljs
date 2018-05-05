(ns tictactoe.events
  (:require [re-frame.core :as rf]
            [tictactoe.db :as db]))

(rf/reg-event-db
  ::initialize-db
  (fn [_ _]
   db/default-db))

(rf/reg-event-db
  ::make-move
  (fn [db [_ index]]
    (if-not (get-in db [:board index])
      (-> db
          (assoc-in [:board index] (:next-to-move db))
          (update :next-to-move #(if (= "x" %) "o" "x")))
      db)))
