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
     [:div.card.has-text-centered {:style {:width "340px"}}
      [:header.card-header.is-paddingless
       [:p.card-header-title.is-centered.animated.fadeInLeft "Tic tac toe"]]
      [:div.card-content
       [:p "Made using ClojureScript "
        [:i.fas.fa-coffee.animated.pulse.anim-forever
         {:aria-hidden true
          :style {:color "#013243"}}]]
       [:p "Repository available at "
        [:a {:href "https://github.com/joncol/tictactoe"}
         "https://github.com/joncol/tictactoe"]]]
      [:footer.card-footer
       "© 2018 Jonas Collberg. No rights reserved."]]]))

(defn cell [index]
  (let [board     (rf/subscribe [::subs/board])
        winner    (rf/subscribe [::subs/winner])
        in-replay (rf/subscribe [::subs/in-replay])
        cell      (get @board index)]
    [:div.button.is-dark.title.is-4
     {:disabled (or @winner @in-replay)
      :style    {:width  cell-size
                 :height cell-size
                 :margin "2px"}
      :on-click #(when (and (not @winner) (not @in-replay))
                   (rf/dispatch [::events/make-move index]))}
     [:div.animated.bounceIn {:hidden (not cell)}
      cell]]))

(defn game-info []
  (let [current-move (rf/subscribe [::subs/current-move])
        winner       (rf/subscribe [::subs/winner])
        next-to-move (rf/subscribe [::subs/next-to-move])]
    [:div.card.has-text-left.has-text-centered #_{:style {:height "150px"}}
     [:div.card-content.is-centered
      "Current move: " @current-move [:br]
      (if @winner
        [:span "Game over " [:br] "Winner: " @winner]
        [:span "Next to move: " @next-to-move])]]))

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
     [:button.button.is-danger.tooltip
      {:disabled (zero? @current-move)
       :data-tooltip (str "Clear game board. Only available when a move has "
                          "been made")
       :on-click #(rf/dispatch [::events/initialize-db])}
      "Reset"]
     [:button.button.is-warning.tooltip
      {:disabled (or (not @winner) @in-replay)
       :data-tooltip "Replay game. Only available when a game is finished"
       :on-click #(rf/dispatch [::events/replay-tick 0])}
      "Replay"]]))

(defn main-panel []
  [:div.section.animated.fadeIn
   [about-screen]
   [:div.card.has-text-centered.is-wide
    [:header.card-header [:h1.title "Tic tac toe"]]
    [:div.card-content
     [:div.columns.is-mobile
      [:div.column.is-narrow
       [board]]
      [:div.column.is-narrow {:style {:width "205px"}} [game-info]]]
     [:div.container
      [buttons]]]
    [:footer.card-footer [about-link]]]])
