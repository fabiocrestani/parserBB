package parserBB;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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

	public static void store(BillList list, String csvFile) {
		String csvContent = list.getCsv();

		try {
			OutputStream os = new FileOutputStream(csvFile);
			os.write(0xEF);
			os.write(0xBB);
			os.write(0xBF);
			PrintWriter w = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
			w.print(csvContent);
			w.flush();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
