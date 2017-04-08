package ui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowMessageDialog {

	public ShowMessageDialog(String title, String header, String message, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
