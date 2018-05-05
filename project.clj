(defproject tictactoe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[camel-snake-kebab "0.4.0"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [log4j/log4j "1.2.17"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238" :scope "provided"]
                 [org.clojure/data.codec "0.1.1"]
                 [org.clojure/tools.logging "0.4.0"]
                 [reagent "0.8.0"]
                 [reagent-utils "0.3.1"]
                 [re-frame "0.10.5"]
                 [ring "1.6.3"]
                 [ring-server "0.5.0"]
                 [ring/ring-defaults "0.3.1"]
                 [ring/ring-json "0.4.0"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.4"
                  :exclusions [org.clojure/tools.reader]]
                 [yogthos/config "1.1.1"]]
  :plugins [[lein-asset-minifier "0.4.4" :exclusions [org.clojure/clojure]]
            [lein-cljsbuild "1.1.7"]
            [lein-environ "1.1.0"]]
  :min-lein-version "2.5.3"
  :uberjar-name "tictactoe.jar"
  :main tictactoe.server
  :clean-targets ^{:protect false}
  ["resources/public/css/site.css"
   "resources/public/css/site.css.map"
   "resources/public/css/site.min.css"
   :target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources" "target/cljsbuild"]
  :minify-assets
  [[:css
    {:source "resources/public/css/site.css"
     :target "resources/public/css/site.min.css"}]]
  :cljsbuild
  {:builds {:min
            {:source-paths ["src/cljs" "src/cljc" "env/prod/cljs"]
             :compiler
             {:output-to       "target/cljsbuild/public/js/app.js"
              :optimizations   :advanced
              :closure-defines {goog.DEBUG false}
              :pretty-print    false}}
            :app
            {:source-paths ["src/cljs" "src/cljc" "env/dev/cljs"]
             :figwheel     {:on-jsload "tictactoe.core/mount-root"}
             :compiler
             {:main                 "tictactoe.dev"
              :asset-path           "/js/out"
              :output-to            "target/cljsbuild/public/js/app.js"
              :output-dir           "target/cljsbuild/public/js/out"
              :source-map           true
              :source-map-timestamp true
              :optimizations        :none
              :pretty-print         true}}}}
  :figwheel
  {:http-server-root "public"
   :server-port      3449
   :nrepl-port       7002
   :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"
                      "cider.nrepl/cider-middleware"
                      "refactor-nrepl.middleware/wrap-refactor"]
   :css-dirs         ["resources/public/css"]
   :ring-handler     tictactoe.handler/app}
  :sass {:src              "src/sass"
         :output-directory "resources/public/css"}
  :profiles {:dev {:repl-options {:init-ns tictactoe.repl
                                  :nrepl-middleware
                                  [cemerick.piggieback/wrap-cljs-repl]}
                   :dependencies [[binaryage/devtools "0.9.10"]
                                  [ring/ring-mock "0.3.2"]
                                  [ring/ring-devel "1.6.3"]
                                  [prone "1.5.2"]
                                  [figwheel-sidecar "0.5.15"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [pjstadig/humane-test-output "0.8.3"]]
                   :jvm-opts ["-Dloglevel=ALL"]
                   :source-paths ["env/dev/clj"]
                   :plugins [[lein-figwheel "0.5.15"]
                             [cider/cider-nrepl "0.15.1"]
                             [org.clojure/tools.namespace "0.3.0-alpha4"
                              :exclusions [org.clojure/tools.reader]]
                             [refactor-nrepl "2.3.1"
                              :exclusions [org.clojure/clojure]]
                             [lein-sass "0.5.0"]]
                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]
                   :env {:dev true}}
             :test {:jvm-opts ["-Dloglevel=OFF"]}
             :uberjar {:hooks [leiningen.sass
                               minify-assets.plugin/hooks
                               leiningen.cljsbuild]
                       :source-paths ["env/prod/clj"]
                       :prep-tasks ["compile"]
                       :env {:production true}
                       :aot :all
                       :omit-source true}})
