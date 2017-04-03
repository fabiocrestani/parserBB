package parserBB;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class Csv {

	/**
	 * Carrega um csv de arquivo
	 * 
	 * @param csvFile
	 * @return
	 */
	public static BillList load(String csvFile) {
		BillList billList = new BillList();

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;

			// Descartar a primeira linha
			line = reader.readNext();

			while ((line = reader.readNext()) != null) {
				BillItem billItem = new BillItem(line[0], line[1], line[2], line[3], line[4], line[5]);
				billList.add(billItem);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return billList;
	}
}
