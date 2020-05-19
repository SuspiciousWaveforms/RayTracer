package main.java.Shapes;
import main.java.Ray;
import main.java.Utilities.Vec;

public abstract class Shape {
    protected Vec color;

    public abstract double checkIntersect(Ray ray);
}
