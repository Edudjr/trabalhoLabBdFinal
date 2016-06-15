package application;

import java.sql.* ;  // for standard JDBC programs
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.CustomerShoppingModel;
import model.Person;

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
