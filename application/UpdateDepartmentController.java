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

public class UpdateDepartmentController implements Initializable {
	
	@FXML ChoiceBox<String> Manager;
    @FXML TextField Nume;
    @FXML TextField CodDepartament;
    
    private Stage stage;
	private Scene scene; 
	
	static String updatedDepartmentName, updatedDepartmentCode;
	
	public void ShowDepartmentScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowDepartments.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void getDepartmentInfo(String departmentName, String departmentCode) {
		updatedDepartmentName = departmentName;
		updatedDepartmentCode = departmentCode;
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
	    
		System.out.println(employees.size());
	    return employees;
	}
	
	public String createUpdateQuery(String updateCell, String updateVal) {
		String updateQuery = "UPDATE Departamente SET " + updateCell + " = '" + updateVal + "' WHERE Nume = '" + updatedDepartmentName + "' AND CodDepartament = '" + updatedDepartmentCode + "'";
		
		return updateQuery;
	}

	public void updateDepartmentName(ActionEvent event)  throws IOException {
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
	
	public void updateDepartmentCode(ActionEvent event)  throws IOException {
		String newVal = CodDepartament.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("CodDepartament", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateDepartmentManager(ActionEvent event)  throws IOException {
		String newVal = Manager.getValue();
		System.out.println(newVal);
		String[] managerInfo = newVal.split(" ");
		String updateQuery = "UPDATE Departamente SET ManagerID = (SELECT AngajatID FROM Angajat WHERE Nume = '" + managerInfo[0] + "' AND Prenume = '" + managerInfo[1] +"' AND CNP = '" + managerInfo[2] + "')" + " WHERE Nume = '" + updatedDepartmentName + "' AND CodDepartament = '" + updatedDepartmentCode + "'";	
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Manager.getItems().addAll(getAllPossibleManagers(Main.con.con));
	}
	
	
}
