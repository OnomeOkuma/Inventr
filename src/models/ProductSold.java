package models;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;

import javafx.collections.ObservableList;

@Entity
public class ProductSold extends Product{
	private Timestamp timestamp;
	private int totalSalesMade;
	public static  ObservableList<ProductSold> productsSold;
	
	public ProductSold(){
	}
	
	@Id
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
