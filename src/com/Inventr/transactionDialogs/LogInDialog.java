package com.Inventr.transactionDialogs;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LogInDialog extends Stage{
	public static boolean isOpen = false;
	
	public LogInDialog(){
		
			super();
			
			TextField username = new TextField();
			username.setPromptText("Username");
			
			PasswordField password = new PasswordField();
			password.setPromptText("Password");
			
			Button logIn = new Button("Log In");
			
			VBox layout = new VBox(10);
			layout.setPadding(new Insets(30, 30, 0, 30));
			layout.getChildren().addAll(username, password, logIn);
			
			Scene scene = new Scene(layout, 300, 150);
			
			super.setScene(scene);
			super.setTitle("Log In");
			super.setResizable(false);
	}

}
