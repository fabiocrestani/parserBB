package keyword;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Keyword {
	
	@Expose
	private String key;
	
	@Expose
	private List<String> keywords;
	
	private final int MINIMAL_KEYWORD_LENGTH = 4;	
	private int occurences;
	
	private static final List<String> forbiddenWords = new LinkedList<String>();
	static {
		forbiddenWords.add("Pagamento");
		forbiddenWords.add("Conta");
		forbiddenWords.add("Doc");
		forbiddenWords.add("Crédito");
		forbiddenWords.add("Débito");
	}

	public Keyword(String key) {
		this.key = key;
		this.occurences = 0;
		this.keywords = new ArrayList<String>();
	}
	
	public Keyword(String key, String...keywords) {
		this.key = key;
		this.occurences = 0;
		List<String> keywordList = new ArrayList<String>();
		for (String k : keywords) {
			keywordList.add(k);
		}
		this.keywords = keywordList;
	}
	
	public String getKey() {
		return key;
	}
	
	public List<String> getKeywords() {
		return keywords;
	}
	
	public int getOccurences() {
		return occurences;
	}

	public void resetOccurences() {
		this.occurences = 0;
	}

	public void incOccurences() {
		this.occurences++;
	}
	
	public void addKeyword(String keyword) {
		if (keyword.length() < MINIMAL_KEYWORD_LENGTH) {
			return;
		}
		for (String f : forbiddenWords) {
			if (f.equals(keyword)) {
				return;
			}
		}
		for (String k : keywords) {
			if (k.equals(keyword)) {
				return;
			}
		}
		keywords.add(keyword);
	}
}
