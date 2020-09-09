import java.io.InputStream;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        if (args.length != 3) {
            System.out.print("ERROR: Not found <start_URL>, <max_depth> and <num_threads> argument");
            System.exit(1);
        }
        System.out.println("Hello");

        String startUrl;
        int maxDepth;
        int numThreads;

        //try {
        startUrl = args[0];
        maxDepth = Integer.parseInt(args[1]);
        numThreads = Integer.parseInt(args[2]);

        Crawler crawler = new Crawler(startUrl, maxDepth, numThreads);
        crawler.run();
        crawler.printResult();
        //}

        /*catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("ERROR. Incorrect arguments. First argument must be String <start_URL>; second - integer <max_depth>.");
            System.exit(1);
        };*/


    }
}
