(ns tictactoe.events
  (:require [re-frame.core :as rf]
            [tictactoe.db :as db]))

(rf/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(rf/reg-event-db
  ::show-about
  (rf/path [:show-about])
  (fn [_ [_ value]]
    value))

(rf/reg-event-db
  ::make-move
  (fn [{:keys [current-move board-history] :as db} [_ cell-index]]
    (let [board (get board-history current-move)]
      (if-not (get board cell-index)
        (let [new-board (assoc board cell-index (:next-to-move db))]
          (-> db
              (update :board-history #(conj % new-board))
              (update :current-move inc)
              (update :next-to-move #(if (= "x" %) "o" "x"))))
        db))))

(rf/reg-event-fx
  ::replay-tick
  (fn [{:keys [db]} [_ move]]
    (let [{:keys [board-history]} db]
      (let [done   (= move (dec (count board-history)))
            new-db (-> db
                       (assoc :current-move move)
                       (assoc :in-replay (not done)))]
        (-> {:db new-db}
            (as-> db
                (if done
                  db
                  (assoc db
                         :dispatch-later
                         [{:ms 500
                           :dispatch [::replay-tick (inc move)]}]))))))))
