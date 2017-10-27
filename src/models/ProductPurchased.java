/*
 * This class models the purchase history of the organization. 
 */

package models;

import java.time.LocalDate;

import javax.persistence.Entity;

import javafx.collections.ObservableList;

@Entity
public class ProductPurchased extends Product  {
	
	// Properties unique to purchase history.
	private int amount;
	private LocalDate timestamp;
	private int totalPurchasesMade;
	
	// ObservableList used by the associated Table.
	public static ObservableList<ProductPurchased> productPurchased;

	public ProductPurchased(){
	}
	
	// Getter and Setter methods used by the Hibernate library.
	public int getAmount() {
		return this.amount;
	}
	
	public LocalDate getTimestamp() {
		return this.timestamp;
	}
	
	public int getTotalPurchasesMade() {
		return this.totalPurchasesMade;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	public void setTotalPurchasesMade(int totalPurchasesMade) {
		this.totalPurchasesMade = totalPurchasesMade;
	}
	
	@Override
	public String toString(){
		return (this.getItemCode() +
				"\n" + this.getProductName()
				+ "\n" + this.getTotalPurchasesMade()
				+ "\n" + this.getAmount()
				+ "\n" + this.getTimestamp());
	}
}
