# Clock Solver

### Description

Briefly, the utilities in this folder use the technique of [discrete event simulation](https://en.wikipedia.org/wiki/Discrete-event_simulation) to determine

1. The number of times during a period of 12 hours that the hands of a clock form a given angle
2. What those times of day are

This is a folder without a test harness, so you'll have to run each program to see what it does. `Clock.java` provides a programmatic representation of a clock. The main point of this folder is to provide two files:

- `ClockSolverAngle.java`:
  - Can be run with one argument `angle` to determine how many times a day the hands on a clock form that angle. In this scenario, the simulation is run using the `DEFAULT_TIME_SLICE` of 60 seconds, meaning that the clock hands are measured at intervals of 60 seconds. If the desired angle was passed over, the next closest angle will still be registered as a hit.
  - can be run with two arguments `angle` and `time slice`. Try more precise time slices to get more precise times of day.
- `ClockSolverLine.java`: does the same thing as `ClockSolverAngle.java`, except it only determines the times of day that the hands of a clock form a straight angle. Specify no arguments to use the `DEFAULT_TIME_SLICE` of 60 seconds, or specify an argument to customize the time slice.
