(defproject db_postgres "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :main ^:skip-aot db_postgres.core
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.github.seancorfield/next.jdbc "1.3.847"]
                 [org.postgresql/postgresql "42.5.0"]]
  :repl-options {:init-ns db-postgres.core})
