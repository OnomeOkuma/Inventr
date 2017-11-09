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
			System.out.println();
			Pane root = new Pane();
			root.setPrefSize(1260, 600);
			
			
			UserInterface ui = new UserInterface();
			UserInterface.stage = primaryStage;
			ui.initializeProductList();
			ui.initializePurchaseHistory();
			ui.initializeSalesHistory();
			
			MenuBar menubar = new MenuBar();
			menubar.setPrefWidth(1260);

			Menu file = new Menu("File");
			Menu user = new Menu("User");
			Menu help = new Menu("Help");
			menubar.getMenus().addAll(user, file, help);
		
			
			TabPane tabpane = new TabPane();
			tabpane.setSide(Side.TOP);
			tabpane.setPrefHeight(680);
			tabpane.setPrefWidth(1260);
			tabpane.relocate(0, 21.5);
			tabpane.getTabs().addAll(ui.currentProductListTab, ui.productSoldTab, ui.productPurchaseTab);
			root.getChildren().add(tabpane);
			root.getChildren().add(menubar);
			Scene scene = new Scene(root,1260,700);
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
