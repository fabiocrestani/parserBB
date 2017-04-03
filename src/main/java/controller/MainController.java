package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import parserBB.BillItem;
import parserBB.BillItemProperty;
import parserBB.BillList;
import parserBB.Csv;
import parserBB.ParserBB;

public class MainController {

	@FXML
	private TableView<BillItemProperty> tabelaTableView;

	private ParserBB parserBB;

	public void setMainApp(ParserBB parserBB) {
		this.parserBB = parserBB;
	}

	@FXML
	public void initialize() {
		BillList list;
		list = Csv.load("extrato.csv");
		System.out.printf("soma = %.2f", list.getSum());

		TableColumn<BillItemProperty, String> dataCol = new TableColumn<BillItemProperty, String>("Data");
		dataCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("data"));
		TableColumn<BillItemProperty, String> descricaoCol = new TableColumn<BillItemProperty, String>("Descrição");
		descricaoCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("descricao"));
		TableColumn<BillItemProperty, String> categoriaCol = new TableColumn<BillItemProperty, String>("Categoria");
		categoriaCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("categoria"));
		categoriaCol.setEditable(true);
		TableColumn<BillItemProperty, Double> valorCol = new TableColumn<BillItemProperty, Double>("Valor");
		valorCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, Double>("valor"));
		tabelaTableView.getColumns().setAll(dataCol, categoriaCol, descricaoCol, valorCol);

		ObservableList<BillItemProperty> propertyList = FXCollections.observableArrayList();
		for (BillItem item : list.getList()) {
			propertyList.add(new BillItemProperty(item.getDataString(), " ", item.getDescricao(), item.getValor()));
			System.out.println("adding " + item.getDescricao());
		}
		tabelaTableView.setItems(propertyList);
		System.out.println("size = " + propertyList.size());
		tabelaTableView.setEditable(true);
	}

}
