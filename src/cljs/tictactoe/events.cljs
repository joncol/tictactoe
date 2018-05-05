(ns tictactoe.events
  (:require [re-frame.core :as rf]
            [tictactoe.db :as db]))

(rf/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(rf/reg-event-db
  ::make-move
  (fn [{:keys [board] :as db} [_ index]]
    (if-not (get-in db [:board index])
      (let [new-board (assoc board index (:next-to-move db))]
        (-> db
            (assoc :board new-board)
            (update :history #(conj % new-board))
            (update :next-to-move #(if (= "x" %) "o" "x"))))
      db)))
