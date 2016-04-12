public class Circle extends Shape {
    private int numberOfDarts = 0;
    private double radius;
    private Point center;

    public Circle(Point c, double r) {
        this.center = c;
        this.radius = r;
    }

    @Override
    public Point maxBound() {
        return new Point(center.getXCoord() + this.radius, center.getYCoord() + this.radius);
    }

    @Override
    public Point minBound() {
        return new Point(center.getXCoord() - this.radius, center.getYCoord() - this.radius);
    }

    @Override
    public boolean insideShape(Point p) {
        return center.distance(p) <= this.radius;
    }

    @Override
    public void incrementNumberOfDarts() {
        this.numberOfDarts++;
    }

    @Override
    public int getNumberOfDarts() {
        return this.numberOfDarts;
    }
}
