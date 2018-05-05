(ns tictactoe.middleware
  (:require [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(defn wrap-middleware [handler]
  (-> handler
      (wrap-json-body {:keywords?    ->kebab-case-keyword
                       :bigdecimals? true})
      wrap-json-response
      (wrap-defaults site-defaults)))
