package controller;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerCountryStateModel;
import model.ProductSalesCategoryModel;

/*Gere um relat�rio com as informa��es sobre vendas de produtos por
* categorias para um determinado ano, recebido por par�metro. O relat�rio
* dever� conter as seguintes informa��es para cada m�s do ano:
* � Para cada produto, o n�mero de itens vendidos, total vendido, um
* ranking da subcategoria, ranking da categoria e ranking geral (pelo
* total vendido);
* � Para cada Subcategoria, o n�mero de itens vendidos, total vendido,
* ranking da categoria e ranking geral (pelo total vendido);
* � Para cada Categoria, o n�mero de itens vendidos, total vendido e
* ranking geral (pelo total vendido);
*/

public class ProductSalesCategoryViewController {
	@FXML
    private TableView<ProductSalesCategoryModel> tableView;
    @FXML
    private TableColumn<ProductSalesCategoryModel, String> categoriaColumn;
    @FXML
    private TableColumn<ProductSalesCategoryModel, String> subcategoriaColumn;
    @FXML
    private TableColumn<ProductSalesCategoryModel, String> produtoColumn;
    @FXML
    private TableColumn<ProductSalesCategoryModel, String> mesColumn;
    @FXML
    private TableColumn<ProductSalesCategoryModel, Integer> unidadeColumn;
    @FXML
    private TableColumn<ProductSalesCategoryModel, Float> total_vendidoColumn;
    @FXML
    private TableColumn<ProductSalesCategoryModel, Integer> rank_categoriaColumn;
    @FXML
    private TableColumn<ProductSalesCategoryModel, Integer> rank_subcategoriaColumn;
    @FXML
    private TableColumn<ProductSalesCategoryModel, Integer> rank_geralColumn;
    
 // Reference to the main application
    private MainApp mainApp;
	
	@FXML
    private void initialize() {
    // Initialize the person table
        categoriaColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, String>("categoria"));
        subcategoriaColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, String>("subcategoria"));
        produtoColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, String>("produto"));
        mesColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, String>("data_venda"));
        unidadeColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, Integer>("unidade"));
        total_vendidoColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, Float>("total_vendido"));
        rank_categoriaColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, Integer>("rank_categoria"));
        rank_subcategoriaColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, Integer>("rank_subcategoria"));
        rank_geralColumn.setCellValueFactory
        (new PropertyValueFactory<ProductSalesCategoryModel, Integer>("rank_geral"));

        
     // Auto resize columns
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        //tableView.setItems(mainApp.getPersonData());
        tableView.setItems(mainApp.getPscData());
        
    }
	
}
