public class AreaEstimator {
    public static void main(String[] args) {
        Triangle triangle = new Triangle(new Point(0.0, 0.0), new Point(10.0, 0.0), new Point(0.0, 10.0));
        System.out.println("The point is inside the shape... " + triangle.insideShape(new Point(0.0, 0.0)));
    }
}
