public class AreaEstimator {
    public static String errorMessage = "For a circle, enter 'circle (centerX) (centerY) (radius)', for a triangle, enter 'triangle (p1x) (p1y) (p2x) (p2y) (p3x) (p3y)', and for a convex polygon, enter 'polygon (number_of_sides) (p1x) (p1y) (p2x) (p2y)...' in clockwise order";
    private Shape[] shapes;
    private int dartsInIntersection = 0, dartsInUnion = 0, dartsInNonIntersection = 0;
    private final int ONE_MILLION = 1000000, PARAMETERS_FOR_CIRCLE = 4, PARAMETERS_FOR_TRIANGLE = 7;

    //Sorts the shapes neatly into an array with a length that has been determined by checkInputAndLength()
    //...Also checks for illegal arguments involving the parameter / characteristics of the different shapes
    public AreaEstimator(String[] a, int l) {
        this.shapes = new Shape[l];
        int interval;
        int placeInArray = 0;

        for (int i = 0; i < a.length; i += interval) {
            if (a[i].equals("circle")) {
                try {
                    Point center = new Point(Double.parseDouble(a[i + 1]), Double.parseDouble(a[i + 2]));
                    double radius = Double.parseDouble(a[i + 3]);
                    this.shapes[placeInArray] = new Circle(center, radius);
                    placeInArray++;
                    interval = PARAMETERS_FOR_CIRCLE;
                } catch (Exception e) {
                    throw new IllegalArgumentException(this.errorMessage);
                }
            } else if (a[i].equals("triangle")) {
                try {
                    Point p1 = new Point(Double.parseDouble(a[i + 1]), Double.parseDouble(a[i + 2]));
                    Point p2 = new Point(Double.parseDouble(a[i + 3]), Double.parseDouble(a[i + 4]));
                    Point p3 = new Point(Double.parseDouble(a[i + 5]), Double.parseDouble(a[i + 6]));
                    this.shapes[placeInArray] = new Triangle(p1, p2, p3);
                    placeInArray++;
                    interval = PARAMETERS_FOR_TRIANGLE;
                } catch (Exception e) {
                    throw new IllegalArgumentException(this.errorMessage);
                }
            } else {
                try {
                    int numberOfSides = Integer.parseInt(a[i + 1]);
                    Point[] pArr = new Point[numberOfSides];
                    for (int j = 0; j < numberOfSides; j++) {
                        pArr[j] = new Point(Double.parseDouble(a[i + (j * 2) + 2]), Double.parseDouble(a[i + (j * 2) + 3]));
                    }
                    this.shapes[placeInArray] = new Polygon(pArr);
                    placeInArray++;
                    interval = (numberOfSides * 2) + 2;
                } catch (Exception e) {
                    throw new IllegalArgumentException(this.errorMessage);
                }
            }
        }

    }

    public Point determineLowerBound() {
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

        return new Point(minX, minY);
    }

    public Point determineUpperBound() {
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

        return new Point(maxX, maxY);
    }

    public void displayBoundingBox() {
        System.out.println("Bounding box:");
        System.out.println("- Upper-right corner: (" + this.determineUpperBound().getXCoord() + ", " + determineUpperBound().getYCoord() + ")");
        System.out.println("- Lower-left corner: (" + this.determineLowerBound().getXCoord() + ", " + determineLowerBound().getYCoord() + ")");
    }

    public void throwDarts() {
        for (int i = 0; i < ONE_MILLION; i++) {
            Point dartThrow = Point.random(this.determineLowerBound(), this.determineUpperBound())
;           boolean addToIntersection = true;     //initialized to true and changed to false if any given dart does not exist in all of the shapes
            boolean addToUnion = false;           //initialized to false and changed to true once a dart has been found to exist in any single shape
            boolean addToNonIntersection = false; //initialized to false; changed to true when a dart exists in one single shape, and changed back to false if the dart exists in any additional shapes
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

    public void displayShapeAreas(int l) {
        double [] shapeAreas = new double[l];
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

    //This output stream is intended to be piped into Dartboard.java... Yes, this means that this output steam
    //involves different darts than the ones that were used to calculate area.  This is because the output stream
    //takes quite a bit of time to be sent to the console.  This way, area can be calculated instantaneously,
    //and then the slow-natured output is saved for last.
    public void output() {
        System.out.println("start");
        for (int i = 0; i < ONE_MILLION; i++) {
            Point dartThrow = Point.random(this.determineLowerBound(), this.determineUpperBound());
            boolean inShape = false;
            for (Shape s : this.shapes) {
                if (s.insideShape(dartThrow)) {
                    inShape = true;
                }
            }
            System.out.println(dartThrow.toString() + (inShape ? " in" : " out"));
        }
        System.out.println("end");
    }

    //Necessary for calculating the area of a shape with a given number of darts in it
    public double areaOfBound() {
        double base = this.determineUpperBound().getXCoord() - this.determineLowerBound().getXCoord();
        double height = this.determineUpperBound().getYCoord() - this.determineLowerBound().getYCoord();

        return base * height;
    }

    public double areaWithDarts(int numOfDarts) {
        return (numOfDarts / (double) ONE_MILLION) * this.areaOfBound();
    }

    public static void main(String[] args) {
        InputSorter input = new InputSorter(args);
        int length = input.getLength();
        AreaEstimator ae = new AreaEstimator(args, length);
        ae.displayBoundingBox();
        ae.throwDarts();
        ae.displayShapeAreas(length);
        ae.output();
    }
}
