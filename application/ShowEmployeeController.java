package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShowEmployeeController extends UpdateEmployeeController implements Initializable{

	@FXML ListView<String> EmployeeListView;
	@FXML ChoiceBox<String> Departament;
	@FXML TextField Salariu;
	
	String selectedEmployee = null;
	
	private Stage stage;
	private Scene scene;
	
	public void MenuPageScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void UpdateEmployeeScene(ActionEvent event) throws IOException {
		if(selectedEmployee != null) {
			Parent root = FXMLLoader.load(getClass().getResource("UpdateEmployee.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			updateEmployeeTable();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Zero selectie");
			alert.setHeaderText("Selectati un angajat pentru a putea face update.");
			alert.showAndWait();
		}
	}
	
	public Connection ConnectToDB() {
		Connection con = null;
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
		
		return con;
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
	
	public List<String> getAllEmployeeNamesAndCNP(Connection con) {
		String SelectQuery = "SELECT Nume, Prenume, CNP FROM Angajat";
		Statement stm;
		String firstName;
		String lastName;
		String cnp;
		List<String> name= new ArrayList<String>();
		
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(SelectQuery);
			while(rs.next()) {
				lastName = rs.getString("Nume");
				firstName = rs.getString("Prenume");
				cnp = rs.getString("CNP");
				name.add(lastName + " " + firstName + " " + cnp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return name;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//List<String> departments = new ArrayList<String>();
		Connection con = ConnectToDB();
		//departments = getAllDepartments(con);
		Departament.getItems().addAll(getAllDepartments(con));
		
		EmployeeListView.getItems().addAll(getAllEmployeeNamesAndCNP(con));
		
		EmployeeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				
				selectedEmployee = EmployeeListView.getSelectionModel().getSelectedItem();
				System.out.println("Current employee " + selectedEmployee);
			}	
		});
	}
	
	public void updateEmployeeTable() throws IOException {
		String[] names;
		String firstName, lastName, cnp;
		
		names = selectedEmployee.split(" ");
		lastName = names[0];
		firstName = names[1];
		cnp = names[2];
		
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateEmployee.fxml"));
		//Parent root = loader.load();
		
		//UpdateEmployeeController updateEmployeeController = loader.getController();
		getFirstAndLastName(lastName, firstName, cnp);
		//stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		//scene = new Scene(root);
//		stage.setScene(scene);
//		stage.show();
		//fillEmployeeInfo(con, lastName, firstName);
	}
	
	public String createDeleteQuery(String lastName, String firstName, String cnp) {
		System.out.println("miau" + lastName + firstName);
		String deleteQuery = "DELETE FROM Angajat WHERE Nume = '" + lastName + "' AND Prenume = '" + firstName + "' AND CNP = '" + cnp + "'";
		
		return deleteQuery;
	}
	
	public void deleteEmployee(ActionEvent event) throws IOException {
		Connection con = ConnectToDB();
		String[] names;
		String firstName, lastName, cnp;
		
		names = selectedEmployee.split(" ");
		lastName = names[0];
		firstName = names[1];
		cnp = names[2];
		
		String deleteQuery = createDeleteQuery(lastName, firstName, cnp);
		System.out.println(deleteQuery);
		Statement stm;
		try {
			stm = (Statement) con.createStatement();
			stm.execute(deleteQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createSalaryFilterQuery(String salary) {
		
	}
	
	public void filterEmployee() {
		String salary = null;
		String department = null;
		int option = 0;
		if(Salariu.getText() != null) {
			salary = Salariu.getText();
		}
		
		if(Departament.getValue() != null) {
			department = Departament.getValue();
		}
		
		if(salary != null && department != null) {
			option = 1;
		}
		else if(salary != null) {
			option = 2;
		}
		else option = 3;
		
		switch(option) {
			case 1:
				
		}
	}
}