public class Main {
    public static void main(String[] args){
        if (args.length != 2) {
            System.out.print("ERROR: Not found <start_URL> and <max_depth> argument");
            System.exit(1);
        }
        System.out.println("Hello");
        String startUrl;
        int maxDepth;
        try {
            startUrl = args[0];
            maxDepth = Integer.parseInt(args[1]);
            Crawler crawler = new Crawler(startUrl, maxDepth);
            crawler.run();
            crawler.printResult();
        }
        catch (CrawlerException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("ERROR. Incorrect arguments. First argument must be String <start_URL>; second - integer <max_depth>.");
            System.exit(1);
        };


    }
}
