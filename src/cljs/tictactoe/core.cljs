(ns tictactoe.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [tictactoe.events :as events]
            [tictactoe.routes :as routes]
            [tictactoe.views :as views]
            [tictactoe.config :as config]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (r/render [views/main-panel] (.getElementById js/document "app")))

(defn ^:export init! []
  (routes/app-routes)
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
