public class Triangle extends Shape {
    private Point a, b, c;

    public Triangle(Point p1, Point p2, Point p3) {
        this.a = p1;
        this.b = p2;
        this.c = p3;
    }

    public Point centroid() {
        return new Point((this.getA().getXCoord() + this.getB().getXCoord() + this.getC().getXCoord()) / 3, (this.getA().getYCoord() + this.getB().getYCoord() + this.getC().getYCoord()) / 3);
    }

    public boolean sameSide(Point p1, Point p2, Point a, Point b) {
        Vector ab = Point.vectorBetween(a, b);
        Vector p1a = Point.vectorBetween(p1, a);
        Vector p2a = Point.vectorBetween(p2, a);
        Vector cp1 = Vector.crossProduct(ab, p1a);
        Vector cp2 = Vector.crossProduct(ab, p2a);
        if (Vector.dotProduct(cp1, cp2) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public Point getA() {
        return this.a;
    }

    public Point getB() {
        return this.b;
    }

    public Point getC() {
        return this.c;
    }

    @Override
    public Point maxBound() {
        double maxX = Math.max(Math.max(this.a.getXCoord(), this.b.getXCoord()), this.c.getXCoord());
        double maxY = Math.max(Math.max(this.a.getYCoord(), this.b.getYCoord()), this.c.getYCoord());
        return new Point(maxX, maxY);
    }

    @Override 
    public Point minBound() {
        double minX = Math.min(Math.min(this.a.getXCoord(), this.b.getXCoord()), this.c.getXCoord());
        double minY = Math.min(Math.min(this.a.getYCoord(), this.b.getYCoord()), this.c.getYCoord());
        return new Point(minX, minY);
    }

    @Override
    public boolean insideShape(Point p) {
        if (sameSide(p, centroid(), this.a, this.b) && sameSide(p, centroid(), this.b, this.c) && sameSide(p, centroid(), this.a, this.c)) {
            return true;
        } else {
            return false;
        }
    }
}
