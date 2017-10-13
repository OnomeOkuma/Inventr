package com.models;

public class Products {
	private String id;
	private String productName;
	private String description;
	private int amount;
	private int quantityAvailable;
	
	public Products(){
	}
	
	public Products(String id, String productName, int amount, int quantity){
		this.setId(id);
		this.setProductName(productName);
		this.setAmount(amount);
		this.setQuantityAvailable(quantity);
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getId() {
		return this.id;
	}

	public String getProductName() {
		return this.productName;
	}

	public int getQuantityAvailable() {
		return this.quantityAvailable;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	
	
}
