/*
 * This class models the sales history of the organization. 
 */

package models;

import java.time.LocalDate;

import javax.persistence.Entity;

import javafx.collections.ObservableList;

@Entity
public class ProductSold extends Product{
	
	// Properties unique to sales history.
	private int amount;
	private LocalDate timestamp;
	private int totalSalesMade;
	private int numberAfterSale;
	
	// ObservableList used by the associated Table.
	public static  ObservableList<ProductSold> productsSold;

	public ProductSold(){
	}
	
	// Getter and Setter methods used by the Hibernate library.
	public int getAmount() {
		return this.amount;
	}
	
	public int getNumberAfterSale() {
		return this.numberAfterSale;
	}
	
	public LocalDate getTimestamp() {
		return this.timestamp;
	}

	public int getTotalSalesMade() {
		return this.totalSalesMade;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setNumberAfterSale(int numberAfterSale) {
		this.numberAfterSale = numberAfterSale;
	}
	
	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setTotalSalesMade(int totatlSalesMade) {
		this.totalSalesMade = totatlSalesMade;
	}
	
}
