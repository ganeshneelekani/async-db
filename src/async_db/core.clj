(ns async-db.core
  (:require [migratus.core :as m]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all :as h]
            [next.jdbc :as jdbc]
            [clojure.core.async :as async :refer [go <! >! >!! <!!]])
  (:refer-clojure :exclude [filter for group-by into partition-by set update])
  (:gen-class))

(def db {:dbname   (or (System/getenv "DEV_DBNAME") "it_data")
         :user     (or (System/getenv "DEV_USER") "myuser")
         :password (or (System/getenv "DEV_PASSWORD") "mypassword")
         :host     (or (System/getenv "DEV_PASSWORD") "localhost")
         :port     (or (System/getenv "DEV_PASSWORD") 5432)
         :dbtype   "postgresql"})

(def migratus-config
  {:store         :database
   :migration-dir "migrations"
   :db            db})


(def database-queue (async/chan))

(go
  (while true
    (let [[op args] (<! database-queue)]
      (case op
        ;;:query (apply jdbc/query args)
        :execute! (jdbc/execute! db args)))))

(defn query [sql cb]
  (>! database-queue [:query sql])
  (async/go (let [results (<! cb)]
              (println "Results: " results))))

(defn execute [sql cb]
  (>!! database-queue [:execute! sql])
  (async/go (let [results (<! cb)]
              (println "Results: " results))))

(defn migrate-sql-statements [] 
  (try 
    (m/migrate migratus-config)
    (catch Exception e
      (m/rollback migratus-config))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args] 
  (migrate-sql-statements)
  (println "Hello, World!"))


(comment
  
  (migrate-sql-statements)
  
  (execute (-> (h/insert-into :students)
               (h/columns :id :first_name :last_name :email :date_of_birth
                          :enrollment_date :graduation_date :major)
               (h/values [[1 "john" "Cena" "john@gmail.com"
                           "1990-02-02" "2005-05-05" "2011-02-05" "CS"]])
               (sql/format {:pretty true}))
            (fn [results]
              (println results)))
  
  (future (jdbc/execute! db (-> (h/insert-into :students)
                                (h/columns :id :first_name :last_name :email :date_of_birth
                                           :enrollment_date :graduation_date :major)
                                (h/values [[1 "john" "Cena" "john@gmail.com"
                                            "1990-02-02" "2005-05-05" "2011-02-05" "CS"]])
                                (sql/format {:pretty true}))))
  )