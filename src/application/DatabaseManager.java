package application;

import java.sql.* ;  // for standard JDBC programs
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.CustomerCountryStateModel;
import model.CustomerShoppingModel;
import model.Person;
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
    	String filter = " ";
    	
    	if (name != null){
    		filter = "WHERE P.NOME = '" + name+"' "; 
    	}
    	if (clientId != null){
    		filter = filter.concat("' OR P.PESSOA_ID = '"+clientId+"' ");
    	}
    	
    	
    	String query = 
    			"SELECT  P.NOME AS Nome,  " +
    			"TO_CHAR(V.data_venda, 'YYYY') AS Ano, COUNT(*) AS Compras, " +
    			"SUM(V.subtotal) AS TotalComprado " +
    			   "FROM Venda V " +
    			      "JOIN Cliente C " + 
    					"ON C.cliente_id = V.cliente_id "+ 
    			      "JOIN Pessoa P " +
    					"ON P.pessoa_id = C.pessoa_id " +
    			    filter +
    			    "AND ROWNUM <= " + maxRowsNumber +
    					"GROUP BY ROLLUP (P.NOME, TO_CHAR(V.data_venda, 'YYYY')) " +
    			   "ORDER BY P.NOME";
    	
    	try {
            ArrayList<CustomerShoppingModel> list = new ArrayList<CustomerShoppingModel>();
            ResultSet rs = select(query);
            while(rs.next()){
            	System.out.println("ok");
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
