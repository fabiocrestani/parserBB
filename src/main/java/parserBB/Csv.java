package parserBB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import com.opencsv.CSVReader;

import ui.util.ErrorMessageDialog;
import ui.util.InfoMessageDialog;

public class Csv {

	/**
	 * Carrega um csv de arquivo
	 * 
	 * @param file
	 * @return
	 */
	public static BillList load(File file) {
		BillList billList = new BillList();

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(file));
			String[] line;

			// Descartar a primeira linha
			line = reader.readNext();

			while ((line = reader.readNext()) != null) {
				try {
					BillItem billItem = new BillItem(line[0], line[1], line[2], line[3], line[4], line[5]);
					billList.add(billItem);
				} catch (Exception e) {
					new ErrorMessageDialog("Erro ao abrir arquivo CSV", "Arquivo inválido", e.getMessage());
					reader.close();
					return null;
				}
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorMessageDialog("Erro ao abrir arquivo CSV", "Não foi possível abrir o arquivo", e.getMessage());
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
			new InfoMessageDialog("Arquivo CSV exportado", "O arquivo CSV foi exportado com sucesso",
					"Arquivo exportado: " + csvFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
