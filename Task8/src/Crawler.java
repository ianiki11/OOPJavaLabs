import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Crawler {
    private String startUrl;
    URLPool urlPool;
    private int maxDepth;
    private int numThreads;

    public Crawler(String startUrl, int maxDepth, int numThreads) throws MalformedURLException {
        this.startUrl = startUrl;
        URLDepthPair firstUrl = new URLDepthPair(this.startUrl, 0);
        this.urlPool = new URLPool(maxDepth);
        urlPool.setNextPair(firstUrl);
        this.numThreads = numThreads;
        this.maxDepth = maxDepth;
    }


    public void run() {
        for (int i = 0; i < numThreads; i++) {
            CrawlerTask c = new CrawlerTask(new URLPool(maxDepth));
            Thread t = new Thread(c);
            t.start();
            t.run();
        }
    }

    //throw new CrawlerException("error");
    public void printResult() {
        urlPool.printViewedList();
    }

}
