package main.java.scene.Shapes;
import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.utilities.Vec;

public class Sphere extends Shape {
    private Vec center;
    private double radius;

    public Sphere(Vec center, double radius, Vec color, int specular, double reflective, boolean transparent, double rIndex) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
        this.transparent = transparent;
        this.rIndex = rIndex;
    }

    // Check where ray intersects with the sphere and return intersections if any.
    @Override
    public RayIntersections checkIntersect(Ray ray) {
        double k1, k2, k3, discriminant, intersection1, intersection2;

        Vec rayOrigin = ray.getOrigin();
        Vec rayDirection = ray.getDirection();
        Vec OC = rayOrigin.sub(center);

        k1 = rayDirection.dotProduct(rayDirection);
        k2 = 2 * OC.dotProduct(rayDirection);
        k3 = OC.dotProduct(OC) - (radius * radius);

        discriminant = (k2 * k2) - (4 * k1 * k3);

        if (discriminant < 0) {
            return null;
        }

        intersection1 = (-k2 + Math.sqrt(discriminant)) / (2 * k1);
        intersection2 = (-k2 - Math.sqrt(discriminant)) / (2 * k1);

        return new RayIntersections(intersection1, intersection2);
    }

    public Vec getCenter() {
        return center;
    }
}
