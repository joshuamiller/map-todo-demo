(ns map-todo-demo.db
  (:require [clojure.spec.alpha :as s]))

;; spec of app-db
(s/def ::lat float?)
(s/def ::lon float?)
(s/def ::name string?)
(s/def ::done? boolean?)
(s/def ::location
  (s/keys :req [::lat ::lon ::done?]))

(s/def ::locations
  (s/map-of string? ::location))
(s/def ::app-db
  (s/keys :req [::locations ::map-center]))

(s/def ::map-center
  (s/keys :req [::lat ::lon]))

;; initial state of app-db
(def app-db {::locations {"Ox"
                           {::lat 45.539412
                            ::lon -122.6640834
                            ::done? true}

                          "Tusk"
                          {::lat 45.5226369
                           ::lon -122.6426823
                           ::done? true}

                          "Nodoguro"
                          {::lat 45.51627
                           ::lon -122.6385087
                           ::done? false}}
             ::map-center {::lat 45.51627
                           ::lon -122.6385087}})
