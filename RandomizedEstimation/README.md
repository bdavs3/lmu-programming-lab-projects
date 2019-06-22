# Area Estimator

### Description

This folder contains utilities that can calculate the areas of shapes using randomized estimation. You can think of this as "throwing darts" and coloring them differently depending on whether they land on the inside or outside of the shape. Here's an example of how to see this happen:

Compile two files:

```sh
javac AreaEstimator.java
javac Dartboard.java
```

Run:

```sh
java AreaEstimator circle 0.0 0.0 0.5 > test.txt
```

to "throw" a million darts and get back a bunch of standard output that we then send to a .txt file.
Now, run:

```sh
java Dartboard < test.txt
```

to see the result. You should see a circle slowly form in a new window. Peek at the source code to see how to do `AreaEstimator` with different shapes. You may also specify how you'd like the dartboard to be layed out by specifying six arguments:

1. width (default `512`)
2. height (default `512`)
3. left (default `-1.0`)
4. top (default `1.0`)
5. right (default `1.0`)
6. bottom (default `-1.0`)
