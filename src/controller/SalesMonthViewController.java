package controller;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SalesMonthModel;

/*Gere um relatório com as informações relacionadas a vendas diárias em
um determinado mês recebido como parâmetro. O relatório deverá conter
as seguintes informações para cada dia do mês:
– O total vendido por dia;
– A média vendida entre os 2 dias anteriores e os 2 dias seguintes.
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
