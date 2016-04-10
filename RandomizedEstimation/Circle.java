public class Circle extends Shape {
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
}
