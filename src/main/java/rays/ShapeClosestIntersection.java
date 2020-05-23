package main.java.rays;

import main.java.scene.Shapes.Shape;

public class ShapeClosestIntersection {
    private Shape shape;
    private double intersection;

    public ShapeClosestIntersection(Shape shape, double intersection) {
        this.shape = shape;
        this.intersection = intersection;
    }

    public Shape getShape() {
        return shape;
    }

    public double getIntersection() {
        return intersection;
    }
}
