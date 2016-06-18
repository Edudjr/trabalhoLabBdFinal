package controller;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerCountryStateModel;
import model.TaxesPaidModel;

/*Gere um relat�rio com as informa��es sobre impostos pagos para um
* determinado ano, recebido por par�metro. O relat�rio dever� conter as
* seguintes informa��es:
* � Para cada Estado, a quantidade de imposto pago em cada m�s e o
* somat�rio do ano todo.
* � Os totais pagos de imposto para cada m�s.
* � O total de imposto pago no ano.
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

    // Reference to the main application
    private MainApp mainApp;
	
	@FXML
    private void initialize() {
        stateColumn.setCellValueFactory
        (new PropertyValueFactory<TaxesPaidModel, String>("estado"));
        monthColumn.setCellValueFactory
        (new PropertyValueFactory<TaxesPaidModel, String>("mes"));
        taxesColumn.setCellValueFactory
        (new PropertyValueFactory<TaxesPaidModel, Double>("total_imposto"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        //tableView.setItems(mainApp.getPersonData());
        tableView.setItems(mainApp.getTpData());
        
    }
    
}
