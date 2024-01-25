package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddMaterialController {
	@FXML TextField Nume;
    @FXML TextField Culoare;
    @FXML TextField Pret;
    @FXML TextField Calitate;
    
    private Stage stage;
    private Scene scene;
    
    public void MenuPageScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    
//    public Connection ConnectToDB() {
//		Connection con = null;
//		String connectionString = "jdbc:sqlserver://DESKTOP-GTC6U0S;Database=FabricaImbracaminte-BiancaIon;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2";
//		try {
//			con = DriverManager.getConnection(connectionString); 
//			System.out.println("Connected");
////			Statement stm=(Statement) con.createStatement();
////		    ResultSet rs=stm.executeQuery(insertQuery);
//		} catch(SQLException e){
//			System.out.println("Connection error");
//			e.printStackTrace();
//		}
//		
//		return con;
//	}
    
    public String createInsertQuery(String name, String color, int price, int quality) {
		String insertProviderQuery = "INSERT INTO Materiale (Nume, Culoare, Pret, Calitate) VALUES ('" + name + "', '"  + color + "', '" + price + "', '" + quality + "');";
		
		return insertProviderQuery;
	}
	
	public void insertMaterialIntoDB(String insertQuery, Connection con) {
		Statement stm;
		try {
			stm = (Statement) con.createStatement();
			stm.execute(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void extractMaterailInfo(ActionEvent event) throws IOException {
		String name = Nume.getText();
		String color = Culoare.getText();
		int price = 0;
		if(Pret.getText().length() > 0) {
			price = Integer.parseInt(Pret.getText());
		}
		else price = 0;
		int quality = 0;
		if(Calitate.getText().length() > 0) {
			quality = Integer.parseInt(Calitate.getText());
		}
		else quality = 0;
		
//		Connection con = ConnectToDB(); // connect to database
		
//		System.out.println("Nume = " + name);
//		System.out.println("ManagerName = " + managerName);
//		System.out.println("departmentCode = " + departmentCode);
		
		if(name.length() <= 0 || color.length() <= 0 || quality == 0 || price == 0) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campuri necompletate");
            alert.setHeaderText("Completati toate campurile pentru a putea adauga un material.");
            alert.showAndWait();
		} else {
			insertMaterialIntoDB(createInsertQuery(name, color, price, quality), Main.con.con);
		}
	}
}
