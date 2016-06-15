package controller;


import application.MainApp;
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
    

 // Reference to the main application
    private MainApp mainApp;
	
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
        
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        //tableView.setItems(mainApp.getPersonData());
        tableView.setItems(mainApp.getCsmData());
        
    }
	
	@FXML
    private void handleFilterButton() {
      String name = clientNameTextfield.getText();
      String clientId = clientNumberTextfield.getText();

      mainApp.setCsmData(name, clientId);
      tableView.setItems(mainApp.getCsmData());
    }
}
