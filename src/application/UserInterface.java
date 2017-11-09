package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
	 private Logger logger;
	 
	public UserInterface(){
		this.logger = Logger.getLogger("Initialization Logger");
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
		
		// Creates the Columns needed
		TableColumn<CurrentProductList,String> idCol = new TableColumn<CurrentProductList,String>();
		idCol.setText("ID");
		idCol.setEditable(false);
		idCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("itemCode"));

		
		TableColumn<CurrentProductList,String> nameCol = new TableColumn<CurrentProductList,String>();
		nameCol.setText("Name");
		nameCol.setEditable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("productName"));
		
		TableColumn<CurrentProductList,String> descriptionCol = new TableColumn<CurrentProductList,String>();
		descriptionCol.setText("Description");
		descriptionCol.setEditable(false);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("description"));
		
		TableColumn<CurrentProductList,String> priceCol = new TableColumn<CurrentProductList,String>();
		priceCol.setText("Price");
		priceCol.setEditable(false);
		priceCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("price"));
		
		TableColumn<CurrentProductList,String> availableCol = new TableColumn<CurrentProductList,String>();
		availableCol.setText("Total Available");
		availableCol.setEditable(false);
		availableCol.setCellValueFactory(new PropertyValueFactory<CurrentProductList, String>("numberAvailable"));
		
		this.productAvailableView.getColumns().setAll(idCol, nameCol, descriptionCol, priceCol, availableCol);
		
		Button sales = new Button("Sell");
		sales.setPrefSize(80, 20);
		sales.relocate(300, 25);
		sales.setOnAction(e -> {
			SaleDialog sale = new SaleDialog();
			sale.initOwner(UserInterface.stage);
			sale.show();
		});
		
		Button purchases = new Button("Buy");
		purchases.setPrefSize(80, 20);
		purchases.relocate(750, 25);
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
		
		try {
			
			Statement dataInitialization = UserInterface.dataaccess.createStatement();

			//logging
			this.logger.info("Statement Created ");
			
			ResultSet currentProductList = dataInitialization.executeQuery(
					"SELECT * FROM CURRENT_PRODUCT_LIST;"
					);
			
			//logging
			this.logger.info("Command executed successfully");
			
			while (currentProductList.next()){
				CurrentProductList temp = new CurrentProductList();
				temp.setItemCode(currentProductList.getString(1));
				temp.setProductName(currentProductList.getString(2));
				temp.setDescription(currentProductList.getString(3));
				temp.setPrice(currentProductList.getInt(4));
				temp.setNumberAvailable(currentProductList.getInt(5));
				
				CurrentProductList.productAvailable.add(temp);
				
			}
			
			dataInitialization.close();
			this.logger.info("Statement closed Successfully" );
			
		} catch (SQLException e1) {
			this.logger.severe("Unable to create Statement Object");
			e1.printStackTrace();
		}
	}
	
	
	
	// Initialize Purchase History.
	@SuppressWarnings("unchecked")
	public void initializePurchaseHistory(){
		
		// Creates the Columns needed
		TableColumn<ProductPurchased,String> idCol = new TableColumn<ProductPurchased,String>();
		idCol.setText("ID");
		idCol.setEditable(false);
		idCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, String>("itemCode"));
		
		TableColumn<ProductPurchased,String> nameCol = new TableColumn<ProductPurchased,String>();
		nameCol.setText("Name");
		nameCol.setEditable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, String>("productName"));
		
		TableColumn<ProductPurchased,String> purchasesMadeCol = new TableColumn<ProductPurchased,String>();
		purchasesMadeCol.setText("Items Purchased");
		purchasesMadeCol.setEditable(false);
		purchasesMadeCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, String>("totalPurchasesMade"));
		
		TableColumn<ProductPurchased,String> purchaseAmountCol = new TableColumn<ProductPurchased,String>();
		purchaseAmountCol.setText("Value");
		purchaseAmountCol.setEditable(false);
		purchaseAmountCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, String>("amount"));
		
		TableColumn<ProductPurchased, String> timeCol = new TableColumn<ProductPurchased,String>();
		timeCol.setText("TimeStamp");
		timeCol.setEditable(false);
		timeCol.setCellValueFactory(new PropertyValueFactory<ProductPurchased, String>("timestamp"));
		this.productPurchasedView.getColumns().addAll(idCol, nameCol, purchasesMadeCol,purchaseAmountCol, timeCol );

		BorderPane layout = new BorderPane(this.productPurchasedView);
		DatePicker datepicker = new DatePicker(LocalDate.now());
		datepicker.setOnAction(e -> {
			try {
				ProductPurchased.productPurchased.clear();
				LocalDate temp = datepicker.getValue();
				
				Statement dataInitialization = UserInterface.dataaccess.createStatement();
				ResultSet purchaseHistory = dataInitialization.executeQuery(
						
						"SELECT * FROM PRODUCT_PURCHASED "
						+ "WHERE TIMESTAMP <= " + "\'" + temp + "\'"
	
						);
				
				while(purchaseHistory.next()){
					
					ProductPurchased temp2 = new ProductPurchased();
					temp2.setItemCode(purchaseHistory.getString(1));
					temp2.setProductName(purchaseHistory.getString(2));
					temp2.setTotalPurchasesMade(purchaseHistory.getInt(3));
					temp2.setAmount(purchaseHistory.getInt(4));
					temp2.setTimestamp(purchaseHistory.getTimestamp(5));
					
					ProductPurchased.productPurchased.add(temp2);
					
				}
				
				dataInitialization.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		layout.setTop(datepicker);
		
		this.productPurchaseTab.setContent(layout);
		
		try {
			Statement dataInitialization = UserInterface.dataaccess.createStatement();
			//logging
			this.logger.info("Statement Created ");
			
			ResultSet purchaseHistory = dataInitialization.executeQuery(
					
										"SELECT * FROM PRODUCT_PURCHASED;"
					
										);
			
			while(purchaseHistory.next()){
				
				ProductPurchased temp = new ProductPurchased();
				temp.setItemCode(purchaseHistory.getString(1));
				temp.setProductName(purchaseHistory.getString(2));
				temp.setTotalPurchasesMade(purchaseHistory.getInt(3));
				temp.setAmount(purchaseHistory.getInt(4));
				temp.setTimestamp(purchaseHistory.getTimestamp(5));
				
				ProductPurchased.productPurchased.add(temp);
				
			}
			
			dataInitialization.close();
			this.logger.info("Statement closed Successfully" );
			
		} catch (SQLException e) {
			this.logger.severe("Unable to create Statement Object");
			e.printStackTrace();
		}
	}
	
	// Initialize Sales History.
	@SuppressWarnings("unchecked")
	public void initializeSalesHistory(){
		
		// Creates the Columns needed
		TableColumn<ProductSold,String> idCol = new TableColumn<ProductSold,String>();
		idCol.setText("ID");
		idCol.setEditable(false);
		idCol.setCellValueFactory(new PropertyValueFactory<ProductSold, String>("itemCode"));
		
		TableColumn<ProductSold,String> nameCol = new TableColumn<ProductSold,String>();
		nameCol.setText("Name");
		nameCol.setEditable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<ProductSold, String>("productName"));
		
		TableColumn<ProductSold,String> salesMadeCol = new TableColumn<ProductSold,String>();
		salesMadeCol.setText("Items Sold");
		salesMadeCol.setEditable(false);
		salesMadeCol.setCellValueFactory(new PropertyValueFactory<ProductSold, String>("totalSalesMade"));
		
		TableColumn<ProductSold,String> saleAmountCol = new TableColumn<ProductSold,String>();
		saleAmountCol.setText("Value");
		saleAmountCol.setEditable(false);
		saleAmountCol.setCellValueFactory(new PropertyValueFactory<ProductSold, String>("amount"));
		
		TableColumn<ProductSold, String> timeCol = new TableColumn<ProductSold,String>();
		timeCol.setText("Time Stamp");
		timeCol.setEditable(false);
		timeCol.setCellValueFactory(new PropertyValueFactory<ProductSold, String>("timestamp"));
		this.productSoldView.getColumns().addAll(idCol, nameCol, salesMadeCol,saleAmountCol, timeCol );
		
		BorderPane layout = new BorderPane(this.productSoldView);
		DatePicker datepicker = new DatePicker(LocalDate.now());
		datepicker.setOnAction(e -> {
			try {
				ProductSold.productsSold.clear();
				LocalDate temp = datepicker.getValue();
				
				Statement dataInitialization = UserInterface.dataaccess.createStatement();
				ResultSet saleHistory = dataInitialization.executeQuery(
						
						"SELECT * FROM PRODUCT_SOLD "
						+ "WHERE TIMESTAMP <= " + "\'" + temp + "\'"
	
						);
				
				while(saleHistory.next()){
					
					ProductSold temp2 = new ProductSold();
					temp2.setItemCode(saleHistory.getString(1));
					temp2.setProductName(saleHistory.getString(2));
					temp2.setTotalSalesMade(saleHistory.getInt(3));
					temp2.setAmount(saleHistory.getInt(4));
					temp2.setTimestamp(saleHistory.getTimestamp(5));
					
					ProductSold.productsSold.add(temp2);
					
				}
				
				dataInitialization.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		layout.setTop(datepicker);
		this.productSoldTab.setContent(layout);
		
		try {
			Statement dataInitialization = UserInterface.dataaccess.createStatement();
			//logging
			this.logger.info("Statement Created ");
		
			ResultSet saleHistory = dataInitialization.executeQuery(
				
										"SELECT * FROM PRODUCT_SOLD;"
				
										);
		
			while(saleHistory.next()){
			
				ProductSold temp = new ProductSold();
				temp.setItemCode(saleHistory.getString(1));
				temp.setProductName(saleHistory.getString(2));
				temp.setTotalSalesMade(saleHistory.getInt(3));
				temp.setAmount(saleHistory.getInt(4));
				temp.setTimestamp(saleHistory.getTimestamp(5));
			
				ProductSold.productsSold.add(temp);
			
		}
		
		dataInitialization.close();
		this.logger.info("Statement closed Successfully" );
		
	} catch (SQLException e) {
		this.logger.severe("Unable to create Statement Object");
		e.printStackTrace();
	}
	}
}

	