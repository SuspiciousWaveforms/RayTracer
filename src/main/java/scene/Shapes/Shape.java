package main.java.scene.Shapes;

import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.utilities.Vec;

public abstract class Shape {
    protected Vec color;
    protected int specular;
    protected double reflective;
    protected boolean transparent;
    protected double rIndex;

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

    public boolean getTransparent() {
        return transparent;
    }

    public double getRIndex() {
        return rIndex;
    }
}
