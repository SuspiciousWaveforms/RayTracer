package main.java.Utilities;
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

    public void normalize() {
        double length = Math.sqrt(x * x + y * y + z * z);
        x = x / length;
        y = y / length;
        z = z / length;
    }

    public double dotProduct(Vec v) {
        return (x * v.getX()) + (y * v.getY()) + (z * v.getZ());
    }

    public void crossProduct(Vec v) {
       x = x * v.x;
       y = y * v.y;
       z = z * v.z;
    }

    public void scale(int factor) {
        x = x * factor;
        y = y * factor;
        z = z * factor;
    }

    public void add(Vec v) {
        x = x + v.x;
        y = y + v.y;
        z = z + v.z;
    }

    public void sub(Vec v) {
        x = x - v.x;
        y = y - v.y;
        z = z - v.z;
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getZ() { return z; }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }

    public void setZ(double z) { this.z = z; }
}
