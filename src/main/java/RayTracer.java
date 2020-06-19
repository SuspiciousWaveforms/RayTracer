package main.java;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import main.java.light.Ambient;
import main.java.light.Directional;
import main.java.light.Light;
import main.java.light.Point;
import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.rays.ClosestShapeIntersections;
import main.java.scene.Camera;
import main.java.scene.Scene;
import main.java.scene.Shapes.Shape;
import main.java.scene.Shapes.Sphere;
import main.java.utilities.Vec;

public class RayTracer {
    private BufferedImage canvas;

    private List<Shape> objects;
    private List<Light> lights;

    Vec cameraDirection;
    Vec[] cameraRotation;
    Vec backgroundColor = new Vec(0, 0, 0);

    private int canvasWidth = 600;
    private int canvasHeight = 600;
    private final int traceMin = 1;
    private final int traceMax = 1_000_000_000;
    private final int recursionDepth = 3;
    private static final double R_INDEX_AIR = 1;

    public static void main(String[] args) {
        RayTracer rt = new RayTracer();
        Camera camera;
        Scene scene;
        Ray ray;
        Vec tempColor;

        // Specify the camera view.
        rt.cameraDirection = new Vec(0, 0, 0);
//        rt.cameraRotation = new Vec[]{
//                new Vec(0.7071, 0, -0.7071),
//                new Vec(0, 1, 0),
//                new Vec(0.7071, 0, 0.7071)};
        rt.cameraRotation = new Vec[]{
                new Vec(0,0,0),
                new Vec(0,0,0),
                new Vec(0,0,0)};
        camera = new Camera(rt.cameraDirection, rt.cameraRotation);

        scene = new Scene();

//        scene.addShape(new Sphere(
//                new Vec(0, 0, 2.5),
//                0.5,
//                new Vec(0, 0, 0),
//                500,
//                0,
//                true,
//                1.5));
//
//        scene.addShape(new Sphere(
//                new Vec(0.6, 0.75, 5),
//                0.75,
//                new Vec(255, 0, 0),
//                500,
//                0.3,
//                false,
//                1));
//
//        scene.addShape(new Sphere(
//                new Vec(0.9, 0.9, 2),
//                0.3,
//                new Vec(255, 165, 0),
//                1000000,
//                0.3,
//                false,
//                1));
//
//        scene.addShape(new Sphere(
//                new Vec(-1, 0, 6),
//                0.75,
//                new Vec(255, 105, 180),
//                500,
//                0.3,
//                false,
//                1));
//
//        scene.addShape(new Sphere(
//                new Vec(0.75, -1, 7),
//                1,
//                new Vec(0, 128, 128),
//                500,
//                0.3,
//                false,
//                1));
//
//        scene.addShape(new Sphere(
//                new Vec(2, -0.5, 4),
//                1,
//                new Vec(0, 0, 255),
//                500,
//                0.3,
//                false,
//                1));
//
//        scene.addShape(new Sphere(
//                new Vec(-2, 0, 4),
//                1,
//                new Vec(0, 255, 0),
//                500,
//                0.3,
//                false,
//                1));
//
//        scene.addShape(new Sphere(
//                new Vec(-0.6, 1, 3),
//                0.4,
//                new Vec(255, 255, 255),
//                1000,
//                0.8,
//                false,
//                1));
//
//        // Bottom wall
//        scene.addShape(new Sphere(
//                new Vec(0, -5001, 0),
//                5000,
//                new Vec(204, 232, 255),
//                100000000,
//                0,
//                false,
//                1));
//
//        // Back wall
//        scene.addShape(new Sphere(
//                new Vec(0, 0, 5009),
//                5000,
//                new Vec(51, 65, 76),
//                100000000,
//                0,
//                false,
//                1));
//
//        // Specify the lighting in the scene.
//        scene.addLight(new Ambient(0.2));
//        scene.addLight(new Point( 0.6, new Vec(2, 5, 0)));
//        scene.addLight(new Directional(0.2, new Vec(1, 4, 4)));

        // Bottom
        scene.addShape(new Sphere(
                new Vec(0, -5001, 0),
                5000,
                new Vec(204, 232, 255),
                100000000,
                0,
                false,
                1));

        // Top
        scene.addShape(new Sphere(
                new Vec(0, 5001, 0),
                5000,
                new Vec(204, 232, 255),
                100000000,
                0,
                false,
                1));

        // Back
        scene.addShape(new Sphere(
                new Vec(0, 0, 5005),
                5000,
                new Vec(204, 232, 255),
                100000000,
                0,
                false,
                1));

        // Front
        scene.addShape(new Sphere(
                new Vec(0, 0, -5000),
                5000,
                new Vec(0, 0, 0),
                100000000,
                0,
                false,
                1));

        // Right
        scene.addShape(new Sphere(
                new Vec(5001, 0, 0),
                5000,
                new Vec(65, 55, 100),
                100000000,
                0,
                false,
                1));

        // Left
        scene.addShape(new Sphere(
                new Vec(-5001, 0, 0),
                5000,
                new Vec(100, 60, 70),
                100000000,
                0,
                false,
                1));

        // Glass ball
        scene.addShape(new Sphere(
                new Vec(0.4, -0.4, 3),
                0.4,
                new Vec(255, 0, 0),
                100, 0,
                true,
                1.5));

        // Mirror
        scene.addShape(new Sphere(
                new Vec(-0.2, 0, 2),
                0.2,
                new Vec(255, 255, 255),
                300,
                1,
                false,
                1));

        // light marker
//        scene.addShape(new Sphere(
//                new Vec(0.1, 0, 2),
//                0.01,
//                new Vec(255, 255, 0),
//                300,
//                0,
//                false,
//                1));

        // Specify the lighting in the scene.
        scene.addLight(new Ambient(0.2));
        scene.addLight(new Point(0.6, new Vec(0.1, 0.8, 2)));
        scene.addLight(new Directional(0.8, new Vec(1, 4, 4)));

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
                ray = new Ray(rt.cameraDirection, (rt.canvasToViewport(x, y)).normalise());

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
        ClosestShapeIntersections closestShapeIntersectionsRefraction;
        Shape closestShape;

        Vec closePoint, farPoint, closeNormal, farNormal, localColor;
        Vec reflectedColor = new Vec(0,0,0);
        Vec transparentColor = new Vec(0,0,0);
        Vec origin = ray.getOrigin();
        Vec direction = ray.getDirection();
        Ray reflectedRay, refractedRay;

        double reflective;
        double rIndex = 1;
        double transparent;
        double closeIntersection, farIntersection;

        // Retrieve the data on the closest shape.
        closestShapeIntersections = getClosestShapeIntersections(ray, traceMin, traceMax);
        closestShape = closestShapeIntersections.getShape();
        closeIntersection = closestShapeIntersections.getCloseIntersection();

        if (closestShape != null) {
            rIndex = closestShape.getRIndex();
        }

        if (closestShape == null) return backgroundColor;

        // Compute lighting.
        closePoint = origin.add(direction.scale(closeIntersection));
        closeNormal = closePoint.sub(((Sphere) closestShape).getCenter()).normalise();
        localColor = closestShape.getColor().scale(computeLighting(closePoint, closeNormal, direction, closestShape.getSpecular()));

        if (closestShape.getTransparent()) {
            reflective = reflectance(direction, closeNormal, R_INDEX_AIR, rIndex);
            transparent = 1 - reflective;
        } else {
            reflective = closestShape.getReflective();
            transparent = 0;
        }

        // Compute reflection.
        if (recursionDepth > 0 && reflective > 0) {
            reflectedRay = new Ray(closePoint, reflectRay(direction, closeNormal));
            reflectedColor = traceRay(reflectedRay, 0.001, traceMax, recursionDepth - 1);
        }

        // Compute refraction.
        if (recursionDepth > 0 && transparent > 0) {
            refractedRay = new Ray(closePoint, refractRay(direction, closeNormal, R_INDEX_AIR, rIndex));

            closestShapeIntersectionsRefraction = getClosestShapeIntersections(refractedRay, traceMin, traceMax);
            farIntersection = closestShapeIntersectionsRefraction.getCloseIntersection();
            farPoint = closePoint.add(refractedRay.getDirection().scale(farIntersection));
            farNormal = (farPoint.sub(((Sphere) closestShape).getCenter())).scale(-1);
            farNormal = farNormal.scale(1.0 / farNormal.length());

            refractedRay = new Ray(farPoint, refractRay(refractedRay.getDirection(), farNormal, rIndex, R_INDEX_AIR));
            transparentColor = traceRay(refractedRay, 0.001, traceMax, recursionDepth- 1);
        }

        return ((localColor.scale(1 - reflective - transparent).add(reflectedColor.scale(reflective))).add(transparentColor.scale(transparent)));
    }

    public double reflectance(Vec I, Vec N, double n1, double n2) {
        double cosThetaI, cosThetaT, sin2t, rPerpendicular, rParallel;

        cosThetaI = N.dotProduct(I.scale(-1));
        sin2t = Math.pow((n1 / n2), 2) * (1 - Math.pow(cosThetaI, 2));
        cosThetaT = Math.sqrt(1.0 - sin2t);

        if (sin2t > 1.0) return 1.0;

        rPerpendicular = Math.pow((((n1 * cosThetaI) - (n2 * cosThetaT)) / ((n1 * cosThetaI) + (n2 * cosThetaT))), 2);
        rParallel = Math.pow((((n2 * cosThetaI) - (n1 * cosThetaT)) / ((n2 * cosThetaI) + (n1 * cosThetaT))), 2);

        return (rPerpendicular + rParallel) / 2.0;
    }

    // Return direction vector for reflected ray.
    public Vec reflectRay(Vec I, Vec N) {
        return ((N.scale(2)).scale(N.dotProduct(I.scale(-1)))).add(I);
    }

    public Vec refractRay(Vec I, Vec N, double n1, double n2) {
        double cosTheta = N.dotProduct(I.scale(-1));
        double n = n1 / n2;
        return I.scale(n).add(N.scale((n * cosTheta) - Math.sqrt(1 - (n * n * (1 - (cosTheta * cosTheta))))));
    }

    // Return the closest shape and the intersections of that shape and a ray.
    public ClosestShapeIntersections getClosestShapeIntersections(Ray ray, double traceMin, double traceMax) {
        Shape closestShape = null;
        RayIntersections rayIntersections;

        double closeIntersection = traceMax + 1;
        double intersection1;
        double intersection2;

        for (Shape shape : objects) {
            rayIntersections = shape.checkIntersect(ray);

            if (rayIntersections != null) {
                intersection1 = rayIntersections.getIntersection1();
                intersection2 = rayIntersections.getIntersection2();

                if (intersection1 >= traceMin && intersection1 <= traceMax && intersection1 < closeIntersection) {
                    closeIntersection = intersection1;
                    closestShape = shape;
                }

                if (intersection2 >= traceMin && intersection2 <= traceMax && intersection2 < closeIntersection) {
                    closeIntersection = intersection2;
                    closestShape = shape;
                }
            }
        }

        return new ClosestShapeIntersections(closestShape, closeIntersection);
//        else return new ClosestShapeIntersections(closestShape, farIntersection, closeIntersection);
    }

    // Compute the double to modify the lighting for a pixel to account for the lighting.
    public double computeLighting(Vec P, Vec N, Vec D, int specular) {
        ClosestShapeIntersections closestShapeIntersections;
        Shape shadowSphere;
        Ray shadowRay;
        double traceMax;
        Vec L, R;

        double i = 0.0;
        double nDotL, rDotD;

        for (Light light : lights) {
            if (light instanceof Ambient) {
                i += light.getIntensity();
            } else {
//                if (light instanceof Point) L = (((Point) light).getPosition()).sub(P).normalise();
                if (light instanceof Point) L = (((Point) light).getPosition()).sub(P);
                else L = ((Directional) light).getDirection();

                traceMax = L.length();
                L = L.normalise();

                // Shadow check
                shadowRay = new Ray(P, L);
                closestShapeIntersections = getClosestShapeIntersections(shadowRay, 0.0001, traceMax);
                shadowSphere = closestShapeIntersections.getShape();

                if (shadowSphere == null) {
                    // Diffuse lighting.
                    nDotL = N.dotProduct(L);

                    if (nDotL > 0) i += (light.getIntensity()) * nDotL / (N.length() * L.length());

                    // Specular lighting.
                    if (specular != -1) {
                        R = reflectRay(L, N);
                        rDotD = R.dotProduct(D);

                        if (rDotD > 0) {
                            i += light.getIntensity() * Math.pow(rDotD / (R.length() * D.length()), specular);
                        }
                    }
                }
            }
        }

        return i;
    }
}
