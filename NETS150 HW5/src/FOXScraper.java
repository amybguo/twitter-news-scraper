import java.util.ArrayList;

public class FOXScraper {

    public static void main(String[] args) {
        URLGetter url = new URLGetter("https://www.foxnews.com");
        url.printStatusCode();
        ArrayList<String> page = url.getContents();
        for (String line : page) {
            System.out.println(line);
        }
    }
    
    
}