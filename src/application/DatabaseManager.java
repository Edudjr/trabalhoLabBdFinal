package application;

import java.sql.* ;  // for standard JDBC programs
import java.util.ArrayList;

import model.CustomerCountryStateModel;
import model.CustomerShoppingModel;
import model.DashboardTopSoldModel;
import model.Person;
import model.ProductSalesCategoryModel;
import model.SalesMonthModel;
import model.SalesYearModel;
import model.SalesYearStateModel;
import model.TaxesPaidModel;
import model.TransportModel;

public class DatabaseManager {
	private final String username = "r7962006";
	private final String password = "r7962006";
	public Connection connection = null;
	public Integer maxRowsNumber = 50;
	
	private static DatabaseManager instance = null;
	
	private DatabaseManager() {
		// Exists only to defeat instantiation.
	}
	public static DatabaseManager getInstance() {
		if(instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}


	public void openConnection(){
		try {
			//Conecta com driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Conecta com banco
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl",
					username,
					password);

		} catch (ClassNotFoundException e) {
			System.out.println("Driver n�o encontrado");
			e.printStackTrace();
			return;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro na conex�o com o banco");
			e.printStackTrace();
			return;
		}

		System.out.println("Conectado com o banco");
	}

	public ResultSet select(String str){
		System.out.println("cmd: " + str);
		try {
			Statement stmt;
			stmt = connection.createStatement();
			return stmt.executeQuery(str);
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}

	public ArrayList<Person> getPeopleArray(Integer limit){
		try {
			ArrayList<Person> list = new ArrayList<Person>();
			String str = "SELECT nome, sobrenome FROM Pessoa where ROWNUM <= ";
			str = str.concat(limit.toString());
			ResultSet rs = select(str);
			while(rs.next()){
				Person p = new Person(rs.getString("nome"), rs.getString("sobrenome"));
				list.add(p);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}

	public ArrayList<CustomerShoppingModel> getCsmArray(String name, String clientId){
		String filter = " ";

		if (name != null && !name.isEmpty()){
			filter = "WHERE P.NOME = '" + name+"' "; 
		}
		if (clientId != null && !clientId.isEmpty()){
			filter = filter.concat(" OR P.PESSOA_ID = '"+clientId+"' ");
		}


		String query = 
				"SELECT P.NOME AS Nome,  " +
						"TO_CHAR(V.data_venda, 'YYYY') AS Ano, COUNT(*) AS Compras, " +
						"SUM(V.subtotal) AS TotalComprado " +
						"FROM Venda V " +
						"JOIN Cliente C " + 
						"ON C.cliente_id = V.cliente_id "+ 
						"JOIN Pessoa P " +
						"ON P.pessoa_id = C.pessoa_id " +
						filter +
						"AND ROWNUM <= " + maxRowsNumber +
						" GROUP BY ROLLUP (P.NOME, TO_CHAR(V.data_venda, 'YYYY')) " +
						"ORDER BY P.NOME";

		try {
			ArrayList<CustomerShoppingModel> list = new ArrayList<CustomerShoppingModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				CustomerShoppingModel csm = 
						new CustomerShoppingModel(
								rs.getString("Nome"),
								rs.getString("Nome"), //TROCAR PARA CODIGO
								rs.getDouble("TotalComprado"),
								rs.getInt("Compras"),
								rs.getInt("Ano"));

				list.add(csm);	
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}

	public ArrayList<ProductSalesCategoryModel> getPscArray(String year){
		String filter = " ";
		if(year != null && !year.isEmpty()){
			filter = "WHERE EXTRACT(YEAR from data_venda) = "+year+" AND ";
		}else{
			filter = "WHERE ";
		}
		
		String query = 
				"SELECT Sub.categoria, " +
						"SUB.NOME AS subcategoria, " +
						"P.NOME AS produto, " +
						"TO_CHAR(V.data_venda, 'MM/YY') AS data_venda, " +
						"COUNT(*) AS unidade, " +
						"SUM(V.subtotal) AS total_vendido, " +
						"DENSE_rank() over (PARTITION BY SUB.CATEGORIA  order by sum(v.subtotal) ) as rank_categoria, " +
						"DENSE_rank() over (partition by p.nome order by sum(v.subtotal) ) as rank_subcategoria, " +
						"DENSE_rank() over (order by sum(v.subtotal)) AS rank_geral " +
						"FROM Venda V " +
						"JOIN Venda_item VI " +
						"ON V.venda_id = VI.venda_id " +
						"JOIN Produto P " +
						"ON P.produto_id = VI.produto_id " +
						"JOIN Subcategoria Sub " +
						"ON P.subcategoria = Sub.subcategoria_id " +
						filter +
						"ROWNUM <= " + maxRowsNumber +
						" GROUP BY ROLLUP(Sub.categoria, Sub.NOME, P.NOME, TO_CHAR(V.data_venda, 'MM/YY')) " +
						"ORDER BY Sub.CATEGORIA, sub.nome , p.nome, TO_CHAR(V.data_venda, 'MM/YY')";

		try {
			ArrayList<ProductSalesCategoryModel> list = new ArrayList<ProductSalesCategoryModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				ProductSalesCategoryModel psc = 
						new ProductSalesCategoryModel(
								rs.getString("categoria"),
								rs.getString("subcategoria"),
								rs.getString("produto"),
								rs.getString("data_venda"),
								rs.getInt("unidade"),
								rs.getFloat("total_vendido"),
								rs.getInt("rank_categoria"),
								rs.getInt("rank_subcategoria"),
								rs.getInt("rank_geral"));

				list.add(psc);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}

	public ArrayList<TaxesPaidModel> getTpArray(String year){
		String filter = " ";
		if(year != null && !year.isEmpty()){
			filter = "WHERE EXTRACT(YEAR from data_venda) = "+year+" AND ";
		}else{
			filter = "WHERE ";
		}
		
		String query = 
				"SELECT E.nome AS estado, TO_CHAR(V.data_venda, 'MM/YY') AS mes, SUM(I.taxa) AS total_imposto " +
						"FROM Venda V " +
						"JOIN Endereco En " +
						"ON V.ENDERECO_ENTREGA = En.endereco_id " +
						"JOIN Estado E " +
						"ON En.estado_id = E.estado_id " +
						"JOIN Imposto I " +
						"ON E.estado_id = I.estado_id " +
						filter +
						"ROWNUM <= " + maxRowsNumber +
						" GROUP BY ROLLUP(E.nome, TO_CHAR(V.data_venda, 'MM/YY')) " +
						"ORDER BY E.nome, TO_CHAR(V.data_venda, 'MM/YY')";

		try {
			ArrayList<TaxesPaidModel> list = new ArrayList<TaxesPaidModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				TaxesPaidModel tp = 
						new TaxesPaidModel(
								rs.getString("estado"),
								rs.getString("mes"),
								rs.getDouble("total_imposto"));

				list.add(tp);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public ArrayList<CustomerCountryStateModel> getCcsArray(){

		String query = 
				"SELECT EST.PAIS AS Pais, EST.NOME AS Estado ,COUNT(C.CLIENTE_ID) as Clientes "+
						"FROM CLIENTE C "+
						"JOIN PESSOA P "+
						"ON C.PESSOA_ID = P.PESSOA_ID "+
						"JOIN ENDERECO ENDE "+
						"ON P.PESSOA_ID = ENDE.PESSOA_ID "+
						"JOIN ESTADO EST "+
						"ON ENDE.ESTADO_ID = EST.ESTADO_ID "+
						"GROUP BY ROLLUP (EST.PAIS, EST.NOME) "+
						"ORDER BY EST.PAIS, EST.NOME";

		try {
			ArrayList<CustomerCountryStateModel> list = new ArrayList<CustomerCountryStateModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				CustomerCountryStateModel csm = 
						new CustomerCountryStateModel(
								rs.getString("Pais"),
								rs.getString("Estado"),
								rs.getInt("Clientes"));


				list.add(csm);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}

	public ArrayList<SalesMonthModel> getSmArray(String mes){
		
		String filter = "";
		if(mes != null && !mes.isEmpty()){
			filter = "WHERE EXTRACT(MONTH from DATA_VENDA) = "+mes+" AND ";
		}else{
			filter = "WHERE ";
		}

		String query = 
				"SELECT SOMA_SUBTOTAL.DIA AS Dia, SOMA_SUBTOTAL.VENDAS_DIA AS VendasDia, "+
						"TRUNC(avg (SOMA_SUBTOTAL.VENDAS_DIA) OVER " +
						"(ORDER BY SOMA_SUBTOTAL.DIA ROWS BETWEEN 2 PRECEDING AND 2 FOLLOWING),5) AS Media "+
						"FROM ( "+
						"SELECT "+
						"TO_CHAR(data_venda, 'DD/MM/YY') as dia, "+
						"SUM(VENDA.TOTAL_DEVIDO) AS VENDAS_DIA "+
						"FROM VENDA "+
						filter+
						"ROWNUM <= " + maxRowsNumber +
						" GROUP BY (TO_CHAR(data_venda, 'MM/YY'), TO_CHAR(data_venda, 'DD/MM/YY'))) SOMA_SUBTOTAL";

		try {
			ArrayList<SalesMonthModel> list = new ArrayList<SalesMonthModel>();
					
			ResultSet rs = select(query);
			
			while(rs.next()){
				SalesMonthModel csm = 
						new SalesMonthModel(
								rs.getString("Dia"),
								rs.getInt("VendasDia"),
								rs.getDouble("Media"));


				list.add(csm);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		
		return null;
	}
	
	public ArrayList<SalesYearStateModel> getSysArray(){
		String query = 
				"SELECT TOTAL.PAIS, " +
						"NOME.ESTADO, " +
						"NOME.TOTAL_ESTADO, " +
						"TRUNC(((NOME.TOTAL_ESTADO * 100)/TOTAL.VENDA_TOTAL_PAIS),4) AS PORCENTAGEM_PAIS, " +
						"TRUNC(((NOME.TOTAL_ESTADO * 100)/TOTAL_MUNDO.VENDA_TOTAL_MUNDO),4) AS PORCENTAGEM_MUNDO " +
						"FROM ( " +
							"SELECT EST.PAIS AS PAIS, " + 
								"EST.NOME AS ESTADO, "+ 
								"SUM(SUBTOTAL) AS TOTAL_ESTADO " +
								"FROM VENDA V " +
								"JOIN ENDERECO ENDE " +
									"ON V.ENDERECO_ENTREGA = ENDE.ENDERECO_ID " +
								"JOIN ESTADO EST " +
									"ON ENDE.ESTADO_ID = EST.ESTADO_ID " +
								"GROUP BY (EST.PAIS, EST.NOME) " +
								"ORDER BY EST.PAIS) NOME, " +
							"(SELECT EST.PAIS AS PAIS, " +
								"SUM(SUBTOTAL) AS VENDA_TOTAL_PAIS " +
								"FROM VENDA V " +
								"JOIN ENDERECO ENDE " +
									"ON V.ENDERECO_ENTREGA = ENDE.ENDERECO_ID " +
								"JOIN ESTADO EST " +
									"ON ENDE.ESTADO_ID = EST.ESTADO_ID " +
								"GROUP BY (EST.PAIS) " +
								"ORDER BY EST.PAIS) TOTAL, " +
							"(SELECT SUM(SUBTOTAL) AS VENDA_TOTAL_MUNDO " +
								"FROM VENDA V " +
								"JOIN ENDERECO ENDE " +
									"ON V.ENDERECO_ENTREGA = ENDE.ENDERECO_ID " +
								"JOIN ESTADO EST " +
									"ON ENDE.ESTADO_ID = EST.ESTADO_ID) TOTAL_MUNDO " +
						"WHERE NOME.PAIS = TOTAL.PAIS " +
						"AND ROWNUM <= " + maxRowsNumber +
						" ORDER BY NOME.PAIS, PORCENTAGEM_PAIS DESC, PORCENTAGEM_MUNDO DESC";

		try {
			ArrayList<SalesYearStateModel> list = new ArrayList<SalesYearStateModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				System.out.println("ok");
				SalesYearStateModel sys = 
						new SalesYearStateModel(
								rs.getString("pais"),
								rs.getString("estado"),
								rs.getDouble("total_estado"),
								rs.getDouble("porcentagem_pais"),
								rs.getDouble("porcentagem_mundo"));

				list.add(sys);	
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public ArrayList<TransportModel> getTrArray(String year){
		String filter = " ";
		if(year != null && !year.isEmpty()){
			filter = "WHERE EXTRACT(YEAR from data_venda) = "+year+" AND ";
		}else{
			filter = "WHERE ";
		}
		
		String query = 
				"SELECT S1.transportadora, S1.mes, S1.mais_mil, S2.ate_mil " +
				"FROM (SELECT MT.NOME AS transportadora, TO_CHAR(DATA_VENDA, 'MM/YY') AS mes, " +
				" SUM(FRETE) AS mais_mil " +
						"FROM VENDA V " +
						"JOIN METODO_ENTREGA MT " +
						"ON V.METODO_ENTREGA_ID = MT.METODO_ENTREGA_ID " +
						filter +
						"SUBTOTAL > 1000 AND " +
						"ROWNUM <= " + maxRowsNumber +
						" GROUP BY ROLLUP (MT.NOME, TO_CHAR(DATA_VENDA, 'MM/YY'))) S1 " +
				"JOIN " +
				"(SELECT MT.NOME AS transportadora, TO_CHAR(DATA_VENDA, 'MM/YY') AS mes, SUM(FRETE) AS ate_mil " +
					"FROM VENDA V " +
					"JOIN METODO_ENTREGA MT " +
					"ON V.METODO_ENTREGA_ID = MT.METODO_ENTREGA_ID " +
					filter +
					"SUBTOTAL < 1000 AND " +
					"ROWNUM <= " + maxRowsNumber +
					" GROUP BY ROLLUP (MT.NOME, TO_CHAR(DATA_VENDA, 'MM/YY')) " +
				"ORDER BY transportadora) S2 ON S1.transportadora = S2.transportadora";

		try {
			ArrayList<TransportModel> list = new ArrayList<TransportModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				TransportModel tr = 
						new TransportModel(
								rs.getString("transportadora"),
								rs.getString("mes"),
								rs.getDouble("ate_mil"),
								rs.getDouble("mais_mil"));

				list.add(tr);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}

	public ArrayList<SalesYearModel> getSyArray(String year){
		String filter = " ";
		if(year != null && !year.isEmpty()){
			filter = "WHERE EXTRACT(YEAR from data_venda) = "+year+" ";
		}
		
		String query = 
				"SELECT NOME.DATAA AS MES, " +
						"NOME.SOMA AS VENDIDO_MES, " +
						"TRUNC((((NOME.NV-NOME.SOMA)/NOME.SOMA)*100),2) AS alteracao " +
						"FROM " +
						"(SELECT " +
						"TO_CHAR(V.data_venda, 'MM/YY') AS DATAA, " +
						"SUM(V.SUBTOTAL) AS SOMA, " +
						"NTH_VALUE(SUM(SUBTOTAL),2) FROM LAST OVER(ORDER BY TO_CHAR(V.data_venda, 'MM/YY')) AS nv " +
						"FROM VENDA V " +
						"JOIN VENDA_ITEM VI " +
						"ON V.VENDA_ID = VI.VENDA_ID " +
						filter +
						" GROUP BY TO_CHAR(V.data_venda, 'MM/YY')) NOME " +
						" ORDER BY MES ASC ";

		try {
			ArrayList<SalesYearModel> list = new ArrayList<SalesYearModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				SalesYearModel sy = 
						new SalesYearModel(
								rs.getString("mes"),
								rs.getDouble("vendido_mes"),
								rs.getDouble("alteracao"));

				list.add(sy);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	
	//Dashboard methods
	public ArrayList<DashboardTopSoldModel> getDashboardTopSold(){

		String query = 
				"SELECT * FROM (" +
				"SELECT Produto.nome, count(*) as quantidade FROM Venda_item "+
				"JOIN Produto ON Venda_item.produto_id = Produto.produto_id "+
				"JOIN Venda V ON Venda_item.venda_id = V.venda_id "+
				"WHERE V.data_venda between " +
				"(select max(V.data_venda) from venda) - 30 " +
 				"and (select max(V.data_venda) from venda) "+
				"GROUP BY Produto.nome " +
				"ORDER BY quantidade DESC) " +
				"WHERE ROWNUM <= 10";

		try {
			ArrayList<DashboardTopSoldModel> list = new ArrayList<DashboardTopSoldModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				DashboardTopSoldModel sy = 
						new DashboardTopSoldModel(
								rs.getString("nome"),
								rs.getDouble("quantidade"));

				list.add(sy);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public ArrayList<DashboardTopSoldModel> getDashboardLowStock(){

		String query = 
				"SELECT nome, quantidade FROM Produto "+
				"WHERE quantidade < 15 "+
				"ORDER BY quantidade ASC";

		try {
			ArrayList<DashboardTopSoldModel> list = new ArrayList<DashboardTopSoldModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				DashboardTopSoldModel sy = 
						new DashboardTopSoldModel(
								rs.getString("nome"),
								rs.getDouble("quantidade"));

				list.add(sy);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	//Retorna soma de todos os valores vendidos em determinado dia
	public String getDashboardSaleSum(String date){

		String query = 
				"select TO_CHAR(data_venda, 'dd/mm/yy') as data, SUM(subtotal) as soma " +
				"from venda "+
				"where trunc(data_venda) = TO_DATE('"+date+"','dd/mm/yy') "+
				"group by data_venda";

		try {
			ResultSet rs = select(query);
			if(rs.next()){
				return rs.getString("soma");
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			return "N/A";
		}
		return "N/A";
	}
	
	//Soma das vendas nos ultimos 30 dias
	public ArrayList<DashboardTopSoldModel> getDashboardSumLast30Days(){

		String query = 
				"select TO_CHAR(data_venda, 'dd/MM/YY') as data_venda, "+
				"SUM(subtotal) as soma from venda "+
				"WHERE data_venda between " +
				"(select max(data_venda) from venda) - 30 "+
				"AND (select max(data_venda) from venda) "+
				"group by data_venda";

		try {
			ArrayList<DashboardTopSoldModel> list = new ArrayList<DashboardTopSoldModel>();
			ResultSet rs = select(query);
			while(rs.next()){
				DashboardTopSoldModel sy = 
						new DashboardTopSoldModel(
								rs.getString("data_venda"),
								rs.getDouble("soma"));

				list.add(sy);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	
	public boolean closeConnection(){
		try {
			connection.close();
			System.out.println("Connection closed");
		} catch (SQLException ex) {
			System.out.println("Failed to close connection");
			return false;
		}
		return true;
	}
}
