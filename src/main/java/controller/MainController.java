package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import parserBB.BillItem;
import parserBB.BillList;
import parserBB.Csv;
import parserBB.ParserBB;

public class MainController {
	
	@FXML
	private TableView<BillList> tabelaTableView;
	@FXML
	private TableColumn<BillItem, String> dataTableColumn;
	@FXML
	private TableColumn<BillItem, String> descricaoTableColumn;
	@FXML
	private TableColumn<BillItem, Double> valorTableColumn;
	
	private ParserBB parserBB;
	
	public void setMainApp(ParserBB parserBB) {
		this.parserBB = parserBB;
	}

	public void ParserBB() {
		BillList list;
		list = Csv.load("extrato.csv");
		list.print();
		System.out.printf("soma = %.2f", list.getSum());
		
	
		ObservableList<BillList> obsList = 
				FXCollections.observableArrayList(list);
		
		//tabela.setItems(obsList);
		
	}
	
}
