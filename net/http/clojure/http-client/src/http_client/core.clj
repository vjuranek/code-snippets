(ns http-client.core
  (:gen-class)
  (:require [clj-http.client :as client]))

(defn -main
  [& args]
  (let [resp (client/get "http://example.com/")]
    (println "Status: " (:status resp))
    (println "Body: " (:body resp))))
