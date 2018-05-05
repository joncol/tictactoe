(ns tictactoe.views
  (:require [re-frame.core :as rf]
            [tictactoe.events :as events]
            [tictactoe.subs :as subs]))

(def cell-size "40px")

(defn cell [index]
  (let [board (rf/subscribe [::subs/board])]
    [:div.button.is-info
     {:style {:width cell-size
              :height cell-size
              :margin "2px"}
      :on-click #(rf/dispatch [::events/make-move index])}
     (get @board index)
     ]))

(defn game-info []
  [:p "Next to move: " @(rf/subscribe [::subs/next-to-move])]
  )

(defn board []
  [:div.container
   [:div.title "Tic tac toe"]
   [:div.section
    [:div.columns
     [:div.column.is-narrow
      (for [row (range 3)]
        ^{:key [row]}
        [:div (for [col (range 3)]
                ^{:key [row col]}
                [cell (+ (* 3 row) col)])])]
     [:div.column.is-narrow {:style {:width "200px"}} [game-info]]]]])

(defn main-panel []
  [board])
