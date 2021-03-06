/*
 * This class com.Inventr.models the sales history of the organization. 
 */

package com.Inventr.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javafx.collections.ObservableList;

public class ProductSold extends Product{
	
	// Properties unique to sales history.
	private String amount;
	private String timestamp;
	private String totalSalesMade;
	
	// ObservableList used by the associated Table.
	public static  ObservableList<ProductSold> productsSold;
	private NumberFormat numberFormat;
	private NumberFormat currencyFormat;
	private DateFormat dateFormat;
	
	public ProductSold(){
		this.numberFormat = NumberFormat.getNumberInstance(new Locale("en", "NG"));
		this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en" , "NG"));
		this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
	}
	
	// Getter and Setter methods used by the Hibernate library.
	public String getAmount() {
		return this.amount;
	}
	
	public int getAmountInt() throws ParseException{
		Number value = this.currencyFormat.parse(this.amount);
		return value.intValue();
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
	public Timestamp getTimestampValue() throws ParseException{
		Date value = this.dateFormat.parse(this.timestamp);
		return Timestamp.from(value.toInstant());
	}
	
	public String getTotalSalesMade(){
		return this.totalSalesMade;
	}
	
	public int getTotalSalesMadeInt() throws ParseException{
		Number value = this.numberFormat.parse(this.totalSalesMade);
		return value.intValue();
	}
	
	public void setAmount(int amount) {
		this.amount = this.currencyFormat.format(amount);
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = this.dateFormat.format(timestamp);
	}
	
	public void setTotalSalesMade(int totalSalesMade) {
		this.totalSalesMade = this.numberFormat.format(totalSalesMade);
	}
	@Override
	public String toString(){
		return (this.getItemCode() +
				"\n" + this.getProductName()
				+ "\n" + this.getTotalSalesMade()
				+ "\n" + this.getAmount()
				+ "\n" + this.getTimestamp());
	}
}
