package models;

import javax.persistence.Entity;
import javafx.collections.ObservableList;


@Entity
public class CurrentProductList extends Product{
	
	private String description;
	private int numberAvailable;
	private int price;
	public static ObservableList<CurrentProductList> productAvailable;
	
	public CurrentProductList(){
	}

	public String getDescription() {
		return this.description;
	}
	
	public int getNumberAvailable() {
		return this.numberAvailable;
	}

	public int getPrice() {
		return this.price;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setNumberAvailable(int numberAvailable) {
		this.numberAvailable = numberAvailable;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
}
