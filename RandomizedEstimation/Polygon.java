public class Polygon extends Shape {
    private int numberOfDarts = 0;
    private static double EPS = 10E-20;
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
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].getYCoord() > points[i + 1].getYCoord()) {
                Point temp = points[i];
                points[i] = points[i + 1];
                points[i + 1] = temp;
            }

            if (p.getYCoord() == points[i].getYCoord() || p.getYCoord() == points[i + 1].getYCoord()) {
                p.setYCoord(p.getYCoord() + EPS);
            }

            if (p.getYCoord() > points[i + 1].getYCoord() || p.getYCoord() < points[i].getYCoord() || p.getXCoord() > Math.max(points[i].getXCoord(), points[i + 1].getXCoord())) {
                continue;
            }

            if (p.getXCoord() < Math.min(points[i].getXCoord(), points[i + 1].getXCoord())) {
                inside = !inside;
                continue;
            }

            try {
                mEdge = (points[i + 1].getYCoord() - points[i].getYCoord()) / (points[i + 1].getXCoord() - points[i].getXCoord());
            } catch (Exception e) {
                throw new ArithmeticException();
            }

            try {
                mPoint = (p.getYCoord() - points[i].getYCoord()) / (p.getXCoord() - points[i].getXCoord());
            } catch (Exception e) {
                throw new ArithmeticException();
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
