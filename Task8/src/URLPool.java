import java.util.LinkedList;

public class URLPool {
    private LinkedList<URLDepthPair> urlsList = new LinkedList<URLDepthPair>();
    private LinkedList<URLDepthPair> viewedList = new LinkedList<URLDepthPair>();
    private int maxDepth;

    public URLPool(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public boolean isEmpty(){
        return urlsList.isEmpty();
    }

    public URLDepthPair getPair() {
        URLDepthPair pair = urlsList.getFirst();
        urlsList.removeFirst();
        return pair;
    }

    public boolean isViewed(String url) {
        boolean alreadyView = false;
        for (URLDepthPair pair: viewedList) {
            if (pair.getFullPath().equals(url)) {
                alreadyView = true;
            }
        }
        return alreadyView;
    }

    public void setViewedPair(URLDepthPair pair) {
        viewedList.add(pair);
    }
    public void setNextPair(URLDepthPair pair) {
        if (!(pair.getDepth() > maxDepth)) {
            urlsList.add(pair);
        }
    }
    public void printViewedList() {
        for (URLDepthPair viewedUrl: viewedList) {
            System.out.println(viewedUrl);
        }
    }
}

