package parserBB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BillItem {

	private Calendar data;
	private String agencia;
	private String descricao;
	private String descricaoOriginal;
	private Calendar dataDoBalancete;
	private String documento;
	private double valor;

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
		// newString = newString.replaceAll("Compra com Cartão", "");
		return newString.trim().replaceAll(" +", " ");
	}
}
