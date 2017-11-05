/*
 * A Runnable class for updating the purchase History in addition to the Current product list information 
 */
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import application.UserInterface;
import models.CurrentProductList;
import models.ProductPurchased;

public class UpdatePurchaseHistory implements Runnable{
	
	private ProductPurchased record;
	private CurrentProductList newProduct;
	private Logger logger; 
	
	public UpdatePurchaseHistory(ProductPurchased record, CurrentProductList newProduct){
		
		this.record = record;
		this.newProduct = newProduct;
		this.logger = Logger.getLogger("Update History");
		
	}
	
	@Override
	public void run() {
		// Updates the User as to the purchase immediately.
		ProductPurchased.productPurchased.add(this.record);
		CurrentProductList.productAvailable.add(this.newProduct);
		
		try {
			
			//for logging purposes.
			this.logger.info("SQL command: INSERT INTO PRODUCT_PURCHASED VALUES(" + "\'" + this.record.getItemCode() + "\'"
					+ "," + "\'" + this.record.getProductName() + "\'" + "," + this.record.getTotalPurchasesMade()
					+ "," + this.record.getAmount() + "," + "\'" + this.record.getTimestamp().toString() + "\'"
					+ ");");
			
			Statement updateStatement = UserInterface.dataaccess.createStatement();
			
			updateStatement.executeUpdate(
					
					"INSERT INTO PRODUCT_PURCHASED VALUES(" + "\'" + this.record.getItemCode() + "\'"
					+ "," + "\'" + this.record.getProductName() + "\'" + "," + this.record.getTotalPurchasesMade()
					+ "," + this.record.getAmount() + "," + "\'" + this.record.getTimestamp().toString() + "\'"
					+ ");"
					
					);
			
			ResultSet currentProductUpdate = updateStatement.executeQuery("SELECT PRICE NUMBER_AVAILABLE FROM CURRENT_PRODUCT_LIST WHERE"
					+ " ITEM_CODE =" + "\'" + this.record.getItemCode() + "\' ;");
			
			System.out.println(currentProductUpdate.next());
			updateStatement.close();
			
		} catch (SQLException e) {
			this.logger.severe("Unable to run statement");
			System.out.println(e.toString());
		}
		
	}

}
