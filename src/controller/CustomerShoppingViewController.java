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
import model.CustomerShoppingModel;

/*Gere um relatório com as informações sobre compras de clientes contendo
 * as seguintes informações. Faça uma filtragem por nome ou código do
 * cliente:
 * – O total gasto e o número de compras realizadas em cada ano e para
 * todas as compras do cliente.
 */

public class CustomerShoppingViewController {

	@FXML
	private TableView<CustomerShoppingModel> tableView;
	@FXML
	private TableColumn<CustomerShoppingModel, String> clientColumn;
	//@FXML
	//private TableColumn<CustomerShoppingModel, String> clientIdColumn;
	@FXML
	private TableColumn<CustomerShoppingModel, Double> totalSpentColumn;
	@FXML
	private TableColumn<CustomerShoppingModel, Integer> purchasesNumberColumn;
	@FXML
	private TableColumn<CustomerShoppingModel, Integer> yearColumn;
	@FXML
	private TextField clientNameTextfield;
	@FXML
	private TextField clientNumberTextfield;

	private ObservableList<CustomerShoppingModel> dataList = FXCollections.observableArrayList();

	DatabaseManager database = DatabaseManager.getInstance();


	@FXML
	private void initialize() {
		// Initialize the person table
		clientColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerShoppingModel, String>("client"));
		//clientIdColumn.setCellValueFactory
		//(new PropertyValueFactory<CustomerShoppingModel, String>("clientId"));
		totalSpentColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerShoppingModel, Double>("total"));
		purchasesNumberColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerShoppingModel, Integer>("purchasesNumber"));
		yearColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerShoppingModel, Integer>("year"));

		// Auto resize columns
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// load list
		loadDataList(null, null);
	}

	public void loadDataList(String name, String clientId){
		ArrayList<CustomerShoppingModel> array = new ArrayList<CustomerShoppingModel>();
		array = database.getCsmArray(name, clientId);

		dataList.clear();
		for (CustomerShoppingModel item : array){
			dataList.add(item);
		}
		tableView.setItems(dataList);
	}

	@FXML
	private void handleFilterButton() {
		String name = clientNameTextfield.getText();
		String clientId = clientNumberTextfield.getText();

		loadDataList(name, clientId);
	}
}
