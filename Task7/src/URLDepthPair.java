import java.net.URL;
import java.net.MalformedURLException;

public class URLDepthPair {
    private final String URL_PREFIX = "http://";

    private URL url;
    private int depth;

    public URLDepthPair(String url, int depth) throws MalformedURLException {
        this.url = new URL(url);
        this.depth = depth;
    }

    @Override
    public String toString() {
        return this.url.getPath() + ' ' + this.depth;
    }

    public URL getUrl(){
        return url;
    }

    public String getPath() {
        return url.getPath();
    }

    public int getDepth(){
        return this.depth;
    }

    public String getFullPath() {
        return url.toString();
    }

    public boolean checkUrl(String url){
        return url.startsWith(URL_PREFIX);
    }

    public String getHost() {
        return url.getHost();
    }
}
