(defproject myface "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:source-paths ["src" "tool-src"]}
             :uberjar {:aot :all}}
  :aliases {"brevity" ["run" "-m" "brevity.core/handle-commands" :project/main]}
  :main ^:skip-aot myface.core
  :dependencies [[org.clojure/clojure "1.10.0-alpha2"]

                 ;; clj dependencies
                 [org.clojure/java.jdbc "0.7.5"]
                 [org.postgresql/postgresql "42.2.1.jre7"]
                 [com.draines/postal "2.0.2"]
                 [com.amazonaws/aws-java-sdk-s3 "1.11.272"]
                 [org.immutant/web "2.1.10"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-devel "1.6.3"]
                 [ring/ring-json "0.5.0-beta1"]
                 [compojure "1.6.0"]
                 [caesium "0.10.0"]
                 [buddy/buddy-auth "2.1.0"]
                 [buddy/buddy-sign "2.2.0"]
                 [buddy/buddy-hashers "1.3.0"]
                 [com.squareup.okhttp3/okhttp "3.9.1"]
                 [cheshire "5.8.0"]
                 [org.clojure/core.cache "0.6.5"]
                 [org.clojure/data.json "0.2.6"]
                 [net.authorize/anet-java-sdk "1.9.5"]
                 [org.jsoup/jsoup "1.11.2"]
                 [ring/ring-codec "1.1.0"]
                 [camel-snake-kebab "0.4.0"]
                 [http-kit "2.2.0"]
                 [org.clojars.civa86/thumbnailz "1.0.0"]
                 [net.coobird/thumbnailator "0.4.8"]
                 [image-resizer "0.1.10"]

                 ;; cljx dependencies
                 [org.clojure/spec.alpha "0.1.143"]

                 ;; cljs dependencies
                 [org.clojure/clojurescript "1.9.946"]
                 [secretary "1.2.3"]
                 [hiccup "2.0.0-alpha1"]
                 [garden "1.3.3"]
                 [reagent "0.8.0-alpha2"]]
  :clean-targets ["static/development/js"
                  "static/release/js"
                  "static/development/index.js"
                  "static/development/index.js.map"
                  "out"
                  "target"]
  :plugins [[lein-cljsbuild "LATEST"]]
  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["cljs-src"]
                :compiler
                {:output-to "static/development/index.js"
                 :source-map true
                 :output-dir "static/development/js"
                 :optimizations :none
                 :main myface.cljs.core
                 :asset-path "development/js"
                 :cache-analysis true
                 :pretty-print true}}
               {:id "release"
                :source-paths ["cljs-src"]
                :compiler
                {:output-to "static/release/index.js"
                 :source-map "static/release/index.js.map"
                 :externs []
                 :main myface.cljs.core
                 :output-dir "static/release/js"
                 :optimizations :advanced
                 :pseudo-names false}}]})
