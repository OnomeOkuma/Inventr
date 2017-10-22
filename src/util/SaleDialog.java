package util;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CurrentProductList;

public class SaleDialog extends Stage{
		private Scene scene;
		
		public SaleDialog(){
			
				// Construct a new Stage object
				super();
				
				//Construct a new ComboBox object, populate it's items with the names of the current products 
				//available and attach a label object to it.
				ComboBox<String> combobox = new ComboBox<String>();
				ObservableList<String> currentItems = FXCollections.observableArrayList();
				Iterator<CurrentProductList> iterator = CurrentProductList.productAvailable.iterator();
				while(iterator.hasNext()){
					currentItems.add(iterator.next().getItemCode());
				}
				combobox.setItems(currentItems);
				combobox.setEditable(false);
				Label items = new Label("Item");
				
				
				TextField costField = new TextField();
				costField.setPromptText("Total Cost");
				costField.setAlignment(Pos.CENTER_LEFT);
				costField.setMaxWidth(150);
				Label cost = new Label("Total Cost");
				costField.setEditable(false);
				
				// Create a TextField Object that takes in the number of items purchased
				// and attach a label object to it.
				TextField itemAmountField = new TextField();
				itemAmountField.setPromptText("Item Amount");
				itemAmountField.setAlignment(Pos.CENTER_LEFT);
				itemAmountField.setMaxWidth(150);
				Label number = new Label("Item Amount");
				itemAmountField.setOnAction(e -> {
					// Checks if the itemAmountField and combobox has any input entered 
					if(itemAmountField.getText() != null && combobox.getValue() != null){
						 
						Iterator<CurrentProductList> iterator2 = CurrentProductList.productAvailable.iterator();
						while(iterator2.hasNext()){
							CurrentProductList temp = iterator2.next();
							
							//Checks if the Item Code selected is the same with the given CurrentProductList.
							if(temp.getItemCode().equals(combobox.getValue())){
								Integer temp2 = Integer.parseInt(itemAmountField.getText()) * temp.getPrice();
								costField.setText(temp2.toString());
								break;
							}
						}
					}else System.out.println("Suckker");
				});
				
				
				
				
				
				//Set the layout of the Scene object to house all these components
				VBox layout = new VBox(10);
				layout.setAlignment(Pos.BASELINE_LEFT);
				layout.setPadding(new Insets(0, 0, 0, 10));
				
				// Create a button object to trigger processing of the transaction.
				Button button = new Button("Process Transaction");
				
				//Attach the components to the layout.
				layout.getChildren().addAll(items, combobox, number, itemAmountField, cost, costField, button);
				
				//Create a Scene Object with the layout above 
				this.scene = new Scene(layout, 400, 300); 
				
				//Add it to the Stage Object.
				super.setScene(this.scene);
				super.setTitle("Process Sale");
				super.setResizable(false);
		}
}
