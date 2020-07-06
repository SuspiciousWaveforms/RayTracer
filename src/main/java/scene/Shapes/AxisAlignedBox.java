package main.java.scene.Shapes;

import main.java.rays.Ray;
import main.java.rays.RayIntersections;
import main.java.utilities.Vec;

class AxisAlignedBox extends Shape {
    AxisAlignedBox(Vec color, int specular, double reflective, boolean transparent, double rIndex) {
        super(color, specular, reflective, transparent, rIndex, true);
    }

    @Override
    public RayIntersections checkIntersect(Ray ray) {
        return null;
    }
}
