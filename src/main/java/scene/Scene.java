package main.java.scene;

import java.util.ArrayList;
import java.util.List;

import main.java.light.Light;
import main.java.scene.Shapes.Shape;

public class Scene {
    List<Shape> objects = new ArrayList<>();
    List<Light> lights = new ArrayList<>();

    public void addShape(Shape shape) {
        objects.add(shape);
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public List<Shape> getObjects() {
        return objects;
    }

    public List<Light> getLights() {
        return lights;
    }
}

