package parserBB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.text.WordUtils;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class BillItem {
	private Calendar data;
	private String agencia;
	private String descricao;
	private String descricaoOriginal;
	private Calendar dataDoBalancete;
	private String documento;
	private double valor;
	private String categoria;
	private boolean pendente;
	private int rank;

	public class BillItemProperty {
		private SimpleStringProperty data;
		private SimpleStringProperty categoria;
		private SimpleStringProperty descricao;
		private SimpleDoubleProperty valor;
		private BillItem that;

		public BillItemProperty(String data, String categoria, String descricao, Double valor, BillItem that) {
			this.data = new SimpleStringProperty(data);
			this.categoria = new SimpleStringProperty(categoria);
			this.descricao = new SimpleStringProperty(descricao);
			this.valor = new SimpleDoubleProperty(valor);
			this.that = that;
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

		public void setCategoria(String newValue) {
			this.categoria.set(newValue);
			that.categoria = newValue;
		}

		public BillItem getBillItem() {
			return that;
		}
	}

	public BillItem(String data, String agencia, String descricao, String dataDoBalancete, String documento,
			String valor) {
		{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			try {
				cal.setTime(sdf.parse(data));
			} catch (ParseException e1) {
				cal = Calendar.getInstance();
			}
			this.data = cal;
		}

		{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			try {
				cal.setTime(sdf.parse(dataDoBalancete));
			} catch (ParseException e1) {
				cal = Calendar.getInstance();
			}
			this.dataDoBalancete = cal;
		}

		this.agencia = agencia;
		this.descricao = parseDescricao(descricao);
		this.descricaoOriginal = descricao;
		this.documento = documento;
		this.pendente = false;
		this.rank = 0;
		try {
			this.valor = Double.parseDouble(valor);
		} catch (Exception e) {
			this.valor = 0;
			System.out.println("Valor inválido");
		}
	}

	public Calendar getData() {
		return data;
	}

	public String getAgencia() {
		return agencia;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDescricaoOriginal() {
		return descricaoOriginal;
	}

	public Calendar getDataDoBalancete() {
		return dataDoBalancete;
	}

	public String getDocumento() {
		return documento;
	}

	public double getValor() {
		return valor;
	}

	public String getDataString() {
		return anyDateToString(data);
	}

	public String getDataDoBalanceteString() {
		return anyDateToString(dataDoBalancete);
	}

	public String anyDateToString(Calendar d) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String s = sdf.format(d.getTime());
			return s;
		} catch (Exception e) {
			return "";
		}
	}

	private String parseDescricao(String string) {
		String newString = string.replaceAll("\\P{L}", " ").toUpperCase().replaceAll("\\s*\\bCOMPRA COM CARTÃO\\b\\s*",
				"");
		newString = newString.trim().replaceAll(" +", " ");
		return WordUtils.capitalize(newString.toLowerCase());
	}

	public BillItemProperty createBillItemProperty(String categoria) {
		return new BillItemProperty(getDataString(), categoria, getDescricao(), getValor(), this);
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public boolean getPendente() {
		return pendente;
	}

	public void setPendente(boolean pendente) {
		this.pendente = pendente;
	}

	public int getRank() {
		return rank;
	}
}
