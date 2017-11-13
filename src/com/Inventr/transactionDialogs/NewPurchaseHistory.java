/*
 * A Runnable class for updating the purchase History in addition to the Current product list information 
 */
package com.Inventr.transactionDialogs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Logger;

import com.Inventr.UserInterface;
import com.Inventr.models.CurrentProductList;
import com.Inventr.models.ProductPurchased;

public class NewPurchaseHistory implements Runnable{
	
	private ProductPurchased record;
	private CurrentProductList newProduct;
	private Logger logger; 
	
	public NewPurchaseHistory(ProductPurchased record, CurrentProductList newProduct){
		
		this.record = record;
		this.newProduct = newProduct;
		this.logger = Logger.getGlobal();
		
	}
	
	
	@Override
	public void run() {
		
		CurrentProductList.productAvailable.clear();
		ProductPurchased.productPurchased.clear();
		try {
					
			Statement updateStatement = UserInterface.dataaccess.createStatement();
			
			//logging
			this.logger.info("Statement created Successfully");
			
			try {
					updateStatement.executeUpdate(
					
											"INSERT INTO PRODUCT_PURCHASED VALUES(" + "\'" + this.record.getItemCode() + "\'"
											+ "," + "\'" + this.record.getProductName() + "\'" + "," + this.record.getTotalPurchasesMadeInt()
											+ "," + this.record.getAmountInt() + "," + "\'" + this.record.getTimestampValue() + "\'"
											+ ");"
					
						);
			
				
					updateStatement.executeUpdate(
											"INSERT INTO CURRENT_PRODUCT_LIST VALUES(" + "\'" + this.newProduct.getItemCode() + "\'"
											+ "," + "\'" + this.newProduct.getProductName() + "\'" + "," + "\'" + this.newProduct.getDescription()
											+ "\'"  + "," + this.newProduct.getPriceInt() + "," + this.newProduct.getNumberAvailableInt()
											+ ");"
								);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			ResultSet currentProductList = updateStatement.executeQuery(
					"SELECT * FROM CURRENT_PRODUCT_LIST;"
					);
			
			while (currentProductList.next()){
				CurrentProductList temp = new CurrentProductList();
				temp.setItemCode(currentProductList.getString(1));
				temp.setProductName(currentProductList.getString(2));
				temp.setDescription(currentProductList.getString(3));
				temp.setPrice(currentProductList.getInt(4));
				temp.setNumberAvailable(currentProductList.getInt(5));
				
				CurrentProductList.productAvailable.add(temp);
				
			}

			
			ResultSet purchaseHistory = updateStatement.executeQuery(
					
					"SELECT * FROM PRODUCT_PURCHASED;"
					
					);
			
			while(purchaseHistory.next()){
				
				ProductPurchased temp = new ProductPurchased();
				temp.setItemCode(purchaseHistory.getString(1));
				temp.setProductName(purchaseHistory.getString(2));
				temp.setTotalPurchasesMade(purchaseHistory.getInt(3));
				temp.setAmount(purchaseHistory.getInt(4));
				temp.setTimestamp(purchaseHistory.getTimestamp(5));
				
				ProductPurchased.productPurchased.add(temp);
				
			}
			
			updateStatement.close();
			this.logger.info("updateStatement Statement object closed Successfully" );
		} catch (SQLException e) {
			this.logger.severe("Unable to run statement");
			e.printStackTrace();
		}
		
	}

}
