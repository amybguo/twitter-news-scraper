import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FoxScraper {

    static List<String> allArticleLinks = new ArrayList<>();
    static Map<String, String> topTenFOXArticles = new HashMap<>();
    static Map<String, String> worldNewsArticles = new HashMap<>();
    static Map<String, String> usNewsArticles = new HashMap<>();
    static Map<String, String> politicsArticles = new HashMap<>();
    static Map<String, String> entertainmentArticles = new HashMap<>();

    public static void main(String[] args) throws IOException {

        getArticles("https://www.foxnews.com", 10);
        
        comparisons();
        Analysis.getSentiments(topTenFOXArticles);
        Analysis.getSentiments(topTenFOXArticles);
    }
    
    public static Map<String, String> getTopTen () {
    	return topTenFOXArticles;
    }

    public static void getArticles(String url, int amount) throws IOException {

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
                if (!topTenFOXArticles.containsKey(allArticleLinks.get(index))) {
                    if (allArticleLinks.get(index).substring(0, 2).equals("//")) {
                        topTenFOXArticles.put("https:" + allArticleLinks.get(index),
                                getArticleText("https:" + allArticleLinks.get(index)));
                    } else {
                        topTenFOXArticles.put(allArticleLinks.get(index), getArticleText(allArticleLinks.get(index)));
                    }
                    counter++;
                }
            }
            index++;
            if (index >= allArticleLinks.size()) {
                break;
            }
        }

        int target = 4;
        counter = 0;
        int index2 = index;
        while (counter != target) {
            if ((!allArticleLinks.get(index2).contains("video")) && allArticleLinks.get(index2).length() > 50) {
                if (!topTenFOXArticles.containsKey(allArticleLinks.get(index2))) {
                    if (allArticleLinks.get(index2).contains("/us/")) {
                        if (allArticleLinks.get(index2).substring(0, 2).equals("//")) {
                            usNewsArticles.put("https:" + allArticleLinks.get(index2),
                                    getArticleText("https:" + allArticleLinks.get(index2)));
                        } else {
                            usNewsArticles.put(allArticleLinks.get(index2),
                                    getArticleText(allArticleLinks.get(index2)));
                        }
                        counter++;
                    }
                }
            }
            index2++;
            if (index2 >= allArticleLinks.size()) {
                break;
            }
        }

        counter = 0;
        index2 = index;
        while (counter != target) {
            if ((!allArticleLinks.get(index2).contains("video")) && allArticleLinks.get(index2).length() > 50) {
                if (!topTenFOXArticles.containsKey(allArticleLinks.get(index2))) {
                    if (allArticleLinks.get(index2).contains("/entertainment/")) {
                        if (allArticleLinks.get(index2).substring(0, 2).equals("//")) {
                            entertainmentArticles.put("https:" + allArticleLinks.get(index2),
                                    getArticleText("https:" + allArticleLinks.get(index2)));
                        } else {
                            entertainmentArticles.put(allArticleLinks.get(index2),
                                    getArticleText(allArticleLinks.get(index2)));
                        }
                        counter++;
                    }
                }
            }
            index2++;
            if (index2 >= allArticleLinks.size()) {
                break;
            }
        }

        counter = 0;
        index2 = index;
        while (counter != target) {
            if ((!allArticleLinks.get(index2).contains("video")) && allArticleLinks.get(index2).length() > 50) {
                if (!topTenFOXArticles.containsKey(allArticleLinks.get(index2))) {
                    if (allArticleLinks.get(index2).contains("/world/")) {
                        if (allArticleLinks.get(index2).substring(0, 2).equals("//")) {
                            worldNewsArticles.put("https:" + allArticleLinks.get(index2),
                                    getArticleText("https:" + allArticleLinks.get(index2)));
                        } else {
                            worldNewsArticles.put(allArticleLinks.get(index2),
                                    getArticleText(allArticleLinks.get(index2)));
                        }
                        counter++;
                    }
                }
            }
            index2++;
            if (index2 >= allArticleLinks.size()) {
                break;
            }
        }

        counter = 0;
        index2 = index;
        while (counter != target) {
            if ((!allArticleLinks.get(index2).contains("video")) && allArticleLinks.get(index2).length() > 50) {
                if (!topTenFOXArticles.containsKey(allArticleLinks.get(index2))) {
                    if (allArticleLinks.get(index2).contains("/politics/")) {
                        if (allArticleLinks.get(index2).substring(0, 2).equals("//")) {
                            politicsArticles.put("https:" + allArticleLinks.get(index2),
                                    getArticleText("https:" + allArticleLinks.get(index2)));
                        } else {
                            politicsArticles.put(allArticleLinks.get(index2),
                                    getArticleText(allArticleLinks.get(index2)));
                        }
                        counter++;
                    }
                }
            }
            index2++;
            if (index2 >= allArticleLinks.size()) {
                break;
            }
        }

    }

    public static String getArticleText(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        Elements nameElements2 = doc.select("p");

        String text = nameElements2.text().substring(161);
        return text;
    }

    public static void comparisons() {

        String articleText = "";
        for (Entry<String, String> e : topTenFOXArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article top10FOX = new Article(articleText, "Top 10 FOX Articles");

        articleText = "";
        for (Entry<String, String> e : usNewsArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article USFOX = new Article(articleText, "FOX US Articles");

        articleText = "";
        for (Entry<String, String> e : entertainmentArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article entertainmentFOX = new Article(articleText, "FOX Entertainment Articles");

        articleText = "";
        for (Entry<String, String> e : worldNewsArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article worldFOX = new Article(articleText, "FOX World Articles");

        articleText = "";
        for (Entry<String, String> e : politicsArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article politicsFOX = new Article(articleText, "FOX Politics Articles");

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
