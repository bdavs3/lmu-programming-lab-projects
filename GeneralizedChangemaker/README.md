# Generalized Changemaker

### Description

This is a classic exercise in [dynamic programming](https://en.wikipedia.org/wiki/Dynamic_programming). The task is to implement a program that, given some denominations of coins (e.g. `int[] usaDenominations = new int[] { 25, 10, 5, 1 };`), returns change using the minimum amount of coins. The so-called "greedy" approach only works in certain coin systems. For example, using USA denominations, if you need to make change for 84 cents:

- Add quarters until you would go over 84 cents (sum = 75 cents, Q = 3)
- Add dimes until you would go over 84 cents (sum = 75 cents, D = 0)
- Add nickles until you would go over 84 cents (sum = 80 cents, N = 1)
- Add pennies until you would go over 84 cents (sum = 84 cents, P = 4)

Thus, the answer to the changemaking problem for 84 cents is { Q = 3, D = 0, N = 1, P = 4 }.
For arbitrary systems of coins, however, the greedy approach does not always return an optimal answer. For example, to make change for 6 cents with denominations { 4, 3, 1 }, the greedy approach returns { 1coin x 4, 0coins x 3, 2coins x 1 } when the optimal answer is { 0coins x 4, 2coins x 3, 0coins x 1 }. If you're interested, you can learn more about the [change-making problem](https://en.wikipedia.org/wiki/Change-making_problem).
_Note_: The `Tuple.java` class aids in processing some of the data invovled with change-making.
