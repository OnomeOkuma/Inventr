package com.Inventr;

import java.sql.ResultSet;
import java.sql.Statement;

import com.Inventr.dialogs.CreateUserDialog;
import com.Inventr.dialogs.LogInDialog;
import com.Inventr.dialogs.PurchaseDialog;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
				if(!LogInDialog.isOpen && !CreateUserDialog.isOpen && !PurchaseDialog.isOpen){
					if(!UserInterface.loggedIn){
						LogInDialog log = new LogInDialog();
						log.initOwner(primaryStage);
						log.show();
					}else{
						Alert alert = new Alert(AlertType.INFORMATION, "Already Logged In");
						alert.show();
					}
				}
			});
			
			MenuItem createUser = new MenuItem("Create User");
			createUser.setOnAction(e -> {
				if(!LogInDialog.isOpen && !CreateUserDialog.isOpen && !PurchaseDialog.isOpen){
					if(UserInterface.loggedIn){
						CreateUserDialog create = new CreateUserDialog();
						create.initOwner(primaryStage);
						create.show();
					}else{
						Alert alert = new Alert(AlertType.INFORMATION, "Log In Before Creating User");
						alert.show();
					}
				}
			});
			
			MenuItem logOut = new MenuItem("Log Out");
			logOut.setOnAction(e -> {
				if(!LogInDialog.isOpen && !CreateUserDialog.isOpen && !PurchaseDialog.isOpen){
						if(UserInterface.loggedIn){
							UserInterface.loggedIn = false;
							Alert alert = new Alert(AlertType.INFORMATION, "Logged Out");
							alert.show();
						}else{
							Alert alert = new Alert(AlertType.INFORMATION, "Log In First");
							alert.show();
						}
				}
			});
			menuButton.getItems().addAll(logIn, createUser, logOut);
		
			
			TabPane tabpane = new TabPane();
			tabpane.setSide(Side.TOP);
			tabpane.setPrefHeight(680);
			tabpane.setPrefWidth(1260);
			tabpane.relocate(0, 25.8);
			tabpane.getTabs().addAll(ui.currentProductListTab, ui.productSoldTab, ui.productPurchaseTab);
			root.getChildren().add(tabpane);
			root.getChildren().add(menuButton);
			Scene scene = new Scene(root,1260,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Inventr");
			primaryStage.show();
			
	// Checks if users have been created for the application and notifies the user to create one if not.			
			Statement statement = UserInterface.dataaccess.createStatement();
			ResultSet result = statement.executeQuery(
					"SELECT username FROM users;"
					);
					
			if(!result.next()){
				CreateUserDialog createUser2 = new CreateUserDialog();
				createUser2.initOwner(primaryStage);
				createUser2.showAndWait();
			}
			statement.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
