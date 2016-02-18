public class Clock {
    private static long INIT_TIME_VALUE = 0;
    private static long SEC_IN_A_MIN = 60;
    private static long MIN_IN_AN_HOUR = 60;
    private static long HOURS_ON_A_CLOCK = 12;
    private long minutes, hours;
    private double seconds;
    private boolean completeRotation = false;

    public Clock() {
        this.hours = INIT_TIME_VALUE;
        this.minutes = INIT_TIME_VALUE;
        this.seconds = INIT_TIME_VALUE;
    }

    public void tick(double slice) {
        this.seconds += slice;
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

    public String toString() {
        return ("Minutes: " + this.minutes + "\n" +
                "Hours: " + this.hours + "\n" +
                "Seconds: " + this.seconds + "\n");
    }

    public long getMinutes() {
        return this.minutes;
    }

    public long getHours() {
        return this.hours;
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
