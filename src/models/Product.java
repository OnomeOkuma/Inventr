package models;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Product {
	
	private String itemCode;
	private String productName;
	private int numberAvailable;
	
	public Product(){
	}
	
	@Id
	public String getItemCode() {
		return this.itemCode;
	}

	public int getNumberAvailable() {
		return this.numberAvailable;
	}
	
	public String getProductName() {
		return this.productName;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public void setNumberAvailable(int numberAvailable) {
		this.numberAvailable = numberAvailable;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
