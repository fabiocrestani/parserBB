package keyword;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Keyword {
	
	@Expose
	private String key;
	@Expose
	private List<String> keywords;
	
	private int occurences;

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
		for (String k : keywords) {
			if (k.equals(keyword)) {
				return;
			}
		}
		keywords.add(keyword);
	}
}