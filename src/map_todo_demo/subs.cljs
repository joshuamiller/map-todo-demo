(ns map-todo-demo.subs
  (:require [map-todo-demo.db :as db]
            [re-frame.core :refer [reg-sub]]))

(reg-sub
  :locations
  (fn [db _]
    (::db/locations db)))

(reg-sub
 :center
 (fn [db _]
   (::db/map-center db)))
