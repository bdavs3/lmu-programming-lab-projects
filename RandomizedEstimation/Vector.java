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
        return new Vector(0, 0, v1.x * v2.y - v1.y * v2.x);
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

    //Getters used for testing purposes
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
