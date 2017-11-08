package transactionDialogs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Logger;

import application.UserInterface;
import models.CurrentProductList;
import models.ProductSold;

public class UpdateSaleHistory implements Runnable{
	private ProductSold record;
	private Logger logger;
	
	public UpdateSaleHistory(ProductSold record){
		this.record = record;
		this.logger = Logger.getGlobal();
	}
	@Override
	public void run() {
		ProductSold.productsSold.clear();
		CurrentProductList.productAvailable.clear();
		
		try {
			Statement statement = UserInterface.dataaccess.createStatement();
			this.logger.info("Statement created successfully");
			
				try {
					statement.executeUpdate(
						
							"INSERT INTO PRODUCT_SOLD VALUES(" + "\'" + this.record.getItemCode() + "\'"
							+ "," + "\'" + this.record.getProductName() + "\'" + "," + this.record.getTotalSalesMadeInt()
							+ "," + this.record.getAmountInt() + "," + "\'" + this.record.getTimestampValue() + "\'"
							+ ");"
						
							);
			
				ResultSet saleHistory = statement.executeQuery(
					
						"SELECT * FROM PRODUCT_SOLD;"
					
						);
			
				while(saleHistory.next()){
				
					ProductSold temp = new ProductSold();
					temp.setItemCode(saleHistory.getString(1));
					temp.setProductName(saleHistory.getString(2));
					temp.setTotalSalesMade(saleHistory.getInt(3));
					temp.setAmount(saleHistory.getInt(4));
					temp.setTimestamp(saleHistory.getTimestamp(5));
				
					ProductSold.productsSold.add(temp);
				
				}
				
				saleHistory.close();
			
				this.logger.info("Update executed successfully");
			
			
				statement.executeUpdate(
							"UPDATE CURRENT_PRODUCT_LIST "
							+ "SET NUMBER_AVAILABLE = NUMBER_AVAILABLE - " + this.record.getTotalSalesMadeInt()
							+ "WHERE ITEM_CODE = " + "\'" + this.record.getItemCode() + "\'"
						);
			
				} catch (ParseException e) {
				
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
			
				e.printStackTrace();
			}
	
		}
		
	}

