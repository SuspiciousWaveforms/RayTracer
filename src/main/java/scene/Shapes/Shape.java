package main.java.scene.Shapes;
import java.awt.Color;

import main.java.rays.Ray;
import main.java.rays.RayIntersections;

public abstract class Shape {
    protected Color color;

    public abstract RayIntersections checkIntersect(Ray ray);

    public Color getColor() {
        return color;
    }
}
