Babysitter Kata
--------------

##### Run the tests
`lein spec` 

##### load in a repl
1. `lein repl`
2. `(load-file "src/babysitter_kata/billing.clj")`
3. `(calculate-price <start-time> <bed-time> <end-time>)`

Background
----------
This kata simulates a babysitter working and getting paid for one night.  The rules are pretty straight forward:

The babysitter
- starts no earlier than 5:00PM
- leaves no later than 4:00AM
- gets paid $12/hour from start-time to bedtime
- gets paid $8/hour from bedtime to midnight
- gets paid $16/hour from midnight to end of job
- gets paid for full hours (no fractional hours)


Feature:
As a babysitter
In order to get paid for 1 night of work
I want to calculate my nightly charge

[by: James Kirkbride](https://gist.github.com/jameskbride/5482722)
