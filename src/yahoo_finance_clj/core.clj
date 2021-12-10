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
    (json/read-str (:body (client/get quote-url {:query-params params :headers headers})))))

(def ^:private options-url (str base-url "/v7/finance/options/"))

(defn options [sym & date]
  (let [headers {:x-api-key @api-key}
        url (str options-url sym)
        opts (if date  {:query-params {:date date} :headers headers}
                       {:headers headers})]
    (json/read-str (:body (client/get url opts)))))

(def ^:private spark-url (str base-url "/v8/finance/spark"))

(defn spark [parameters]
  (let [headers {:x-api-key @api-key}
        params (assoc parameters :symbols (s/join "," (:symbols parameters)))]
    (json/read-str (:body (client/get spark-url {:query-params params :headers headers})))))
