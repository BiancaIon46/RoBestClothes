package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateEmployeeController implements Initializable{
	@FXML TextField Nume;
	@FXML TextField Prenume;
	@FXML TextField CNP;
	@FXML TextField Mail;
	@FXML DatePicker DataNastere;
	
	@FXML CheckBox sex_F;
	@FXML CheckBox sex_M;
	
	@FXML TextField Judet;
	@FXML TextField Oras;
	@FXML TextField Strada;
	@FXML TextField Nr;
	
	@FXML DatePicker DataAngajare;
	@FXML TextField Salariu;
	
	@FXML ChoiceBox<String> Filiala;
	@FXML ChoiceBox<String> Departament;
	
	private Stage stage;
	private Scene scene; 
	
	static String lastName, firstName, cnp;
	
	public void ShowEmployeeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowAndFilterEmployees.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void getFirstAndLastName(String lName, String fName, String CNP) {
		lastName = lName;
		firstName = fName;
		cnp = CNP;
		System.out.println(lastName + firstName + cnp);
		//return lastName + firstName;
	}
	
	public String createUpdateQuery(String updateCell, String updateVal) {
		System.out.println("miau" + lastName + firstName);
		String updateQuery = "UPDATE Angajat SET " + updateCell + " = '" + updateVal + "' WHERE Nume = '" + lastName + "' AND Prenume = '" + firstName + "' AND CNP = '" + cnp + "'";
		
		return updateQuery;
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
	
	public void updateName(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = Nume.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Nume", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateFirstName(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = Prenume.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Prenume", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCNP(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = CNP.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("CNP", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateMail(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = Mail.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Mail", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCityDepartment(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = Oras.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Oras", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCity(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = Judet.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Judet", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateStreet(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = Strada.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Strada", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateSalary(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = Salariu.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Salariu", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateNr(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = Nr.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Nr", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBirthday(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = DataNastere.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("DataNasterii", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateHireday(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = DataAngajare.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("DataAngajarii", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateSex(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String newVal = null;
		if(sex_F.isSelected()) {
			newVal = "F";
		}
		else if(sex_M.isSelected()) {
			newVal = "M";
		}
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Sex", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getDepartmentID(String department, Connection con){
		String SelectQuery = "SELECT DepartamentID FROM Departamente WHERE Nume = '" + department + "'";
		System.out.println(SelectQuery);
		Statement stm;
		int DepartmentID = -1;
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(SelectQuery);
			while(rs.next())
				DepartmentID = rs.getInt("DepartamentID");
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return DepartmentID;
	}
	
	public void updateDepartment(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String department = Departament.getValue();
		int newVal = getDepartmentID(department, Main.con.con);
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("DepartamentID", Integer.toString(newVal));
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getHeadquarterID(String headquarter, Connection con){
		String SelectQuery = "SELECT FilialaID FROM Filiala WHERE Oras = '" + headquarter + "'";
		Statement stm;
		int headquarterID = -1;
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(SelectQuery);
			while(rs.next())
		    	headquarterID = rs.getInt("FilialaID");
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return headquarterID;
	}
	
	public void updateHeadquarter(ActionEvent event)  throws IOException {
//		Connection con = ConnectToDB();
		String headquarter = Filiala.getValue();
		int newVal = getHeadquarterID(headquarter, Main.con.con);
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("FilialaID", Integer.toString(newVal));
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getAllDepartments(Connection con){
		String SelectQuery = "SELECT Nume FROM Departamente";
		Statement stm;
		List<String> departments = new ArrayList<String>();

		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(SelectQuery);
			while(rs.next())
				departments.add(rs.getString("Nume"));
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return departments;
	}
	
	public List<String> getAllHeadquarters(Connection con){
		String SelectQuery = "SELECT Oras FROM Filiala";
		Statement stm;
		List<String> headquarters = new ArrayList<String>();

		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(SelectQuery);
			while(rs.next())
		    	headquarters.add(rs.getString("Oras"));
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return headquarters;
	}
	
	 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		Connection con = ConnectToDB();
		Filiala.getItems().addAll(getAllHeadquarters(Main.con.con));
		Departament.getItems().addAll(getAllDepartments(Main.con.con));
	}
}
