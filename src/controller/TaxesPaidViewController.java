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

/*Gere um relatório com as informações sobre impostos pagos para um
 * determinado ano, recebido por parâmetro. O relatório deverá conter as
 * seguintes informações:
 * – Para cada Estado, a quantidade de imposto pago em cada mês e o
 * somatório do ano todo.
 * – Os totais pagos de imposto para cada mês.
 * – O total de imposto pago no ano.
 */

public class TaxesPaidViewController {
	@FXML
	private TableView<TaxesPaidModel> tableView;
	@FXML
	private TableColumn<TaxesPaidModel, String> stateColumn;
	@FXML
	private TableColumn<TaxesPaidModel, String> monthColumn;
	@FXML
	private TableColumn<TaxesPaidModel, Double> taxesColumn;
	@FXML
	private TextField yearTextfield;

	private ObservableList<TaxesPaidModel> dataList = FXCollections.observableArrayList();

	DatabaseManager database = DatabaseManager.getInstance();

	@FXML
	private void initialize() {
		stateColumn.setCellValueFactory
		(new PropertyValueFactory<TaxesPaidModel, String>("estado"));
		monthColumn.setCellValueFactory
		(new PropertyValueFactory<TaxesPaidModel, String>("mes"));
		taxesColumn.setCellValueFactory
		(new PropertyValueFactory<TaxesPaidModel, Double>("total_imposto"));

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//Init list
		loadDataList(null);

	}

	public void loadDataList(String year){
		ArrayList<TaxesPaidModel> array = new ArrayList<TaxesPaidModel>();
		array = database.getTpArray(year);

		dataList.clear();
		for (TaxesPaidModel item : array){
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
