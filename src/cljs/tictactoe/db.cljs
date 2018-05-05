(ns tictactoe.db)

(def default-db
  {:current-move  0
   :next-to-move  "x"
   :board-history [(vec (repeat 9 nil))]
   :in-replay     false})
