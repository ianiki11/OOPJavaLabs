import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay image;
    private FractalGenerator generator;
    private Rectangle2D.Double rectangle;

    public FractalExplorer(int displaySize) {
        this.displaySize = displaySize;
        this.rectangle = new Rectangle2D.Double(0,0,0,0);
        this.generator = new Mandelbrot();
        generator.getInitialRange(this.rectangle);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");
        image = new JImageDisplay(this.displaySize, this.displaySize);
        JButton resetButton = new JButton("Reset Display");

        ActionHandle action = new ActionHandle();
        resetButton.addActionListener(action);
        MouseHandle mouse = new MouseHandle();
        image.addMouseListener(mouse);

        frame.setLayout(new BorderLayout());
        frame.add(this.image,BorderLayout.CENTER);
        frame.add(resetButton, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        for (int x=0; x<this.displaySize; x++){
            for (int y=0; y<this.displaySize; y++){
                double xCoord = FractalGenerator.getCoord(this.rectangle.x,
                        this.rectangle.x + this.rectangle.width, this.displaySize, x);
                double yCoord = FractalGenerator.getCoord(rectangle.y,
                        rectangle.y + rectangle.height, displaySize, y);
                int numIterations = this.generator.numIterations(xCoord, yCoord);
                if (numIterations == -1) {
                    this.image.drawPixel(x, y, 0);
                } else {
                    float hue = 0.7f + (float) numIterations / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    this.image.drawPixel(x, y, rgbColor);
                }
            }
        }
        this.image.repaint();
    }

    public class ActionHandle implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            generator.getInitialRange(rectangle);
            drawFractal();
        }
    }

    public class MouseHandle extends MouseAdapter {
        public void mouseClicked(MouseEvent event) {
            double xCoord = FractalGenerator.getCoord(rectangle.x,
                    rectangle.x + rectangle.height, displaySize, event.getX());
            double yCoord = FractalGenerator.getCoord(rectangle.y,
                    rectangle.y + rectangle.width, displaySize, event.getY());
            generator.recenterAndZoomRange(rectangle, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }
}
