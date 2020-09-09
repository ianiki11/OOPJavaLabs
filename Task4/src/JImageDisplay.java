import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent{
    private BufferedImage image;

    public JImageDisplay(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        super.setPreferredSize(new Dimension(width, height));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void clearImage() {
        int black = 0;
        for (int x=0; x<this.image.getWidth(); x++) {
            for (int y=0; y<this.image.getHeight(); y++) {
                this.image.setRGB(x, y, black);
            }
        }
    }

    public void drawPixel(int x, int y, int rgbColor) {
        this.image.setRGB(x, y, rgbColor);
    }
}
