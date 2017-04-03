package parserBB;

public class ParserBB {

	public static void main(String[] args) {

		BillList list;
		list = Csv.load("extrato.csv");
		list.print();
		System.out.printf("soma = %.2f", list.getSum());

	}

}
