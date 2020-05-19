package main.java.scene.Shapes;
import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.utilities.Vec;

public abstract class Shape {
    protected Vec color;

    public abstract RayIntersections checkIntersect(Ray ray);
}
