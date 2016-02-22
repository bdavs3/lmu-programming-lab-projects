public class ClockSolverAngle {
    private static double DEFAULT_TIME_SLICE = 60.0;
    private static long numberOfDesiredAngles;
    private static double desiredAngle;
    private static double timeSlice;

    public ClockSolverAngle(Clock clock, double timeSlice) {
        clock.tick(timeSlice);
    }

    public static void main(String[] args) {
        Clock clock = new Clock();
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
        timeSlice  = args.length == 1 ? DEFAULT_TIME_SLICE : Double.parseDouble(args[0]);

        while(!clock.getCompleteRotation()) {
            new ClockSolverAngle(clock, timeSlice);
            String angle = Double.toString(Math.round(ClockSolverLine.getAngle(clock) * 10.0) / 10.0);
            long hours = clock.getHours() == 0 ? 12 : clock.getHours();
            String minutes = clock.getMinutes() < 10 ? "0" + Long.toString(clock.getMinutes()) : Long.toString(clock.getMinutes());
            String seconds = Double.toString(Math.round(clock.getSeconds() * 10.0) / 10.0);
            
            if (Math.abs(desiredAngle - ClockSolverLine.getAngle(clock)) < ClockSolverLine.changePerTick(timeSlice) && clock.getHours() < 12) {
                numberOfDesiredAngles++;
                new ClockSolverLine(clock, timeSlice);
                System.out.println("Angle of " + angle + " occurs at " + Long.toString(hours) + ":" + minutes + ":" + seconds + "."); 
            }
        }
        System.out.println("There are " + numberOfDesiredAngles + " " + desiredAngle + " degree angles.");
    }
}
