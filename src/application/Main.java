package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Pane root = new Pane();
			root.setPrefSize(800, 600);
			
			UserInterface ui = new UserInterface();
			TabPane tabpane = new TabPane();
			
			tabpane.setPrefHeight(600);
			tabpane.setPrefWidth(800);
			
			tabpane.getTabs().addAll(ui.product_tab, ui.sales_tab, ui.purchase_tab);
			root.getChildren().add(tabpane);
			
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
