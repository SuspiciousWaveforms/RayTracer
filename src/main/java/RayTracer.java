package main.java;

import java.util.ArrayList;
import javax.swing.JPanel;

import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.scene.Scene;
import main.java.scene.Shapes.Shape;
import main.java.scene.Shapes.Sphere;
import main.java.utilities.Vec;

public class RayTracer extends JPanel {

    private ArrayList<Shape> objects;

    Vec camera = new Vec(0, 0, 0);
    Vec backgroundColor = new Vec(0, 0, 0);

    private int canvasWidth = 200;
    private int canvasHeight = 200;
    private int viewportWidth = 200;
    private int viewportHeight = 200;
    private int distanceToViewport = 1;
    private int traceMin = 1;
    private int traceMax = 10;

    public static void main(String[] args) {
        RayTracer rayTracer = new RayTracer();

        Scene scene = new Scene();

        rayTracer.objects = scene.getObjects();
    }

    // Determine the square on the viewport corresponding to a pixel.
    public Vec canvasToViewport(int x, int y) {
        return new Vec(x * (double) viewportWidth / (double) canvasWidth,
                y * (double) viewportHeight / (double) canvasHeight,
                distanceToViewport);
    }

    public Vec traceRay(Ray ray) {
        double closestIntersection = traceMax + 1;
        Shape closestShape = null;
        RayIntersections rayIntersections;

        for (Shape shape : objects) {
            rayIntersections = shape.checkIntersect(ray);
            double intersection1 = rayIntersections.getIntersection1();
            double intersection2 = rayIntersections.getIntersection2();

            if (intersection1 <= traceMin && intersection1 <= traceMax && intersection1 < closestIntersection) {
               closestIntersection = intersection1;
               closestShape = shape;
            }

            if (intersection2 <= traceMin && intersection2 <= traceMax && intersection2 < closestIntersection) {
                closestIntersection = intersection2;
                closestShape = shape;
            }
        }

        if (closestShape == null) return backgroundColor;
        else return closestShape.getColor();
    }
}
