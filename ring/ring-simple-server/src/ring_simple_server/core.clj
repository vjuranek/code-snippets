(ns ring-simple-server.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.response :as res]
            [hiccup.page :as p]))

(defn render-page [request]
  (p/html5
   [:head
    [:title "Ring and hiccup test"]]
   [:body
    [:h1 "Hello world!"]
    [:p (str "page was accesed from " (:remote-addr request))]
    [:p "Headers:"]
    [:ul (for [keyval (:headers request)] [:li keyval])]
    [:p "HTTP method: " (:request-method request)]
  ])
)

(defn gen-res
  [page-content]
  (-> (res/response page-content)
      (res/content-type "text/html")))

(defn handler [request]
  (gen-res(render-page request)))

(defn start-server [port]
  (println "Starting Jetty on port " port)
  (jetty/run-jetty handler {:port port}))

(defn -main []
  (start-server 3000))
