package application;


import javafx.application.Application;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Pane root = new Pane();
			root.setPrefSize(950, 600);
			
			
			UserInterface ui = new UserInterface();
			UserInterface.stage = primaryStage;
			ui.initializeProductList();
			ui.initializePurchaseHistory();
			ui.initializeSalesHistory();
			
			MenuBar menubar = new MenuBar();
			menubar.setPrefWidth(950);

			Menu file = new Menu("File");
			Menu user = new Menu("User");
			Menu help = new Menu("Help");
			menubar.getMenus().addAll(user, file, help);
		
			
			TabPane tabpane = new TabPane();
			tabpane.setSide(Side.BOTTOM);
			tabpane.setPrefHeight(680);
			tabpane.setPrefWidth(950);
			tabpane.relocate(0, 25.5);
			tabpane.getTabs().addAll(ui.currentProductListTab, ui.productSoldTab, ui.productPurchaseTab);
			root.getChildren().add(tabpane);
			root.getChildren().add(menubar);
			Scene scene = new Scene(root,950,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
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
