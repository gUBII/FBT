import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FractalAnimation extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int MAX_ITERATIONS = 100;

    private double zoom = 1.0;
    private double offsetX = -0.5;
    private double offsetY = 0.0;

    private BufferedImage image;

    public FractalAnimation() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double zx = 0;
                double zy = 0;
                double cx = (x - WIDTH / 2.0) / (0.5 * zoom * WIDTH) + offsetX;
                double cy = (y - HEIGHT / 2.0) / (0.5 * zoom * HEIGHT) + offsetY;
                int iteration = 0;
                while (zx * zx + zy * zy < 4 && iteration < MAX_ITERATIONS) {
                    double temp = zx * zx - zy * zy + cx;
                    zy = 2.0 * zx * zy + cy;
                    zx = temp;
                    iteration++;
                }
                if (iteration == MAX_ITERATIONS) {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    float hue = (float) iteration / MAX_ITERATIONS;
                    Color color = Color.getHSBColor(hue, 1, 1);
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }
        g.drawImage(image, 0, 0, this);
    }

    public void animateFractal() {
        while (true) {
            offsetX += 0.01;
            offsetY += 0.01;
            

            repaint();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        FractalAnimation fractal = new FractalAnimation();
        frame.add(fractal);
        frame.setVisible(true);

        fractal.animateFractal();
    }
}
