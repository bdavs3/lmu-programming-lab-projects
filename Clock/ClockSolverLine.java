public class ClockSolverLine {
    private static double DEFAULT_TIME_SLICE = 60.0;
    private static double STRAIGHT_ANGLE = 180.0;
    private static long numberOfStraightAngles = 0;
    private static double timeSlice;

    //"do while" is used to maintain consistency with the ClockSolverAngle class
    //also, it is good practice to check for the angle at 12:00, even though we know that
    //it is impossible for a 180 degree angle to occur at that time
    public ClockSolverLine(Clock clock) {
        do {
            String angle = Double.toString(Math.round(clock.getAngle() * 10.0) / 10.0);
            if (clock.formsDesiredAngle(STRAIGHT_ANGLE)) {
                this.numberOfStraightAngles++;
                System.out.println("Angle of " + angle + " occurs at " + clock.toString()); 
            }

            clock.tick(this.timeSlice);
        } while(!clock.getCompleteRotation());
        System.out.println("There are " + this.numberOfStraightAngles + " straight angles.");
    }

    //catches and throws exceptions for all kinds of invalid input
    public static void main(String[] args) {
        Clock clock = new Clock();
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
        clock.setDegreesPerTick(timeSlice);
        new ClockSolverLine(clock);
    }
}
