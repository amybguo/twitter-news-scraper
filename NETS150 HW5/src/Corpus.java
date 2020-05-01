
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class represents a corpus of Articles. It will create an inverted index
 * for these Articles.
 * 
 * @author swapneel
 *
 */
public class Corpus {

	/**
	 * An arraylist of all Articles in the corpus.
	 */
	private ArrayList<Article> Articles;

	/**
	 * The inverted index. It will map a term to a set of Articles that contain that
	 * term.
	 */
	private HashMap<String, Set<Article>> invertedIndex;

	/**
	 * The constructor - it takes in an arraylist of Articles. It will generate the
	 * inverted index based on the Articles.
	 * 
	 * @param Articles2 the list of Articles
	 */
	public Corpus(ArrayList<Article> Articles2) {
		this.Articles = Articles2;
		invertedIndex = new HashMap<String, Set<Article>>();

		createInvertedIndex();
	}

	/**
	 * This method will create an inverted index.
	 */
	private void createInvertedIndex() {
		System.out.println("Creating the inverted index");

		for (Article Article : Articles) {
			Set<String> terms = Article.getTermList();

			for (String term : terms) {
				if (invertedIndex.containsKey(term)) {
					Set<Article> list = invertedIndex.get(term);
					list.add(Article);
				} else {
					Set<Article> list = new TreeSet<Article>();
					list.add(Article);
					invertedIndex.put(term, list);
				}
			}
		}
	}

	/**
	 * This method returns the idf for a given term.
	 * 
	 * @param term a term in a Article
	 * @return the idf for the term
	 */
	public double getInverseArticleFrequency(String term) {
		if (invertedIndex.containsKey(term)) {
			double size = Articles.size();
			Set<Article> list = invertedIndex.get(term);
			double ArticleFrequency = list.size();

			return Math.log10(size / ArticleFrequency);
		} else {
			return 0;
		}
	}

	/**
	 * @return the Articles
	 */
	public ArrayList<Article> getArticles() {
		return Articles;
	}

	/**
	 * @return the invertedIndex
	 */
	public HashMap<String, Set<Article>> getInvertedIndex() {
		return invertedIndex;
	}
}