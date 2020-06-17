package main.java.light;

import main.java.utilities.Vec;

public class Directional extends Light {
    private Vec direction;

    public Directional(double intensity, Vec direction) {
        super(intensity);

        this.direction = direction;
    }

    public Vec getDirection() {
        return direction;
    }
}

