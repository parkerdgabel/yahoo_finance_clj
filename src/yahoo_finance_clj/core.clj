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

(def ^:private chart-url (str base-url "/v8/finance/chart/"))

(defn chart [ticker & parameters]
  (let [headers {:x-api-key @api-key}
        url (str chart-url ticker)
        events (if (contains? parameters :events)
                 (s/join "," (:events parameters))
                 (s/join "," ["div" "split"]))
        comparisons (if (contains? parameters :comparisons)
                      (s/join "," (:comparisons parameters))
                      "")
        params (assoc parameters :events events :comparisons comparisons)
        opts {:query-params params :headers headers}]
    (yahoo-get url opts)))

(def ^:private recommendations-by-symbol-url (str base-url "/v6/finance/recommendationsbysymbol/"))

(defn recommendations-by-symbol [sym]
  (let [headers {:x-api-key @api-key}
        url (str recommendations-by-symbol-url sym)
        opts {:headers headers}]
    (yahoo-get url opts)))

(def ^:private watchlist-url (str base-url "/ws/screeners/v1/finance/screener/predefined/saved"))

(defn watchlist [& params]
  (let [headers {:x-api-key @api-key}
        opts {:query-params (if params params {:scrIds "day_gainers"}) :headers headers}]
    (yahoo-get watchlist-url opts)))
