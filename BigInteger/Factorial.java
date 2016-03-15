public class Factorial {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        try {
            int n = Integer.valueOf(args[0]);
            System.out.println(calculateFactorial(n).toString());
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static BigInteger calculateFactorial(int n) {
        BigInteger result = n == 0 ? BigInteger.ONE : BigInteger.valueOf(n);
        for (int i = n; i > 1; i--) {
            result = result.multiply(new BigInteger("0").valueOf(i - 1));
        }
        return result;
    }
}
