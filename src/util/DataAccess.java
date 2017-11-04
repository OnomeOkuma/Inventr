/*
 * This class initialises and hold the SessionFactory used to create Sessions.
 * This was done because of how expensive it is, resources-wise, to create a SessionFactory.  
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DataAccess {
	private Connection connection;
	private Logger logger;
	public DataAccess(){
		this.logger = Logger.getLogger("logger");
		
		try {
			
			this.connection = DriverManager.getConnection("jdbc:h2:mem:");
			this.logger.info("Connection was successfully created \n");
			this.connection.setAutoCommit(true);
			
			Statement statement = this.connection.createStatement();
			
			statement.execute("CREATE TABLE IF NOT EXISTS PRODUCT_PURCHASED (ITEM_CODE VARCHAR(20)"
					+ ", ITEM_NAME VARCHAR(40), AMOUNT INTEGER, TIMESTAMP TIMESTAMP PRIMARY KEY, TOTAL_PURCHASES_MADE INTEGER"
					+ ");");	
			
			statement.execute("CREATE TABLE IF NOT EXISTS PRODUCT_SOLD (ITEM_CODE VARCHAR(20)"
					+ ", ITEM_NAME VARCHAR(40), AMOUNT INTEGER, TIMESTAMP TIMESTAMP PRIMARY KEY, TOTAL_SALES_MADE INTEGER"
					+ ");");
			
			statement.execute("CREATE TABLE IF NOT EXISTS CURRENT_PRODUCT_LIST (ITEM_CODE VARCHAR(20) PRIMARY KEY"
					+ ", ITEM_NAME VARCHAR(40), DESCRIPTION VARCHAR(1000), NUMBER_AVAILABLE INTEGER, TOTAL_SALES_MADE INTEGER"
					+ ");");
			
			statement.close();
			
		} catch (SQLException e) {
			
			this.logger.severe("Unable to establish connection \n");
			this.logger.severe(e.getMessage());
			
		}

	}
	
	public Statement createStatement() throws SQLException{
		
		return this.connection.createStatement();
		
	}
	
	
	public void close(){
		
		try {
			
			this.connection.close();
			this.logger.info("Connection closed \n");
			
		} catch (SQLException e) {
			
			this.logger.severe("Unable to close connection \n");
			this.logger.severe(e.getMessage());
			
		}
		
	}
	
}
