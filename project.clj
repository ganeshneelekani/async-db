(defproject async-db "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [migratus "1.4.9"]
                 [org.slf4j/slf4j-log4j12 "2.0.6" :extension "pom"]
                 [org.postgresql/postgresql "42.5.2"]
                 [mysql/mysql-connector-java "8.0.11"] 
                 [com.github.seancorfield/honeysql "2.4.972"]
                 [com.github.seancorfield/next.jdbc "1.3.847"]] 
  :main ^:skip-aot async-db.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
