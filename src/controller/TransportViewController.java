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
import model.TaxesPaidModel;
import model.TransportModel;

/*
* Gere um relatório com as informações relacionadas a transporte contendo
* as seguintes informações. Receba como parâmetro um ano:
* – Para cada transportadora, liste o total gasto com frete em cada mês
* e o total gasto com frete no ano:
* – Para as vendas com subtotal < 1000
* – Para as vendas com subtotal > 1000.
* – O valor total gasto com frete no ano.
*/

public class TransportViewController {
	@FXML
	private TableView<TransportModel> tableView;
	@FXML
	private TableColumn<TransportModel, String> companyColumn;
	@FXML
	private TableColumn<TransportModel, String> monthColumn;
	@FXML
	private TableColumn<TransportModel, Double> to_1000Column;
	@FXML
	private TableColumn<TransportModel, Double> from_1000Column;
	@FXML
	private TextField yearTextfield;

	private ObservableList<TransportModel> dataList = FXCollections.observableArrayList();

	DatabaseManager database = DatabaseManager.getInstance();

	@FXML
	private void initialize() {
		companyColumn.setCellValueFactory
		(new PropertyValueFactory<TransportModel, String>("company"));
		monthColumn.setCellValueFactory
		(new PropertyValueFactory<TransportModel, String>("month"));
		from_1000Column.setCellValueFactory
		(new PropertyValueFactory<TransportModel, Double>("to_1000"));
		to_1000Column.setCellValueFactory
		(new PropertyValueFactory<TransportModel, Double>("from_1000"));

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//Init list
		loadDataList(null);

	}

	public void loadDataList(String year){
		ArrayList<TransportModel> array = new ArrayList<TransportModel>();
		array = database.getTrArray(year);

		dataList.clear();
		for (TransportModel item : array){
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
