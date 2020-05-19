package main.java.scene;

import java.util.ArrayList;
import main.java.scene.Shapes.Shape;
import main.java.utilities.Vec;

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
