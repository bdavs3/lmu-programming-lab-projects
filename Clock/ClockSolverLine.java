public class ClockSolverLine {
    private static double DEFAULT_TIME_SLICE = 60.0;
    private static long DEGREES_IN_AN_HOUR = 360 / 12;
    private static long DEGREES_IN_A_MIN = 360 / 60;
    private static double MIN_HAND_DEGREES_PER_SEC = (360 / 60 / 60);
    private static double HOUR_HAND_DEGREES_PER_MIN = (360 / 12 / 60);
    private static double HOUR_HAND_DEGREES_PER_SEC = (360 / 12 / 60 / 60);
    private static long numberOfStraightAngles = 0;

    public ClockSolverLine(Clock clock, double timeSlice) {
        clock.tick(timeSlice);
    }

    public static double getAngle(Clock clock) {
        double hourAngle = 0;
        double minuteAngle = 0;

        double hours = clock.getHours();
        double min = clock.getMinutes();
        double sec = clock.getSeconds();
        hourAngle += hours * DEGREES_IN_AN_HOUR;
        hourAngle += min * HOUR_HAND_DEGREES_PER_MIN;
        hourAngle += sec * HOUR_HAND_DEGREES_PER_SEC;
        minuteAngle += min * DEGREES_IN_A_MIN;
        minuteAngle += sec * MIN_HAND_DEGREES_PER_SEC;

        return Math.abs(minuteAngle - hourAngle);
    }

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
        double slice  = args.length == 0 ? DEFAULT_TIME_SLICE : Double.parseDouble(args[0]);

        while(!clock.getCompleteRotation()) {
            ClockSolverLine clockSolverLine = new ClockSolverLine(clock, slice);
            
            if (getAngle(clock) == 180.0) {
                numberOfStraightAngles++;
                System.out.println("Angle of 180 deg " + " occurs at " + clock.getHours() + ":" + clock.getMinutes() + ":" + clock.getSeconds()); 
            } else {
                continue;
            }
        }
    }
}
