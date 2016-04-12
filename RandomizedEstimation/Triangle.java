public class Triangle extends Shape {
    private Point a, b, c;
    private int numberOfDarts = 0;

    public Triangle(Point p1, Point p2, Point p3) {
        this.a = p1;
        this.b = p2;
        this.c = p3;
    }

    //Used in insideShape()
    public Point centroid() {
        return new Point((this.a.getXCoord() + this.b.getXCoord() + this.c.getXCoord()) / 3, (this.a.getYCoord() + this.b.getYCoord() + this.c.getYCoord()) / 3);
    }

    public boolean sameSide(Point p1, Point p2, Point a, Point b) {
        Vector a_b = Point.vectorBetween(a, b);
        Vector p1_a = Point.vectorBetween(p1, a);
        Vector p2_a = Point.vectorBetween(p2, a);
        Vector cp1 = Vector.crossProduct(a_b, p1_a);
        Vector cp2 = Vector.crossProduct(a_b, p2_a);
        if (Vector.dotProduct(cp1, cp2) >= 0) {
            return true;
        } else {
            return false;
        }
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

    //To determine whether a point is inside the triangle, the method tests the relationship between the cross
    //product of the point in question and the cross product of a point known to be inside the triangle e.g. the centroid
    @Override
    public boolean insideShape(Point p) {
        if (sameSide(p, this.centroid(), this.a, this.b) && sameSide(p, this.centroid(), this.b, this.c) && sameSide(p, this.centroid(), this.a, this.c)) {
            return true;
        } else {
            return false;
        }
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
