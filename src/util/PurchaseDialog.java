package util;

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

public class PurchaseDialog extends Stage{
	private Scene scene;
	
	public PurchaseDialog(){
		// Construct a new Stage object
		super();
		
		//Construct a new ComboBox object, populate it's items with the names of the current products 
		//available and attach a label object to it.
		TextField itemCodeField = new TextField();
		itemCodeField.setPromptText("Item Code");
		itemCodeField.setAlignment(Pos.CENTER_LEFT);
		itemCodeField.setMaxWidth(150);
		Label itemCode = new Label("Item Code");
		
		TextField nameField = new TextField();
		nameField.setPromptText("Enter Name");
		nameField.setAlignment(Pos.CENTER_LEFT);
		nameField.setMaxWidth(150);
		Label itemName = new Label("Item Name");
		
		
		TextField priceField = new TextField();
		priceField.setPromptText("Price");
		priceField.setAlignment(Pos.CENTER_LEFT);
		priceField.setMaxWidth(150);
		Label itemCost = new Label("Price");
		
		// Create a TextField Object that takes in the number of items purchased
		// and attach a label object to it.
		TextField itemAmountField = new TextField();
		itemAmountField.setPromptText("Item Amount");
		itemAmountField.setAlignment(Pos.CENTER_LEFT);
		itemAmountField.setMaxWidth(150);
		Label itemAmount = new Label("Item Amount");
		
		
		
		//Set the layout of the Scene object to house all these components
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.BASELINE_LEFT);
		layout.setPadding(new Insets(0, 0, 0, 10));
		
		// Create a button object to trigger processing of the transaction.
		Button button = new Button("Process Transaction");
		
		//Attach the components to the layout.
		layout.getChildren().addAll();
		
		//Create a Scene Object with the layout above 
		this.scene = new Scene(layout, 400, 300); 
		
		//Add it to the Stage Object.
		super.setScene(this.scene);
		super.setTitle("Process Sale");
		super.setResizable(false);
	}
}
