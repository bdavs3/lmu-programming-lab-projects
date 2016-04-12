public class AreaEstimator {
    private String errorMessage = "For a circle, enter 'circle (centerX) (centerY) (radius)' and for a triangle, enter 'triangle (p1x) (p1y) (p2x) (p2y) (p3x) (p3y)'";
    private Shape[] shapes;
    private double[] shapeAreas;
    private final int ONE_MILLION = 1000000;
    private int length = 0, placeInArray = 0, interval;
    private Point lowerBound, upperBound;

    public AreaEstimator() {

    }

    public void checkInput(String[] a) {
        if (a.length < 4) {
            throw new IllegalArgumentException(this.errorMessage);
        }

        for (int i = 0; i < a.length; i += interval) {
            if (a[i].equals("circle")) {
                this.length++;
                this.interval = 4;
            } else if (a[i].equals("triangle")) {
                this.length++;
                this.interval = 7;
            } else {
                throw new IllegalArgumentException(this.errorMessage);
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
                    throw new IllegalArgumentException(this.errorMessage);
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
                    throw new IllegalArgumentException(this.errorMessage);
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

    public double areaOfBound() {
        double base = this.upperBound.getXCoord() - this.lowerBound.getXCoord();
        double height = this.upperBound.getYCoord() - this.lowerBound.getYCoord();

        return base * height;
    }

    public void throwDarts() {
        for (int i = 0; i < ONE_MILLION; i++) {
            Point dartThrow = Point.random(this.lowerBound, this.upperBound);
            for (Shape s : this.shapes) {
                if (s.insideShape(dartThrow)) {
                    s.incrementNumberOfDarts();
                }
            }
        }
    }

    public void calculateShapeAreas() {
        shapeAreas = new double[this.length];
        int i = 0;
        for (Shape s : this.shapes) {
            shapeAreas[i] = (s.getNumberOfDarts() / (double) ONE_MILLION) * this.areaOfBound();
            System.out.println(shapeAreas[i]);
            i++;
        }
    }

    public void output() {
        for (int i = 0; i < ONE_MILLION; i++) {
            Point dartThrow = Point.random(this.lowerBound, this.upperBound);
            System.out.println("start");
            for (Shape s : this.shapes) {
                inner:
                if (s.insideShape(dartThrow)) {
                    System.out.println(dartThrow.getXCoord() + " " + dartThrow.getYCoord() + " in");
                    break inner;
                } else {
                    System.out.println(dartThrow.getXCoord() + " " + dartThrow.getYCoord() + " out");
                }
            }
            System.out.println("end");
        }
    }

    public static void main(String[] args) {
        AreaEstimator ae = new AreaEstimator();
        ae.checkInput(args);
        ae.createArray(args);
        ae.determineLowerBound();
        ae.determineUpperBound();
        ae.throwDarts();
        ae.calculateShapeAreas();
        ae.output();
    }
}
