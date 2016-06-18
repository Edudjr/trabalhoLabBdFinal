package controller;

import java.util.ArrayList;

import application.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ProductSalesCategoryModel;

/*Gere um relatório com as informações sobre vendas de produtos por
 * categorias para um determinado ano, recebido por parâmetro. O relatório
 * deverá conter as seguintes informações para cada mês do ano:
 * – Para cada produto, o número de itens vendidos, total vendido, um
 * ranking da subcategoria, ranking da categoria e ranking geral (pelo
 * total vendido);
 * – Para cada Subcategoria, o número de itens vendidos, total vendido,
 * ranking da categoria e ranking geral (pelo total vendido);
 * – Para cada Categoria, o número de itens vendidos, total vendido e
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

	private ObservableList<ProductSalesCategoryModel> dataList = FXCollections.observableArrayList();

	DatabaseManager database = DatabaseManager.getInstance();

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
		
		//Init list
		loadDataList(null);

	}

	public void loadDataList(String year){
		ArrayList<ProductSalesCategoryModel> array = new ArrayList<ProductSalesCategoryModel>();
		array = database.getPscArray(year);

		dataList.clear();
		for (ProductSalesCategoryModel item : array){
			dataList.add(item);
		}
		tableView.setItems(dataList);
	}

}
