public class ClockSolverLine {
    private static double DEFAULT_TIME_SLICE = 60.0;
    private static double STRAIGHT_ANGLE = 180.0;
    private static long numberOfStraightAngles = 0;
    private static double timeSlice;

    public ClockSolverLine(Clock clock, double timeSlice) {
        do {
            String angle = Double.toString(Math.round(clock.getAngle() * 10.0) / 10.0);
            if (clock.formsDesiredAngle(STRAIGHT_ANGLE)) {
                numberOfStraightAngles++;
                System.out.println("Angle of " + angle + " occurs at " + clock.toString()); 
            }

            clock.tick(timeSlice);
        } while(!clock.getCompleteRotation());
        System.out.println("There are " + numberOfStraightAngles + " straight angles.");
    }

    public static void main(String[] args) {
        try {
            if (args.length != 0) {
                Double.parseDouble(args[0]);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Illegal arguments supplied.");
            return;
        }
        if (args.length != 0) {
            if (args.length > 1 || Double.parseDouble(args[0]) <= 0 || Double.parseDouble(args[0]) >= 1800.0) {
                throw new IllegalArgumentException();
            }
        }
        timeSlice = args.length == 0 ? DEFAULT_TIME_SLICE : Double.parseDouble(args[0]);
        clock.degreesPerTick(timeSlice);
        new ClockSolverLine(new Clock(), timeSlice);
    }
}
