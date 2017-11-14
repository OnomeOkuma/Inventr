package com.Inventr.transactionDialogs;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;

import com.Inventr.models.CurrentProductList;
import com.Inventr.models.ProductPurchased;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExistingPurchaseDialog extends Stage{
	
	public ExistingPurchaseDialog(){
			
			super();
			
			// Construct a new ComboBox object, populate it's items with the names of the current products 
			// available and attach a label object to it.
			ComboBox<String> combobox = new ComboBox<String>();
			ObservableList<String> currentItems = FXCollections.observableArrayList();
			Iterator<CurrentProductList> iterator = CurrentProductList.productAvailable.iterator();
			while(iterator.hasNext()){
				currentItems.add(iterator.next().getItemCode());
			}
			combobox.setItems(currentItems);
			combobox.setEditable(false);
			
			
			TextField costOfItemsField = new TextField();
			costOfItemsField.setPromptText("Cost of Purchase");
			
		
			TextField numOfItemsField = new TextField();
			numOfItemsField.setPromptText("Number of Items");
		
			
			
			VBox layout = new VBox(10);
			layout.setPadding(new Insets(10, 30, 30, 10));
			
			Button button = new Button("Process Transaction");
			button.setOnAction( e -> {
				
				try{
					int numberOfItems = Integer.parseInt(numOfItemsField.getText());
					int costOfItems = Integer.parseInt(costOfItemsField.getText());
					
					ProductPurchased product = new ProductPurchased();
					product.setItemCode(combobox.getValue());
					
					Iterator<CurrentProductList> iterator2 = CurrentProductList.productAvailable.iterator();
					while (iterator2.hasNext()){
						CurrentProductList temp = iterator2.next();
						if(combobox.getValue().equals(temp.getItemCode())){
							product.setProductName(temp.getProductName());
						}
					}
					
					product.setAmount(costOfItems);
					product.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
					product.setTotalPurchasesMade(numberOfItems);
					
					UpdatePurchaseHistory update = new UpdatePurchaseHistory(product);
					Thread run = new Thread(update);
					run.start();
					this.close();
					
				}catch(NumberFormatException error){
					Alert alert = new Alert(AlertType.ERROR, "One or More Fields require Numbers");
					alert.showAndWait();
					}		
				
				
			});
			layout.getChildren().addAll(combobox, numOfItemsField, costOfItemsField, button);
			
			Scene scene = new Scene(layout, 300, 180); 
			super.setScene(scene);
			super.setTitle("Process Purchase");
			super.setOnShown(e -> {
				PurchaseDialog.isOpen = true;
			});
			super.setOnHidden(e -> {
				PurchaseDialog.isOpen = false;
			});
			super.setResizable(false);
	}
}
