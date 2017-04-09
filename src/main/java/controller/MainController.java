package controller;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import keyword.KeywordDictionary;
import parserBB.BillItem;
import parserBB.BillItem.BillItemProperty;
import parserBB.BillList;
import parserBB.Csv;
import parserBB.ParserBB;
import parserBB.ParserException;
import parserBB.ParserStatus;
import ui.util.EditingCell;
import ui.util.ErrorMessageDialog;

public class MainController {

	@FXML
	private TableView<BillItemProperty> tabelaTableView;

	@FXML
	private HBox statusHBox;

	@FXML
	private ImageView statusImageView;

	@FXML
	private Label statusLabel;

	private ParserBB parserBB;

	private BillList list;
	private KeywordDictionary dictionary;

	public void setMainApp(ParserBB parserBB) {
		this.parserBB = parserBB;
		this.list = parserBB.getBillList();
		this.dictionary = parserBB.getDictionary();
		setStatus(ParserStatus.NO_FILE_SELECTED);
	}

	@FXML
	public void initialize() {
		statusHBox.setPrefHeight(0);
		statusHBox.setVisible(false);
	}

	private ParserStatus updateList(BillList list) {
		ParserStatus status = ParserStatus.PARSER_OK;

		TableColumn<BillItemProperty, String> categoriaCol = new TableColumn<BillItemProperty, String>("Categoria");
		categoriaCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("categoria"));
		TableColumn<BillItemProperty, String> descricaoCol = new TableColumn<BillItemProperty, String>("Descri��o");
		descricaoCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("descricao"));
		TableColumn<BillItemProperty, String> dataCol = new TableColumn<BillItemProperty, String>("Data");
		dataCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("data"));
		TableColumn<BillItemProperty, Double> valorCol = new TableColumn<BillItemProperty, Double>("Valor");
		valorCol.setCellValueFactory(new PropertyValueFactory<BillItemProperty, Double>("valor"));

		Callback<TableColumn<BillItem.BillItemProperty, String>, TableCell<BillItem.BillItemProperty, String>> cellFactory = new Callback<TableColumn<BillItem.BillItemProperty, String>, TableCell<BillItem.BillItemProperty, String>>() {
			@Override
			public TableCell<BillItemProperty, String> call(TableColumn<BillItemProperty, String> p) {
				return new EditingCell<BillItemProperty>();
			}
		};

		categoriaCol.setCellFactory(cellFactory);
		categoriaCol.setOnEditCommit(new EventHandler<CellEditEvent<BillItemProperty, String>>() {
			@Override
			public void handle(CellEditEvent<BillItemProperty, String> t) {
				((BillItemProperty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setCategoria(t.getNewValue());

				// TODO atualizar dicion�rio
			}
		});

		tabelaTableView.getColumns().setAll(dataCol, categoriaCol, descricaoCol, valorCol);

		ObservableList<BillItemProperty> propertyList = FXCollections.observableArrayList();
		for (BillItem item : list.getList()) {
			String categoria = dictionary.search(item.getDescricao());
			item.setCategoria(categoria);
			if (categoria.toLowerCase().equals(Csv.PENDING_CAT_STRING)) {
				status = ParserStatus.PARSER_PENDING;
				item.setPendente(true);
			}
			propertyList.add(item.createBillItemProperty(categoria));
		}

		tabelaTableView.setItems(propertyList);
		System.out.println("size = " + propertyList.size());
		tabelaTableView.setEditable(true);
		tabelaTableView.getSelectionModel().cellSelectionEnabledProperty().set(true);

		return status;
	}

	@FXML
	public void abrirArquivoViaMenu(ActionEvent event) {
		ParserStatus status;
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Abrir arquivo csv de extrato");
		File file = chooser.showOpenDialog(tabelaTableView.getScene().getWindow());

		if (file == null) {
			setStatus(ParserStatus.NO_FILE_SELECTED);
			updateStatusMessage(getStatus());
			return;
		}

		try {
			list = Csv.load(file);
		} catch (ParserException e) {
			setStatus(ParserStatus.PARSER_ERROR);
			new ErrorMessageDialog("Erro ao importar arquivo CSV", "Arquivo inv�lido", e.getMessage());
			return;
		}

		setStatus(ParserStatus.PARSER_OK);
		dictionary = KeywordDictionary.loadDictionaryFromFile("user.dic");
		dictionary.saveIntoFile("user.dic");

		status = updateList(list);
		setStatus(status);
		updateStatusMessage(getStatus());
	}

	private void setStatus(ParserStatus parserStatus) {
		parserBB.setStatus(parserStatus);
	}

	private ParserStatus getStatus() {
		return parserBB.getStatus();
	}

	@FXML
	public void salvarArquivoViaMenu(ActionEvent event) {
		Csv.store(list, "outputeste.csv");
	}

	void updateStatusMessage(ParserStatus status) {
		statusHBox.setVisible(true);
		statusHBox.setPadding(new Insets(32, 18, 18, 18));
		statusHBox.setPrefHeight(24);

		switch (status) {
			case FILE_ERROR:
				statusLabel.setText("N�o foi poss�vel abrir o arquivo.");
				statusImageView.setImage(new Image("/ui/icons/ic_error_black_24dp_1x.png"));
				break;
			case PARSER_ERROR:
				statusLabel.setText("N�o foi poss�vel importar o arquivo. Arquivo inv�lido.");
				statusImageView.setImage(new Image("/ui/icons/ic_error_black_24dp_1x.png"));
				break;
			case PARSER_PENDING:
				statusLabel.setText("N�o foi poss�vel determinar a categoria de alguns items. Clique para rever...");
				statusImageView.setImage(new Image("/ui/icons/ic_report_problem_black_24dp_1x.png"));
				break;
			case PARSER_OK:
				statusLabel.setText("Arquivo importado com sucesso!");
				statusImageView.setImage(new Image("/ui/icons/ic_done_all_black_24dp_1x.png"));
				break;
			default:
				statusLabel.setText("Nenhum arquivo selecionado.");
				statusImageView.setImage(new Image("/ui/icons/ic_report_problem_black_24dp_1x.png"));
				break;
		}

		statusImageView.setVisible(true);
		statusLabel.setVisible(true);
	}

}
