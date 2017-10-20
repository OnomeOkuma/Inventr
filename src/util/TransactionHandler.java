package util;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TransactionHandler {
		public Scene scene;
		
		public TransactionHandler(){
				ComboBox<String> combobox = new ComboBox<String>();
				combobox.setItems(FXCollections.observableArrayList("Marrr", "mmmanna", "nnakjjbdc", "jajgvjx", "gjgjgjgvja", "jgghgjgkhz"));
				Label items = new Label("Items");
				
				TextField textfield = new TextField();
				textfield.setPromptText("Item Amount");
				textfield.setAlignment(Pos.CENTER);
				textfield.setMaxWidth(200);
				
				Label number = new Label("Amount");
				
				VBox layout = new VBox(10);
				layout.setAlignment(Pos.BASELINE_LEFT);
				layout.setPadding(new Insets(0, 10, 0, 10));
				
				Button button = new Button("Process Transaction");
				
				layout.getChildren().addAll(items, combobox, number, textfield, button);
				
				this.scene = new Scene(layout, 250, 200); 
		}
}
