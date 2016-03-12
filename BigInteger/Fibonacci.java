public class Fibonacci {
    public static void main(String[] args) {
        if (args.length > 1) {
            throw new IllegalArgumentException();
        }

        try {
            String.valueOf(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }

        int term = Integer.valueOf(args[0]);
        calculateFibonacci(term);
    }

    public static void calculateFibonacci(int n) {
        BigInteger currentVal = BigInteger.ZERO;
        BigInteger previousVal = BigInteger.ZERO;
        for (int i = 0; i < n; i++) {
            if (currentVal.equals(BigInteger.ZERO)) {
                currentVal = currentVal.add(BigInteger.ONE);
            } else {
                BigInteger temp = new BigInteger(currentVal.toString());
                currentVal = previousVal.add(currentVal);
                previousVal = new BigInteger(temp.toString());
            }
        }
        System.out.println(currentVal.toString());
    }
}
