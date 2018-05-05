(ns tictactoe.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [config.core :refer [env]]
            [hiccup.page :refer [include-js include-css html5]]
            [tictactoe.middleware :refer [wrap-middleware]]))

(def mount-target
  [:div#app
   [:h3 "ClojureScript has not been compiled!"]
   [:p "please run "
    [:b "lein figwheel"]
    " in order to start the compiler"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))
   [:link {:type        "text/css"
           :href        (str "https://use.fontawesome.com/releases/v5.0.10/"
                             "css/all.css")
           :rel         "stylesheet"
           :integrity   (str "sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOK"
                             "mrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg")
           :crossorigin "anonymous"}]])

(defn loading-page []
  (html5
   (head)
   [:body {:class "body-container"}
    mount-target
    (include-js "/js/app.js")]))

(defroutes routes
  (GET "/" [] (loading-page))
  (GET "/about" [] (loading-page))
  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'routes))
