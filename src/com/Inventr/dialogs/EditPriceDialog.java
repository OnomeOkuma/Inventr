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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditPriceDialog extends Stage{
		 
		
		public EditPriceDialog(){
			super();
			ComboBox<String> combobox = new ComboBox<String>();
			ObservableList<String> combobox_list = FXCollections.observableArrayList();
			Iterator<CurrentProductList> iterator = CurrentProductList.productAvailable.iterator();
			while(iterator.hasNext()){
				CurrentProductList temp = iterator.next();
				combobox_list.add(temp.getItemCode());
			}
			combobox.setItems(combobox_list);
			
			TextField priceField = new TextField();
			priceField.setPromptText("New Price");

			Button editPrice = new Button("Edit Price");
			editPrice.setOnAction(e -> {
				try{
					int price = Integer.parseInt(priceField.getText());
					try {
						Statement statement = UserInterface.dataaccess.createStatement();
						statement.executeUpdate(
								"UPDATE CURRENT_PRODUCT_LIST "
								+ "SET PRICE = " + price
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
						
					} catch (Exception e1) {

						e1.printStackTrace();
						
					}
				}catch (NumberFormatException error){
					Alert alert = new Alert(AlertType.ERROR, "Enter Number");
					alert.showAndWait();
				}
			});
			
			VBox layout = new VBox(10);
			layout.setPadding(new Insets(20, 30, 0, 30));
			layout.getChildren().addAll(combobox, priceField, editPrice);
			
			Scene scene = new Scene(layout, 300, 150);
			
			super.setScene(scene);
			super.setTitle("Edit Price");
			super.setResizable(false);
		}

}
