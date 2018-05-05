(ns tictactoe.views
  (:require [re-frame.core :as rf]
            [tictactoe.events :as events]
            [tictactoe.subs :as subs]))

(def cell-size "50px")

(defn about-link []
  (let [in-replay (rf/subscribe [::subs/in-replay])]
    [:a
     {:href "#/"
      :disabled @in-replay
      :on-click #(rf/dispatch [::events/show-about true])} "About"]))

(defn about-screen []
  (let [show (rf/subscribe [::subs/show-about])]
    [:div.modal.animated.fadeIn
     {:class (when @show "is-active")}
     [:div.modal-background
      {:on-click #(rf/dispatch [::events/show-about false])}
      [:button.modal-close.is-large
       {:aria-label "close"
        :on-click #(rf/dispatch [::events/show-about false])}]]
     [:div.card
      [:header.card-header
       [:p.card-header-title "Ditherer"]]
      [:div.card-content
       [:p "Made by Jonas Collberg" [:br]
        "using ClojureScript "
        [:i.fas.fa-coffee
         {:aria-hidden true
          :style {:color "#013243"}}]]]]]))

(defn cell [index]
  (let [board     (rf/subscribe [::subs/board])
        winner    (rf/subscribe [::subs/winner])
        in-replay (rf/subscribe [::subs/in-replay])]
    [:div.button.is-dark.title.is-4
     {:disabled (or @winner @in-replay)
      :style    {:width  cell-size
                 :height cell-size
                 :margin "2px"}
      :on-click #(when (and (not @winner) (not @in-replay))
                   (rf/dispatch [::events/make-move index]))}
     (get @board index)]))

(defn game-info []
  (let [current-move (rf/subscribe [::subs/current-move])
        winner       (rf/subscribe [::subs/winner])
        next-to-move (rf/subscribe [::subs/next-to-move])]
    [:div
     [:p "Current move: " @current-move]
     (if @winner
       [:p "Game over (" @winner " won)"]
       [:p "Next to move: " @next-to-move])]))

(defn board []
  [:div (for [row (range 3)]
          ^{:key [row]}
          [:div (for [col (range 3)]
                  ^{:key [row col]}
                  [cell (+ (* 3 row) col)])])])

(defn buttons []
  (let [current-move (rf/subscribe [::subs/current-move])
        winner       (rf/subscribe [::subs/winner])
        in-replay    (rf/subscribe [::subs/in-replay])]
    [:div.buttons
     [:button.button.is-danger
      {:disabled (zero? @current-move)
       :on-click #(rf/dispatch [::events/initialize-db])}
      "Reset"]
     [:button.button.is-warning
      {:disabled (or (not @winner) @in-replay)
       :on-click #(rf/dispatch [::events/replay-tick 0])}
      "Replay"]]))

(defn main-panel []
  [:div.container.section
   [about-screen]
   [:nav.navbar.is-light
    [:div.navbar-menu.is-active
     [:div.navbar-start [:h1.title "Tic tac toe"]]
     [:div.navbar-end
      [:div.navbar-item [about-link]]]]]
   [:div.section
    [:div.columns.is-mobile
     [:div.column.is-narrow
      [board]]
     [:div.column.is-narrow {:style {:width "200px"}} [game-info]]]]
   [buttons]])
