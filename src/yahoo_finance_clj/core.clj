(ns yahoo-finance-clj.core
  (:require [clj-http.client :as client]
            [clojure.string :as s]
            [clojure.data.json :as json]))

(defonce ^:private api-key (atom ""))

(defn set-api-key [key] (reset! api-key key))

(def ^:private base-url "https://yfapi.net")

(def ^:private quote-url (str base-url "/v6/finance/quote"))

(defn quote [parameters]
  (let [headers {:x-api-key @api-key}
        params (assoc parameters :symbols (s/join "," (:symbols parameters)))]
    (json/read-str (client/get quote-url {:query-params params :headers headers}))))





