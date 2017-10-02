(ns map-todo-demo.events
  (:require
   [re-frame.core :refer [reg-event-db after]]
   [clojure.spec.alpha :as s]
   [map-todo-demo.db :as db :refer [app-db]]))

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;
(defn check-and-throw
  "Throw an exception if db doesn't have a valid spec."
  [spec db [event]]
  (when-not (s/valid? spec db)
    (let [explain-data (s/explain-data spec db)]
      (throw (ex-info (str "Spec check after " event " failed: " explain-data) explain-data)))))

(def validate-spec
  (if goog.DEBUG
    (after (partial check-and-throw ::db/app-db))
    []))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 validate-spec
 (fn [_ _]
   app-db))

(reg-event-db
 :set-map-center
 validate-spec
 (fn [db [_ [lat lon]]]
   (-> db
       (assoc-in [::db/map-center ::db/lat] lat)
       (assoc-in [::db/map-center ::db/lon] lon))))

(reg-event-db
 :update-location-done
 validate-spec
 (fn [db [_ name]]
   (update-in db [::db/locations name ::db/done?] not)))
