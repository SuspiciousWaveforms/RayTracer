package main.java;

import java.awt.Color;
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
import main.java.rays.ClosestShapeIntersections;
import main.java.scene.Scene;
import main.java.scene.Shapes.Shape;
import main.java.scene.Shapes.Sphere;
import main.java.utilities.Vec;

public class RayTracer extends JPanel {
    private BufferedImage canvas;

    private ArrayList<Shape> objects;
    private ArrayList<Light> lights;

    Vec cameraDirection;
    Vec[] cameraRotation;
    Vec backgroundColor = new Vec(0, 0, 0);

    private int canvasWidth = 600;
    private int canvasHeight = 600;
    private final int traceMin = 1;
    private final int traceMax = 1000000000;
    private final int recursionDepth = 3;

    public static void main(String[] args) {
        RayTracer rt = new RayTracer();
        Camera camera;
        Scene scene;
        Ray ray;
        Vec tempColor;

        // Specify the camera view.
        rt.cameraDirection = new Vec(0, 0, 0);
        rt.cameraRotation = new Vec[]{
                new Vec(0.7071, 0, -0.7071),
                new Vec(0, 1, 0),
                new Vec(0.7071, 0, 0.7071)};
//        rt.cameraRotation = new Vec[]{
//                new Vec(0,0,0),
//                new Vec(0,0,0),
//                new Vec(0,0,0)};
        camera = new Camera(rt.cameraDirection, rt.cameraRotation);

        scene = new Scene();

        // Specify the objects in the scene.
        scene.addShape(new Sphere(
                new Vec(0, -1, 3),
                1,
                new Vec(255, 0, 0),
                100000000,
                0,
                0.4));

        scene.addShape(new Sphere(
                new Vec(2, 0, 4),
                1,
                new Vec(0, 0, 255),
                500,
                0.3,
                0));

        scene.addShape(new Sphere(
                new Vec(-0.5, 0, 6),
                1,
                new Vec(0, 255, 0),
                10,
                0.4,
                0));

        scene.addShape(new Sphere(
                new Vec(0, -5001, 0),
                5000,
                new Vec(255, 255, 0),
                1000,
                0.5,
                0));


        // Specify the lighting in the scene.
        scene.addLight(new Ambient(0.2));
        scene.addLight(new Point( 0.6, new Vec(2, 1, 0)));
        scene.addLight(new Directional(0.2, new Vec(1, 4, 4)));

        // Retrieve the lights and objects, and camera details..
        rt.objects = scene.getObjects();
        rt.lights = scene.getLights();

        rt.cameraRotation = camera.getCameraRotation();
        rt.cameraDirection = camera.getCameraDirection();

        // Create the image to draw pixels on.
        rt.canvas = new BufferedImage(rt.canvasWidth, rt.canvasHeight, BufferedImage.TYPE_INT_RGB);

        // Draw the image.
        for (int x = -(rt.canvasWidth / 2) ; x < (rt.canvasWidth / 2); x++) {
            for (int y = -(rt.canvasHeight / 2); y < (rt.canvasHeight / 2); y++) {
//                ray = new Ray(rt.cameraDirection, (rt.canvasToViewport(x, y)).mxV(rt.cameraRotation));
                ray = new Ray(rt.cameraDirection, (rt.canvasToViewport(x, y)));

                tempColor = (rt.traceRay(ray, rt.traceMin, rt.traceMax, rt.recursionDepth)).colorBind();

                rt.canvas.setRGB((
                        rt.canvasWidth / 2) + x,
                        (rt.canvasHeight / 2) - y - 1,
                        (new Color(
                                (int) tempColor.getX(),
                                (int) tempColor.getY(),
                                (int) tempColor.getZ())).getRGB());
            }
        }

        // Save the image to a file.
        File output = new File("image.png");

        try {
            ImageIO.write(rt.canvas, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Determine the square on the viewport corresponding to a pixel.
    public Vec canvasToViewport(int x, int y) {
        int viewportWidth = 1;
        int viewportHeight = 1;
        int distanceToViewport = 1;

        return new Vec(
                x * (double) viewportWidth / (double) canvasWidth,
                y * (double) viewportHeight / (double) canvasHeight,
                distanceToViewport);
    }

    // Return a color for a pixel on the canvas.
    public Vec traceRay(Ray ray, double traceMin, double traceMax, int recursionDepth) {
        ClosestShapeIntersections closestShapeIntersections;
        Shape closestShape;
        Vec closePoint, farPoint, closeNormal, farNormal, localColor;
        Vec reflectedColor = new Vec(0,0,0);
        Vec transparentColor = new Vec(0,0,0);
        Vec origin = ray.getOrigin();
        Vec direction = ray.getDirection();
        Ray reflectedRay, transparentRay;

        double reflective;
        double transparent;
        double closeIntersection, farIntersection;

        // Retrieve the data on the closest shape.
        closestShapeIntersections = geteClosestShapeIntersection(ray, traceMin, traceMax);
        closestShape = closestShapeIntersections.getShape();
        closeIntersection = closestShapeIntersections.getCloseIntersection();
        farIntersection = closestShapeIntersections.getFarIntersection();

        if (closestShape == null) return backgroundColor;

        // Compute lighting.
        closePoint = origin.add(direction.scale(closeIntersection));
        closeNormal = closePoint.sub(((Sphere) closestShape).getCenter());
        closeNormal = closeNormal.scale(1.0 / closeNormal.length());
        localColor = closestShape.getColor().scale(computeLighting(closePoint, closeNormal, direction.scale(-1), closestShape.getSpecular()));

        reflective = closestShape.getReflective();
        transparent = closestShape.getTransparent();

        // Compute reflection.
        if (recursionDepth > 0 && reflective > 0) {
            reflectedRay = new Ray(closePoint, reflectRay(direction.scale(-1), closeNormal));
            reflectedColor = traceRay(reflectedRay, 0.001, traceMax, recursionDepth - 1);
        }

        // Compute transparency.
        if (recursionDepth > 0 && transparent > 0) {
            farPoint = origin.add(direction.scale(farIntersection));
            transparentRay = new Ray(farPoint, direction);
            transparentColor = traceRay(transparentRay, 0.001, traceMax, recursionDepth - 1);
        }

        return (localColor.scale(1 - reflective - transparent).add(reflectedColor.scale(reflective))).add(transparentColor.scale(transparent));
    }


    // Return a shape and the closest intersection of that shape and a ray.
    public ClosestShapeIntersections geteClosestShapeIntersection(Ray ray, double traceMin, double traceMax) {
        double closeIntersection = traceMax + 1;
        double farIntersection = traceMax + 1;
        Shape closestShape = null;
        RayIntersections rayIntersections;

        for (Shape shape : objects) {
            rayIntersections = shape.checkIntersect(ray);

            if (rayIntersections != null) {
                double intersection1 = rayIntersections.getIntersection1();
                double intersection2 = rayIntersections.getIntersection2();

                if (intersection1 >= traceMin && intersection1 <= traceMax && intersection1 < closeIntersection) {
                    closestShape = shape;

                    closeIntersection = intersection1;
                    if (intersection2 >= traceMin && intersection2 <= traceMax) farIntersection = intersection2;

                }

                if (intersection2 >= traceMin && intersection2 <= traceMax && intersection2 < closeIntersection) {
                    closestShape = shape;

                    closeIntersection = intersection2;
                    if (intersection1 >= traceMin && intersection1 <= traceMax) farIntersection = intersection1;
                }
            }
        }
        return new ClosestShapeIntersections(closestShape, closeIntersection, farIntersection);
    }

    // Return direction vector for reflected ray.
    public Vec reflectRay(Vec R, Vec N) {
        return ((N.scale(2)).scale(N.dotProduct(R))).sub(R);
    }

    // Compute the double to modify the lighting for a pixel to account for the lighting.
    public double computeLighting(Vec point, Vec normal, Vec view, int specular) {
        ClosestShapeIntersections closestShapeIntersections;
        Shape shadowSphere;
        Ray shadowRay;
        Vec l, r;

        double i = 0.0;
        double nDotL, rDotV;

        for (Light light : lights) {
            if (light instanceof Ambient) {
                i += light.getIntensity();
            } else {
                if (light instanceof Point) l = (((Point) light).getPosition()).sub(point);
                else l = ((Directional) light).getDirection();

                // Shadow check
                shadowRay = new Ray(point, l);
                closestShapeIntersections = geteClosestShapeIntersection(shadowRay, 0.001, traceMax);
                shadowSphere = closestShapeIntersections.getShape();

                if (shadowSphere == null) {
                    // Diffuse lighting.
                    nDotL = normal.dotProduct(l);

                    if (nDotL > 0) i += (light.getIntensity()) * nDotL / (normal.length() * l.length());

                    // Specular lighting.
                    if (specular != -1) {
                        r = reflectRay(l, normal);
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
