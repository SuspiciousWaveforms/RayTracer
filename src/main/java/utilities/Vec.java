package main.java.utilities;
public class Vec {

    private double[] vec = new double[3];

    public Vec(double x, double y, double z) {
        vec[0] = x;
        vec[1] = y;
        vec[2] = z;
    }

    public Vec(Vec v) {
        vec[0] = v.getX();
        vec[1] = v.getY();
        vec[2] = v.getZ();
    }

    public Vec colorBind() {
        double x, y, z;

        x = Math.min(vec[0], 255);
        y = Math.min(vec[1], 255);
        z = Math.min(vec[2], 255);

        return new Vec(x, y, z);
    }

    public Vec mxV(Vec[] matrix) {
        double[] ans = {0, 0, 0};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               ans[i] += vec[j] * matrix[i].getN(j);
            }
        }

        return new Vec(ans[0], ans[1], ans[2]);
    }

    public Vec normalise() {
        double t = Math.sqrt((vec[0] * vec[0]) + (vec[1] * vec[1]) + (vec[2] * vec[2]));

        if (t != 0) return new Vec(vec[0] / t, vec[1] / t, vec[2] / t);
        else return new Vec(0, 0, 0);

    }

    public double dotProduct(Vec v) {
        return (vec[0] * v.getX()) + (vec[1] * v.getY()) + (vec[2] * v.getZ());
    }

    public double length() {
        return Math.sqrt(this.dotProduct(this));
    }

    public Vec scale(double factor) {
        return new Vec(vec[0] * factor, vec[1] * factor, vec[2] * factor);
    }

    public Vec add(Vec v) {
        return new Vec(vec[0] + v.getX(), vec[1] + v.getY(), vec[2] + v.getZ());
    }

    public Vec sub(Vec v) {
        return new Vec(vec[0] - v.getX(), vec[1] - v.getY(), vec[2] - v.getZ());
    }

    public double getX() { return vec[0]; }

    public double getY() { return vec[1]; }

    public double getZ() { return vec[2]; }

    public double getN(int n) {
        return vec[n];
    }
}
