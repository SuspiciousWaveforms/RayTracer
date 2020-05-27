package main.java.rays;

import main.java.scene.Shapes.Shape;

public class ClosestShapeIntersections {
    private Shape shape;
    private double closeIntersection;
    private double farIntersection;

    public ClosestShapeIntersections(Shape shape, double closeIntersection, double farIntersection) {
        this.shape = shape;
        this.closeIntersection = closeIntersection;
        this.farIntersection = farIntersection;
    }

    public Shape getShape() {
        return shape;
    }

    public double getCloseIntersection() {
        return closeIntersection;
    }

    public double getFarIntersection() {
        return farIntersection;
    }
}
