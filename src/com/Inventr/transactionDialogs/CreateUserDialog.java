package com.Inventr.transactionDialogs;

import java.sql.ResultSet;
import java.sql.Statement;

import com.Inventr.UserInterface;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateUserDialog extends Stage{
	public static boolean isOpen = false;
	
		public CreateUserDialog(){
				super();
	
				TextField username = new TextField();
				username.setPromptText("Username");
				
				PasswordField password = new PasswordField();
				password.setPromptText("Password");
				
				PasswordField confirmPassword = new PasswordField();
				confirmPassword.setPromptText("Confirm Password");
				
				Button logIn = new Button("Create User");
				logIn.setOnAction(e -> {
					try {
						Statement statement = UserInterface.dataaccess.createStatement();
						ResultSet result = statement.executeQuery(
								
								"SELECT username FROM users WHERE username = " + "\'" + username.getText() + "\' ;"
								
								);
						if(!result.next()){
							
							if (password.getText().equals(confirmPassword.getText())){
								
								statement.executeUpdate(
										
										"INSERT INTO users VALUES (" + "\'" + username.getText() + "\'" + "," + "\'" + password.getText() + "\' );" 
										
									);
							
								Alert alert = new Alert(AlertType.CONFIRMATION, "User Created");
								alert.show();
								this.close();
							}else{
								Alert alert = new Alert(AlertType.ERROR, "Password does not match");
								alert.show();
							}
							
						}else{
							
							Alert alert = new Alert(AlertType.ERROR, "Username Taken");
							alert.show();
							
						}
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				});
				VBox layout = new VBox(10);
				layout.setPadding(new Insets(30, 30, 0, 30));
				layout.getChildren().addAll(username, password, confirmPassword, logIn);
	
				Scene scene = new Scene(layout, 300, 200);
	
				super.setScene(scene);
				super.setTitle("Create User");
				super.setOnShown(e -> {
					CreateUserDialog.isOpen = true;
				});
				super.setOnHidden(e -> {
					CreateUserDialog.isOpen = false;
				});

				super.setResizable(false);
		}

}
