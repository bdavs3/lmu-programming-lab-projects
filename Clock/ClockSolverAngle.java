//PLEASE NOTE
//There is an inherant issue with input involving this class,
//specifically instances where the user enters a rather small
//angle using a rather large timeslice.  In these cases,
//certain instances of that angle are skipped over.

public class ClockSolverAngle {
    private static double DEFAULT_TIME_SLICE = 60.0;
    private static long numberOfDesiredAngles;
    private static double desiredAngle;
    private static double timeSlice;

    //"do while" is used to ensure that 12:00 is checked before the clock starts ticking.
    //this constructor is similar to ClockSolverLine, except it also accounts for inverse angles,
    //which are not relevant for the 180 degree angles found in the ClockSolverLine class
    public ClockSolverAngle(Clock clock) {
        do {
            String angle = Double.toString(Math.round(clock.getAngle() * 10.0) / 10.0);
            String inverseAngle = Double.toString(Math.round((360.0 - clock.getAngle()) * 10.0) / 10.0);
            if (clock.formsDesiredAngle(this.desiredAngle)) {
                this.numberOfDesiredAngles++;
                System.out.println("Angle of " + angle + " occurs at " + clock.toString()); 
            } else if (clock.formsDesiredAngle(360 - this.desiredAngle)) {
                this.numberOfDesiredAngles++;
                System.out.println("Angle of " + inverseAngle + " occurs at " + clock.toString());
            }

            clock.tick(this.timeSlice);
        } while(!clock.getCompleteRotation());
        System.out.println("There are " + this.numberOfDesiredAngles + " " + this.desiredAngle + " degree angles.");
    }

    //catches and throws exceptions for all kinds of invalid input
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
        timeSlice  = args.length == 1 ? DEFAULT_TIME_SLICE : Double.parseDouble(args[1]);
        clock.setDegreesPerTick(timeSlice);
        new ClockSolverAngle(clock);
    }
}
