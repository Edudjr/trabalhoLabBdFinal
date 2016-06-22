package controller;

import java.util.ArrayList;

import application.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DashboardTopSoldModel;

public class DashboardViewController {
	@FXML
	private TableView<DashboardTopSoldModel> topTableview;
	@FXML
	private TableView<DashboardTopSoldModel> lowTableview;
	@FXML
	private TableColumn<DashboardTopSoldModel, String> topNameColumn;
	@FXML
	private TableColumn<DashboardTopSoldModel, Double> topSoldColumn;
	@FXML
	private TableColumn<DashboardTopSoldModel, String> lowNameColumn;
	@FXML
	private TableColumn<DashboardTopSoldModel, Double> lowQttColumn;
	@FXML
	private TextField dateTextfield;
	@FXML
	private Label totalLabel;
	
	private ObservableList<DashboardTopSoldModel> dataList = FXCollections.observableArrayList();
	private ObservableList<DashboardTopSoldModel> dataLowList = FXCollections.observableArrayList();

	DatabaseManager database = DatabaseManager.getInstance();
	
	@FXML
	private void initialize() {
		// Initialize the person table
		topNameColumn.setCellValueFactory
		(new PropertyValueFactory<DashboardTopSoldModel, String>("name"));
		topSoldColumn.setCellValueFactory
		(new PropertyValueFactory<DashboardTopSoldModel, Double>("quantity"));
		lowNameColumn.setCellValueFactory
		(new PropertyValueFactory<DashboardTopSoldModel, String>("name"));
		lowQttColumn.setCellValueFactory
		(new PropertyValueFactory<DashboardTopSoldModel, Double>("quantity"));

		// Auto resize columns
		topTableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		Thread t = new Thread() {
		    public void run() {
		    	// load list
				loadDataList();
		    }
		};
		t.start();
		
		Thread t2 = new Thread() {
		    public void run() {
		    	// load list
				loadDataLowList();
		    }
		};
		t2.start();
	}

	//Setta lista de 10 items mais vendidos
	public void loadDataList(){
		ArrayList<DashboardTopSoldModel> array = new ArrayList<DashboardTopSoldModel>();
		array = database.getDashboardTopSold();

		dataList.clear();
		for (DashboardTopSoldModel item : array){
			dataList.add(item);
		}
		topTableview.setItems(dataList);
	}
	
	//Setta lista de produtos com baixa quantidade em estoque
	public void loadDataLowList(){
		ArrayList<DashboardTopSoldModel> array = new ArrayList<DashboardTopSoldModel>();
		array = database.getDashboardLowStock();

		dataLowList.clear();
		for (DashboardTopSoldModel item : array){
			dataLowList.add(item);
		}
		lowTableview.setItems(dataLowList);
	}
	
	@FXML
	private void handleFilterButton() {
		String date = dateTextfield.getText();
		totalLabel.setText(database.getDashboardSaleSum(date));
	}

}
