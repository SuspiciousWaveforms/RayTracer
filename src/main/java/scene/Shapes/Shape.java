package main.java.scene.Shapes;
import java.awt.Color;

import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.utilities.Vec;

public abstract class Shape {
    protected Color color;

    public abstract RayIntersections checkIntersect(Ray ray);

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
