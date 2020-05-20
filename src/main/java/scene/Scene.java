package main.java.scene;

import java.util.ArrayList;

import main.java.light.Light;
import main.java.scene.Shapes.Shape;

public class Scene {
    ArrayList<Shape> objects = new ArrayList<>();
    ArrayList<Light> lights = new ArrayList<>();

    public Scene() {
    }

    public void addShape(Shape shape) {
        objects.add(shape);
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public ArrayList<Shape> getObjects() {
        return objects;
    }

    public ArrayList<Light> getLights() {
        return lights;
    }
}
