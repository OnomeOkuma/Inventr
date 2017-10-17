package models;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;

import javafx.collections.ObservableList;

@Entity
public class ProductPurchased extends Product  {
	private Timestamp timestamp;
	private int totalPurchasesMade;
	public static ObservableList<ProductPurchased> productPurchased;
	public ProductPurchased(){
	}
	
	@Id
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	public int getTotalPurchasesMade() {
		return this.totalPurchasesMade;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setTotatlPurchasesMade(int totalPurchasesMade) {
		this.totalPurchasesMade = totalPurchasesMade;
	}
}
