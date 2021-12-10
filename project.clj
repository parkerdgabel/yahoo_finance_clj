(defproject org.clojars.parkerdgabel/yahoo_finance_clj "0.1.0"
  :description "Wrapper for yahoo finance api."
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-http "3.12.3"]
                 [org.clojure/data.json "2.4.0"]]
  :deploy-repositories [["releases" :clojars :sign-releases false]
                        ["snapshots" :clojars :sign-releases false]]
  :repl-options {:init-ns yahoo-finance-clj.core})
