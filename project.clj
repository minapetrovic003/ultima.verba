(defproject ultima-verba "0.1.0-SNAPSHOT"
  :description "A multiplayer detective game"
  :dependencies [[org.clojure/clojure "1.12.2"]]
  :main ultima-verba.core
  :profiles {:dev {:dependencies [[midje "1.10.10"]]
                   :plugins [[lein-midje "3.2.1"]]}}
  :repl-options {:init-ns ultima-verba.core})
