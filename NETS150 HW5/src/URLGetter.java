import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class URLGetter {
    private URL url;
    private HttpURLConnection httpConnection;

    public URLGetter(String url) {
        try {
            this.url = new URL(url);
            URLConnection connection = this.url.openConnection();
            httpConnection = (HttpURLConnection) connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void printStatusCode() {
        try {
            int code = httpConnection.getResponseCode();
            String message = httpConnection.getResponseMessage();

            System.out.println(code + " : " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getContents() {
        ArrayList<String> contents = new ArrayList<String>();
        Scanner in;
        try {
            in = new Scanner(httpConnection.getInputStream());

            while (in.hasNextLine()) {
                String line = in.nextLine();
                contents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
}
