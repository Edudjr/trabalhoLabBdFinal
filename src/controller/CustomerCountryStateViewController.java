package controller;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerCountryStateModel;
import model.CustomerShoppingModel;

/*Gere um relat�rio com as informa��es sobre clientes por pa�s e estado
* contendo as seguintes informa��es:
* � N�mero de clientes cadastrados por pa�s;
* � N�mero de clientes cadastrados por estado;
* � N�mero total de clientes;
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
    
 // Reference to the main application
    private MainApp mainApp;
	
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
        
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        //tableView.setItems(mainApp.getPersonData());
        tableView.setItems(mainApp.getCcsData());
        
    }
}
