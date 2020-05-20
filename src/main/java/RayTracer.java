package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.light.Ambient;
import main.java.light.Directional;
import main.java.light.Light;
import main.java.light.Point;
import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.scene.Scene;
import main.java.scene.Shapes.Shape;
import main.java.scene.Shapes.Sphere;
import main.java.utilities.Vec;

public class RayTracer extends JPanel {

    private ArrayList<Shape> objects;
    private ArrayList<Light> lights;

    Vec camera = new Vec(0, 0, 0);

    Vec backgroundColor = new Vec(255, 255, 255);

    private int canvasWidth = 400;
    private int canvasHeight = 400;
    private int viewportWidth = 1;
    private int viewportHeight = 1;
    private int distanceToViewport = 1;
    private int traceMin = 1;
    private int traceMax = 10000;

    private BufferedImage canvas;

    public static void main(String[] args) {
        RayTracer rayTracer = new RayTracer();
        Scene scene = new Scene();

        Vec center = new Vec(1, 1, 4);
        Vec color = new Vec(255, 0, 0);
        Sphere sphere = new Sphere(center,1, color);
        scene.addShape(sphere);

        Vec center2 = new Vec(1, 2, 4);
        Vec color2 = new Vec(0, 0, 255);
        Sphere sphere2 = new Sphere(center2,0.5, color2);
        scene.addShape(sphere2);

//        Vec center3 = new Vec(1, 4500, 0);
//        Vec color3 = new Vec(255, 255, 0);
//        Sphere sphere3 = new Sphere(center3,5000, color3);
//        scene.addShape(sphere3);

//        Ambient ambientLight = new Ambient(0.2);
//        scene.addLight(ambientLight);

//        Vec position = new Vec(3, 2, 0);
//        Point pointLight = new Point(0.6, position);
//        scene.addLight(pointLight);

        Vec direction = new Vec(5, 2, 0);
        Directional directionalLight = new Directional(0.7, direction);
        scene.addLight(directionalLight);

        rayTracer.lights = scene.getLights();
        rayTracer.objects = scene.getObjects();

        Ray ray;
        Vec tempColor;
        double tempX, tempY, tempZ;

        rayTracer.canvas = new BufferedImage(rayTracer.canvasWidth, rayTracer.canvasHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < rayTracer.canvasWidth; x++) {
            for (int y = 0; y < rayTracer.canvasHeight; y++) {
                ray = new Ray(rayTracer.camera, rayTracer.canvasToViewport(x, y));
                tempColor = rayTracer.traceRay(ray);
                tempX = tempColor.getX();
                tempY = tempColor.getY();
                tempZ = tempColor.getZ();

                rayTracer.canvas.setRGB(x, y, (new Color((int) tempX, (int) tempY, (int) tempZ)).getRGB());
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

    public Vec traceRay(Ray ray) {
        double closestIntersection = traceMax + 1;
        Shape closestShape = null;
        RayIntersections rayIntersections;
        Vec point, normal;
        Vec origin = ray.getOrigin();
        Vec direction = ray.getDirection();

        for (Shape shape : objects) {
            rayIntersections = shape.checkIntersect(ray);
            if (rayIntersections != null) {
                double intersection1 = rayIntersections.getIntersection1();
                double intersection2 = rayIntersections.getIntersection2();

                if (intersection1 >= traceMin && intersection1 <= traceMax && intersection1 < closestIntersection) {
                    closestIntersection = intersection1;
                    closestShape = shape;
                }

                if (intersection2 >= traceMin && intersection2 <= traceMax && intersection2 < closestIntersection) {
                    closestIntersection = intersection2;
                    closestShape = shape;
                }

            }
        }

        if (closestShape == null) return backgroundColor;
        else {
            point = origin.add(direction.scale(closestIntersection));
            // Should not be hard coded to sphere.
            normal = point.sub(((Sphere) closestShape).getCenter());
            normal = normal.scale(1.0 / normal.length());
            return closestShape.getColor().scale(computeLighting(point, normal));
        }
    }

    public double computeLighting(Vec point, Vec normal) {
        double i = 0.0;
        double nDotL;
        Vec l;

        for (Light light : lights) {
            if (light instanceof Ambient) {
                i += light.getIntensity();
            } else {
                if (light instanceof Point) l = (((Point) light).getPosition()).sub(point);
                else l = ((Directional) light).getDirection();

                nDotL = normal.dotProduct(l);

                if (nDotL > 0) i += (light.getIntensity()) * nDotL / (normal.length() * l.length());
            }
        }

        return i;
    }
}
