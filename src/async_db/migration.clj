(ns async-db.migration)

(def migratus-config
  {:store         :database
   :migration-dir "migrations"
   :db            {:dbname   (or (System/getenv "DEV_DBNAME") "it_data")
                   :user     (or (System/getenv "DEV_USER") "myuser")
                   :password (or (System/getenv "DEV_PASSWORD") "mypassword")
                   :host     (or (System/getenv "DEV_PASSWORD") "localhost")
                   :port     (or (System/getenv "DEV_PASSWORD") 5432)
                   :dbtype   "postgresql"}})