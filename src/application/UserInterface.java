package application;

import javafx.scene.control.Tab;

public class UserInterface {
	 Tab sales_tab;
	 Tab purchase_tab;
	 Tab product_tab;
	public UserInterface(){
		this.purchase_tab = new Tab();
		this.purchase_tab.setText("Purchase Tab");
		this.purchase_tab.setClosable(false);
		
		this.sales_tab = new Tab();
		this.sales_tab.setText("Sales Tab");
		this.sales_tab.setClosable(false);
		
		this.product_tab = new Tab();
		this.product_tab.setText("Product List");
		this.product_tab.setClosable(false);
	}
}
