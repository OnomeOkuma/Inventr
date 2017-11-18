package com.Inventr.dialogs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Logger;

import com.Inventr.UserInterface;
import com.Inventr.models.CurrentProductList;
import com.Inventr.models.ProductPurchased;

public class UpdatePurchaseHistory implements Runnable{
	
	private ProductPurchased record;
	private Logger logger;
	
	public UpdatePurchaseHistory(ProductPurchased record){
		this.record = record;
		this.logger = Logger.getGlobal();
	}
	
	@Override
	public void run() {
			ProductPurchased.productPurchased.clear();
			CurrentProductList.productAvailable.clear();
			
			try {
				Statement statement = UserInterface.dataaccess.createStatement();
				this.logger.info("Statement created successfully");
				
				try {
					statement.executeUpdate(
							
							"INSERT INTO PRODUCT_PURCHASED VALUES(" + "\'" + this.record.getItemCode() + "\'"
							+ "," + "\'" + this.record.getProductName() + "\'" + "," + this.record.getTotalPurchasesMadeInt()
							+ "," + this.record.getAmountInt() + "," + "\'" + this.record.getTimestampValue() + "\'"
							+ ");"
							
							);
				
				ResultSet purchaseHistory = statement.executeQuery(
						
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
					
				purchaseHistory.close();
				
				this.logger.info("Update executed successfully");
				
				
				statement.executeUpdate(
						"UPDATE CURRENT_PRODUCT_LIST "
						+ "SET NUMBER_AVAILABLE = NUMBER_AVAILABLE + " + this.record.getTotalPurchasesMadeInt()
						+ "WHERE ITEM_CODE = " + "\'" + this.record.getItemCode() + "\'"
						);
				
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
