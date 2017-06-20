package gdi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {
	// 	JDBC driver name and database URL
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	// URL to database
	static final String DB_URL = "jdbc:oracle://localhost/";
	
	//  Database credentials
	static final String USER = "sys";
	static final String PASS = "123";


	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			//Loads oracle driver
			Class.forName("oracle.jdbc.driver.OracleDriver	");
			
			//Connects to DB
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			//Manages queries
			stmt = conn.createStatement();
			
			//Test query
			ResultSet query = stmt.executeQuery("SELECT F.NOME AS NOME FROM TB_FUNCIONARIO");
			System.out.println(query.getString("NOME"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
