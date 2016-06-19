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
import model.SalesYearModel;

/*Gere um relat�rio com as informa��es relacionadas a vendas em um deter-
* minado ano, recebido por par�metro. O relat�rio dever� conter as seguintes
* informa��es para cada m�s:
* � O total vendido no m�s;
* � O percentual de altera��o em rela��o ao m�s anterior.
*/

public class SalesYearViewController {
	@FXML
	private TableView<SalesYearModel> tableView;
	@FXML
	private TableColumn<SalesYearModel, String> monthColumn;
	@FXML
	private TableColumn<SalesYearModel, Double> total_monthColumn;
	@FXML
	private TableColumn<SalesYearModel, Double> changeColumn;
	@FXML
	private TextField yearTextfield;

	private ObservableList<SalesYearModel> dataList = FXCollections.observableArrayList();

	DatabaseManager database = DatabaseManager.getInstance();

	@FXML
	private void initialize() {
		monthColumn.setCellValueFactory
		(new PropertyValueFactory<SalesYearModel, String>("mes"));
		total_monthColumn.setCellValueFactory
		(new PropertyValueFactory<SalesYearModel, Double>("vendido_mes"));
		changeColumn.setCellValueFactory
		(new PropertyValueFactory<SalesYearModel, Double>("alteracao"));

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//Init list
		loadDataList(null);

	}

	public void loadDataList(String year){
		ArrayList<SalesYearModel> array = new ArrayList<SalesYearModel>();
		array = database.getSyArray(year);

		dataList.clear();
		for (SalesYearModel item : array){
			dataList.add(item);
		}
		tableView.setItems(dataList);
	}

	@FXML
	private void handleFilterButton() {
		String year = yearTextfield.getText();

		loadDataList(year);
	}	

}
