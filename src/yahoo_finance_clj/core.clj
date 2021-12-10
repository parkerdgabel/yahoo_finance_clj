(ns yahoo-finance-clj.core
  (:require [clj-http.client :as client]
            [clojure.string :as s]
            [clojure.data.json :as json]))

(defonce ^:private api-key (atom ""))

(defn set-api-key [key] (reset! api-key key))

(def ^:private base-url "https://yfapi.net")

(defn- yahoo-get [url opts]
  (json/read-str (:body (client/get url opts))))

(def ^:private quote-url (str base-url "/v6/finance/quote"))

(defn quote [parameters]
  (let [headers {:x-api-key @api-key}
        params (assoc parameters :symbols (s/join "," (:symbols parameters)))
        opts {:query-params params :headers headers}]
    (yahoo-get quote-url opts)))

(def ^:private options-url (str base-url "/v7/finance/options/"))

(defn options [sym & date]
  (let [headers {:x-api-key @api-key}
        url (str options-url sym)
        opts (if date  {:query-params {:date date} :headers headers}
                       {:headers headers})]
    (yahoo-get url opts)))

(def ^:private spark-url (str base-url "/v8/finance/spark"))

(defn spark [parameters]
  (let [headers {:x-api-key @api-key}
        params (assoc parameters :symbols (s/join "," (:symbols parameters)))
        opts {:query-params params :headers headers}]
    (yahoo-get spark-url opts)))

(def ^:private quoteSummary-url (str base-url "/v11/finance/quoteSummary/"))

(defn quote-summary [sym & parameters]
  (let [headers {:x-api-key @api-key}
        url (str quoteSummary-url sym)
        modules (if (contains? parameters :modules)
                  (s/join "," (:modules parameters))
                  (s/join "," ["defaultKeyStatistics" "assetProfile"]))
        params (assoc parameters :modules modules)
        opts {:query-params params :headers headers}]
    (yahoo-get url opts)))
