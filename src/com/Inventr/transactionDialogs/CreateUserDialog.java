package com.Inventr.transactionDialogs;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateUserDialog extends Stage{
	
	
		public CreateUserDialog(){
				super();
	
				TextField username = new TextField();
				username.setPromptText("Username");
				
				PasswordField password = new PasswordField();
				password.setPromptText("Password");
				
				PasswordField confirmPassword = new PasswordField();
				confirmPassword.setPromptText("Confirm Password");
				
				Button logIn = new Button("Create User");
	
				VBox layout = new VBox(10);
				layout.setPadding(new Insets(30, 30, 0, 30));
				layout.getChildren().addAll(username, password, confirmPassword, logIn);
	
				Scene scene = new Scene(layout, 300, 200);
	
				super.setScene(scene);
				super.setTitle("Create User");
				super.setResizable(false);
		}

}
