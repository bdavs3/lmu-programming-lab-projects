# Map-colorer

### Description

`MapColorer.java` implements backtracking in order to solve the general problem of [coloring maps](https://en.wikipedia.org/wiki/Map_coloring), which is a special and interesting instance of [graph coloring](https://en.wikipedia.org/wiki/Graph_coloring).

To try it out, compile:

```sh
javac MapColorer.java
```

and run with some arguments (explanation in a moment):

```sh
java MapColorer
1,2
2,3
3,4
4,1

...
1:1
2:2
3:1
4:2
```

The program reads standard input, which is intended to be a list of adjacent nodes in a graph, or in the context of this problem, bordering states on a map. To finish your entries, type CTRL+D on MacOS and Windows (signifies EndOfFile). In the example above,\
1 borders 2\
2 borders 3\
3 borders 4 and\
4 borders 1\
The desired output of the program is an optimal coloring of the map such that the least number of colors are used and no two bordering states have the same color. The output

```sh
1:1
2:2
3:1
4:2
```

indicates that\
Region 1 has Color 1\
Region 2 has Color 2\
Region 3 has Color 1\
Region 4 has Color 2\
_Note_: One of the unsolved problems in computer science is devising a map that requires more than 4 colors.
