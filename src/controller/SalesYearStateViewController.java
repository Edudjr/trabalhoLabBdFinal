package controller;

import java.util.ArrayList;

import application.DatabaseManager;
import application.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerShoppingModel;
import model.SalesYearStateModel;

/*Gere um relat�rio com as informa��es relacionadas a venda por pa�s e
* estado. O relat�rio dever� conter as seguintes informa��es para cada
* estado:
* � O valor total vendido no estado;
* � A porcentagem do valor das vendas deste estado em rela��o ao pa�s
* (4 d�gitos decimais);
* � A porcentagem do valor das vendas deste estado em rela��o ao mundo
* (4 d�gitos decimais);
* � Ordena��o por nome do pa�s e percentual (decrescente);
*/

public class SalesYearStateViewController {
	@FXML
    private TableView<SalesYearStateModel> tableView;
    @FXML
    private TableColumn<SalesYearStateModel, String> countryColumn;
    @FXML
    private TableColumn<SalesYearStateModel, String> stateColumn;
    @FXML
    private TableColumn<SalesYearStateModel, Double> total_stateColumn;
    @FXML
    private TableColumn<SalesYearStateModel, Double> p_countryColumn;
    @FXML
    private TableColumn<SalesYearStateModel, Double> p_totalColumn;
    
    private ObservableList<SalesYearStateModel> dataList = FXCollections.observableArrayList();
    
    DatabaseManager database = DatabaseManager.getInstance();

	@FXML
    private void initialize() {
        countryColumn.setCellValueFactory
        (new PropertyValueFactory<SalesYearStateModel, String>("country"));
        stateColumn.setCellValueFactory
        (new PropertyValueFactory<SalesYearStateModel, String>("state"));
        total_stateColumn.setCellValueFactory
        (new PropertyValueFactory<SalesYearStateModel, Double>("total_state"));
        p_countryColumn.setCellValueFactory
        (new PropertyValueFactory<SalesYearStateModel, Double>("p_state"));
        p_totalColumn.setCellValueFactory
        (new PropertyValueFactory<SalesYearStateModel, Double>("p_total"));
        
        // Auto resize columns
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //Init list
        loadDataList();
    }
	
	public void loadDataList(){
		ArrayList<SalesYearStateModel> array = new ArrayList<SalesYearStateModel>();
		array = database.getSysArray();

		dataList.clear();
		for (SalesYearStateModel item : array){
			dataList.add(item);
		}
		tableView.setItems(dataList);
	}
	
}
