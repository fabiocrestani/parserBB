package ui.util;

import javafx.scene.control.Alert.AlertType;

public class ErrorMessageDialog extends ShowMessageDialog {
	public ErrorMessageDialog(String title, String header, String message) {
		super(title, header, message, AlertType.ERROR);
	}
}
