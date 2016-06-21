package controller;

import java.util.ArrayList;

import application.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DashboardTopSoldModel;

public class DashboardViewController {
	@FXML
	private TableView<DashboardTopSoldModel> topTableview;
	@FXML
	private TableColumn<DashboardTopSoldModel, String> topNameColumn;
	@FXML
	private TableColumn<DashboardTopSoldModel, Double> topSoldColumn;
	
	private ObservableList<DashboardTopSoldModel> dataList = FXCollections.observableArrayList();

	DatabaseManager database = DatabaseManager.getInstance();
	
	@FXML
	private void initialize() {
		// Initialize the person table
		topNameColumn.setCellValueFactory
		(new PropertyValueFactory<DashboardTopSoldModel, String>("name"));
		topSoldColumn.setCellValueFactory
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
	}

	public void loadDataList(){
		ArrayList<DashboardTopSoldModel> array = new ArrayList<DashboardTopSoldModel>();
		array = database.getDashboardTopSold();

		dataList.clear();
		for (DashboardTopSoldModel item : array){
			dataList.add(item);
		}
		topTableview.setItems(dataList);
	}

}
