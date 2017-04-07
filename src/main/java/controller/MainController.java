package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import keyword.KeywordDictionary;
import parserBB.BillItem;
import parserBB.BillItem.BillItemProperty;
import parserBB.BillList;
import parserBB.Csv;
import parserBB.ParserBB;
import ui.util.EditingCell;

public class MainController {

	@FXML
	private TableView<BillItemProperty> tabelaTableView;

	private ParserBB parserBB;

	private BillList list;
	private KeywordDictionary dictionary;

	public void setMainApp(ParserBB parserBB) {
		this.parserBB = parserBB;
	}

	@FXML
	public void initialize() {

	}

	private void updateList(BillList list) {
		TableColumn<BillItemProperty, String> categoriaCol = new TableColumn<BillItemProperty, String>("Categoria");
		categoriaCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("categoria"));
		TableColumn<BillItemProperty, String> descricaoCol = new TableColumn<BillItemProperty, String>("Descrição");
		descricaoCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("descricao"));
		TableColumn<BillItemProperty, String> dataCol = new TableColumn<BillItemProperty, String>("Data");
		dataCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("data"));
		TableColumn<BillItemProperty, Double> valorCol = new TableColumn<BillItemProperty, Double>("Valor");
		valorCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, Double>("valor"));

		Callback<TableColumn<BillItem.BillItemProperty, String>, TableCell<BillItem.BillItemProperty, String>> cellFactory = new Callback<TableColumn<BillItem.BillItemProperty, String>, TableCell<BillItem.BillItemProperty, String>>() {
			@Override
			public TableCell<BillItemProperty, String> call(TableColumn<BillItemProperty, String> p) {
				return new EditingCell();
			}
		};

		categoriaCol.setCellFactory(cellFactory);
		categoriaCol.setOnEditCommit(new EventHandler<CellEditEvent<BillItemProperty, String>>() {
			@Override
			public void handle(CellEditEvent<BillItemProperty, String> t) {
				((BillItemProperty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setCategoria(t.getNewValue());
			}
		});

		tabelaTableView.getColumns().setAll(dataCol, categoriaCol, descricaoCol, valorCol);

		ObservableList<BillItemProperty> propertyList = FXCollections.observableArrayList();
		for (BillItem item : list.getList()) {
			String categoria = dictionary.search(item.getDescricao());
			item.setCategoria(categoria);
			propertyList.add(item.createBillItemProperty(categoria));
		}

		tabelaTableView.setItems(propertyList);
		System.out.println("size = " + propertyList.size());
		tabelaTableView.setEditable(true);
		tabelaTableView.getSelectionModel().cellSelectionEnabledProperty().set(true);
	}

	@FXML
	public void abrirArquivoViaMenu(ActionEvent event) {
		list = Csv.load("extrato.csv");
		dictionary = KeywordDictionary.loadDictionaryFromFile("user.dic");
		dictionary.saveIntoFile("user.dic");
		System.out.println("soma = " + list.getSumString());
		updateList(list);
	}

	@FXML
	public void salvarArquivoViaMenu(ActionEvent event) {
		Csv.store(list, "outputeste.csv");
	}

}
