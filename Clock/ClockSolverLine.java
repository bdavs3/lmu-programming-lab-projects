public class ClockSolverLine {
    private static double DEFAULT_TIME_SLICE = 60.0;
    private static double DEGREES_IN_AN_HOUR = 30;
    private static double DEGREES_IN_A_MIN = 6;
    private static double MIN_HAND_DEGREES_PER_SEC = 0.1;
    private static double HOUR_HAND_DEGREES_PER_MIN = 0.5;
    private static double HOUR_HAND_DEGREES_PER_SEC = 0.5 / 60;
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

        /*new ClockSolverLine(clock, 30.0);
        System.out.println(clock.getHours());
        System.out.println(clock.getMinutes());
        System.out.println(clock.getSeconds());
        System.out.println(getAngle(clock));
        
        new ClockSolverLine(clock, 30.0);
        System.out.println(clock.getHours());
        System.out.println(clock.getMinutes());
        System.out.println(clock.getSeconds());
        System.out.println(getAngle(clock));
        
        new ClockSolverLine(clock, 30.0);
        System.out.println(clock.getHours());
        System.out.println(clock.getMinutes());
        System.out.println(clock.getSeconds());
        System.out.println(getAngle(clock));*/
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
            new ClockSolverLine(clock, slice / 2);
            double firstAngleInRange = getAngle(clock);

            new ClockSolverLine(clock, slice / 2);
            String actualAngle = Double.toString(getAngle(clock));
            long hours = clock.getHours() == 0 ? 12 : clock.getHours();
            String minutes = clock.getMinutes() < 10 ? "0" + Long.toString(clock.getMinutes()) : Long.toString(clock.getMinutes());
            String seconds = Double.toString(clock.getSeconds());

            new ClockSolverLine(clock, slice / 2);
            double secondAngleInRange = getAngle(clock);
            
            if ((firstAngleInRange <= 180.0 && secondAngleInRange >= 180.0) || (firstAngleInRange >= 180.0 && secondAngleInRange <= 180.0)) {
                numberOfStraightAngles++;
                System.out.println(firstAngleInRange);
                System.out.println(actualAngle);
                System.out.println(secondAngleInRange);
                System.out.println("Angle of " + actualAngle + " occurs at " + Long.toString(hours) + ":" + minutes + ":" + seconds); 
            }
        }
    }
}
