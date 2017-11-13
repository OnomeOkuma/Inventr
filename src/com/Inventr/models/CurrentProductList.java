package com.Inventr.models;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javafx.collections.ObservableList;


public class CurrentProductList extends Product{
	
	private String description;
	private String numberAvailable;
	private String price;
	
	// ObservableList used by the associated Table.
	public static ObservableList<CurrentProductList> productAvailable;
	private NumberFormat numberFormat;
	private NumberFormat currencyFormat;
	
	
	public CurrentProductList(){
		this.numberFormat = NumberFormat.getNumberInstance(new Locale("en", "NG"));
		this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en" , "NG"));
	}

	public String getDescription() {
		return this.description;
	}
	
	public String getNumberAvailable(){
			return this.numberAvailable;
	}
	
	// Returns the integer value of the numberAvailable property.
	public int getNumberAvailableInt() throws ParseException{
		Number value = this.numberFormat.parse(this.numberAvailable);
		return value.intValue();
	}
	
	
	public String getPrice(){
		return this.price;
	}
	
	// Returns the Integer value of the price property. 
	public int getPriceInt() throws ParseException{
		Number value = this.currencyFormat.parse(this.price);
		return value.intValue();
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setNumberAvailable(int numberAvailable) {
		this.numberAvailable = this.numberFormat.format(numberAvailable);
	}
	
	public void setPrice(int price) {
		this.price = this.currencyFormat.format(price);
	}
}
