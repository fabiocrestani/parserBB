package ui.util;

import javafx.scene.control.Alert.AlertType;

public class InfoMessageDialog extends ShowMessageDialog {
	public InfoMessageDialog(String title, String header, String message) {
		super(title, header, message, AlertType.INFORMATION);
	}
}
