package main.java;

import java.util.ArrayList;
import javax.swing.JPanel;
import main.java.Shapes.Shape;

public class RayTracer extends JPanel {
    public static void main(String[] args) {
        Scene scene = new Scene();

        ArrayList<Shape> shapes = scene.getObjects();
    }
}
