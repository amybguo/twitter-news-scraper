import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.models.*;

public class Analysis {

	// Testing out the analyze function :)
	public static void main(String[] args) throws IOException {
		System.out.println(analyze(FoxScraper.getArticleText("https://www.foxnews.com/world/kenya-floods-landslides-heavy-rain-east-africa-severe-weather-displaced")));
		
	}
	
	//0 - Very Negative 
	//1 - Negative
	//2 - Neutral
	//3 - Positive
	//4 - Very Positive
	public static int analyze(String article) {
		Properties prop = new Properties();
		prop.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
		Annotation annotation = pipeline.process(article);
		
		
		for (CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			return RNNCoreAnnotations.getPredictedClass(tree);
		}
		return 0;
	}
	
	public static Map<String, Integer> getSentiments(Map<String, String> articles) {
		Map<String, Integer> results = new HashMap();
		for (Map.Entry<String, String> entry : articles.entrySet()) {
			Integer sentiment = -1; 
			sentiment = analyze(entry.getValue());
			results.put(entry.getKey(), sentiment);
		}
		System.out.println(results);
		return results;
	}
	
 }
