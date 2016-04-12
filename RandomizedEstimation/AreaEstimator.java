public class AreaEstimator {
    private String errorMessage = "For a circle, enter 'circle (centerX) (centerY) (radius)' and for a triangle, enter 'triangle (p1x) (p1y) (p2x) (p2y) (p3x) (p3y)'";
    private Shape[] shapes;
    private double[] shapeAreas;
    private int dartsInIntersection = 0, dartsInUnion = 0, dartsInNonIntersection = 0;
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

    public void displayBoundingBox() {
        System.out.println("Bounding box:");
        System.out.println("- Upper-right corner: (" + upperBound.getXCoord() + ", " + upperBound.getYCoord() + ")");
        System.out.println("- Lower-left corner: (" + lowerBound.getXCoord() + ", " + lowerBound.getYCoord() + ")");
    }

    public void throwDarts() {
        for (int i = 0; i < ONE_MILLION; i++) {
            Point dartThrow = Point.random(this.lowerBound, this.upperBound)
;            boolean addToIntersection = true;
            boolean addToUnion = false;
            boolean addToNonIntersection = false;
            int count = 0;
            for (Shape s : this.shapes) {
                if (s.insideShape(dartThrow)) {
                    s.incrementNumberOfDarts();
                    addToUnion = true;
                    if (count == 0) {
                        addToNonIntersection = true;
                        count++;
                    } else {
                        addToNonIntersection = false;
                    }
                } else {
                    addToIntersection = false;
                }
            }
            this.dartsInIntersection += addToIntersection ? 1 : 0;
            this.dartsInUnion += addToUnion ? 1 : 0;
            this.dartsInNonIntersection += addToNonIntersection ? 1 : 0;
        }
    }

    public void displayShapeAreas() {
        shapeAreas = new double[this.length];
        int i = 0;
        for (Shape s : this.shapes) {
            shapeAreas[i] = this.areaWithDarts(s.getNumberOfDarts());
            System.out.println("Area of shape " + (i + 1) + ": " + shapeAreas[i]);
            i++;
        }

        double areaOfIntersection = this.areaWithDarts(this.dartsInIntersection);
        System.out.println("Area of intersection: " + areaOfIntersection);

        double areaOfUnion = this.areaWithDarts(this.dartsInUnion);
        System.out.println("Area of union: " + areaOfUnion);

        double areaOfNonIntersection = this.areaWithDarts(this.dartsInNonIntersection);
        System.out.println("Area of non-intersection: " + areaOfNonIntersection);
    }

    public void output() {
        System.out.println("start");
        for (int i = 0; i < ONE_MILLION; i++) {
            Point dartThrow = Point.random(this.lowerBound, this.upperBound);
            boolean inShape = false;
            for (Shape s : this.shapes) {
                if (s.insideShape(dartThrow)) {
                    inShape = true;
                }
            }
            System.out.println(dartThrow.getXCoord() + " " + dartThrow.getYCoord() + (inShape ? " in" : " out"));
        }
        System.out.println("end");
    }

    public double areaOfBound() {
        double base = this.upperBound.getXCoord() - this.lowerBound.getXCoord();
        double height = this.upperBound.getYCoord() - this.lowerBound.getYCoord();

        return base * height;
    }

    public double areaWithDarts(int numOfDarts) {
        return (numOfDarts / (double) ONE_MILLION) * this.areaOfBound();
    }

    public static void main(String[] args) {
        AreaEstimator ae = new AreaEstimator();
        ae.checkInput(args);
        ae.createArray(args);
        ae.determineLowerBound();
        ae.determineUpperBound();
        ae.displayBoundingBox();
        ae.throwDarts();
        ae.displayShapeAreas();
        ae.output();
    }
}
