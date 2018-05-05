(ns tictactoe.db)

(def default-db
  {:board        (vec (repeat 9 nil))
   :next-to-move "x"})
