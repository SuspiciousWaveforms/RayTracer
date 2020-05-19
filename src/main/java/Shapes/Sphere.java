package main.java.Shapes;
import main.java.Ray;
import main.java.Utilities.Vec;

public class Sphere extends Shape {
    private Vec color;
    private Vec center;
    private double radius;

    @Override
    public double checkIntersect(Ray ray) {
        return 0.0;
    }

    public void setColor(Vec color) {
        this.color = color;
    }

    public void setCenter(Vec center) {
        this.center = center;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vec getColor() {
        return color;
    }

    public Vec getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}
