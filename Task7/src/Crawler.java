import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Crawler {
    private String startUrl;
    private int maxDepth;
    private LinkedList<URLDepthPair> urlsList = new LinkedList<URLDepthPair>();
    private LinkedList<URLDepthPair> viewedList = new LinkedList<URLDepthPair>();
    private final String HREF_TAG = "<a href=\"http";
    private final String HTTP = "http://";

    public Crawler(String startUrl, int maxDepth) throws MalformedURLException {
        this.startUrl = startUrl;
        this.maxDepth = maxDepth;
        URLDepthPair firstUrl = new URLDepthPair(this.startUrl, 0);
        this.urlsList.add(firstUrl);
    }


    public void run() throws CrawlerException, IOException {
        while (!urlsList.isEmpty()) {
            URLDepthPair urlPair = urlsList.getFirst();
            urlsList.removeFirst();
            boolean pass = false;
            for (URLDepthPair viewedUrl: viewedList) {
                if (urlPair.getDepth() > maxDepth || urlPair.getFullPath().equals(viewedUrl.getFullPath())) {
                    pass = true;
                }
            }
            if (pass) {continue;}
            viewedList.add(new URLDepthPair(urlPair.getFullPath(), urlPair.getDepth()));

            URL url = urlPair.getUrl();
            HttpURLConnection con;
            try {
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.flush();
                out.close();
            } catch (Exception err) {
                System.out.println(err);
                continue;
            }


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                int idx = inputLine.indexOf(HREF_TAG);
                if (idx > 0) {
                    String refStart = inputLine.substring(idx + HREF_TAG.length());
                    String ref = "http" + refStart.substring(0, refStart.indexOf('"'));
                    this.urlsList.add(new URLDepthPair(ref, urlPair.getDepth()+1));
                }
            }
            in.close();
        }

        //throw new CrawlerException("error");
    }
    public void printResult() {
        for (URLDepthPair viewedUrl: viewedList) {
            System.out.println(viewedUrl);
        }
    }

}
