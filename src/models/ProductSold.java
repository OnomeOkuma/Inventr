/*
 * This class models the sales history of the organization. 
 */

package models;

import java.sql.Timestamp;

import javafx.collections.ObservableList;

public class ProductSold extends Product{
	
	// Properties unique to sales history.
	private int amount;
	private Timestamp timestamp;
	private int totalSalesMade;
	
	// ObservableList used by the associated Table.
	public static  ObservableList<ProductSold> productsSold;

	public ProductSold(){
	}
	
	// Getter and Setter methods used by the Hibernate library.
	public int getAmount() {
		return this.amount;
	}
	
	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public int getTotalSalesMade() {
		return this.totalSalesMade;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setTotalSalesMade(int totatlSalesMade) {
		this.totalSalesMade = totatlSalesMade;
	}
	@Override
	public String toString(){
		return (this.getItemCode() +
				"\n" + this.getProductName()
				+ "\n" + this.getTotalSalesMade()
				+ "\n" + this.getAmount()
				+ "\n" + this.getTimestamp());
	}
}
