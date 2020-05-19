package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

import javax.swing.JFrame;
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
    Color backgroundColor = new Color(0,0,0);

    private int canvasWidth = 200;
    private int canvasHeight = 200;
    private int viewportWidth = 1;
    private int viewportHeight = 1;
    private int distanceToViewport = 1;
    private int traceMin = 1;
    private int traceMax = 1000;

    private BufferedImage canvas;
    private Graphics2D g;

    public static void main(String[] args) {
        RayTracer rayTracer = new RayTracer();
        Scene scene = new Scene();

        Vec center = new Vec(0, -1, 3);
        Sphere sphere = new Sphere(center,1, Color.RED);
        scene.addShape(sphere);

        rayTracer.objects = scene.getObjects();

        Ray ray;
        Color color;

        rayTracer.canvas = new BufferedImage(rayTracer.canvasWidth, rayTracer.canvasHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < rayTracer.canvasWidth; x++) {
            for (int y = 0; y < rayTracer.canvasHeight; y++) {
                ray = new Ray(rayTracer.camera, rayTracer.canvasToViewport(x, y));
                color = rayTracer.traceRay(ray);

                rayTracer.canvas.setRGB(x, y, color.getRGB());
            }
        }

        JFrame frame = new JFrame();
        frame.getContentPane().add(rayTracer);
        frame.setSize(rayTracer.canvasWidth, rayTracer.canvasHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        g.drawImage(canvas, canvasWidth, canvasHeight, this);
    }

    // Determine the square on the viewport corresponding to a pixel.
    public Vec canvasToViewport(int x, int y) {
        return new Vec(x * (double) viewportWidth / (double) canvasWidth,
                y * (double) viewportHeight / (double) canvasHeight,
                distanceToViewport);
    }

    public Color traceRay(Ray ray) {
        double closestIntersection = traceMax + 1;
        Shape closestShape = null;
        RayIntersections rayIntersections;

        for (Shape shape : objects) {
            rayIntersections = shape.checkIntersect(ray);
            if (rayIntersections != null) {
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
        }

        if (closestShape == null) return backgroundColor;
        else return closestShape.getColor();
    }
}
