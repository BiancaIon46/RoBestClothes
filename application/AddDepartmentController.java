package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddDepartmentController implements Initializable {

	@FXML ChoiceBox<String> Manager;
    @FXML TextField Nume;
    @FXML TextField CodDepartament;
	
	private Stage stage;
	private Scene scene; 
	
	public void MenuPageScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
//	public Connection ConnectToDB() {
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
	
	// nu imi mai trebuie
	public int getManagerID(String managerName, Connection con) {
		String[] names = managerName.split(" ");
		String firstName, lastName;
		
		lastName = names[0];
		firstName = names[1];
		
		String getManagerIDQuery = "SELECT AngajatID FROM Angajat WHERE Nume = '" + lastName + "' AND Prenume = '" + firstName + "'";
		Statement stm;
		int managerID = -1;
		
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(getManagerIDQuery);
			while(rs.next())
				managerID = rs.getInt("AngajatID");
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return managerID;
	}
	
	public String createInsertQuery(String name, String managerLastName, String managerFirstName, String managerCNP, String departmentCode) {
		String insertDepartmentQuery = "INSERT INTO Departamente (Nume, ManagerID, CodDepartament) VALUES ('" + name + "', " + "(SELECT AngajatID FROM Angajat WHERE Nume = '" + managerLastName + "' AND Prenume = '" + managerFirstName + "' AND CNP = '" + managerCNP + "')" + ", '" + departmentCode + "');";
		System.out.println("Subcerere 1 (Interogare complexa");
		return insertDepartmentQuery;
	}
	
	public void insertDepartmentIntoDB(String insertQuery, Connection con) {
		Statement stm;
		try {
			stm = (Statement) con.createStatement();
			stm.execute(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void extractDepartmentInfo(ActionEvent event) throws IOException {
		String name = Nume.getText();
		String departmentCode = CodDepartament.getText();
		
		String managerName = Manager.getValue();
		
//		Connection con = ConnectToDB(); // connect to database
		
		//int managerID = getManagerID(managerName, con);
		
//		System.out.println("Nume = " + name);
//		System.out.println("ManagerName = " + managerName);
//		System.out.println("departmentCode = " + departmentCode);
		
		if(name.length() <= 0 || departmentCode.length() <= 0 || managerName.length() <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campuri necompletate");
            alert.setHeaderText("Completati toate campurile pentru a putea adauga un departament.");
            alert.showAndWait();
		} else {
			String[] managerInfo = managerName.split(" ");
			insertDepartmentIntoDB(createInsertQuery(name, managerInfo[0], managerInfo[1], managerInfo[2], departmentCode), Main.con.con);
		}
	}
	
	public List<String> getAllPossibleManagers(Connection con) {
		String SelectQuery = "SELECT A.AngajatID, A.Nume, A.Prenume, A.CNP FROM Angajat A WHERE A.AngajatID NOT IN (SELECT ManagerID FROM Departamente)";
		Statement stm;
		List<String> employees = new ArrayList<String>();
		String lastName, firstName, cnp, info;
		
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(SelectQuery);
			while(rs.next()) {
				lastName = rs.getString("Nume");
				firstName = rs.getString("Prenume");
				cnp = rs.getString("CNP");
				info = lastName + " " + firstName + " " + cnp;
		    	employees.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return employees;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Manager.getItems().addAll(getAllPossibleManagers(Main.con.con));
	}
}
