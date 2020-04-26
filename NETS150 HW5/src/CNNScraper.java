import java.util.ArrayList;

public class CNNScraper {

    public CNNScraper() {
        URLGetter url = new URLGetter("https://www.cnn.com");
        url.printStatusCode();
        ArrayList<String> page = url.getContents();
        for (String line : page) {
            System.out.println(line);
        }
    }
    
    

}
