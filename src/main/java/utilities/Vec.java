package main.java.utilities;
public class Vec {

    private double x, y, z;

    public Vec() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vec(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec(Vec v) {
        x = v.getX();
        y = v.getY();
        z = v.getZ();
    }

//    public void normalize() {
//        double length = Math.sqrt(x * x + y * y + z * z);
//        x = x / length;
//        y = y / length;
//        z = z / length;
//    }

    public double dotProduct(Vec v) {
        return (x * v.getX()) + (y * v.getY()) + (z * v.getZ());
    }

    public double length() {
        return Math.sqrt(this.dotProduct(this));
    }

    public Vec scale(double factor) {
        return new Vec(x * factor, y * factor, z * factor);
    }

    public Vec add(Vec v) {
        return new Vec(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    public Vec sub(Vec v) {
        return new Vec(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getZ() { return z; }
}
