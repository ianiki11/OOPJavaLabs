import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;
    public void getInitialRange(Rectangle2D.Double rectangle) {
        rectangle.x = -2.0;
        rectangle.y = -1.5;
        rectangle.height = 3.0;
        rectangle.width = 3.0;
    }

    public int numIterations(double x, double y) {
        double re = x;
        double im = y;
        double nextRe;
        double nextIm;

        for (int iteration=0; iteration < MAX_ITERATIONS; iteration++) {
            nextRe = x + re*re - im*im;
            nextIm = y + 2*re*im;
            re = nextRe;
            im = nextIm;
            if (re*re + im*im > 4){
                return iteration;
            }
        }
        return -1;
    }

    public String toString() {
        return "Mandelbrot";
    }
}
