(defproject bookmenow "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [compojure "1.5.1"]
                 [ring-server "0.4.0"]
                 [ring/ring-json "0.4.0"]
                 [environ "1.1.0"]
                 [hiccup "1.0.5"]
                 [clj-http "2.3.0"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler bookmenow.handler/app
         :init bookmenow.handler/init
         :destroy bookmenow.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
