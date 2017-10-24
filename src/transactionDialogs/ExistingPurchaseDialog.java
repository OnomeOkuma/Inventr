package transactionDialogs;

import java.util.Iterator;

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
			
			TextField numOfItemsField = new TextField();
			numOfItemsField.setPromptText("Number of Items");
			numOfItemsField.setAlignment(Pos.CENTER_LEFT);
			numOfItemsField.setMaxWidth(150);
			numOfItemsField.setEditable(false);
			
			TextField costOfItemsField = new TextField();
			costOfItemsField.setPromptText("Cost of Purchase");
			costOfItemsField.setAlignment(Pos.CENTER_LEFT);
			costOfItemsField.setMaxWidth(150);
			costOfItemsField.setEditable(false);
			
			
			VBox layout = new VBox(10);
			layout.setAlignment(Pos.BASELINE_LEFT);
			layout.setPadding(new Insets(0, 0, 0, 10));
			
			Button button = new Button("Process Transaction");
			
			layout.getChildren().addAll(combobox, numOfItemsField, costOfItemsField, button);
			
			Scene scene = new Scene(layout, 300, 180); 
			
			super.setScene(scene);
			super.setTitle("Process Purchase");
			super.setResizable(false);
	}
}
