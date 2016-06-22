package controller;

import java.util.ArrayList;

import application.DatabaseManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
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
	@FXML
	private StackedBarChart<String, Number> barChart;

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

		Thread t3 = new Thread() {
			public void run() {
				// load list
				loadChart();
			}
		};
		 t3.start();
		
//		 Task<Void> task = new Task<Void>() {
//
//		     @Override protected Void call() throws Exception {
//
//               
//		             Platform.runLater(new Runnable() {
//		                 @Override public void run() {
//		                     loadChart();
//		                 }
//		             });
//		         
//		         return null;
//		     }
//		 };
//		 task.run();
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

	public void loadChart(){

		ArrayList<XYChart.Series<String,Number>> series = new ArrayList<XYChart.Series<String,Number>>();
		ArrayList<DashboardTopSoldModel> array = new ArrayList<DashboardTopSoldModel>();
		XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
		
		array = database.getDashboardSumLast30Days();
		
		for (DashboardTopSoldModel item : array){
			series1.getData().add(new XYChart.Data(item.getName(), item.getQuantity()));
			series.add(series1);
		}
		
		Platform.runLater(new Runnable() {
            @Override public void run() {
            	barChart.getXAxis().setLabel("Total");
    			barChart.getData().addAll(series1);
            }
        });
	}

	@FXML
	private void handleFilterButton() {
		String date = dateTextfield.getText();
		totalLabel.setText(database.getDashboardSaleSum(date));
	}

}
