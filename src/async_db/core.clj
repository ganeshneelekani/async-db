(ns async-db.core
  (:require [migratus.core :as m]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all :as h]
            [next.jdbc :as jdbc])
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
  
  (jdbc/execute! db (-> (h/insert-into :students)
                        (h/columns :id :first_name :last_name :email :date_of_birth
                                   :enrollment_date :graduation_date :major)
                        (h/values [[1 "john" "Cena" "john@gmail.com"
                                    "1990-02-02" "2005-05-05" "2011-02-05" "CS"]])
                        (sql/format {:pretty true})))
  )