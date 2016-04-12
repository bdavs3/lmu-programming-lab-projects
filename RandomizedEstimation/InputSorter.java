public class InputSorter {
    private int length = 0;
    private final int PARAMETERS_FOR_CIRCLE = 4, PARAMETERS_FOR_TRIANGLE = 7;

    public InputSorter(String[] a) {
        int interval;

        if (a.length < PARAMETERS_FOR_CIRCLE) {                         //least amount of parameters possible with this program
            throw new IllegalArgumentException(AreaEstimator.errorMessage);
        }

        for (int i = 0; i < a.length; i += interval) {
            if (a[i].equals("circle")) {
                this.length++;
                interval = PARAMETERS_FOR_CIRCLE;
            } else if (a[i].equals("triangle")) {
                this.length++;
                interval = PARAMETERS_FOR_TRIANGLE;
            } else if (a[i].equals("polygon")) {
                this.length++;
                try {
                    interval = (2 * Integer.parseInt(a[i + 1])) + 2;
                } catch (Exception e) {
                    throw new IllegalArgumentException(AreaEstimator.errorMessage);
                }
            } else {
                throw new IllegalArgumentException(AreaEstimator.errorMessage);
            }
        }
    }

    //Returns the number of shapes declared in the input
    public int getLength() {
        return this.length;
    }
}
