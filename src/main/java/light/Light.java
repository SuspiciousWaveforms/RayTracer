package main.java.light;

public abstract class Light {
    private double intensity;

    Light(double intensity) {
        this.intensity = intensity;
    }

    public double getIntensity() {
        return intensity;
    }
}
