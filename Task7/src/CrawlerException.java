public class CrawlerException extends Exception {
    public CrawlerException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        //return super.getMessage();
        return "ERROR. Crawler error";
    }
}
