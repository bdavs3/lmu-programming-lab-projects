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
            double product = v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
            return product;
        } catch (Exception e) {
            double product = v1.getX() * v2.getX() + v1.getY() * v2.getY();
            return product;
        }
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}
