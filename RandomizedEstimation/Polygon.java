public class Polygon extends Shape {
    private int numberOfDarts = 0;
    private Point[] points;

    public Polygon(Point[] p) {
        this.points = p;
    }

    @Override
    public Point maxBound() {
        double maxX = points[0].getXCoord();
        double maxY = points[0].getYCoord();
        for (Point p : this.points) {
            if (p.getXCoord() > maxX) {
                maxX = p.getXCoord();
            } 
            if (p.getYCoord() > maxY) {
                maxY = p.getYCoord();
            }
        }

        return new Point(maxX, maxY);
    }

    @Override
    public Point minBound() {
        double minX = points[0].getXCoord();
        double minY = points[0].getYCoord();
        for (Point p : this.points) {
            if (p.getXCoord() < minX) {
                minX = p.getXCoord();
            }
            if (p.getYCoord() < minY) {
                minY = p.getYCoord();
            }
        }

        return new Point(minX, minY);
    }

    public boolean insideShape(Point p) {
        boolean inside = false;

        double mEdge;
        double mPoint;
        for (int i = 0; i < points.length; i++) {
            Point a = points[i];
            Point b;
            if (i < points.length - 1) {
                b = points[i + 1];
            } else {
                b = points[0];
            }

            if (a.getYCoord() > b.getYCoord()) {
                Point temp = a;
                a = b;
                b = temp;
            }

            if (p.getYCoord() < a.getYCoord() || p.getYCoord() > b.getYCoord() || p.getXCoord() > Math.max(a.getXCoord(), b.getXCoord())) {
                continue;
            }

            if (p.getXCoord() < Math.min(a.getXCoord(), b.getXCoord())) {
                inside = !inside;
                continue;
            }

            try {
                mEdge = (b.getYCoord() - a.getYCoord()) / (b.getXCoord() - a.getXCoord());
            } catch (Exception e) {
                mEdge = Double.MAX_VALUE;
            }

            try {
                mPoint = (p.getYCoord() - a.getYCoord()) / (p.getXCoord() - a.getXCoord());
            } catch (Exception e) {
                mPoint = Double.MAX_VALUE;
            }

            if (mPoint >= mEdge) {
                inside = !inside;
                continue;
            }
        }

        return inside;
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
