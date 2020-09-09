import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CrawlerTask implements Runnable {
    URLPool urlPool;
    private final String HREF_TAG = "<a href=\"http";


    public CrawlerTask(URLPool urlPool) {
        this.urlPool = urlPool;
    }

    @Override
    public void run() {
        while (!urlPool.isEmpty()) {
            try {
                URLDepthPair urlPair = urlPool.getPair();
                boolean pass = false;
                if (urlPool.isViewed(urlPair.getFullPath())) {
                    pass = true;
                }
                if (pass) {
                    continue;
                }

                urlPool.setViewedPair(new URLDepthPair(urlPair.getFullPath(), urlPair.getDepth()));

                URL url = urlPair.getUrl();

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.flush();
                out.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    int idx = inputLine.indexOf(HREF_TAG);
                    if (idx > 0) {
                        String href = inputLine.trim().substring(HREF_TAG.length());
                        idx = href.indexOf('\"');
                        href = "http" + href.substring(0, idx);
                        urlPool.setNextPair(new URLDepthPair(href, urlPair.getDepth() + 1));
                    }
                }
                in.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
