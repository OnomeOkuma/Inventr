/*
 * This is an extension of the Stage Class for the custom Dialog needed to get input concerning the purchase
 * being made. 
 */
package com.Inventr.transactionDialogs;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.Inventr.models.CurrentProductList;
import com.Inventr.models.ProductPurchased;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewPurchaseDialog extends Stage{
	
	public NewPurchaseDialog(){
		// Construct a new Stage object
		super();
		
		// Construction and configuration of the input fields needed by this class.
		TextField itemCodeField = new TextField();
		itemCodeField.setPromptText("Item Code");
		
		TextField nameField = new TextField();
		nameField.setPromptText("Enter Name");
		
		TextArea descripField = new TextArea();
		descripField.setPromptText("Enter Description");
		descripField.setWrapText(true);
		
		
		TextField amountSpentField = new TextField();
		amountSpentField.setPromptText("Amount Spent");
		
		TextField priceField = new TextField();
		priceField.setPromptText("Selling Price");
		
		TextField itemAmountField = new TextField();
		itemAmountField.setPromptText("Number of Items");
		
		
		// Set the layout of the Scene object to house all these components
		VBox layout = new VBox(8.5);
		layout.setPadding(new Insets(20, 20, 20, 20));
		
		// Create a button object to trigger processing of the transaction.
		Button button = new Button("Process Transaction");
		button.setOnAction(e -> {
			
			// Variables from the User Input.
			String itemCode = itemCodeField.getText();
			String itemName = nameField.getText();
			String itemDescrip = descripField.getText();
			int amountSpent = 0;
			int price = 0;
			int numberOfItems = 0;
			
			// Checks if any of the string fields is empty.
			if(itemCode.length() == 0 || itemName.length() == 0 || itemDescrip.length() == 0){
				Alert alert = new Alert(AlertType.ERROR, "Error in one or More fields describing the Item.");
				alert.showAndWait();
			}
			
			// Checks if any of the numeric fields is empty.
			try{
				amountSpent = Integer.parseInt(amountSpentField.getText());
				price = Integer.parseInt(priceField.getText());
				numberOfItems = Integer.parseInt(itemAmountField.getText());
				
				// Run if all fields has been filled by the user.
				if(itemCode.length() != 0 && itemName.length() != 0 && itemDescrip.length() != 0 &&
					amountSpent != 0 && price != 0 && numberOfItems != 0){
					
					ProductPurchased recordPurchase = new ProductPurchased();
					recordPurchase.setAmount(amountSpent);
					recordPurchase.setItemCode(itemCode);
					recordPurchase.setProductName(itemName);
					recordPurchase.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
					recordPurchase.setTotalPurchasesMade(numberOfItems);
					
					
					CurrentProductList newProduct = new CurrentProductList();
					newProduct.setItemCode(itemCode);
					newProduct.setProductName(itemName);
					newProduct.setDescription(itemDescrip);
					newProduct.setNumberAvailable(numberOfItems);
					newProduct.setPrice(price);
					
					// Run this action on a separate thread to free up the JavaFX com.Inventr thread.
					NewPurchaseHistory temp = new NewPurchaseHistory(recordPurchase, newProduct);
					Thread thread = new Thread(temp);
					thread.start();
					this.close();
				}
				
			}catch(NumberFormatException error){
				Alert alert = new Alert(AlertType.ERROR, "Error in one or More fields requiring Numbers");
				alert.showAndWait();
			}
		});
		
		// Attach the components to the layout.
		layout.getChildren().addAll( itemCodeField, nameField, descripField, amountSpentField, priceField,itemAmountField, button );
		
		// Create a Scene Object with the layout above 
		Scene scene = new Scene(layout, 400, 400); 
		
		// Add it to the Stage Object.
		super.setScene(scene);
		super.setTitle("Process New Purchase");
		super.setResizable(false);
	}
}
