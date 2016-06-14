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
  "returns true if time is AM"
	[time]
	(boolean (re-find #"AM" time)))

(defn evening?
  "returns true if time is PM"
	[time]
	(not (morning? time)))

(defn before?
  "returns true if start is before end"
	[start end]
  (cond
    (= start end) false
    (and (evening? start) (morning? end)) true
    (< (to-hour start) (to-hour end)) true))

(defn after?
  "returns true if start is after end"
	[start end]
  (cond
    (= start end) false
    (and (morning? start) (evening? end)) true
    (> (to-hour start) (to-hour end)) true))

(defn valid?
  "validates start and end times"
	[start end]
	(and
		(after? start too-early)
		(before? end too-late)))

(defn flip-period
  "returns AM if PM, vice-verca"
	[period]
	(if (= "AM" period) "PM" "AM"))

(defn inc-hour
  "increments the time by 1 hour"
	[time]
	(let [hour (to-hour time)
				period (re-find #"AM|PM" time)]
		(str
			(if (= 12 hour) 1 (inc hour))
			":00"
			(if (= 11 hour) (flip-period period) period))))

(defn as-seq
  "returns a list of consectutive hours from start to end"
	[start end]
	(if (= start end)
		nil
		(lazy-seq
			(cons start (as-seq (inc-hour start) end)))))

(defn determine-value
  "returns dollar value of hour"
	[time bed]
	(cond
		(or (= time "12:00AM")
				(after? time "11:00PM")) after-midnight
		(before? time bed) before-bed-time
		:else after-bed-time))

(defn return-dollar-amt-for
	[hours bed]
	(map #(determine-value % bed) hours))

(defn total
	[list-of-amounts]
	(reduce + list-of-amounts))

(defn calculate-price
  "calculates total price of the day"
	[start bed end]
	(if (valid? start end)
		(let [every-hour (as-seq start end)]
			(total (return-dollar-amt-for every-hour bed)))
		0))
