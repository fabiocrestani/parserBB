package parserBB;

import java.util.ArrayList;
import java.util.List;

public class BillList {

	private double sum = 0;
	private List<BillItem> items = new ArrayList<BillItem>();

	public void add(BillItem billItem) {
		items.add(billItem);
		sum += billItem.getValor();
	}

	public void printAll() {
		for (BillItem item : items) {
			System.out.println("Data: " + item.getDataString() + ", Agência: " + item.getAgencia() + " , Descrição: "
					+ item.getDescricao() + " Data do balancete: " + item.getDataDoBalanceteString() + " Documento: "
					+ item.getDocumento() + " Valor: " + item.getValor());
		}
	}

	public void print() {
		for (BillItem item : items) {
			System.out.println("Data: " + item.getDataString() + " Descrição: " + item.getDescricao() + " Valor: "
					+ item.getValor());
		}
	}

	public double getSum() {
		return sum;
	}

	public List<BillItem> getList() {
		return items;
	}

}
