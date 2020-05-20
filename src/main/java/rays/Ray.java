package main.java.rays;

import main.java.utilities.Vec;

public class Ray {
    Vec origin;
    Vec direction;

    public Ray(Vec origin, Vec direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec getOrigin() {
        return origin;
    }

    public Vec getDirection() {
        return direction;
    }
}
