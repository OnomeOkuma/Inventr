/*
 * This class models the sales history of the organization. 
 */

package models;

import java.sql.Timestamp;
import javax.persistence.Entity;

import javafx.collections.ObservableList;

@Entity
public class ProductSold extends Product{
	
	// Properties unique to sales history.
	private Timestamp timestamp;
	private int totalSalesMade;
	
	// ObservableList used by the associated Table.
	public static  ObservableList<ProductSold> productsSold;
	
	public ProductSold(){
	}
	
	// Getter and Setter methods used by the Hibernate library.
	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public int getTotalSalesMade() {
		return this.totalSalesMade;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setTotalSalesMade(int totatlSalesMade) {
		this.totalSalesMade = totatlSalesMade;
	}
	
}
