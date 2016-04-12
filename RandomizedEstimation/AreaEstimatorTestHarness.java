public class AreaEstimatorTestHarness {
    private static int attempts = 0;
    private static int successes = 0;

    public static void main(String[] args) {
        attempts = 0;
        successes = 0;

        test_constructor();
        test_bounds();          //AreaEstimator methods... It isn't necessary to test all the helper
        test_shapeAreas();      //e.g. areaOfBound() because all of those are used to calculate total area

        test_insideShape();     //From Shape... Bounds are not tested because they were already tested in the
                                //context of the AreaEstimator class

        test_crossProduct();    //Vector methods
        test_dotProduct();

        test_toString();
        test_distance();        //Point methods
        test_vectorBetween();
        test_random();

        System.out.println(successes + "/" + attempts + " tests passed.");
    }

    private static void displaySuccessIfTrue(boolean value) {
        attempts++;
        successes += value ? 1 : 0;

        System.out.println(value ? "success" : "failure");
    }
    
    private static void displayUnimplementedMethodFailure() {
        attempts++;
        System.out.println("failure (NYI)");
    }

    private static void test_constructor() {
        System.out.println("Testing constructor...");

        try {
            String[] s = {"circle", "0", "0", "3"};
            new AreaEstimator(s, 1);
            displaySuccessIfTrue(true);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            String[] s = {"circle", "0", "0", "3", "4"};
            new AreaEstimator(s, 1);
            displaySuccessIfTrue(false);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(true);
        }

        try {
            String[] s = {"polygon", "4", "-1", "0", "0", "1", "1", "0", "0", "-1"};
            new AreaEstimator(s, 1);
            displaySuccessIfTrue(true);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            String[] s = {"circle", "0", "0", "3", "triangle", "1", "2", "3", "4", "5", "6"};
            new AreaEstimator(s, 2);
            displaySuccessIfTrue(true);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            String[] s = {"circle", "oops!", "0", "0", "3", "triangle", "1", "2", "3", "4", "5", "6"};
            new AreaEstimator(s, 2);
            displaySuccessIfTrue(false);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(true);
        }

    }

    private static void test_bounds() {
        System.out.println("Testing boundingMethods...");

        String[] s = {"circle", "0", "0", "3", "triangle", "3", "3", "5", "10", "5", "0"};
        String[] t = {"circle", "0", "0", "3", "circle", "3", "0", "5"};

        try {
            displaySuccessIfTrue(new AreaEstimator(s, 2).determineLowerBound().getXCoord() == -3);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(new AreaEstimator(s, 2).determineLowerBound().getYCoord() == -3);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(new AreaEstimator(s, 2).determineUpperBound().getXCoord() == 5);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(new AreaEstimator(s, 2).determineUpperBound().getYCoord() == 10);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(new AreaEstimator(t, 2).determineLowerBound().getXCoord() == -3);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(new AreaEstimator(t, 2).determineLowerBound().getYCoord() == -5);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(new AreaEstimator(t, 2).determineUpperBound().getXCoord() == 8);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(new AreaEstimator(t, 2).determineUpperBound().getYCoord() == 5);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_shapeAreas() {
        System.out.println("Testing shapeAreas...");

        String[] s = {"circle", "0", "0", "3"};
        String[] t = {"triangle", "3", "3", "5", "10", "5", "0"};
        String[] u = {"polygon", "4", "-1", "0", "0", "1", "1", "0", "0", "-1"};
        String[] v = {"circle", "0", "0", "2", "triangle", "1", "0", "3", "3", "6", "-3", "polygon", "4", "1.5", "0", "2.5", "2", "6", "1.5", "1.9", "-3"};
        try {
            AreaEstimator ae = new AreaEstimator(s, 1);
            ae.throwDarts();
            double area = ae.areaWithDarts(ae.getShapes()[0].getNumberOfDarts());
            displaySuccessIfTrue(area >= 27.75 && area <= 28.75);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            AreaEstimator ae = new AreaEstimator(t, 1);
            ae.throwDarts();
            double area = ae.areaWithDarts(ae.getShapes()[0].getNumberOfDarts());
            displaySuccessIfTrue(area >= 9.5 && area <= 10.5);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            AreaEstimator ae = new AreaEstimator(u, 1);
            ae.throwDarts();
            double area = ae.areaWithDarts(ae.getShapes()[0].getNumberOfDarts());
            displaySuccessIfTrue(area >= 1.8 && area <= 2.2);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            AreaEstimator ae = new AreaEstimator(v, 3);
            ae.throwDarts();
            double intersection = ae.areaWithDarts(ae.getDartsInIntersection());
            displaySuccessIfTrue(intersection >= 0 && intersection <= 1);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            AreaEstimator ae = new AreaEstimator(v, 3);
            ae.throwDarts();
            double union = ae.areaWithDarts(ae.getDartsInUnion());
            displaySuccessIfTrue(union >= 26 && union <= 27);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            AreaEstimator ae = new AreaEstimator(v, 3);
            ae.throwDarts();
            double nonIntersection = ae.areaWithDarts(ae.getDartsInNonIntersection());
            displaySuccessIfTrue(nonIntersection >= 19 && nonIntersection <= 20);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

    }

    private static void test_insideShape() {
        System.out.println("Testing insideShape...");
        Circle c = new Circle(new Point(0, 1), 5);
        Triangle t = new Triangle(new Point(-3, -3), new Point(0, 0), new Point(0, 5));
        Point[] pArr = {new Point(-1, 0), new Point(-1, 5), new Point(3, 12), new Point(3, 4)};
        Polygon p = new Polygon(pArr);

        try {
            displaySuccessIfTrue(c.insideShape(new Point(0, 6)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(!c.insideShape(new Point(10, 10)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(c.insideShape(new Point(0, 0)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(t.insideShape(new Point(-1, 1)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(!t.insideShape(new Point(1, -1)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(t.insideShape(new Point(-2.95, -2.95)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(p.insideShape(new Point(0, 3)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(!p.insideShape(new Point(3.01, 5)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(p.insideShape(new Point(1, 6)));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_crossProduct() {
        System.out.println("Testing crossProduct...");
        Vector v1 = new Vector(2, 5);
        Vector v2 = new Vector(-2, 8);
        Vector v3 = new Vector(0, 1);
        Vector v4 = new Vector(5, -11);
        Vector v5 = new Vector(-10, -10);
        Vector v6 = new Vector(5, 2);

        try {
            displaySuccessIfTrue(Vector.crossProduct(v1, v2).getZ() == 26);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(Vector.crossProduct(v3, v4).getZ() == -5);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(Vector.crossProduct(v5, v6).getZ() == 30);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

    }

    private static void test_dotProduct() {
        System.out.println("Testing dotProduct...");
        Vector v1 = new Vector(-2, 3, 5);
        Vector v2 = new Vector(0, 1, 1);
        Vector v3 = new Vector(8, 2, -10);
        Vector v4 = new Vector(10, 15, 20);

        try {
            displaySuccessIfTrue(Vector.dotProduct(v1, v2) == 8);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(Vector.dotProduct(v3, v4) == -90);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_toString() {
        System.out.println("Testing toString...");

        try {
            String stringOfPoint = new Point(3, 5).toString();
            displaySuccessIfTrue(stringOfPoint.equals("3.0 5.0"));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            String stringOfPoint = new Point(0, -5).toString();
            displaySuccessIfTrue(stringOfPoint.equals("0.0 -5.0"));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            String stringOfPoint = new Point(10, 10000).toString();
            displaySuccessIfTrue(stringOfPoint.equals("10.0 10000.0"));
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

    }

    private static void test_distance() {
        System.out.println("Testing distance...");
        Point p1 = new Point(10, 2);
        Point p2 = new Point(-2, 5);
        Point p3 = new Point(-1, -1);
        Point p4 = new Point(100, 100);
        Point p5 = new Point(0, 5);
        Point p6 = new Point(7, 7);

        try {
            double distance = p1.distance(p2);
            displaySuccessIfTrue(distance == 12.36931687685298);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            double distance = p3.distance(p4);
            displaySuccessIfTrue(distance == 142.8355697996826);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            double distance = p5.distance(p6);
            displaySuccessIfTrue(distance == 7.280109889280518);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

    }

    private static void test_vectorBetween() {
        System.out.println("Testing vectorBetween...");
        Point p1 = new Point(-1, 5);
        Point p2 = new Point(5, 10);
        Point p3 = new Point(10, 15);
        Point p4 = new Point(2, 2);

        try {
            Vector vectorBetween = Point.vectorBetween(p1, p2);
            displaySuccessIfTrue(vectorBetween.getX() == -6);
            displaySuccessIfTrue(vectorBetween.getY() == -5);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            Vector vectorBetween = Point.vectorBetween(p3, p4);
            displaySuccessIfTrue(vectorBetween.getX() == 8);
            displaySuccessIfTrue(vectorBetween.getY() == 13);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

    }

    private static void test_random() {
        System.out.println("Testing random...");

        try {
            Point random = Point.random(new Point(-5, -3), new Point(5, 3));
            displaySuccessIfTrue(random.getXCoord() >= -5 && random.getYCoord() <= 5);
            displaySuccessIfTrue(random.getYCoord() >= -3 && random.getYCoord() <= 3);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            Point random = Point.random(new Point(0, -10), new Point(12, -8));
            displaySuccessIfTrue(random.getXCoord() >= 0 && random.getYCoord() <= 12);
            displaySuccessIfTrue(random.getYCoord() >= -10 && random.getYCoord() <= -8);
        } catch (UnsupportedOperationException uoe) {
            displayUnimplementedMethodFailure();
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

    }
}
