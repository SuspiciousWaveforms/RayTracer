import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RayTracer extends JPanel {

    public Vec O = new Vec(0, 0, 0);

    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.getContentPane().add(new RayTracer());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(200, 200);
//        frame.setVisible(true);
    }

    // Determine the square on the viewport that corresponds to the pixel on the canvas.
    // Vx = Cx * (Vw / Cw)
    // Vy = Cy * (Vh / Ch)
    // Vz = d
    public float canvasToViewPort(float x, float y) {
        return 0;
    }


}
