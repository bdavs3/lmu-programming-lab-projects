# 2016 Spring Programming Lab Projects

### Description

This repository contains various projects for LMU CMSI 186: Programming Lab.

**Update June 21, 2019** – Revisiting this repository after a few years, as I am organizing some of my old projects. I wish I put the assignment descriptions in each folder at the time I was taking this class. I can't track down the original descriptions, so all that is left is the original code.

### How To Run

_Note_: You'll need a working version of Java on your machine to run these programs.

Clone the repository using

```sh
git clone git@github.com:bdavs3/lmu-programming-lab-projects.git
```

From there, you can `cd` into one of the assignment folders, for example:

```sh
cd BigInteger
```

For folders that have a test harness, you can look at the test code to see some example input/outputs for the main program (and whether they succeed). Compile the test harness like so:

```sh
javac BigIntegerTestHarness.java
```

at which point you can run the harness:

```sh
java BigIntegerTestHarness
```

For folders that don't have a test harness, you'll have to try the program out yourself. The READMEs in each folder will show you what the program does and what kind of args you need to supply. For example:

```sh
javac DateDistance.java
java DateDistance 10 3 1996 6 21 2019
8296 // the amount of days between Oct. 3, 1996 and Jun. 21, 2019
```
