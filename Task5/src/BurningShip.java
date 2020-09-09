import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator{
    public static final int MAX_ITERATIONS = 2000;
    public void getInitialRange(Rectangle2D.Double rectangle) {
        rectangle.x = -2.0;
        rectangle.y = -2.5;
        rectangle.height = 4.0;
        rectangle.width = 4.0;
    }

    public int numIterations(double x, double y) {
        double re = x;
        double im = y;
        double nextRe;
        double nextIm;

        for (int iteration=0; iteration < MAX_ITERATIONS; iteration++) {
            nextRe = x + Math.abs(re) * Math.abs(re) - Math.abs(im) * Math.abs(im);
            nextIm = y + 2 * Math.abs(re) * Math.abs(im);
            re = nextRe;
            im = nextIm;
            if (re*re + im*im > 4){
                return iteration;
            }
        }
        return -1;
    }
    public String toString() {
        return "Burning Ship";
    }
}
