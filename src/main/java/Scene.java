package main.java;

import java.util.ArrayList;
import main.java.Shapes.Shape;

public class Scene {
    ArrayList<Shape> objects = new ArrayList<>();

    public Scene() {
    }

    public void addShape(Shape shape) {
        objects.add(shape);
    }

    public ArrayList<Shape> getObjects() {
        return objects;
    }
}
