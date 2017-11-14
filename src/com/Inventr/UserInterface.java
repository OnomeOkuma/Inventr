/* This class creates the UI components and sets the state and functionality of
 *  all the UI components needed by the com.Inventr
 */
package com.Inventr;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.logging.Logger;

import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

import com.Inventr.models.CurrentProductList;
import com.Inventr.models.ProductPurchased;
import com.Inventr.models.ProductSold;
import com.Inventr.transactionDialogs.CreateUserDialog;
import com.Inventr.transactionDialogs.LogInDialog;
import com.Inventr.transactionDialogs.PurchaseDialog;
import com.Inventr.transactionDialogs.SaleDialog;
import com.Inventr.util.DataAccess;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserInterface {
	 Tab productSoldTab;
	 Tab productPurchaseTab;
	 Tab currentProductListTab;
	 
	 private TableView<CurrentProductList> productAvailableView;							
	 private TableView<ProductPurchased> productPurchasedView;
	 private TableView<ProductSold> productSoldView;
	 public static DataAccess dataaccess;
	 public static Stage stage;
	 public static boolean loggedIn;
	 private Logger logger;
	 
	public UserInterface(){
		this.logger = Logger.getLogger("Initialization Logger");
		
		//Initialize the Current Product Tab. 
		this.currentProductListTab = new Tab();
		this.currentProductListTab.setText("Product List");
		this.currentProductListTab.setClosable(false);
		
		// Initilize the Static property of CurrentProductList.
		CurrentProductList.productAvailable = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		this.productAvailableView = new TableView<CurrentProductList> (CurrentProductList.productAvailable);
		
		// Initialize the SessionFactory.
		dataaccess = new DataAccess();
		
		loggedIn = false;
		
		//Initialize the Purchase History Tab. 
		this.productPurchaseTab = new Tab();
		this.productPurchaseTab.setText(" Purchases ");
		this.productPurchaseTab.setClosable(false);
		
		// Initilize the Static property of ProductPurchased.
		ProductPurchased.productPurchased = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		this.productPurchasedView = new TableView<ProductPurchased>(ProductPurchased.productPurchased);
		
		
		//Initialize the Sale History Tab.
		this.productSoldTab = new Tab();
		this.productSoldTab.setText(" Sales ");
		this.productSoldTab.setClosable(false);
		
		// Initilize the Static property of ProductSold.
		ProductSold.productsSold = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
		this.productSoldView = new TableView<ProductSold>(ProductSold.productsSold);
		
	}
	
	// Initialize the Current Product List.
	@SuppressWarnings("unchecked")
	public void initializeProductList(){
		
		// Create the Columns needed
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
		
		
		// Create the buttons for triggering com.Inventr behaviour.
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
			if(!LogInDialog.isOpen && !CreateUserDialog.isOpen && !PurchaseDialog.isOpen){
				PurchaseDialog purchase = new PurchaseDialog();
				purchase.initOwner(UserInterface.stage);
				purchase.show();
			}
		});
		
		// Add buttons to a layout pane.
		Pane buttonLayout = new Pane();
		buttonLayout.setPrefSize(950, 70);
		buttonLayout.getChildren().addAll(sales, purchases);
		
		// Add button layout and Product Available table to another layout pane.
		BorderPane layout = new BorderPane(this.productAvailableView);
		layout.setBottom(buttonLayout);

		this.currentProductListTab.setContent(layout);
		
		// Try and obtain the most recent data from the database.
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
		
		// Create the Columns needed
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
		
		// Create layout for button.
		Pane buttonLayout = new Pane();
		buttonLayout.setPrefSize(950, 70);
		
		Button button = new Button("Export CSV");
		button.relocate(580, 20);
		
		// On action, show a FileChooser dialog, get its result and use it to export
		// the current contents of the table to a .csv file.
		button.setOnAction(e -> {
			FileChooser filechooser = new FileChooser();
			filechooser.setTitle("Export CSV");
			File file = filechooser.showSaveDialog(UserInterface.stage);
			if (file != null && !ProductPurchased.productPurchased.isEmpty()){
				
				SimpleResultSet resultset = new SimpleResultSet();
				resultset.addColumn("Item Code", Types.VARCHAR, 30, 0);
				resultset.addColumn("Item Name", Types.VARCHAR, 40, 0);
				resultset.addColumn("Total Purchases Made", Types.VARCHAR, 40, 0);
				resultset.addColumn("Amount", Types.VARCHAR, 40, 0);
				resultset.addColumn("Timestamp", Types.VARCHAR, 40, 0);
				
				Iterator<ProductPurchased> iterator = ProductPurchased.productPurchased.iterator();
				while(iterator.hasNext()){
					ProductPurchased temp = iterator.next();
					resultset.addRow(temp.getItemCode(), temp.getProductName(), temp.getTotalPurchasesMade(),
									temp.getAmount(), temp.getTimestamp());
				}
				
				Csv csv = new Csv();
				
				try {
					
					csv.write(file.getPath() + ".csv" , resultset, null);
					
				} catch (Exception e1) {
			
					e1.printStackTrace();
				}
			}
		});
		
		// Create a DatePicker object for filtering History according to Timestamp.
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
		
		buttonLayout.getChildren().add(button);
		layout.setTop(datepicker);
		layout.setBottom(buttonLayout);
		
		this.productPurchaseTab.setContent(layout);
		
		// Try and obtain the most recent data from the database.
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
		
		// Create the Columns needed
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
		
		// Create layout for button.
		Pane buttonLayout = new Pane();
		buttonLayout.setPrefSize(950, 70);
		
		Button button = new Button("Export CSV");
		button.relocate(580, 20);
		
		// On action, show a FileChooser dialog, get its result and use it to export
		// the current contents of the table to a .csv file.
		button.setOnAction(e -> {
			FileChooser filechooser = new FileChooser();
			filechooser.setTitle("Export CSV");
			File file = filechooser.showSaveDialog(UserInterface.stage);
			if (file != null && !ProductSold.productsSold.isEmpty()){
				
				SimpleResultSet resultset = new SimpleResultSet();
				resultset.addColumn("Item Code", Types.VARCHAR, 30, 0);
				resultset.addColumn("Item Name", Types.VARCHAR, 40, 0);
				resultset.addColumn("Total Sales Made", Types.VARCHAR, 40, 0);
				resultset.addColumn("Amount", Types.VARCHAR, 40, 0);
				resultset.addColumn("Timestamp", Types.VARCHAR, 40, 0);
				
				Iterator<ProductSold> iterator = ProductSold.productsSold.iterator();
				while(iterator.hasNext()){
					ProductSold temp = iterator.next();
					resultset.addRow(temp.getItemCode(), temp.getProductName(), temp.getTotalSalesMade(),
									temp.getAmount(), temp.getTimestamp());
				}
				
				Csv csv = new Csv();
				
				try {
					
					csv.write(file.getPath() + ".csv" , resultset, null);
					
				} catch (Exception e1) {
			
					e1.printStackTrace();
				}
			}
		});
		
		// Create a DatePicker object for filtering History according to Timestamp.
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
			
				e1.printStackTrace();
			}
		});
		
		buttonLayout.getChildren().add(button);
		layout.setTop(datepicker);
		layout.setBottom(buttonLayout);
		this.productSoldTab.setContent(layout);
		
		// Try and obtain the most recent data from the database.
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

	