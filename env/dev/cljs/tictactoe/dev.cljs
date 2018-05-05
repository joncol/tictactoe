(ns ^:figwheel-no-load tictactoe.dev
  (:require [devtools.core :as devtools]
            [tictactoe.core :as core]))

(devtools/install!)

(enable-console-print!)

(core/init!)
