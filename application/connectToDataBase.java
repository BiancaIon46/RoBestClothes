package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectToDataBase {
	
	public Connection con;
	
	public void ConnectToDB() {
		con = null;
		String connectionString = "jdbc:sqlserver://DESKTOP-GTC6U0S;Database=FabricaImbracaminte-BiancaIon;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2";
		try {
			con = DriverManager.getConnection(connectionString); 
			System.out.println("Connected");
//			Statement stm=(Statement) con.createStatement();
//		    ResultSet rs=stm.executeQuery(insertQuery);
		} catch(SQLException e){
			System.out.println("Connection error");
			e.printStackTrace();
		}
	}
	
	public connectToDataBase() {
		ConnectToDB();
	}
}
