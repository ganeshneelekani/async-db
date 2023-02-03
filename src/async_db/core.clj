(ns async-db.core
  (:require [migratus.core :as m]
            [async-db.migration :as mg])
  (:gen-class))


(defn migrate-sql-statements []
  (try 
    (m/migrate mg/migratus-config)
    (finally 
      (m/rollback mg/migratus-config))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args] 
  (migrate-sql-statements)
  (println "Hello, World!"))

(comment
  
   (migrate-sql-statements)
  )