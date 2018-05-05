(ns tictactoe.middleware
  (:require [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn wrap-middleware [handler]
  (-> handler
      (wrap-json-body {:keywords?    ->kebab-case-keyword
                       :bigdecimals? true})
      (wrap-json-response {:pretty true})
      (wrap-defaults site-defaults)
      wrap-exceptions
      wrap-reload))
