(ns babysitter-kata.billing)

(def earliest "5:00PM")
(def latest "4:00AM")

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

(defn calculate-price
  [start bed end]
  (if (valid? start end)
    12 0))
