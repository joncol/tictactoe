(ns tictactoe.db)

(def default-db
  {:board        (vec (repeat 9 nil))
   :history      []
   :next-to-move "x"})
