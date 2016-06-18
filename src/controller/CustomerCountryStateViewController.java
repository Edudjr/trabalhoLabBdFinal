package controller;

import java.util.ArrayList;

import application.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerCountryStateModel;

/*Gere um relatório com as informações sobre clientes por país e estado
 * contendo as seguintes informações:
 * – Número de clientes cadastrados por país;
 * – Número de clientes cadastrados por estado;
 * – Número total de clientes;
 */

public class CustomerCountryStateViewController {
	@FXML
	private TableView<CustomerCountryStateModel> tableView;
	@FXML
	private TableColumn<CustomerCountryStateModel, String> countryColumn;
	@FXML
	private TableColumn<CustomerCountryStateModel, String> stateColumn;
	@FXML
	private TableColumn<CustomerCountryStateModel, Integer> totalColumn;

	private ObservableList<CustomerCountryStateModel> dataList = FXCollections.observableArrayList();

	DatabaseManager database = DatabaseManager.getInstance();


	@FXML
	private void initialize() {
		// Initialize the person table
		countryColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerCountryStateModel, String>("country"));
		stateColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerCountryStateModel, String>("state"));
		totalColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerCountryStateModel, Integer>("total"));


		// Auto resize columns
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//Init list
		loadDataList();

	}

	public void loadDataList(){
		ArrayList<CustomerCountryStateModel> array = new ArrayList<CustomerCountryStateModel>();
		array = database.getCcsArray();

		dataList.clear();
		for (CustomerCountryStateModel item : array){
			dataList.add(item);
		}
		tableView.setItems(dataList);
	}

}
