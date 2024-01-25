package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowDepartmentsController extends UpdateDepartmentController {
	@FXML TableView<Department> departmentTableView;
    @FXML TableColumn<Department, String> NumeCol;
    @FXML TableColumn<Department, String> NumeManagerCol;
    @FXML TableColumn<Department, String> PrenumeManagerCol;
    @FXML TableColumn<Department, String> CNPManagerCol;
    @FXML TableColumn<Department, String> CodDepartamentCol;
    
    private Stage stage;
    private Scene scene;
    
    ObservableList<Department> selectedDepartment;

    public void MenuPageScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
   
    public List<Department> getAllDepartments(Connection con) {
		String SelectQuery = "SELECT D.Nume AS NumeDepartament, A.Nume, A.Prenume, A.CNP, D.CodDepartament FROM Angajat A JOIN Departamente D ON D.ManagerID = A.AngajatID";
		Statement stm;
		String firstName = null, lastName = null, cnp = null;
		String departmentCode = null, departmentName = null;
		List<Department> departments = new ArrayList<Department>();
		
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(SelectQuery);
			while(rs.next()) {
				departmentName = rs.getString("NumeDepartament");
				lastName = rs.getString("Nume");
				firstName = rs.getString("Prenume");
				cnp = rs.getString("CNP");
				departmentCode = rs.getString("CodDepartament");
				Department department = new Department(departmentName, lastName, firstName, cnp, departmentCode);
				departments.add(department);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return departments;
	}
    
    public void populateTableView(Connection con) {
    	NumeCol.setCellValueFactory(new PropertyValueFactory<Department, String>("NumeCol"));
        NumeManagerCol.setCellValueFactory(new PropertyValueFactory<Department, String>("NumeManagerCol"));
        PrenumeManagerCol.setCellValueFactory(new PropertyValueFactory<Department, String>("PrenumeManagerCol"));
        CNPManagerCol.setCellValueFactory(new PropertyValueFactory<Department, String>("CNPManagerCol"));
        CodDepartamentCol.setCellValueFactory(new PropertyValueFactory<Department, String>("CodDepartamentCol"));

        List<Department> departments = getAllDepartments(con);
        ObservableList<Department> d = FXCollections.observableArrayList(departments);
        System.out.println(d.get(0).getNumeCol());
        System.out.println(departments.size());
        departmentTableView.setItems(d);
    }
    
    public void rowSelected(MouseEvent event) throws IOException {
		selectedDepartment = departmentTableView.getSelectionModel().getSelectedItems();
        if(selectedDepartment.size() > 0)
        	System.out.println(selectedDepartment.get(0).getNumeCol());
	}
    
    public String createDeleteQuery(String departmentName, String departmentCode) {
		String deleteQuery = "DELETE FROM Departamente WHERE Nume = '" + departmentName + "' AND CodDepartament = '" + departmentCode + "'";
		System.out.println(deleteQuery);
		return deleteQuery;
	}
    
    public void deleteDepartment(ActionEvent event) throws IOException {
    	if (selectedDepartment != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("CONFIRMATION");
            alert.setTitle("Stergere departament");
            alert.setContentText("Inregistrarea va fi stearsa din baza de date. Doriti sa continuati?");
            alert.showAndWait();
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
				String departmentName = selectedDepartment.get(0).getNumeCol();
		        String departmentCode = selectedDepartment.get(0).getCodDepartamentCol();
				
				String deleteQuery = createDeleteQuery(departmentName, departmentCode);
				System.out.println(deleteQuery);
				Statement stm;
				try {
					stm = (Statement) Main.con.con.createStatement();
					stm.execute(deleteQuery);
					populateTableView(Main.con.con);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
    	}
            else {
            	Alert alert1 = new Alert(AlertType.WARNING);
	       		alert1.setHeaderText("WARNING");
                alert1.setTitle("Zero selectie");
                alert1.setContentText("Pentru executarea operatiunii de stergere trebuie sa selectati un departament.");
                alert1.showAndWait();
            }
    }
    
    public void UpdateDepartmentScene(ActionEvent event) throws IOException {
        if (selectedDepartment != null) {
            Parent root = FXMLLoader.load(getClass().getResource("UpdateDepartment.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            updateDepartmentTable();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Zero selectie");
            alert.setHeaderText("WARNING");
            alert.setContentText("Selectati un departament pentru a putea face update.");
            alert.showAndWait();
        }
    }
    
    public void updateDepartmentTable() throws IOException {
        String departmentName = selectedDepartment.get(0).getNumeCol();
        String departmentCode = selectedDepartment.get(0).getCodDepartamentCol();

        getDepartmentInfo(departmentName, departmentCode);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		populateTableView(Main.con.con);
	}
}
