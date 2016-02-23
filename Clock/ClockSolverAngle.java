public class ClockSolverAngle {
    private static double DEFAULT_TIME_SLICE = 60.0;
    private static long numberOfDesiredAngles;
    private static double desiredAngle;
    private static double timeSlice;

    public ClockSolverAngle(Clock clock, double desiredAngle, double timeSlice) {
        do {
            String angle = Double.toString(Math.round(clock.getAngle() * 10.0) / 10.0);
            if (clock.formsDesiredAngle(desiredAngle) || clock.formsDesiredAngle(360 - desiredAngle)) {
                numberOfDesiredAngles++;
                System.out.println("Angle of " + angle + " occurs at " + clock.toString()); 
            }

            clock.tick(timeSlice);
        } while(!clock.getCompleteRotation());
        System.out.println("There are " + numberOfDesiredAngles + " " + desiredAngle + " degree angles.");
    }

    public static void main(String[] args) {
        try {
            if (args.length == 1) {
                Double.parseDouble(args[0]);
            } else if (args.length == 2) {
                Double.parseDouble(args[0]);
                Double.parseDouble(args[1]);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println("Illegal arguments supplied.");
            return;
        }
        if (args.length == 1) {
            if (args.length > 2 || Double.parseDouble(args[0]) < 0 || Double.parseDouble(args[0]) >= 360.0) {
                throw new IllegalArgumentException();
            }
        } else if (args.length == 2) {
            if (args.length > 2 || Double.parseDouble(args[0]) < 0 || Double.parseDouble(args[0]) >= 360.0 || Double.parseDouble(args[1]) <= 0 || Double.parseDouble(args[1]) >= 1800.0) {
                throw new IllegalArgumentException();
            }
        }
        desiredAngle = Double.parseDouble(args[0]);
        timeSlice  = args.length == 1 ? DEFAULT_TIME_SLICE : Double.parseDouble(args[1]);
        clock.degreesPerTick(timeSlice);
        new ClockSolverAngle(new Clock(), desiredAngle, timeSlice);
    }
}
