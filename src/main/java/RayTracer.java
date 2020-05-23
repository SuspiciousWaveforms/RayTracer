package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.java.light.Ambient;
import main.java.light.Directional;
import main.java.light.Light;
import main.java.light.Point;
import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.rays.ShapeClosestIntersection;
import main.java.scene.Scene;
import main.java.scene.Shapes.Shape;
import main.java.scene.Shapes.Sphere;
import main.java.utilities.Vec;

public class RayTracer extends JPanel {
    private BufferedImage canvas;

    private ArrayList<Shape> objects;
    private ArrayList<Light> lights;

    Vec camera = new Vec(0, 0, 0);
    Vec backgroundColor = new Vec(255, 255, 255);

    private int canvasWidth = 600;
    private int canvasHeight = 600;
    private final int viewportWidth = 1;
    private final int viewportHeight = 1;
    private final int distanceToViewport = 1;
    private final int traceMin = 1;
    private final int traceMax = 1000000000;


    public static void main(String[] args) {
        RayTracer rayTracer = new RayTracer();
        Scene scene = new Scene();

        Vec center = new Vec(0, -1, 3);
        Vec color = new Vec(255, 0, 0);
        Sphere sphere = new Sphere(center,1, color, 500);
        scene.addShape(sphere);

        Vec center2 = new Vec(2, 0, 4);
        Vec color2 = new Vec(0, 0, 255);
        Sphere sphere2 = new Sphere(center2,1, color2, 500);
        scene.addShape(sphere2);

        Vec center3 = new Vec(-2, 0, 4);
        Vec color3 = new Vec(0, 255, 0);
        Sphere sphere3 = new Sphere(center3,1, color3, 10);
        scene.addShape(sphere3);

        Vec center4 = new Vec(0, -5001, 0);
        Vec color4 = new Vec(255, 255, 0);
        Sphere sphere4 = new Sphere(center4,5000, color4, 1000);
        scene.addShape(sphere4);

        Ambient ambientLight = new Ambient(0.2);
        scene.addLight(ambientLight);

        Vec position = new Vec(2, 1, 0);
        Point pointLight = new Point(0.6, position);
        scene.addLight(pointLight);

        Vec direction = new Vec(1, 4, 4);
        Directional directionalLight = new Directional(0.2, direction);
        scene.addLight(directionalLight);

        rayTracer.lights = scene.getLights();
        rayTracer.objects = scene.getObjects();

        rayTracer.canvas = new BufferedImage(rayTracer.canvasWidth, rayTracer.canvasHeight, BufferedImage.TYPE_INT_RGB);

        Ray ray;
        Vec tempColor;
        int tempX, tempY, tempZ;

        for (int x = -(rayTracer.canvasWidth / 2) ; x < (rayTracer.canvasWidth / 2); x++) {
            for (int y = -(rayTracer.canvasHeight / 2); y < (rayTracer.canvasHeight / 2); y++) {
                ray = new Ray(rayTracer.camera, rayTracer.canvasToViewport(x, y));

                tempColor = (rayTracer.traceRay(ray)).colorBind();
                tempX = (int) tempColor.getX();
                tempY = (int) tempColor.getY();
                tempZ = (int) tempColor.getZ();

                rayTracer.canvas.setRGB((rayTracer.canvasWidth / 2) + x,
                                         (rayTracer.canvasHeight / 2) - y - 1,
                                            (new Color(tempX, tempY, tempZ)).getRGB());
            }
        }

            File output = new File("image.png");
        try {
            ImageIO.write(rayTracer.canvas, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Determine the square on the viewport corresponding to a pixel.
    public Vec canvasToViewport(int x, int y) {
        return new Vec(x * (double) viewportWidth / (double) canvasWidth,
                y * (double) viewportHeight / (double) canvasHeight,
                distanceToViewport);
    }

    public Vec traceRay(Ray ray) {
        Vec point, normal;
        Vec origin = ray.getOrigin();
        Vec direction = ray.getDirection();

        Shape closestShape;
        double closestIntersection;
        ShapeClosestIntersection shapeClosestIntersection = getShapeClosestIntersection(ray, traceMin, traceMax);

        closestShape = shapeClosestIntersection.getShape();
        closestIntersection = shapeClosestIntersection.getIntersection();

        if (closestShape == null) return backgroundColor;

        point = origin.add(direction.scale(closestIntersection));
        normal = point.sub(((Sphere) closestShape).getCenter());
        normal = normal.scale(1.0 / normal.length());
        return closestShape.getColor().scale(computeLighting(point, normal, direction.scale(-1), closestShape.getSpecular()));
    }

    public ShapeClosestIntersection getShapeClosestIntersection(Ray ray, double traceMin, double traceMax) {
        double closestIntersection = traceMax + 1;
        Shape closestShape = null;
        RayIntersections rayIntersections;

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
        return new ShapeClosestIntersection(closestShape, closestIntersection);
    }

    public double computeLighting(Vec point, Vec normal, Vec view, int specular) {
        double i = 0.0;
        double nDotL, rDotV;
        Vec l, r;

        ShapeClosestIntersection shapeClosestIntersection;
        Ray shadowRay;
        Shape shadowSphere;


        for (Light light : lights) {
            if (light instanceof Ambient) {
                i += light.getIntensity();
            } else {
                if (light instanceof Point) {
                    l = (((Point) light).getPosition()).sub(point);
//                    System.out.println(l.getX() + "\n" + l.getY() + "\n" + l.getZ() + "\n");
                } else {
                    l = ((Directional) light).getDirection();
                }

                // Shadow check.
                shadowRay = new Ray(point, l);
                shapeClosestIntersection = getShapeClosestIntersection(shadowRay, 0.001, traceMax);
                shadowSphere = shapeClosestIntersection.getShape();

                if (shadowSphere == null) {
                    // Diffuse lighting.
                    nDotL = normal.dotProduct(l);

                    if (nDotL > 0) i += (light.getIntensity()) * nDotL / (normal.length() * l.length());

                    // Specular lighting.
                    if (specular != -1) {
                        r = ((normal.scale(2)).scale(normal.dotProduct(l))).sub(l);
                        rDotV = r.dotProduct(view);

                        if (rDotV > 0) {
                            i += light.getIntensity() * Math.pow(rDotV / (r.length() * view.length()), specular);
                        }
                    }

                }
            }
        }

        return i;
    }
}
