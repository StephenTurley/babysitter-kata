(ns babysitter-kata.billing-spec
  (:require
    [speclj.core :refer :all]
    [babysitter-kata.billing :as underTest]))

(describe "calculate price"
  (context "invalid time"
    (it "should return zero when start time is too early"
        (should= 0 (underTest/calculate-price "3:00PM" "8:00PM" "1:00AM")))
    (it "should return zero when end time is too late"
        (should= 0 (underTest/calculate-price "5:00PM" "8:00PM" "5:00AM"))))
  (context "billable time"
    (it "should charge $12 for one hour before bedtime"
        (should= 12 (underTest/calculate-price "5:00PM" "8:00PM" "6:00PM")))
    (xit "should charge $24 for two hours before bedtime"
        (should= 24 (underTest/calculate-price "5:00PM" "8:00PM" "7:00PM")))))

(describe "before?"
  (it "should return true when time is before"
      (should (underTest/before? "5:00PM" "6:00PM")))
  (it "should return true when start is PM and end is AM"
      (should (underTest/before? "5:00PM" "4:00AM"))))

(describe "after?"
  (it "should return true when time is after"
      (should (underTest/after? "6:00PM" "5:00PM")))
  (it "should return true when start is PM and end is AM"
      (should (underTest/after? "5:00AM" "4:00PM"))))

(describe "to-hour"
  (it "return the hour as a number"
    (should= 5 (underTest/to-hour "5:00PM"))
    (should= 10 (underTest/to-hour "10:00PM"))))

(describe "morning?"
  (it "should return true if the time contains AM"
    (should (underTest/morning? "4:00AM"))
    (should-not (underTest/morning? "4:00PM"))))

(describe "evening?"
  (it "should return true if the time contains PM"
    (should (underTest/evening? "4:00PM"))
    (should-not (underTest/evening? "4:00AM"))))

(describe "flip-period"
  (it "should flip the period"
    (should= "AM" (underTest/flip-period "PM"))
    (should= "PM" (underTest/flip-period "AM"))))

(describe "as-seq"
  (it "should return empty when start and end are equal"
    (should= nil (underTest/as-seq "1:00PM" "1:00PM")))
  (it "should return first one hour"
    (should= '("1:00PM") (underTest/as-seq "1:00PM" "2:00PM")))
  (it "should return two or more hours"
    (should= '("1:00PM" "2:00PM") (underTest/as-seq "1:00PM" "3:00PM"))
    (should= '("1:00PM" "2:00PM" "3:00PM") (underTest/as-seq "1:00PM" "4:00PM"))
    (should= '("12:00PM" "1:00AM") (underTest/as-seq "12:00PM" "2:00AM"))))

(describe "inc-hour"
  (it "should return the next hour"
    (should= "4:00PM" (underTest/inc-hour "3:00PM"))
    (should= "2:00AM" (underTest/inc-hour "1:00AM")))
  (it "should change the period"
    (should= "1:00PM" (underTest/inc-hour "12:00AM"))
    (should= "1:00AM" (underTest/inc-hour "12:00PM"))))

(run-specs)
