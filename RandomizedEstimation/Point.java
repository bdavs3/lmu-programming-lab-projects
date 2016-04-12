public class Point {
    private double xCoord, yCoord;

    public Point(double x, double y) {
        this.xCoord = x;
        this.yCoord = y;
    }

    public String toString() {
        return ("(" + this.xCoord + "," + this.yCoord + ")");
    }

    public double setXCoord(double x) {
        this.xCoord = x;
    }

    public double getXCoord() {
        return this.xCoord;
    }

    public void setYCoord(double y) {
        this.yCoord = y;
    }

    public double getYCoord() {
        return this.yCoord;
    }

    public double distance(Point p) {
        return Math.sqrt(Math.pow(this.xCoord - p.xCoord, 2) + Math.pow(this.yCoord - p.yCoord, 2));
    }

    public static Vector vectorBetween(Point p1, Point p2) {
        return new Vector(p1.getXCoord() - p2.getXCoord(), p1.getYCoord() - p2.getYCoord());
    }

    public static Point random(Point min, Point max) {
        double randX = Math.random() * (max.getXCoord() - min.getXCoord()) + min.getXCoord();
        double randY = Math.random() * (max.getYCoord() - min.getYCoord()) + min.getYCoord();

        return new Point(randX, randY);
    }
}
