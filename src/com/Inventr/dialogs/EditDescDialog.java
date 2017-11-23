package com.Inventr.dialogs;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import com.Inventr.UserInterface;
import com.Inventr.models.CurrentProductList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditDescDialog extends Stage{
	
		public EditDescDialog(){
			super();
			ComboBox<String> combobox = new ComboBox<String>();
			ObservableList<String> combobox_list = FXCollections.observableArrayList();
			Iterator<CurrentProductList> iterator = CurrentProductList.productAvailable.iterator();
			while(iterator.hasNext()){
				CurrentProductList temp = iterator.next();
				combobox_list.add(temp.getItemCode());
			}
			combobox.setItems(combobox_list);
			combobox.setPromptText("Item Code");
			combobox.setEditable(false);
			
			TextArea descriptionField = new TextArea();
			descriptionField.setPromptText("New Description");
			descriptionField.setWrapText(true);

			Button editDescription = new Button("Edit Description");
			editDescription.setOnAction(e -> {
				
				try {
					Statement statement = UserInterface.dataaccess.createStatement();
					
						statement.executeUpdate(
									"UPDATE CURRENT_PRODUCT_LIST "
									+ "SET DESCRIPTION = " + "\'" + descriptionField.getText() + "\'"
									+ "WHERE ITEM_CODE = " + "\'" + combobox.getValue() + "\'"
									);
	
						
						CurrentProductList.productAvailable.clear();
						
						ResultSet currentProduct = statement.executeQuery(
								
								"SELECT * FROM CURRENT_PRODUCT_LIST;"
							
								);
					
						while (currentProduct.next()){
						
							CurrentProductList temp = new CurrentProductList();
							temp.setItemCode(currentProduct.getString(1));
							temp.setProductName(currentProduct.getString(2));
							temp.setDescription(currentProduct.getString(3));
							temp.setPrice(currentProduct.getInt(4));
							temp.setNumberAvailable(currentProduct.getInt(5));
						
							CurrentProductList.productAvailable.add(temp);	
						}
						
						statement.close();
						
						this.close();
			}catch (Exception e1) {
	
							e1.printStackTrace();
							
						}
					
			});
			
			VBox layout = new VBox(10);
			layout.setPadding(new Insets(20, 30, 0, 30));
			layout.getChildren().addAll(combobox, descriptionField, editDescription);
			
			Scene scene = new Scene(layout, 300, 300);
			
			super.setScene(scene);
			super.setTitle("Edit Description");
			super.setResizable(false);
		
		}

}
