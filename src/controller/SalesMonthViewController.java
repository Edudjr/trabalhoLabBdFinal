package controller;

import application.MainApp;
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

 // Reference to the main application
    private MainApp mainApp;
	
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
        
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        //tableView.setItems(mainApp.getPersonData());
        tableView.setItems(mainApp.getSmData());
        
    }
	
	@FXML
    private void handleFilterButton() {
      String month = monthTextfield.getText();

      mainApp.setSmData(month);
      tableView.setItems(mainApp.getSmData());
    }
}
