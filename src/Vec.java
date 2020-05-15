class Vec {

    public double x, y, z;

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
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public void normalize() {
        double length = Math.sqrt(x * x + y * y + z * z);
        x = x / length;
        y = y / length;
        z = z / length;
    }

    public double dotProduct(Vec v) {
        x = x * v.x;
        y = y * v.y;
        z = z * v.z;

        return x + y + z;
    }

    public void crossProduct(Vec v) {
       x = x * v.x;
       y = y * v.y;
       z = z * v.z;
    }

    public void mult(int factor) {
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
}
