public class Vector {
    private double x, y, z;

    public Vector(double x0, double y0) {
        this.x = x0;
        this.y = y0;
    }

    public Vector(double x0, double y0, double z0) {
        this.x = x0;
        this.y = y0;
        this.z = z0;
    }

    public static Vector crossProduct(Vector v1, Vector v2) {
        return new Vector(0, 0, v1.getX() * v2.getY() - v1.getY() * v2.getX());
    }

    public static double dotProduct(Vector v1, Vector v2) {
        try {
            double product = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
            return product;
        } catch (Exception e) {
            double product = v1.x * v2.x + v1.y * v2.y;
            return product;
        }
    }
}
