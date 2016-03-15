public class Fibonacci {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        try {
            int term = Integer.valueOf(args[0]);
            System.out.println(calculateFibonacci(term).toString());
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static BigInteger calculateFibonacci(int term) {
        BigInteger currentVal = BigInteger.ZERO;
        BigInteger previousVal = BigInteger.ZERO;
        for (int i = 0; i < term; i++) {
            if (currentVal.equals(BigInteger.ZERO)) {
                currentVal = currentVal.add(BigInteger.ONE);
            } else {
                BigInteger temp = new BigInteger(currentVal.toString());
                currentVal = previousVal.add(currentVal);
                previousVal = new BigInteger(temp.toString());
            }
        }
        return currentVal;
    }
}
