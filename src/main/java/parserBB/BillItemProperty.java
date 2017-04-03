package parserBB;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class BillItemProperty {
	private SimpleStringProperty data;
	private SimpleStringProperty categoria;
	private SimpleStringProperty descricao;
	private SimpleDoubleProperty valor;

	public BillItemProperty(String data, String categoria, String descricao, Double valor) {
		this.data = new SimpleStringProperty(data);
		this.categoria = new SimpleStringProperty(categoria);
		this.descricao = new SimpleStringProperty(descricao);
		this.valor = new SimpleDoubleProperty(valor);
	}

	public String getData() {
		return data.get();
	}

	public String getCategoria() {
		return categoria.get();
	}

	public String getDescricao() {
		return descricao.get();
	}

	public Double getValor() {
		return valor.get();
	}
}