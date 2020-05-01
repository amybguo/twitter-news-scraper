import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FoxScraper {

	static List<String> allArticleLinks = new ArrayList<>();
	static List<String> selectedArticleLinks = new ArrayList<>();
	static List<String> articleContents = new ArrayList<>();
	static List<String> articleHeadlines = new ArrayList<>();

	public static void main(String[] args) throws IOException {

		getArticles("https://www.foxnews.com", 10);

		for (int i = 0; i < selectedArticleLinks.size(); i++) {
			System.out.println(selectedArticleLinks.get(i));
		}
		for (int i = 0; i < selectedArticleLinks.size(); i++) {
			getArticleText(selectedArticleLinks.get(i));
		}

		comparisons();
		// System.out.println("Article contents size: " + articleContents.size());

//		for (int i = 0; i < selectedArticleLinks.size(); i++) {
//			System.out.println(articleContents.get(i));
//		}
	}

	public static List<String> getArticles(String url, int amount) throws IOException {

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

		while (counter != amount) {
			if ((!allArticleLinks.get(index).contains("video")) && allArticleLinks.get(index).length() > 50) {
				if (!selectedArticleLinks.contains(allArticleLinks.get(index))) {
					selectedArticleLinks.add(allArticleLinks.get(index));
					// System.out.println(allArticleLinks.get(index));
					counter++;
				}
			}
			index++;
		}

		counter = 0;
		int index2 = index;
		while (counter != 4) {
			if ((!allArticleLinks.get(index2).contains("video")) && allArticleLinks.get(index2).length() > 50) {
				if (!selectedArticleLinks.contains(allArticleLinks.get(index2))) {
					if (allArticleLinks.get(index2).contains("/us/")) {
						selectedArticleLinks.add(allArticleLinks.get(index2));
						// System.out.println(allArticleLinks.get(index));
						counter++;
					}
				}
			}
			index2++;
		}

		counter = 0;
		index2 = index;
		while (counter != 4) {
			if ((!allArticleLinks.get(index2).contains("video")) && allArticleLinks.get(index2).length() > 50) {
				if (!selectedArticleLinks.contains(allArticleLinks.get(index2))) {
					if (allArticleLinks.get(index2).contains("/entertainment/")) {
						selectedArticleLinks.add(allArticleLinks.get(index2));
						// System.out.println(allArticleLinks.get(index));
						counter++;
					}
				}
			}
			index2++;
		}

		counter = 0;
		index2 = index;
		while (counter != 4) {
			if ((!allArticleLinks.get(index2).contains("video")) && allArticleLinks.get(index2).length() > 50) {
				if (!selectedArticleLinks.contains(allArticleLinks.get(index2))) {
					if (allArticleLinks.get(index2).contains("/world/")) {
						selectedArticleLinks.add(allArticleLinks.get(index2));
						// System.out.println(allArticleLinks.get(index));
						counter++;
					}
				}
			}
			index2++;
		}

		counter = 0;
		index2 = index;
		while (counter != 4) {
			if ((!allArticleLinks.get(index2).contains("video")) && allArticleLinks.get(index2).length() > 50) {
				if (!selectedArticleLinks.contains(allArticleLinks.get(index2))) {
					if (allArticleLinks.get(index2).contains("/politics/")) {
						selectedArticleLinks.add(allArticleLinks.get(index2));
						// System.out.println(allArticleLinks.get(index));
						counter++;
					}
				}
			}
			index2++;
		}

		for (int i = 0; i < selectedArticleLinks.size(); i++) {
			if (selectedArticleLinks.get(i).substring(0, 2).equals("//")) {
				String placeholder = "https:" + selectedArticleLinks.get(i);
				selectedArticleLinks.set(i, placeholder);
				System.out.println(selectedArticleLinks.get(i));
			}
		}

		return selectedArticleLinks;
	}

	public static void getArticleText(String url) throws IOException {

		Document doc = Jsoup.connect(url).get();

		String title = doc.title();

		Elements nameElements2 = doc.select("p");

		// System.out.printf("Title: %s%n", title);

		String placeholder = nameElements2.text().substring(161);
		articleContents.add(placeholder);
		articleHeadlines.add(title);

	}

	public static void comparisons() {

		String placeholder = "";

		for (int i = 0; i < 10; i++) {
			placeholder = placeholder + articleContents.get(i);
		}

		Article top10FOX = new Article(placeholder, "Top 10 FOX Articles");

		placeholder = "";

		for (int i = 10; i < 14; i++) {
			placeholder = placeholder + articleContents.get(i);
		}
		Article USFOX = new Article(placeholder, "FOX US Articles");

		placeholder = "";
		for (int i = 14; i < 18; i++) {
			placeholder = placeholder + articleContents.get(i);
		}
		Article entertainmentFOX = new Article(placeholder, "FOX Entertainment Articles");

		placeholder = "";
		for (int i = 18; i < 22; i++) {
			placeholder = placeholder + articleContents.get(i);
		}
		Article worldFOX = new Article(placeholder, "FOX World Articles");

		placeholder = "";
		for (int i = 22; i < 26; i++) {
			placeholder = placeholder + articleContents.get(i);
		}
		Article politicsFOX = new Article(placeholder, "FOX Politics Articles");

		ArrayList<Article> documents = new ArrayList<Article>();
		documents.add(top10FOX);
		documents.add(USFOX);
		documents.add(entertainmentFOX);
		documents.add(worldFOX);
		documents.add(politicsFOX);
		Corpus corpus = new Corpus(documents);
		VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
		for (int i = 0; i < documents.size(); i++) {
			for (int j = i + 1; j < documents.size(); j++) {
				Article doc1 = documents.get(i);
				Article doc2 = documents.get(j);
				System.out.println("\nComparing " + doc1.getName() + " and " + doc2.getName());
				System.out.println(vectorSpace.cosineSimilarity(doc1, doc2));
			}
		}
	}

}
