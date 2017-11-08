/*
 * This is an extension of the Stage Class for the custom Dialog needed to get input concerning the sale
 * being made. 
 */
package transactionDialogs;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CurrentProductList;
import models.ProductSold;

public class SaleDialog extends Stage{
		
		public SaleDialog(){
			
				// Construct a new Stage object
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
				
				TextField costField = new TextField();
				costField.setPromptText("Total Cost");
				costField.setAlignment(Pos.CENTER_LEFT);
				costField.setMaxWidth(150);
				costField.setEditable(false);
				
				// Create a TextField Object that takes in the number of items purchased
				// and attach a label object to it.
				TextField itemAmountField = new TextField();
				itemAmountField.setPromptText("Item Amount");
				itemAmountField.setAlignment(Pos.CENTER_LEFT);
				itemAmountField.setMaxWidth(150);
				
				combobox.setOnAction(e -> {
					itemAmountField.setText("0");
				});
				
				
				itemAmountField.setOnKeyReleased(e -> {
					try{
						Integer itemAmount = Integer.parseInt(itemAmountField.getText());
						Iterator<CurrentProductList> iterator2 = CurrentProductList.productAvailable.iterator();
						while(iterator2.hasNext()){
							CurrentProductList temp = iterator2.next();
							
							// Checks if the Item Code selected is the same with the given CurrentProductList.
							if(temp.getItemCode().equals(combobox.getValue())){
								
								Integer temp2;
								try {
									
									temp2 = itemAmount * temp.getPriceInt();
									NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "NG"));  
									costField.setText(format.format(temp2));
							
								}catch (ParseException e1) {

									e1.printStackTrace();
								}
								
								break;
							}
						}
						
					}catch(NumberFormatException error){
						costField.setText("NGN0");
					}
				});
				
				// Set the layout of the Scene object to house all these components
				VBox layout = new VBox(10);
				layout.setAlignment(Pos.BASELINE_LEFT);
				layout.setPadding(new Insets(0, 0, 0, 10));
				
				// Create a button object to trigger processing of the transaction.
				Button button = new Button("Process Transaction");
				button.setOnAction(e -> {
					ProductSold productSold = new ProductSold();
					productSold.setItemCode(combobox.getValue());
					
					Iterator<CurrentProductList> iterator2 = CurrentProductList.productAvailable.iterator();
					while(iterator2.hasNext()){
						CurrentProductList temp = iterator2.next();
						if (combobox.getValue().equals(temp.getItemCode())){
							productSold.setProductName(temp.getProductName());
						}
					}
					
					Integer number = Integer.parseInt(itemAmountField.getText());
					if(number > 0){
						productSold.setTotalSalesMade(number);
						productSold.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
						NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "NG"));
						try {
							
							productSold.setAmount(format.parse(costField.getText()).intValue());
							UpdateSaleHistory update = new UpdateSaleHistory(productSold);
							Thread thread = new Thread(update);
							thread.start();
							this.close();
							
						} catch (Exception e1) {
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
