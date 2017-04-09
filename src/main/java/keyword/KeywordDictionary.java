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

import org.apache.commons.lang3.text.WordUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import parserBB.BillItem;
import parserBB.ParserStatus;
import ui.util.ErrorMessageDialog;

public class KeywordDictionary {
	public static final String PENDING_CAT_STRING = "#pendente";
	private List<Keyword> keywords = new ArrayList<Keyword>();

	public KeywordDictionary() {
	}

	public KeywordDictionary(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	// Procura a melhor categoria para a query recebida
	public ParserStatus search(BillItem item) {
		Keyword foundKeyword = new Keyword("#pendente");
		String query = item.getDescricao();
		ParserStatus status;
		int numberOfOccurences = 0;

		for (Keyword keyword : keywords) {
			keyword.resetOccurences();
			for (String searchingKeyword : keyword.getKeywords()) {
				if (query.toLowerCase().indexOf(searchingKeyword.toLowerCase()) != -1) {
					keyword.incOccurences();
				}
			}
			numberOfOccurences = keyword.getOccurences(); 
			if (numberOfOccurences > foundKeyword.getOccurences()) {
				foundKeyword = keyword;
			}
		}
		
		if (foundKeyword.getKey().toLowerCase().equals(KeywordDictionary.PENDING_CAT_STRING)) {
			status = ParserStatus.PARSER_PENDING;
			item.setPendente(true);
			numberOfOccurences = 0;
		} else {
			status = ParserStatus.PARSER_OK;
			item.setPendente(false);
		}
		
		item.setCategoria(firstLetterUppercase(foundKeyword.getKey()));
		
		return status;
	}

	public static String firstLetterUppercase(String string) {
		return WordUtils.capitalize(string.toLowerCase());
	}

	// Carrega dicionário de arquivo
	public static KeywordDictionary loadDictionaryFromFile(String filePath) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			new ErrorMessageDialog("Erro ao abrir arquivo de dicionário",
					"Não foi possível abrir o arquivo de dicionário " + filePath, e.getMessage());
			return null;
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Type type = new TypeToken<List<Keyword>>() {
		}.getType();
		List<Keyword> k = gson.fromJson(reader, type);
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		k.add(new Keyword("comida", "kops", "alimento", "panifc", "panificadora", "pasini", "marconi", "macdonalds",
				"pizzaria", "patisserie", "grill", "restaurante", "food", "santos", "precoma", "alimen"));
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
