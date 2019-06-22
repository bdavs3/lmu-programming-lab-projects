# Big Integer

### Description

This program is a self-made implementation of Java's [BigInteger](https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html) class, which allows for arithmetic with numbers that have extrememly large magnitudes (smaller than –2,147,483,648 or larger than 2,147,483,647).

Here are the supported methods:

- `abs`: gets the absolute value of a BigInteger (e.g. strips the prepended sign)
- `add`: adds two BigIntegers
- `subtract`: subtracts two BigIntegers
- `toString`: converts a BigInteger to a string
- `compareTo`: returns
  - –1 if the first BigInteger is less than the second
  - 0 if the BigInteger's are equal
  - 1 if the first BigInteger is greater than the second
- `equals`: determines whether a BigInteger is equal to a given object
- `valueOf`: converts longs in BigIntegers
- `multiply`: multiplies two BigIntegers
- `divide`: divides two BigIntegers
- `remainder`: modulo division with two BigIntegers

You'll also notice that there are files for handling a few more operations:

- `Factorial.java`: Calculate `n!`, where `n` is an integer, but the result is a BigInteger
- `Fibonacci.java`: Calculates the `n`th term of the Fibonacci sequence, where `n` is an integer, but the result is a BigInteger
- `GCD.java`: calculates the [greatest common divisor](https://en.wikibooks.org/wiki/Undergraduate_Mathematics/Greatest_common_divisor) of two BigIntegers
