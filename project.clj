(defproject org.clojars.akiel/shortid "0.2-SNAPSHOT"
  :description "Generates short base62 encoded identifiers."
  :url "https://github.com/alexanderkiel/shortid"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]]

  :profiles {:dev {:dependencies [[org.clojure/test.check "0.9.0"]]}})
