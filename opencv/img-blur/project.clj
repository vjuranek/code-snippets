(defproject test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [opencv/opencv "3.1.0"]
                 [opencv/opencv-native "3.1.0"]]
  :main ^:skip-aot blur.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :injections [(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)])
