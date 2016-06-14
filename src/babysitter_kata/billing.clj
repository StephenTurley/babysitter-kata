(ns babysitter-kata.billing)

(def too-early "4:00PM")
(def too-late "5:00AM")
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
	(if (= start end)
		false
		(or (and (evening? start) (morning? end))
				(< (to-hour start) (to-hour end)))))

(defn after?
	[start end]
	(if (= start end)
		false
		(or (and (morning? start) (evening? end))
				(> (to-hour start) (to-hour end)))))

(defn valid?
  [start end]
  (and
		(after? start too-early)
		(before? end too-late)))

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

(defn as-seq
  [start end]
  (if (not (before? start end))
    nil
    (lazy-seq (cons start (as-seq (inc-hour start) end)))))

(defn determine-value
	[time bed]
	(cond
		(or (= time "12:00PM")
				(after? time "11:00PM")) after-midnight
		(before? time bed) before-bed-time
		:else after-bed-time))

(defn calculate-price
  [start bed end]
  (if (valid? start end)
		(reduce + (map  #(determine-value % bed) (as-seq start end)))
		0))
