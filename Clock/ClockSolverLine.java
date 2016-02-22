public class ClockSolverLine {
    private static double DEFAULT_TIME_SLICE = 60.0;
    private static double DEGREES_IN_AN_HOUR = 30;
    private static double DEGREES_IN_A_MIN = 6;
    private static double MIN_HAND_DEGREES_PER_SEC = 0.1;
    private static double HOUR_HAND_DEGREES_PER_MIN = 0.5;
    private static double HOUR_HAND_DEGREES_PER_SEC = 0.5 / 60;
    private static double STRAIGHT_ANGLE = 180.0;
    private static long numberOfStraightAngles = 0;
    private static double timeSlice;

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

        double hourToMinAngle = Math.abs(hourAngle - minuteAngle);
        double minToHourAngle = Math.abs(minuteAngle - hourAngle);
        return hourToMinAngle < minToHourAngle ? hourToMinAngle : minToHourAngle;
    }

    public static double changePerTick(double timeSlice) {
        return Math.abs(HOUR_HAND_DEGREES_PER_SEC * timeSlice - MIN_HAND_DEGREES_PER_SEC * timeSlice);
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
        timeSlice  = args.length == 0 ? DEFAULT_TIME_SLICE : Double.parseDouble(args[0]);

        while(!clock.getCompleteRotation()) {
            new ClockSolverLine(clock, timeSlice);
            String angle = Double.toString(getAngle(clock));
            long hours = clock.getHours() == 0 ? 12 : clock.getHours();
            String minutes = clock.getMinutes() < 10 ? "0" + Long.toString(clock.getMinutes()) : Long.toString(clock.getMinutes());
            String seconds = Double.toString(clock.getSeconds());
            
            if (Math.abs(STRAIGHT_ANGLE - getAngle(clock)) < changePerTick(timeSlice)) {
                numberOfStraightAngles++;
                new ClockSolverLine(clock, timeSlice);
                System.out.println("Angle of " + angle + " occurs at " + Long.toString(hours) + ":" + minutes + ":" + seconds + "."); 
            }
        }
        System.out.println("There are " + numberOfStraightAngles + " straight angles.");
    }
}
