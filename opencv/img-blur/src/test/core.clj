(ns test.core
  (:gen-class))


(import '[org.opencv.core Mat Size CvType]
               '[org.opencv.imgcodecs Imgcodecs]
               '[org.opencv.imgproc Imgproc])
(defn -main
  "Simple OpenCV test based on http://docs.opencv.org/2.4/doc/tutorials/introduction/clojure_dev_intro/clojure_dev_intro.html"
  [& args]
  (println "OpenCV test")
  (def lena (Imgcodecs/imread "resources/images/lena.png"))
  (def blurred (Mat. 512 512 CvType/CV_8UC3))
  (Imgproc/GaussianBlur lena blurred (Size. 5 5) 3 3)
  (Imgcodecs/imwrite "resources/images/blurred.png" blurred))
