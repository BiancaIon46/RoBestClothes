package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class AddEmployeeController implements Initializable{
	
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
	
	String[] Filiale = {"Buzau", "Bucuresti"};
	String[] Departamente = {"Design", "Productie", "Resurse Umane", "Contabilitate"};
	
	String tableName = "Angajat";
	
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
	
	public String createInsertQuery(String lastName, String firstName, String cnp, String BirthDate, String HiringDate, String city_department, String city, int salary, int headquarterID, int departmentID, String sex, String street, int number, String mail) {
		String insertQuery = "INSERT INTO " + tableName + " VALUES " + "('" + lastName + "', " + "'" + firstName + "', " + "'" + cnp + "', " + "'" + BirthDate + "', " + "'" + HiringDate + "', " + "'" + city_department + "', " + "'" + city + "', " + salary + ", " + headquarterID + ", " + departmentID + ", " + "'" + sex + "', " + "'" + street + "', " + number + ", " + "'" + mail + "')";
		
		return insertQuery;
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
	
	public void insertEmployeeIntoDB(String insertQuery, Connection con) {
		Statement stm;
		try {
			stm = (Statement) con.createStatement();
			stm.execute(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void extractEmployeeInfo(ActionEvent event) throws IOException {
		String lastName = Nume.getText();
		String firstName = Prenume.getText();
		String cnp = CNP.getText();
		String mail = Mail.getText();
		String BirthDate = "";
		if(DataNastere.getValue() != null)
			BirthDate = DataNastere.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		String sex = "";
		if(sex_F.isSelected())
			sex = "F";
		else if(sex_M.isSelected())
			sex = "M";
		
		String city_department = Oras.getText();
		String city = Judet.getText();
		System.out.println("Salariu = " + Salariu.getText());
		int salary = 0;
		if(Salariu.getText().length() > 0) {
			salary = Integer.parseInt(Salariu.getText());
		}
		else salary = 0;
		
		String HiringDate = "";
		if(DataAngajare.getValue() != null)
			HiringDate = DataAngajare.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String street = Strada.getText();
		
		int number;
		if(Nr.getText().length() > 0) {
			number = Integer.parseInt(Nr.getText());
		}
		else number = 0;
		
		String headquarter = Filiala.getValue();
		String department = Departament.getValue();
		
		 
		
		int headquarterID = getHeadquarterID(headquarter, Main.con.con);
		int departmentID = getDepartmentID(department, Main.con.con);
		
		System.out.println("Nume = " + lastName);
		System.out.println("Prenume = " + firstName);
		System.out.println("cnp = " + cnp);
		System.out.println("mail = " + mail);
		System.out.println("date = " + BirthDate);
		System.out.println("FilialaID = " + headquarterID);
		System.out.println("DepartamentID = " + departmentID);
		System.out.println("Salariu = " + salary);
		
		//String insertQuery = "INSERT INTO " + tableName + " VALUES " + "('" + lastName + "', " + "'" + firstName + "', " + "'" + cnp + "', " + "'" + BirthDate + "', " + "'" + HiringDate + "', " + "'" + city_department + "', " + "'" + city + "', " + salary + ", " + headquarterID + ", " + departmentID + ", " + "'" + sex + "', " + "'" + street + "', " + number + ", " + "'" + mail + "')";
		//System.out.println(insertQuery);
		
//		String connectionString = "jdbc:sqlserver://DESKTOP-GTC6U0S;Database=FabricaImbracaminte-BiancaIon;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2";
//		System.out.println(connectionString);
//		
//		try {
//			try(Connection connection = DriverManager.getConnection(connectionString)) {
//				System.out.println("Connected");
//				Statement stm=(Statement) connection.createStatement();
//		        ResultSet rs=stm.executeQuery(insertQuery);
//			}
//		} catch(SQLException e){
//			System.out.println("Connection error");
//			e.printStackTrace();
//		}
		
		
		insertEmployeeIntoDB(createInsertQuery(lastName, firstName, cnp, BirthDate, HiringDate, city_department, city, salary, headquarterID, departmentID, sex, street, number, mail), Main.con.con);
        
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Filiala.getItems().addAll(getAllHeadquarters(Main.con.con));
		Departament.getItems().addAll(getAllDepartments(Main.con.con));
	}

}
