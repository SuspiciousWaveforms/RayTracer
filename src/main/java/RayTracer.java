package main.java;

import java.util.ArrayList;
import javax.swing.JPanel;
import main.java.Shapes.Shape;
import main.java.Utilities.Vec;

public class RayTracer extends JPanel {

    Vec camera = new Vec(0, 0, 0);
    private int canvasWidth = 200;
    private int canvasHeight = 200;
    private int viewportWidth = 200;
    private int viewportHeight = 200;
    private int distanceToViewport = 1;

    public static void main(String[] args) {
        Scene scene = new Scene();
        ArrayList<Shape> shapes = scene.getObjects();
    }

    // Determine the square on the viewport corresponding to a pixel.
    public Vec canvasToViewport(int x, int y) {
        return new Vec(x * (double) viewportWidth / (double) canvasWidth,
                y * (double) viewportHeight / (double) canvasHeight,
                distanceToViewport);
    }
}
