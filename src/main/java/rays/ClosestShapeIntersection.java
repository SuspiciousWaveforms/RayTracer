package main.java.rays;

import main.java.scene.Shapes.Shape;

public class ClosestShapeIntersection {
    private Shape shape;
    private double closestIntersection;

    public ClosestShapeIntersection(Shape shape, double closestIntersection) {
        this.shape = shape;
        this.closestIntersection = closestIntersection;
    }

    public Shape getShape() {
        return shape;
    }

    public double getClosestIntersection() {
        return closestIntersection;
    }
}
