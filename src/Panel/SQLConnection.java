package Panel;

import java.sql.*;
import javax.swing.*;


public class SQLConnection {
	Connection conn = null;
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_62";

	//  Database credentials
	static final String USER = "cs4400_Group_62";
	static final String PASS = "nUs2GmSz";

	public static Connection dbConnector(){
		Connection conn = null;
		//have to call this method after every initialiczation

		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;

		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}


	}

}
