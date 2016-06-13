(ns babysitter-kata.billing)

(def earliest "5:00PM")
(def latest "4:00AM")
(def before-bed-time 12)
(def after-bed-time 8)
(def after-midnight 16)

(defn to-hour
  [time]
  (Integer/parseInt (re-find #"^\d{1,2}" time)))

(defn morning?
  [time]
  (boolean (re-find #"AM" time)))

(defn evening?
  [time]
  (not (morning? time)))

(defn before?
  [start end]
  (or (and (evening? start) (morning? end))
      (< (to-hour start) (to-hour end))))

(defn after?
  [start end]
  (not (before? start end)))

(defn valid?
  [start end]
  (and
    (after? start earliest)
    (before? end latest)))

(defn flip-period
  [period]
  (if (= "AM" period) "PM" "AM"))

(defn inc-hour
  [time]
  (let [hour (to-hour time)
        period (re-find #"AM|PM" time)]
    (str
      (if (= 12 hour) 1 (inc hour))
      ":00"
      (if (= 12 hour) (flip-period period) period))))

(defn calculate-price
  [start bed end]
  (if (valid? start end)
    12 0))
