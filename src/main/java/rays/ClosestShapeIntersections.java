package main.java.rays;

import main.java.scene.Shapes.Shape;

public class ClosestShapeIntersections {
    private Shape shape;
    private double closeIntersection;

    public ClosestShapeIntersections(Shape shape, double closeIntersection) {
        this.shape = shape;
        this.closeIntersection = closeIntersection;
    }

    public Shape getShape() {
        return shape;
    }

    public double getCloseIntersection() {
        return closeIntersection;
    }
}
