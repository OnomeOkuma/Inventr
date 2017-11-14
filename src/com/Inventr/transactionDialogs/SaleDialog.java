/*
 * This is an extension of the Stage Class for the custom Dialog needed to get input concerning the sale
 * being made. 
 */
package com.Inventr.transactionDialogs;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Locale;

import com.Inventr.models.CurrentProductList;
import com.Inventr.models.ProductSold;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaleDialog extends Stage{
		private boolean invalidAmount;
	
		public SaleDialog(){
			
				// Construct a new Stage object
				super();
				
				// Construct a new ComboBox object, populate it's items with the names of the current products 
				// available.
				ComboBox<String> combobox = new ComboBox<String>();
				ObservableList<String> currentItems = FXCollections.observableArrayList();
				Iterator<CurrentProductList> iterator = CurrentProductList.productAvailable.iterator();
				while(iterator.hasNext()){
					currentItems.add(iterator.next().getItemCode());
				}
				combobox.setItems(currentItems);
				combobox.setEditable(false);
				
				
				// Create a TextField object for displaying the cost of items.
				TextField costField = new TextField();
				costField.setPromptText("Total Cost");
				costField.setEditable(false);
				
				// Create a TextField Object that takes in the number of items purchased.
				TextField itemAmountField = new TextField();
				itemAmountField.setPromptText("Item Amount");
				
				// Prevents fraud by causing the field to have a default value of 0 when a new item is selected.
				combobox.setOnAction(e -> {
					itemAmountField.setText("0");
				});
				
				
				itemAmountField.setOnKeyReleased(e -> {
					try{
						// Try parsing the amount entered in the field
						Integer itemAmount = Integer.parseInt(itemAmountField.getText());
						Iterator<CurrentProductList> iterator2 = CurrentProductList.productAvailable.iterator();
						
						// Looking for the CurrentProductList object with the specified item code.
						while(iterator2.hasNext()){
							CurrentProductList temp = iterator2.next();
							
							// Checks if the item code selected is the same with the given CurrentProductList object.
							if(temp.getItemCode().equals(combobox.getValue())){
								
							try{
								// Try parsing data from the string format stored in the CurrentProductList object.
								
									// Checks if the item amount entered is greater than the stock available.
									if(itemAmount <= temp.getNumberAvailableInt()){
										int	temp2 = itemAmount * temp.getPriceInt();
										NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "NG"));  
										costField.setText(format.format(temp2));
										
										// This property tells the button to process a transaction or not.
										this.invalidAmount = false;
										
									}else{
										// Show an alert to inform the user of the situation.
										Alert alert = new Alert(AlertType.ERROR, "Number greater than Stock amount");
										alert.showAndWait();
										this.invalidAmount = true;
									}
									
							}catch (Exception e1) {

								e1.printStackTrace();
								
							}
							}
						}
						
					}catch(NumberFormatException error){
						costField.setText("NGN0");
					}
				});
				
				// Set the layout of the Scene object to house all these components
				VBox layout = new VBox(10);
				layout.setPadding(new Insets(10, 20, 20, 10));
				
				// Create a button object to trigger processing of the transaction.
				Button button = new Button("Process Transaction");
				button.setOnAction(e -> {
					
					// Create the ProductSold object to be processed.
					ProductSold productSold = new ProductSold();
					productSold.setItemCode(combobox.getValue());
					
					// Searching for the CurrentProductList object with the specified item code. 
					Iterator<CurrentProductList> iterator2 = CurrentProductList.productAvailable.iterator();
					while(iterator2.hasNext()){
						CurrentProductList temp = iterator2.next();
						if (combobox.getValue().equals(temp.getItemCode())){
							productSold.setProductName(temp.getProductName());
						}
					}
					
					// Note: On interaction, a value is set on the itemAmountField.
					// it has a default value of 0 on selecting an item on the combobox.
					Integer number = Integer.parseInt(itemAmountField.getText());
					
					// Checks if the item amount entered is greater than 0 and not above stock levels.
					if(number > 0 && !this.invalidAmount){
						productSold.setTotalSalesMade(number);
						productSold.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
						NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "NG"));
						try{
							
							productSold.setAmount(format.parse(costField.getText()).intValue());
							UpdateSaleHistory update = new UpdateSaleHistory(productSold);
							Thread thread = new Thread(update);
							thread.start();
							this.close();
							
						}catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				// Attach the components to the layout.
				layout.getChildren().addAll(combobox, itemAmountField, costField, button);
				
				// Create a Scene Object with the layout above 
				Scene scene = new Scene(layout, 300, 200); 
				
				// Add it to the Stage Object.
				super.setScene(scene);
				super.setTitle("Process Sale");
				super.setResizable(false);
		}
}
