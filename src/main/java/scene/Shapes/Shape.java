package main.java.scene.Shapes;

import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.utilities.Vec;

public abstract class Shape {
    private Vec color;
    private int specular;
    private double reflective;
    private boolean transparent;
    private double rIndex;

    Shape(Vec color, int specular, double reflective, boolean transparent, double rIndex) {
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
        this.transparent = transparent;
        this.rIndex = rIndex;
    }

    public abstract RayIntersections checkIntersect(Ray ray);

    public Vec getColor() {
        return color;
    }

    public int getSpecular() {
       return specular;
    }

    public double getReflective() {
        return reflective;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public double getRIndex() {
        return rIndex;
    }
}
