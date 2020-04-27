import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FOXScraper {
	static List<String> allArticleLinks = new ArrayList<>();
	static List<String> selectedArticleLinks = new ArrayList<>();

	public static void main(String[] args) throws IOException {

		get10Articles("https://www.foxnews.com");
	}

	public static List<String> get10Articles(String url) throws IOException {

		Document document = Jsoup.connect(url).get();
		Elements links = document.select("a[href]");

		for (Element link : links) {
			allArticleLinks.add(link.attr("href"));
		}

		int counter = 0;
		int index = 0;
		for (int i = 0; i < allArticleLinks.size(); i++) {
			if (allArticleLinks.get(i).contains("mailto:")) {
				index = i + 1;
				break;
			}
		}

		while (counter != 10) {
			if ((!allArticleLinks.get(index).contains("video")) && allArticleLinks.get(index).length() > 50) {
				if (!selectedArticleLinks.contains(allArticleLinks.get(index))) {
					selectedArticleLinks.add(allArticleLinks.get(index));
					System.out.println(allArticleLinks.get(index));
					counter++;
				}

			}

			index++;

		}
		return selectedArticleLinks;
	}

//		Document doc = Jsoup.parse(fullPage);
//		String title = doc.title();
//		String body = doc.body().text();
//
//		System.out.printf("Title: %s%n", title);
//		System.out.printf("Body: %s", body.toString());
//		for (String line : page) {
//			System.out.println(line);
//		}
}
