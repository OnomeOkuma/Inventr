/*
 * A Runnable class for updating the purchase History in addition to the Current product list information 
 */
package util;

import models.ProductPurchased;

public class UpdatePurchaseHistory implements Runnable{
	
	private ProductPurchased record;
	
	public UpdatePurchaseHistory(ProductPurchased record){
		this.record = record;
	}
	
	@Override
	public void run() {
		ProductPurchased.productPurchased.add(this.record);
	}

}
