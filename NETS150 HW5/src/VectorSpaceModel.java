
import java.util.HashMap;
import java.util.Set;

/**
 * This class implements the Vector-Space model. It takes a corpus and creates
 * the tf-idf vectors for each Article.
 * 
 * @author swapneel
 *
 */
public class VectorSpaceModel {

	/**
	 * The corpus of Articles.
	 */
	private Corpus corpus;

	/**
	 * The tf-idf weight vectors. The hashmap maps a Article to another hashmap. The
	 * second hashmap maps a term to its tf-idf weight for this Article.
	 */
	private HashMap<Article, HashMap<String, Double>> tfIdfWeights;

	/**
	 * The constructor. It will take a corpus of Articles. Using the corpus, it will
	 * generate tf-idf vectors for each Article.
	 * 
	 * @param corpus the corpus of Articles
	 */
	public VectorSpaceModel(Corpus corpus) {
		this.corpus = corpus;
		tfIdfWeights = new HashMap<Article, HashMap<String, Double>>();

		createTfIdfWeights();
	}

	/**
	 * This creates the tf-idf vectors.
	 */
	private void createTfIdfWeights() {
		System.out.println("Creating the tf-idf weight vectors");
		Set<String> terms = corpus.getInvertedIndex().keySet();

		for (Article Article : corpus.getArticles()) {
			HashMap<String, Double> weights = new HashMap<String, Double>();

			for (String term : terms) {
				double tf = Article.getTermFrequency(term);
				double idf = corpus.getInverseArticleFrequency(term);

				double weight = tf * idf;

				weights.put(term, weight);
			}
			tfIdfWeights.put(Article, weights);
		}
	}

	/**
	 * This method will return the magnitude of a vector.
	 * 
	 * @param Article the Article whose magnitude is calculated.
	 * @return the magnitude
	 */
	private double getMagnitude(Article Article) {
		double magnitude = 0;
		HashMap<String, Double> weights = tfIdfWeights.get(Article);

		for (double weight : weights.values()) {
			magnitude += weight * weight;
		}

		return Math.sqrt(magnitude);
	}

	/**
	 * This will take two Articles and return the dot product.
	 * 
	 * @param d1 Article 1
	 * @param d2 Article 2
	 * @return the dot product of the Articles
	 */
	private double getDotProduct(Article d1, Article d2) {
		double product = 0;
		HashMap<String, Double> weights1 = tfIdfWeights.get(d1);
		HashMap<String, Double> weights2 = tfIdfWeights.get(d2);

		for (String term : weights1.keySet()) {
			product += weights1.get(term) * weights2.get(term);
		}

		return product;
	}

	/**
	 * This will return the cosine similarity of two Articles. This will range from
	 * 0 (not similar) to 1 (very similar).
	 * 
	 * @param d1 Article 1
	 * @param d2 Article 2
	 * @return the cosine similarity
	 */
	public double cosineSimilarity(Article d1, Article d2) {
		return getDotProduct(d1, d2) / (getMagnitude(d1) * getMagnitude(d2));
	}
}