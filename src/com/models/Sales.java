package com.models;

public class Sales {
		private String itemCode;
		private String description;
		private int purchasesMade;
		private int amount;
		private int quantityAfterPurchase;
		
		public Sales(){
		}

		public int getAmount() {
			return this.amount;
		}

		public String getDescription() {
			return this.description;
		}

		public String getItemCode() {
			return this.itemCode;
		}
		
		public int getPurchasesMade() {
			return this.purchasesMade;
		}

		public int getQuantityAfterPurchase() {
			return this.quantityAfterPurchase;
		}
		
		public void setAmount(int amount) {
			this.amount = amount;
		}
		
		public void setDescription(String description) {
			this.description = description;
		}

		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		
		public void setPurchasesMade(int purchasesMade) {
			this.purchasesMade = purchasesMade;
		}

		public void setQuantityAfterPurchase(int quantityAfterPurchase) {
			this.quantityAfterPurchase = quantityAfterPurchase;
		}
}
