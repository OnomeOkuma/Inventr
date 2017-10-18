package application;

import java.sql.Timestamp;

import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.CurrentProductList;
import models.ProductPurchased;
import models.ProductSold;
import util.DataAccess;

public class UserInterface {
	 Tab productSoldTab;
	 Tab productPurchaseTab;
	 Tab currentProductListTab;
	 
	 private TableView<CurrentProductList> productAvailableView;							
	 private TableView<ProductPurchased> productPurchasedView;
	 private TableView<ProductSold> productSoldView;
	 public static DataAccess dataaccess;
	 
	public UserInterface(){
		this.currentProductListTab = new Tab();
		this.currentProductListTab.setText("Product List");
		this.currentProductListTab.setClosable(false);
		CurrentProductList.productAvailable = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		this.productAvailableView = new TableView<CurrentProductList> (CurrentProductList.productAvailable);
		
		dataaccess = new DataAccess();
		
		this.productPurchaseTab = new Tab();
		this.productPurchaseTab.setText(" Purchases ");
		this.productPurchaseTab.setClosable(false);
		ProductPurchased.productPurchased = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		this.productPurchasedView = new TableView<ProductPurchased>(ProductPurchased.productPurchased);
		
		
		this.productSoldTab = new Tab();
		this.productSoldTab.setText(" Sales ");
		this.productSoldTab.setClosable(false);
		ProductSold.productsSold = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		this.productSoldView = new TableView<ProductSold>(ProductSold.productsSold);
		
	}
	
	@SuppressWarnings("unchecked")
	public void initializeProductList(){
		TableColumn<CurrentProductList,String> idCol = new TableColumn<CurrentProductList,String>();
		idCol.setText("ID");
		idCol.setEditable(false);
		idCol.setResizable(false);
		idCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("itemCode"));

		
		TableColumn<CurrentProductList,String> nameCol = new TableColumn<CurrentProductList,String>();
		nameCol.setText("Name");
		nameCol.setEditable(false);
		nameCol.setResizable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("productName"));
		
		TableColumn<CurrentProductList,String> descriptionCol = new TableColumn<CurrentProductList,String>();
		descriptionCol.setText("Description");
		descriptionCol.setPrefWidth(300);
		descriptionCol.setResizable(false);
		descriptionCol.setEditable(false);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("description"));
		
		TableColumn<CurrentProductList,Integer> priceCol = new TableColumn<CurrentProductList,Integer>();
		priceCol.setText("Price");
		priceCol.setEditable(false);
		priceCol.setResizable(false);
		priceCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, Integer>("price"));
		
		TableColumn<CurrentProductList,Integer> availableCol = new TableColumn<CurrentProductList,Integer>();
		availableCol.setText("Total Available");
		availableCol.setEditable(false);
		availableCol.setResizable(false);
		availableCol.setPrefWidth(200);
		availableCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, Integer>("numberAvailable"));
		
		this.productAvailableView.getColumns().setAll(idCol, nameCol, descriptionCol, priceCol, availableCol);
		this.currentProductListTab.setContent(this.productAvailableView);
	}
	
	@SuppressWarnings("unchecked")
	public void initializePurchaseHistory(){
		TableColumn<ProductPurchased,String> idCol = new TableColumn<ProductPurchased,String>();
		idCol.setText("ID");
		idCol.setEditable(false);
		idCol.setResizable(false);
		idCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, String>("itemCode"));
		
		TableColumn<ProductPurchased,String> nameCol = new TableColumn<ProductPurchased,String>();
		nameCol.setText("Name");
		nameCol.setEditable(false);
		nameCol.setResizable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, String>("productName"));
		
		TableColumn<ProductPurchased,Integer> purchasesMadeCol = new TableColumn<ProductPurchased,Integer>();
		purchasesMadeCol.setText("Purchases Made");
		purchasesMadeCol.setEditable(false);
		purchasesMadeCol.setPrefWidth(200);
		purchasesMadeCol.setResizable(false);
		purchasesMadeCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, Integer>("totalPurchasesMade"));
		
		TableColumn<ProductPurchased,Integer> availableCol = new TableColumn<ProductPurchased,Integer>();
		availableCol.setText("Available");
		availableCol.setEditable(false);
		availableCol.setPrefWidth(200);
		availableCol.setResizable(false);
		availableCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, Integer>("numberAvailable"));
		
		TableColumn<ProductPurchased, Timestamp> timeCol = new TableColumn<ProductPurchased,Timestamp>();
		timeCol.setText("Time Stamp");
		timeCol.setEditable(false);
		timeCol.setPrefWidth(150);
		timeCol.setResizable(false);
		timeCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, Timestamp>("timestamp"));
		this.productPurchasedView.getColumns().addAll(idCol, nameCol, purchasesMadeCol, availableCol, timeCol );
		this.productPurchaseTab.setContent(this.productPurchasedView);
	}
	
	@SuppressWarnings("unchecked")
	public void initializeSalesHistory(){
		TableColumn<ProductSold,String> idCol = new TableColumn<ProductSold,String>();
		idCol.setText("ID");
		idCol.setEditable(false);
		idCol.setResizable(false);
		idCol.setCellValueFactory(new PropertyValueFactory<ProductSold, String>("itemCode"));
		
		TableColumn<ProductSold,String> nameCol = new TableColumn<ProductSold,String>();
		nameCol.setText("Name");
		nameCol.setEditable(false);
		nameCol.setResizable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<ProductSold, String>("productName"));
		
		TableColumn<ProductSold,Integer> salesMadeCol = new TableColumn<ProductSold,Integer>();
		salesMadeCol.setText("Sale Made");
		salesMadeCol.setEditable(false);
		salesMadeCol.setPrefWidth(200);
		salesMadeCol.setResizable(false);
		salesMadeCol.setCellValueFactory(new PropertyValueFactory<ProductSold, Integer>("totalSalesMade"));
		
		TableColumn<ProductSold,Integer> availableCol = new TableColumn<ProductSold,Integer>();
		availableCol.setText("Available");
		availableCol.setEditable(false);
		availableCol.setPrefWidth(200);
		availableCol.setResizable(false);
		availableCol.setCellValueFactory(new PropertyValueFactory<ProductSold, Integer>("numberAvailable"));
		
		TableColumn<ProductSold, Timestamp> timeCol = new TableColumn<ProductSold,Timestamp>();
		timeCol.setText("Time Stamp");
		timeCol.setEditable(false);
		timeCol.setPrefWidth(150);
		timeCol.setResizable(false);
		timeCol.setCellValueFactory(new PropertyValueFactory<ProductSold, Timestamp>("timestamp"));
		this.productSoldView.getColumns().addAll(idCol, nameCol, salesMadeCol, availableCol, timeCol );
		this.productSoldTab.setContent(this.productSoldView);
	}
}
