public class GCD {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        try {
            BigInteger first = BigInteger.valueOf(Long.valueOf(args[0]));
            BigInteger second = BigInteger.valueOf(Long.valueOf(args[1]));
            System.out.println(calculateGCD(first, second).toString());
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static BigInteger calculateGCD(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO)) {
            return b;
        } else if (b.equals(BigInteger.ZERO)) {
            return a;
        }

        return calculateGCD(b, a.remainder(b));
    }
}
