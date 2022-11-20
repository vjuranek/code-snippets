(ns db_postgres.core
  (:require [next.jdbc :as jdbc]))

(def db {:host "172.17.0.2"
         :dbtype "postgresql"
         :user "postgres"
         :password "postgres"
         :dbname "postgres"})

(def ds (jdbc/get-datasource db))

(defn -main
  []
  (println "Inserting into DB")
  (jdbc/execute-one! ds ["insert into inventory.customers(first_name, last_name, email) values('Someone', 'Else', 'some@elsewhere.com')"]))
