package parserBB;

import java.io.IOException;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import keyword.KeywordDictionary;

public class ParserBB extends Application {
	
	// Aplicação
	private BillList list;
	private KeywordDictionary dictionary;
	private ParserStatus status;
	
	public final static String DEFAULT_DICTIONARY_PATH = "user.dic";
	
	// JavaFx
	private Stage janelaPrincipal;
	private BorderPane rootLayout;
	private MainController controller;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.janelaPrincipal = primaryStage;
		this.janelaPrincipal.setTitle("Parser BB");
		initRootLayout();
		mostraJanelaPrincipal();
	}

	public void initRootLayout() {
        try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(ParserBB.class.getResource("../ui/RootLayout.fxml"));
	        rootLayout = (BorderPane) loader.load();
	        Scene scene = new Scene(rootLayout);
	        janelaPrincipal.setScene(scene);
	        janelaPrincipal.show();	        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void mostraJanelaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ParserBB.class.getResource("../ui/Main.fxml"));
            VBox janelaPrincipal = (VBox) loader.load();
            rootLayout.setCenter(janelaPrincipal);
            
            // Dá ao controlador acesso à app main.
            controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public BillList getBillList() {
		return list;
	}
	
	public KeywordDictionary getDictionary() {
		return dictionary;
	}
	
	public ParserStatus getStatus() {
		return status;
	}
	
	public void setStatus(ParserStatus status) {
		this.status = status;
	}
}
