/*
 * A Runnable class for updating the purchase History in addition to the Current product list information 
 */
package util;

import org.hibernate.Session;

import application.UserInterface;
import models.CurrentProductList;
import models.ProductPurchased;

public class UpdatePurchaseHistory implements Runnable{
	
	private ProductPurchased record;
	private CurrentProductList newProduct;
	
	public UpdatePurchaseHistory(ProductPurchased record, CurrentProductList newProduct){
		this.record = record;
		this.newProduct = newProduct;
	}
	
	@Override
	public void run() {
		// Updates the User as to the purchase immediately.
		ProductPurchased.productPurchased.add(this.record);
		CurrentProductList.productAvailable.add(this.newProduct);
		
		// Obtain a session and presists this information to the database.
		Session sess = UserInterface.dataaccess.getSession();
		sess.persist(this.record);
		sess.persist(this.newProduct);
		sess.close();
		
	}

}
