public class GeneralizedChangemaker {

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        try {
            int amount = Integer.parseInt(args[0]);
            if (amount < 0) {
                System.out.println("Change cannot be made for negative amounts.");
                System.out.println();
                printUsage();
                return;
            }

            String[] denominationStrings = args[1].split(",");
            int[] denominations = new int[denominationStrings.length];

            for (int i = 0; i < denominations.length; i++) {
                denominations[i] = Integer.parseInt(denominationStrings[i]);
                if (denominations[i] <= 0) {
                    System.out.println("Denominations must all be greater than zero.");
                    System.out.println();
                    printUsage();
                    return;
                }

                for (int j = 0; j < i; j++) {
                    if (denominations[j] == denominations[i]) {
                        System.out.println("Duplicate denominations are not allowed.");
                        System.out.println();
                        printUsage();
                        return;
                    }
                }
            }

            Tuple change = makeChangeWithDynamicProgramming(denominations, amount);
            if (change.isImpossible()) {
                System.out.println("It is impossible to make " + amount + " cents with those denominations.");
            } else {
                int coinTotal = change.total();
                System.out.println(amount + " cents can be made with " + coinTotal + " coin" +
                        getSimplePluralSuffix(coinTotal) + " as follows:");

                for (int i = 0; i < denominations.length; i++) {
                    int coinCount = change.getElement(i);
                    System.out.println("- "  + coinCount + " " + denominations[i] + "-cent coin" +
                            getSimplePluralSuffix(coinCount));
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Denominations and amount must all be integers.");
            System.out.println();
            printUsage();
        }
    }

    public static Tuple makeChangeWithDynamicProgramming(int[] denominations, int amount) {
        final int FIRST_ROW = 0, LAST_ROW = denominations.length, FIRST_COLUMN = 0, LAST_COLUMN = amount, ONE_COIN = 1;

        Tuple[][] result = new Tuple[denominations.length][amount + 1];

        for (int row = FIRST_ROW; row < LAST_ROW; row++) {
            result[row][FIRST_COLUMN] = new Tuple(denominations.length);

            for (int column = FIRST_COLUMN + 1; column <= LAST_COLUMN; column++) {
                int denomination = denominations[row];
                if (column < denomination) {
                    result[row][column] = row > FIRST_ROW ? result[row - 1][column] : Tuple.IMPOSSIBLE;
                } else {
                    Tuple currentTuple = new Tuple(denominations.length);
                    currentTuple.setElement(row, ONE_COIN);
                    result[row][column] = currentTuple.add(result[row][column - denomination]);
                    if (row > FIRST_ROW && result[row - 1][column].total() < result[row][column].total()) {
                        result[row][column] = result[row - 1][column];
                    }
                }
            }
        }
        
        return result[denominations.length - 1][amount];
    }

    private static void printUsage() {
        System.out.println("Usage: java GeneralizedChangemaker <denominations> <amount>");
        System.out.println("  - <denominations> is a comma-separated list of denominations (no spaces)");
        System.out.println("  - <amount> is the amount for which to make change");
    }

    private static String getSimplePluralSuffix(int count) {
        return count == 1 ? "" : "s";
    }

}
