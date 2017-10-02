(ns map-todo-demo.ios.core
  (:require [goog.object :as go]
            [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [map-todo-demo.db :as db]
            [map-todo-demo.events]
            [map-todo-demo.subs]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def switch (r/adapt-react-class (.-Switch ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(def map-view (r/adapt-react-class (js/require "react-native-maps")))
(def map-marker (r/adapt-react-class (.-Marker (js/require "react-native-maps"))))


(defn alert [title]
      (.alert (.-Alert ReactNative) title))

(defn update-region
  [region]
  (dispatch [:set-map-center [(go/get region "latitude")
                              (go/get region "longitude")]]))

(defn my-map
  [lat lon]
  (let [locations (subscribe [:locations])]
    [map-view {:style {:flex 1}
               :initialRegion {:latitude lat
                               :longitude lon
                               :latitudeDelta 0.0922
                               :longitudeDelta 0.0421}
;               :onRegionChange update-region
               }
     (for [[name location] @locations]
       [map-marker {:key name
                    :coordinate #js {:latitude (::db/lat location)
                                     :longitude (::db/lon location)}
                    :title name
                    :description (str "Visited? " (if (::db/done? location) "Yes" "No"))}])]))

(defn app-root []
  (let [center    (subscribe [:center])
        locations (subscribe [:locations])]
    (fn []
      [view {:style {:flex 1
                     :flex-direction "column"
                     :padding 20}}
       [my-map (::db/lat @center) (::db/lon @center)]
       [view {:style {:flex-direction "column"
                      :height 300}}
        (for [[name location] @locations]
          [view {:key name
                 :style {:flex-direction "row"
                         :justify-content "space-between"
                         :padding 10}}
           [text name]
           [switch {:value (::db/done? location)
                    :onValueChange #(dispatch [:update-location-done name])}]])]])))

(defn init []
      (dispatch-sync [:initialize-db])
      (.registerComponent app-registry "MapTodoDemo" #(r/reactify-component app-root)))
