package main.java.light;

import main.java.utilities.Vec;

public class Point extends Light {
    private Vec position;

    public Point(double intensity, Vec position) {
        this.intensity = intensity;
        this.position = position;
    }

    public Vec getPosition() {
        return position;
    }
}
