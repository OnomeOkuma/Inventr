package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserInterface {
	 Tab salesTab;
	 Tab purchaseTab;
	 Tab productTab;
	 private TableView<AvailableProducts> productView;
	 ObservableList<AvailableProducts> availableProducts;
	 
	@SuppressWarnings("unchecked")
	public UserInterface(){
		this.purchaseTab = new Tab();
		this.purchaseTab.setText(" PURCHASES ");
		this.purchaseTab.setClosable(false);
		
		this.salesTab = new Tab();
		this.salesTab.setText(" SALES ");
		this.salesTab.setClosable(false);
		
		this.productTab = new Tab();
		this.availableProducts = FXCollections.observableArrayList();
		this.availableProducts.add(new AvailableProducts("llll", "Maxwell", 200, 8));
		this.availableProducts.add(new AvailableProducts("llll", "Maxwell", 200, 8));
		this.availableProducts.add(new AvailableProducts("llll", "Maxwell", 200, 8));
		this.availableProducts.add(new AvailableProducts("llll", "Maxwell", 200, 8));
		this.availableProducts.add(new AvailableProducts("llll", "Maxwell", 200, 8));
		this.availableProducts.add(new AvailableProducts("llll", "Maxwell", 200, 8));
		this.productView = new TableView<AvailableProducts> (this.availableProducts);
		
		TableColumn<AvailableProducts,String> idCol = new TableColumn<AvailableProducts,String>("id");
		idCol.setText("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<AvailableProducts, String>("id"));
		
		TableColumn<AvailableProducts,String> nameCol = new TableColumn<AvailableProducts,String>("Product Name");
		nameCol.setText("Product Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<AvailableProducts, String>("productName"));
		this.productView.getColumns().setAll(idCol, nameCol);
		
		this.productTab.setContent(this.productView);
		this.productTab.setText("AVAILABLE PRODUCTS");
		this.productTab.setClosable(false);
	}
}
