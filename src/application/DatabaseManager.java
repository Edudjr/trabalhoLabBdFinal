package application;

import java.sql.* ;  // for standard JDBC programs
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.CustomerCountryStateModel;
import model.CustomerShoppingModel;
import model.Person;
import model.ProductSalesCategoryModel;
import model.SalesMonthModel;

public class DatabaseManager {
	private final String username = "e7986538";
    private final String password = "e7986538";
    public Connection connection = null;
    public Integer maxRowsNumber = 50;
    
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
			System.out.println("Driver não encontrado");
			e.printStackTrace();
			return;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro na conexão com o banco");
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
    	
    	String query = 
        		"SELECT SUM(V.subtotal) AS Soma, COUNT(*) AS Numero, " +
        		"TO_CHAR(V.data_venda, 'YYYY') AS Ano, C.cliente_id AS ClienteId, P.nome AS Nome "+
        		"FROM Venda V " +
        		      "JOIN Cliente C "+
        				"ON C.cliente_id = V.cliente_id "+
        		      "JOIN Pessoa P "+
        				"ON P.pessoa_id = C.pessoa_id "+
        				"WHERE ROWNUM <= " + maxRowsNumber +
        				" GROUP BY ROLLUP (C.cliente_id, TO_CHAR(V.data_venda, 'YYYY')) "+
        		   "ORDER BY C.cliente_id";
    	
    	try {
            ArrayList<CustomerShoppingModel> list = new ArrayList<CustomerShoppingModel>();
            ResultSet rs = select(query);
            while(rs.next()){
            	CustomerShoppingModel csm = 
            			new CustomerShoppingModel(
            					rs.getString("Nome"),
            					rs.getString("ClienteId"),
            					rs.getDouble("Soma"),
            					rs.getInt("Numero"),
            					rs.getInt("Ano"));
            		
            	list.add(csm);
            	System.out.println("ok");
            }
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ArrayList<ProductSalesCategoryModel> getPscArray(){
    	
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
        				"ON P.subcategoria = Sub.subcategoria_id" +
        			"WHERE data_venda LIKE '%/13' " +
        			"GROUP BY ROLLUP(Sub.categoria, Sub.NOME, P.NOME, TO_CHAR(V.data_venda, 'MM/YY')) " +
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
            	System.out.println("ok");
            }
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
public ArrayList<CustomerCountryStateModel> getCcsArray(){
    	
    	String query = 
    	"SELECT EST.PAIS AS pais, EST.NOME AS estado , COUNT(C.CLIENTE_ID) AS total "+
    	  "FROM CLIENTE C "+
    	    "JOIN PESSOA P "+
    	      "ON C.PESSOA_ID = P.PESSOA_ID "+
    	    "JOIN ENDERECO ENDE "+
    	      "ON P.PESSOA_ID = ENDE.PESSOA_ID "+
    	    "JOIN ESTADO EST "+
    	      "ON ENDE.ESTADO_ID = EST.ESTADO_ID "+
    	      "WHERE ROWNUM <= " + maxRowsNumber +
    	  " GROUP BY ROLLUP (EST.PAIS, EST.NOME) "+
    	  "ORDER BY PAIS";
    	
    	try {
            ArrayList<CustomerCountryStateModel> list = new ArrayList<CustomerCountryStateModel>();
            ResultSet rs = select(query);
            while(rs.next()){
            	CustomerCountryStateModel csm = 
            			new CustomerCountryStateModel(
            					rs.getString("pais"),
            					rs.getString("estado"),
            					rs.getInt("total"));
            	
            		
            	list.add(csm);
            	System.out.println("ok");
            }
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

public ArrayList<SalesMonthModel> getSmArray(String mes){
	
	String query = 
	"SELECT NOME.data AS data, NOME.TOTAL_VENDIDO_POR_DIA AS total, "+
    "SUM (NOME.TOTAL_VENDIDO_POR_DIA) OVER (ORDER BY NOME.dataa ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) AS TOT, "+
    "SUM (NOME.TOTAL_VENDIDO_POR_DIA) OVER (ORDER BY NOME.dataa ROWS BETWEEN CURRENT ROW AND 2 FOLLOWING) AS TOT2 "+
    "FROM (" +
       "SELECT " + 
          "TO_CHAR(data_venda, 'DD/MM/YY') as dataa, "+
          "SUM(SUBTOTAL) AS TOTAL_VENDIDO_POR_DIA "+
       "FROM VENDA "+
          "WHERE DATA_VENDA LIKE '%/12/13' "+ //usar mes aqui
          "AND ROWNUM <= " + maxRowsNumber +
       "GROUP BY (TO_CHAR(data_venda, 'MM/YY'), TO_CHAR(data_venda, 'DD/MM/YY'))) NOME";
	
	try {
        ArrayList<SalesMonthModel> list = new ArrayList<SalesMonthModel>();
        ResultSet rs = select(query);
        while(rs.next()){
        	SalesMonthModel csm = 
        			new SalesMonthModel(
        					rs.getString("date"),
        					rs.getDouble("total"));
        	
        		
        	list.add(csm);
        	System.out.println("ok");
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
