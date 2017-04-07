package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import keyword.KeywordDictionary;
import parserBB.BillItem;
import parserBB.BillItem.BillItemProperty;
import parserBB.BillList;
import parserBB.Csv;
import parserBB.ParserBB;

public class MainController {

	@FXML
	private TableView<BillItemProperty> tabelaTableView;

	@FXML
	private TextField totalTextField;

	private ParserBB parserBB;

	private BillList list;
	private KeywordDictionary dictionary;

	public void setMainApp(ParserBB parserBB) {
		this.parserBB = parserBB;
	}

	@FXML
	public void initialize() {
		list = Csv.load("extrato.csv");
		// dictionary = KeywordDictionary.loadTestDictionary();
		dictionary = KeywordDictionary.loadDictionaryFromFile("user.dic");
		dictionary.saveIntoFile("user.dic");
		System.out.println("soma = " + list.getSumString());
		totalTextField.setText("R$ " + list.getSumString());
		updateList(list);
	}

	private void updateList(BillList list) {
		TableColumn<BillItemProperty, String> categoriaCol = new TableColumn<BillItemProperty, String>("Categoria");
		categoriaCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("categoria"));
		TableColumn<BillItemProperty, String> descricaoCol = new TableColumn<BillItemProperty, String>("Descri��o");
		descricaoCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("descricao"));
		TableColumn<BillItemProperty, String> dataCol = new TableColumn<BillItemProperty, String>("Data");
		dataCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("data"));
		TableColumn<BillItemProperty, Double> valorCol = new TableColumn<BillItemProperty, Double>("Valor");
		valorCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, Double>("valor"));
		tabelaTableView.getColumns().setAll(dataCol, categoriaCol, descricaoCol, valorCol);

		ObservableList<BillItemProperty> propertyList = FXCollections.observableArrayList();
		for (BillItem item : list.getList()) {
			String categoria = dictionary.search(item.getDescricao());
			item.setCategoria(categoria);
			propertyList
					// .add(item.new BillItemProperty(item.getDataString(),
					// categoria, item.getDescricao(), item.getValor()));
					.add(item.createBillItemProperty(categoria));
		}

		tabelaTableView.setItems(propertyList);
		System.out.println("size = " + propertyList.size());
		tabelaTableView.setEditable(true);
		tabelaTableView.getSelectionModel().cellSelectionEnabledProperty().set(true);

	}
	
	@FXML
	public void abrirArquivoViaMenu(ActionEvent event) {
		// TODO
	}

	@FXML
	public void salvarArquivoViaMenu(ActionEvent event) {
		Csv.store(list, "outputeste.csv");
	}

}
