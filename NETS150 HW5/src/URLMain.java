import java.util.ArrayList;

public class URLMain {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        URLGetter url = new URLGetter("https://www.cis.upenn.edu");
        url.printStatusCode();
        ArrayList<String> page = url.getContents();
        for (String line : page) {
            System.out.println(line);
        }
    }
}
