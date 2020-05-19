package main.java;

import main.java.Utilities.Vec;

public class Ray {
    Vec origin;
    Vec direction;

    public void setOrigin(Vec origin) {
        this.origin = origin;
    }

    public void setDirection(Vec direction) {
        this.direction = direction;
    }

    public Vec getOrigin() {
        return origin;
    }

    public Vec getDirection() {
        return direction;
    }
}
