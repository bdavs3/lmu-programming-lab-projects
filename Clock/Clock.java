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

    public Clock() {
        this.hours = INIT_TIME_VALUE;
        this.minutes = INIT_TIME_VALUE;
        this.seconds = INIT_TIME_VALUE;
    }

    public void tick(double slice) {
        this.seconds += slice;
        angle += this.degreesEachTick;
        if (angle >= 360) {
            angle -= 360;
        }

        if (this.seconds >= SEC_IN_A_MIN) {
            setMinutes((long) (this.minutes + Math.floor(this.seconds / SEC_IN_A_MIN)));
            setSeconds(this.seconds % SEC_IN_A_MIN);
        }
        if (this.minutes >= MIN_IN_AN_HOUR) {
            setHours(hours + 1);
            setMinutes(this.minutes % MIN_IN_AN_HOUR);
        }
        if (this.hours >= HOURS_ON_A_CLOCK) {
            completeRotation = true;
        }
    }

    public void degreesPerTick(double slice) {
        this.degreesEachTick = Math.abs(HOUR_HAND_DEGREES_PER_MIN * slice - MIN_HAND_DEGREES_PER_SEC * slice);
    }

    public double getDegreesEachTick() {
        return this.degreesEachTick;
    }

    public double getAngle() {
        return this.angle;
    }

    public boolean formsDesiredAngle(double angle) {
        return (Math.abs(angle - this.getAngle()) < this.degreesEachTick / 2 && this.hours < 12);
    }

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
