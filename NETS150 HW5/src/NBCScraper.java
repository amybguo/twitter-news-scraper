import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NBCScraper {

    static List<String> allArticleLinks = new ArrayList<>();
    static Map<String, String> topTenNBCArticles = new HashMap<>();
    static Map<String, String> worldNewsArticles = new HashMap<>();
    static Map<String, String> usNewsArticles = new HashMap<>();
    static Map<String, String> politicsArticles = new HashMap<>();
    static Map<String, String> businessArticles = new HashMap<>();

    public static void main(String[] args) throws IOException {

        getArticles("https://www.nbcnews.com", 10);
        comparisons();
        Analysis.getSentiments(topTenNBCArticles);
        
        Analysis.getSentiments(topTenNBCArticles);

    }

    public static void getArticles(String url, int amount) throws IOException {

        Document document = Jsoup.connect(url).get();

        Elements links = document.select("a[href]");

        for (Element link : links) {
            allArticleLinks.add(link.attr("href"));
            System.out.println(link);
        }

        int counter = 0;
        int index = 0;
        for (int i = 0; i < allArticleLinks.size(); i++) {
            if (allArticleLinks.get(i).contains("share-icon")) {
                index = i + 1;
                break;
            }
        }

        while (counter != amount) {
            if ((!allArticleLinks.get(index).contains("_tps")) && allArticleLinks.get(index).length() > 60) {
                if (!topTenNBCArticles.containsKey(allArticleLinks.get(index))) {
                    topTenNBCArticles.put(allArticleLinks.get(index), getArticleText(allArticleLinks.get(index)));
                    counter++;
                }
            }
            index++;
            if (index >= allArticleLinks.size()) {
                break;
            }
        }

        int target = 3;
        counter = 0;
        int index2 = index;
        while (counter != target) {
            if ((!allArticleLinks.get(index2).contains("_tps")) && allArticleLinks.get(index2).length() > 60) {
                if (!topTenNBCArticles.containsKey(allArticleLinks.get(index2))) {
                    if (allArticleLinks.get(index2).contains("/us-news/")) {
                        usNewsArticles.put(allArticleLinks.get(index2), getArticleText(allArticleLinks.get(index2)));
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
            if ((!allArticleLinks.get(index2).contains("_tps")) && allArticleLinks.get(index2).length() > 60) {
                if (!topTenNBCArticles.containsKey(allArticleLinks.get(index2))) {
                    if (allArticleLinks.get(index2).contains("/business/")) {
                        businessArticles.put(allArticleLinks.get(index2), getArticleText(allArticleLinks.get(index2)));
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
            if ((!allArticleLinks.get(index2).contains("_tps")) && allArticleLinks.get(index2).length() > 60) {
                if (!topTenNBCArticles.containsKey(allArticleLinks.get(index2))) {
                    if (allArticleLinks.get(index2).contains("/world/")) {
                        worldNewsArticles.put(allArticleLinks.get(index2), getArticleText(allArticleLinks.get(index2)));
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
            if ((!allArticleLinks.get(index2).contains("_tps")) && allArticleLinks.get(index2).length() > 60) {
                if (!topTenNBCArticles.containsKey(allArticleLinks.get(index2))) {
                    if (allArticleLinks.get(index2).contains("/politics/")) {
                        politicsArticles.put(allArticleLinks.get(index2), getArticleText(allArticleLinks.get(index2)));
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

        String text = nameElements2.text().substring(0, nameElements2.text().length() - 21);
        return text;
    }

    public static void comparisons() {

        String articleText = "";
        for (Entry<String, String> e : topTenNBCArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article top10NBC = new Article(articleText, "Top 10 NBC Articles");

        articleText = "";
        for (Entry<String, String> e : usNewsArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article USNBC = new Article(articleText, "NBC US Articles");

        articleText = "";
        for (Entry<String, String> e : businessArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article businessNBC = new Article(articleText, "NBC Business Articles");

        articleText = "";
        for (Entry<String, String> e : worldNewsArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article worldNBC = new Article(articleText, "NBC World Articles");

        articleText = "";
        for (Entry<String, String> e : politicsArticles.entrySet()) {
            articleText += " " + e.getValue();
        }
        Article politicsNBC = new Article(articleText, "NBC Politics Articles");

        ArrayList<Article> documents = new ArrayList<Article>();
        documents.add(top10NBC);
        documents.add(USNBC);
        documents.add(businessNBC);
        documents.add(worldNBC);
        documents.add(politicsNBC);
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
