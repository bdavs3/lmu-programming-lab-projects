public class Clock {
    private static long INIT_TIME_VALUE = 0;
    private static long SEC_IN_A_MIN = 60;
    private static long MIN_IN_AN_HOUR = 60;
    private static long HOURS_ON_A_CLOCK = 12;
    private static double MIN_HAND_DEGREES_PER_SEC = 0.1;
    private static double HOUR_HAND_DEGREES_PER_MIN = 0.5 / 60;
    private long minutes, hours;
    private double seconds, angle, degreesEachTick;
    private boolean completeRotation = false;

    //creates a clock with hours, minutes, and seconds all initializing to 0
    public Clock() {
        this.hours = INIT_TIME_VALUE;
        this.minutes = INIT_TIME_VALUE;
        this.seconds = INIT_TIME_VALUE;
    }

    //for each tick, the hours, min, and sec hands all have their values updated,
    //and the angle between the min and hour hand is calculated
    public void tick(double slice) {
        this.seconds += slice;
        this.angle += this.degreesEachTick;
        if (this.angle >= 360) {
            this.angle -= 360;
        }

        if (this.seconds >= SEC_IN_A_MIN) {
            setMinutes((long) (this.minutes + Math.floor(this.seconds / SEC_IN_A_MIN)));
            setSeconds(this.seconds % SEC_IN_A_MIN);
        }
        if (this.minutes >= MIN_IN_AN_HOUR) {
            setHours(this.hours + 1);
            setMinutes(this.minutes % MIN_IN_AN_HOUR);
        }
        if (this.hours >= HOURS_ON_A_CLOCK) {
            this.completeRotation = true;
        }
    }

    //uses a slice (from ClockSolverLine or ClockSolverAngle class) to determine
    //how many degrees the angle between the hands increases for each slice
    public void setDegreesPerTick(double slice) {
        this.degreesEachTick = Math.abs(HOUR_HAND_DEGREES_PER_MIN * slice - MIN_HAND_DEGREES_PER_SEC * slice);
    }

    public double getDegreesPerTick() {
        return this.degreesEachTick;
    }

    public double getAngle() {
        return this.angle;
    }

    //most important method in the project
    public boolean formsDesiredAngle(double angle) {
        return (Math.abs(angle - this.angle) < this.degreesEachTick / 2 && this.hours < 12);
    }

    //string representation of the clock in the form HH:MM:SS.S
    //...compensates for when seconds are rounded to 60.0, which is visually confusing
    public String toString() {
        long hours = this.hours == 0 ? 12 : this.hours;
        String hoursStr = Long.toString(hours);
        String minutesStr = this.minutes < 10 ? "0" + Long.toString(this.minutes) : Long.toString(this.minutes);
        String secondsStr = this.seconds <= 59.9 ? Double.toString(Math.round(this.seconds * 10.0) / 10.0) : "59.9";
        return (hoursStr + ":" + minutesStr + ":" + secondsStr + ".");
    }

    public long getHours() {
        return this.hours;
    }

    public long getMinutes() {
        return this.minutes;
    }

    public double getSeconds() {
        return this.seconds;
    }

    //determines whether the clock has gone around once from 12:00 till 12:00
    public boolean getCompleteRotation() {
        return this.completeRotation;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }
}
