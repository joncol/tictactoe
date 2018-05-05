(ns tictactoe.routes
  (:require [goog.events :as gevents]
            [goog.history.EventType :as EventType]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [tictactoe.events :as events])
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  (hook-browser-navigation!))
