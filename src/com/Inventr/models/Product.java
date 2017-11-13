/*
 * 
 * This is the super class for all Entity com.Inventr.models in this project. It defines the 
 * properties common to all com.Inventr.models.
 * 
 */

package com.Inventr.models;

public class Product {
	
	// Properties Common to all transactions.
	private String itemCode;
	private String itemName;
	
	public Product(){
	}
	
	//Getter and Setter methods used by the Hibernate library to access these properties.
	public String getItemCode() {
		return this.itemCode;
	}
	
	public String getProductName() {
		return this.itemName;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public void setProductName(String productName) {
		this.itemName = productName;
	}
}
