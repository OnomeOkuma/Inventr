package com.Inventr;

import com.Inventr.transactionDialogs.CreateUserDialog;
import com.Inventr.transactionDialogs.LogInDialog;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();
			root.setPrefSize(1260, 600);
			
			
			UserInterface ui = new UserInterface();
			UserInterface.stage = primaryStage;
			ui.initializeProductList();
			ui.initializePurchaseHistory();
			ui.initializeSalesHistory();
			
			MenuButton menuButton = new MenuButton("User");
			menuButton.setPrefWidth(100);

			MenuItem logIn = new MenuItem("Log In");
			logIn.setOnAction(e -> {
				LogInDialog log = new LogInDialog();
				log.initOwner(primaryStage);
				log.show();
			});
			
			MenuItem createUser = new MenuItem("Create User");
			createUser.setOnAction(e -> {
				CreateUserDialog create = new CreateUserDialog();
				create.initOwner(primaryStage);
				create.show();
			});
			menuButton.getItems().addAll(logIn, createUser);
		
			
			TabPane tabpane = new TabPane();
			tabpane.setSide(Side.TOP);
			tabpane.setPrefHeight(680);
			tabpane.setPrefWidth(1260);
			tabpane.relocate(0, 26);
			tabpane.getTabs().addAll(ui.currentProductListTab, ui.productSoldTab, ui.productPurchaseTab);
			root.getChildren().add(tabpane);
			root.getChildren().add(menuButton);
			Scene scene = new Scene(root,1260,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Inventr");
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
