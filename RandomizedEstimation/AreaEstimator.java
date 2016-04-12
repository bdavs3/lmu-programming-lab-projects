public class AreaEstimator {
    private Shape[] shapes;
    private int length = 0, placeInArray = 0, interval;
    private Point lowerBound, upperBound;

    public AreaEstimator() {

    }

    public void checkInput(String[] a) {
        for (int i = 0; i < a.length; i += interval) {
            if (a[i].equals("circle")) {
                this.length++;
                this.interval = 4;
            } else if (a[i].equals("triangle")) {
                this.length++;
                this.interval = 7;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public void createArray(String[] a) {
        this.shapes = new Shape[this.length];

        for (int i = 0; i < a.length; i += interval) {
            if (a[i].equals("circle")) {
                try {
                    Point center = new Point(Double.parseDouble(a[i + 1]), Double.parseDouble(a[i + 2]));
                    double radius = Double.parseDouble(a[i + 3]);
                    this.shapes[placeInArray] = new Circle(center, radius);
                    this.placeInArray++;
                    this.interval = 4;
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
            } else {
                try {
                    Point p1 = new Point(Double.parseDouble(a[i + 1]), Double.parseDouble(a[i + 2]));
                    Point p2 = new Point(Double.parseDouble(a[i + 3]), Double.parseDouble(a[i + 4]));
                    Point p3 = new Point(Double.parseDouble(a[i + 5]), Double.parseDouble(a[i + 6]));
                    this.shapes[placeInArray] = new Triangle(p1, p2, p3);
                    this.placeInArray++;
                    this.interval = 7;
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
            }
        }

    }

    public void determineLowerBound() {
        double minX = this.shapes[0].minBound().getXCoord();
        double minY = this.shapes[0].minBound().getYCoord();
        for (Shape s : this.shapes) {
            Point pointOfConsideration = s.minBound();
            if (pointOfConsideration.getXCoord() < minX) {
                minX = pointOfConsideration.getXCoord();
            }

            if (pointOfConsideration.getYCoord() < minY) {
                minY = pointOfConsideration.getYCoord();
            }
        }

        this.lowerBound = new Point(minX, minY);
    }

    public void determineUpperBound() {
        double maxX = this.shapes[0].maxBound().getXCoord();
        double maxY = this.shapes[0].maxBound().getYCoord();
        for (Shape s : this.shapes) {
            Point pointOfConsideration = s.maxBound();
            if (pointOfConsideration.getXCoord() > maxX) {
                maxX = pointOfConsideration.getXCoord();
            }

            if (pointOfConsideration.getYCoord() > maxY) {
                maxY = pointOfConsideration.getYCoord();
            }
        }

        this.upperBound = new Point(maxX, maxY);
    }

    public void throwDarts() {
        System.out.println("start");
        for (int i = 0; i < 1000000; i++) {
            Point dartThrow = Point.random(this.lowerBound, this.upperBound);
            for (Shape s : this.shapes) {
                System.out.println(dartThrow.getXCoord() + " " + dartThrow.getYCoord() + (s.insideShape(dartThrow) ? " in" : " out"));
                System.out.println(i);
            }
        }
        System.out.println("end");
    }

    public static void main(String[] args) {
        AreaEstimator ae = new AreaEstimator();
        ae.checkInput(args);
        ae.createArray(args);
        ae.determineLowerBound();
        ae.determineUpperBound();
        ae.throwDarts();
    }
}
