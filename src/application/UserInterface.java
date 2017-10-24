package application;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.CurrentProductList;
import models.ProductPurchased;
import models.ProductSold;
import transactionDialogs.PurchaseDialog;
import transactionDialogs.SaleDialog;
import util.DataAccess;

public class UserInterface {
	 Tab productSoldTab;
	 Tab productPurchaseTab;
	 Tab currentProductListTab;
	 
	 private TableView<CurrentProductList> productAvailableView;							
	 private TableView<ProductPurchased> productPurchasedView;
	 private TableView<ProductSold> productSoldView;
	 public static DataAccess dataaccess;
	 public static Stage stage;
	 
	public UserInterface(){
		this.currentProductListTab = new Tab();
		this.currentProductListTab.setText("Product List");
		this.currentProductListTab.setClosable(false);
		CurrentProductList.productAvailable = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		this.productAvailableView = new TableView<CurrentProductList> (CurrentProductList.productAvailable);
		
		// Initialize the SessionFactory.
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
	
	// Initialize the Current Product List.
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
		nameCol.setPrefWidth(200);
		nameCol.setResizable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("productName"));
		
		TableColumn<CurrentProductList,String> descriptionCol = new TableColumn<CurrentProductList,String>();
		descriptionCol.setText("Description");
		descriptionCol.setPrefWidth(385);
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
		
		Button sales = new Button("Sell");
		sales.setPrefSize(80, 20);
		sales.relocate(200, 25);
		sales.setOnAction(e -> {
			SaleDialog sale = new SaleDialog();
			sale.initOwner(UserInterface.stage);
			sale.show();
		});
		
		Button purchases = new Button("Buy");
		purchases.setPrefSize(80, 20);
		purchases.relocate(650, 25);
		purchases.setOnAction(e -> {
			PurchaseDialog purchase = new PurchaseDialog();
			purchase.initOwner(UserInterface.stage);
			purchase.show();
		});
		Pane buttonLayout = new Pane();
		buttonLayout.setPrefSize(950, 70);
		buttonLayout.getChildren().addAll(sales, purchases);
		
		BorderPane layout = new BorderPane(this.productAvailableView);
		layout.setBottom(buttonLayout);
		
		this.currentProductListTab.setContent(layout);
	}
	
	// Initialize Purchase History.
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
		nameCol.setPrefWidth(200);
		nameCol.setResizable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, String>("productName"));
		
		TableColumn<ProductPurchased,Integer> purchasesMadeCol = new TableColumn<ProductPurchased,Integer>();
		purchasesMadeCol.setText("Items Purchased");
		purchasesMadeCol.setEditable(false);
		purchasesMadeCol.setPrefWidth(200);
		purchasesMadeCol.setResizable(false);
		purchasesMadeCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, Integer>("totalPurchasesMade"));
		
		TableColumn<ProductPurchased,Integer> purchaseAmountCol = new TableColumn<ProductPurchased,Integer>();
		purchaseAmountCol.setText("Value");
		purchaseAmountCol.setEditable(false);
		purchaseAmountCol.setPrefWidth(200);
		purchaseAmountCol.setResizable(false);
		purchaseAmountCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, Integer>("amount"));
		
		TableColumn<ProductPurchased, LocalDate> timeCol = new TableColumn<ProductPurchased,LocalDate>();
		timeCol.setText("TimeStamp");
		timeCol.setEditable(false);
		timeCol.setPrefWidth(200);
		timeCol.setResizable(false);
		timeCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, LocalDate>("timestamp"));
		this.productPurchasedView.getColumns().addAll(idCol, nameCol, purchasesMadeCol,purchaseAmountCol, timeCol );
		this.productPurchaseTab.setContent(this.productPurchasedView);
	}
	
	// Initialize Sales History.
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
		nameCol.setPrefWidth(200);
		nameCol.setResizable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<ProductSold, String>("productName"));
		
		TableColumn<ProductSold,Integer> salesMadeCol = new TableColumn<ProductSold,Integer>();
		salesMadeCol.setText("Items Sold");
		salesMadeCol.setEditable(false);
		salesMadeCol.setPrefWidth(200);
		salesMadeCol.setResizable(false);
		salesMadeCol.setCellValueFactory(new PropertyValueFactory<ProductSold, Integer>("totalSalesMade"));
		
		TableColumn<ProductSold,Integer> saleAmountCol = new TableColumn<ProductSold,Integer>();
		saleAmountCol.setText("Value");
		saleAmountCol.setEditable(false);
		saleAmountCol.setPrefWidth(200);
		saleAmountCol.setResizable(false);
		saleAmountCol.setCellValueFactory(new PropertyValueFactory<ProductSold, Integer>("amount"));
		
		TableColumn<ProductSold, LocalDate> timeCol = new TableColumn<ProductSold,LocalDate>();
		timeCol.setText("Time Stamp");
		timeCol.setEditable(false);
		timeCol.setPrefWidth(200);
		timeCol.setResizable(false);
		timeCol.setCellValueFactory(new PropertyValueFactory<ProductSold, LocalDate>("timestamp"));
		this.productSoldView.getColumns().addAll(idCol, nameCol, salesMadeCol,saleAmountCol, timeCol );
		this.productSoldTab.setContent(this.productSoldView);
	}
}
