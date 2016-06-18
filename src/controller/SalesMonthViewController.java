package controller;

import java.util.ArrayList;

import application.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SalesMonthModel;

/*Gere um relat�rio com as informa��es relacionadas a vendas di�rias em
um determinado m�s recebido como par�metro. O relat�rio dever� conter
as seguintes informa��es para cada dia do m�s:
� O total vendido por dia;
� A m�dia vendida entre os 2 dias anteriores e os 2 dias seguintes.
 */

public class SalesMonthViewController {
	@FXML
	private TableView<SalesMonthModel> tableView;
	@FXML
	private TableColumn<SalesMonthModel, String> dateColumn;
	@FXML
	private TableColumn<SalesMonthModel, Integer> totalColumn;
	@FXML
	private TableColumn<SalesMonthModel, Double> mediaColumn;
	@FXML
	private TextField monthTextfield;

	private ObservableList<SalesMonthModel> dataList = FXCollections.observableArrayList();

	private DatabaseManager database = DatabaseManager.getInstance();

	@FXML
	private void initialize() {
		// Initialize the person table
		dateColumn.setCellValueFactory
		(new PropertyValueFactory<SalesMonthModel, String>("date"));
		totalColumn.setCellValueFactory
		(new PropertyValueFactory<SalesMonthModel, Integer>("total"));
		mediaColumn.setCellValueFactory
		(new PropertyValueFactory<SalesMonthModel, Double>("media"));

		// Auto resize columns
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//Init list
		loadDataList(null);

	}

	public void loadDataList(String month){
		ArrayList<SalesMonthModel> array = new ArrayList<SalesMonthModel>();
		array = database.getSmArray(month);

		dataList.clear();
		for (SalesMonthModel item : array){
			dataList.add(item);
		}
		tableView.setItems(dataList);
	}

	@FXML
	private void handleFilterButton() {
		String month = monthTextfield.getText();

		loadDataList(month);
	}
}
