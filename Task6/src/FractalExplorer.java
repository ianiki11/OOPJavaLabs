import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay image;
    private FractalGenerator generator;
    private Rectangle2D.Double rectangle;
    private JComboBox<FractalGenerator> fractalComboBox;
    private JButton saveButton;
    private JButton resetButton;
    private int remainingRows;


    public FractalExplorer(int displaySize) {
        this.displaySize = displaySize;
        this.rectangle = new Rectangle2D.Double(0,0,0,0);
        this.generator = new Mandelbrot();
        generator.getInitialRange(this.rectangle);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");
        image = new JImageDisplay(this.displaySize, this.displaySize);

        fractalComboBox = new JComboBox<FractalGenerator>();
        fractalComboBox.addItem(new Mandelbrot());
        fractalComboBox.addItem(new Tricorn());
        fractalComboBox.addItem(new BurningShip());
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.add(fractalComboBox);

        JPanel buttonsPanel = new JPanel();
        resetButton = new JButton("Reset Display");
        resetButton.setActionCommand("reset");
        saveButton = new JButton("Save Image");
        saveButton.setActionCommand("save");
        buttonsPanel.add(resetButton);
        buttonsPanel.add(saveButton);

        JLabel label = new JLabel("Fractal: ");
        comboBoxPanel.add(label);

        ActionHandle action = new ActionHandle();
        fractalComboBox.addActionListener(action);
        resetButton.addActionListener(action);
        saveButton.addActionListener(action);
        MouseHandle mouse = new MouseHandle();
        image.addMouseListener(mouse);

        frame.setLayout(new BorderLayout());
        frame.add(comboBoxPanel, BorderLayout.NORTH);
        frame.add(this.image,BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        /*
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
        */
        enableUI(false);
        remainingRows = displaySize;
        for (int y=0; y<displaySize; y++) {
            FractalWorker worker = new FractalWorker(y);
            worker.execute();
        }
        this.image.repaint();
    }

    private void enableUI(boolean val){
        saveButton.setEnabled(val);
        resetButton.setEnabled(val);
        fractalComboBox.setEnabled(val);
    }

    public class ActionHandle implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand() == "reset") {
                generator.getInitialRange(rectangle);
                drawFractal();
            }
            else if (actionEvent.getSource() == (Object) fractalComboBox) {
                generator = (FractalGenerator) fractalComboBox.getSelectedItem();
                generator.getInitialRange(rectangle);
                drawFractal();
            }
            else if (actionEvent.getActionCommand() == "save") {
                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int result = fileChooser.showSaveDialog(image);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        javax.imageio.ImageIO.write(image.getBufferedImage(), "png", fileChooser.getSelectedFile());
                    } catch (NullPointerException | IOException e1) {
                        javax.swing.JOptionPane.showMessageDialog(image, e1.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    return;
                }
            }

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

    private class FractalWorker extends SwingWorker<Object, Object>{
        private int yCoord;
        private int[] rgbValues;

        public FractalWorker(int yCoord) {
            this.yCoord = yCoord;
        }

        public Object doInBackground() {
            rgbValues = new int[displaySize];
            double yCoord = FractalGenerator.getCoord(rectangle.y,
                    rectangle.y + rectangle.height, displaySize, this.yCoord);
            for (int x=0; x<displaySize; x++) {
                double xCoord = FractalGenerator.getCoord(rectangle.x,
                        rectangle.x + rectangle.width, displaySize, x);
                int numIterations = generator.numIterations(xCoord, yCoord);
                if (numIterations == -1) {
                    rgbValues[x] = 0;
                } else {
                    float hue = 0.7f + (float) numIterations / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    rgbValues[x] = rgbColor;
                }
            }
            return null;
        }

        public void done(){
            for (int x=0; x<displaySize; x++) {
                image.drawPixel(x, this.yCoord, this.rgbValues[x]);
                remainingRows -= 1;
            }
            image.repaint(0, 0, this.yCoord, displaySize, 1);
            if (remainingRows == 0) {
                enableUI(true);
            }
        }
    }

    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }
}
