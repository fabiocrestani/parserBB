package keyword;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class KeywordDictionary {
	private List<Keyword> keywords = new ArrayList<Keyword>();

	public KeywordDictionary() {}
	
	public KeywordDictionary(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	// Procura a melhor categoria para a query recebida
	public String search(String query) {
		Keyword foundKeyword = new Keyword("outros");

		for (Keyword keyword : keywords) {
			keyword.resetOccurences();
			for (String searchingKeyword : keyword.getKeywords()) {
				if (query.toLowerCase().indexOf(searchingKeyword.toLowerCase()) != -1) {
					keyword.incOccurences();
				}
			}
			if (keyword.getOccurences() > foundKeyword.getOccurences()) {
				foundKeyword = keyword;
			}
		}
		return foundKeyword.getKey();
	}
	
	// Carrega dicionário de arquivo
	public static KeywordDictionary loadDictionaryFromFile(String filePath) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Type type = new TypeToken<List<Keyword>>(){}.getType();
		List<Keyword> k = gson.fromJson(reader, type);
		return new KeywordDictionary(k);
	}
	
	// Salva dicionário em arquivo
	public void saveIntoFile(String filePath) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String stringJson = gson.toJson(keywords);
		
		BufferedWriter writer = null;
		try {
        	writer = new BufferedWriter(new FileWriter(filePath));
        	writer.write(stringJson);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

	// Carrega array com dados de teste
	public static KeywordDictionary loadTestDictionary() {
		List<Keyword> k = new ArrayList<Keyword>();
		k.add(new Keyword("comida", "kops", "alimento", "panifc", "panificadora", "pasini", "marconi",
				"macdonalds", "pizzaria", "patisserie", "grill", "restaurante", "food", "santos", "precoma",
				"alimen"));
		k.add(new Keyword("mercado", "big", "mercadorama", "condor"));
		k.add(new Keyword("roupas", "riachuelo"));
		k.add(new Keyword("eletrônicos", "pontofrio", "beta"));
		k.add(new Keyword("casa", "balaroti", "panelas"));
		k.add(new Keyword("poupança", "poupança"));
		k.add(new Keyword("sair", "flango"));
		k.add(new Keyword("saque", "saque", "taa"));
		return new KeywordDictionary(k);
	}
}
