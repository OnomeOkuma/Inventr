package com.Inventr.dialogs;

import com.Inventr.UserInterface;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PurchaseDialog extends Stage{
	public static boolean isOpen = false;
	
	public PurchaseDialog(){
		super();
		Button existingProduct = new Button("Existing Product");
		existingProduct.setOnAction(e -> {

					ExistingPurchaseDialog existingPurchase = new ExistingPurchaseDialog();
					existingPurchase.initOwner(UserInterface.stage);
					existingPurchase.show();
					
		});
		Button newProduct = new Button("New Product");
		newProduct.setOnAction(e -> {

				if(UserInterface.loggedIn){
					NewPurchaseDialog newPurchase = new NewPurchaseDialog();
					newPurchase.initOwner(UserInterface.stage);
					newPurchase.show();

				}else{
					Alert alert = new Alert(AlertType.INFORMATION, "Log In First");
					alert.show();
				}

		});
		
		HBox layout = new HBox(100, newProduct, existingProduct);
		layout.setPadding(new Insets(6,0,0,40));
		
		Scene scene = new Scene(layout, 400, 40);
		
		this.setScene(scene);
		this.setResizable(false);
		
		super.setOnShown(e -> {
			PurchaseDialog.isOpen = true;
		});
		super.setOnHidden(e -> {
			PurchaseDialog.isOpen = false;
		});
	}

}
