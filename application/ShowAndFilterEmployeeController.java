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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowAndFilterEmployeeController extends UpdateEmployeeController implements Initializable {

	@FXML private TableView<Employee> employeeTableView;
    @FXML private TableColumn<Employee, String> NumeCol;
    @FXML private TableColumn<Employee, String> PrenumeCol;
    @FXML private TableColumn<Employee, String> CNPCol;
//    @FXML private TableColumn<Employee, String> DataNasterii;
//    @FXML private TableColumn<Employee, String> DataAngajarii;
    @FXML private TableColumn<Employee, String> OrasCol;
    @FXML private TableColumn<Employee, String> JudetCol;
    @FXML private TableColumn<Employee, String> SalariuCol;
    @FXML private TableColumn<Employee, String> FilialaCol;
    @FXML private TableColumn<Employee, String> DepartamentCol;
    @FXML private TableColumn<Employee, String> SexCol;
    @FXML private TableColumn<Employee, String> StradaCol;
    @FXML private TableColumn<Employee, String> NrCol;
    @FXML private TableColumn<Employee, String> MailCol;
    
    @FXML ChoiceBox<String> Department;
    @FXML TextField Salary;

    ObservableList<Employee> selectedEmployee;

    private Stage stage;
    private Scene scene;

    public void MenuPageScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void UpdateEmployeeScene(ActionEvent event) throws IOException {
        if (selectedEmployee != null) {
            Parent root = FXMLLoader.load(getClass().getResource("UpdateEmployee.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            updateEmployeeTable();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Zero selectie");
            alert.setHeaderText("Selectati un angajat pentru a putea face update.");
            alert.showAndWait();
        }
    }

//    public Connection ConnectToDB() {
//        Connection con = null;
//        String connectionString = "jdbc:sqlserver://DESKTOP-GTC6U0S;Database=FabricaImbracaminte-BiancaIon;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2";
//        try {
//            con = DriverManager.getConnection(connectionString);
//            System.out.println("Connected");
//        } catch (SQLException e) {
//            System.out.println("Connection error");
//            e.printStackTrace();
//        }
//
//        return con;
//    }

    public List<String> getAllDepartments(Connection con) {
        String SelectQuery = "SELECT Nume FROM Departamente";
        Statement stm;
        List<String> departments = new ArrayList<String>();

        try {
            stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(SelectQuery);
            while (rs.next())
                departments.add(rs.getString("Nume"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }
    
    public String getHeadquarterNameByID(int headquarterID, Connection con) {
    	String headquarterQuery = "SELECT Oras FROM Filiala WHERE FilialaID = " + headquarterID;
    	Statement stm;
    	String headquarterName = null;
    	
    	try {
            stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(headquarterQuery);
            while (rs.next()) {
            	headquarterName = rs.getString("Oras");
            }
        } catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return headquarterName;
    }
    
    public String getDepartmentNameByID(int departmentID, Connection con) {
    	String departmentQuery = "SELECT Nume FROM Departamente WHERE DepartamentID = " + departmentID;
    	Statement stm;
    	String departmentName = null;
    	
    	try {
            stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(departmentQuery);
            while (rs.next()) {
            	departmentName = rs.getString("Nume");
            }
        } catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return departmentName;
    }

    public List<Employee> getAllEmployees(Connection con) {
        String SelectQuery = "SELECT A.Nume, A.Prenume, A.CNP, A.DataNasterii, A.DataAngajarii, A.Oras, A.Judet, A.Salariu, B.Oras AS Filiala, C.Nume AS Departament, A.Sex, A.Strada, A.Nr, A.Mail " + 
        					"FROM Angajat A LEFT JOIN Filiala B ON A.FilialaID = B.FilialaID LEFT JOIN Departamente C ON A.DepartamentID = C.DepartamentID";
        Statement stm;
        List<Employee> employees = new ArrayList<Employee>();
        
        try {
            stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(SelectQuery);
            while (rs.next()) {
                String lastName = rs.getString("Nume");
                String firstName = rs.getString("Prenume");
                String cnp = rs.getString("CNP");
//                String birthDate = rs.getString("DataNasterii");
//                String hiringDate = rs.getString("DataAngajarii");
                String city_department = rs.getString("Oras");
                String city = rs.getString("Judet");
                int salary = rs.getInt("Salariu");
                String headquarterName = rs.getString("Filiala");
                String departmentName = rs.getString("Departament");
                String sex = rs.getString("Sex");
                String street = rs.getString("Strada");
                int nr = rs.getInt("Nr");
                String mail = rs.getString("Mail");
                
//                String headquarterName = getHeadquarterNameByID(headquarterID, con);
//                String departmentName = getDepartmentNameByID(departmentID, con);
                
                System.out.println(headquarterName + " " + departmentName);
                Employee employee = new Employee(lastName, firstName, cnp, city_department, city, Integer.toString(salary), headquarterName, departmentName, sex, street, Integer.toString(nr), mail);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Interogare simpla -> JOIN 1");
        System.out.println(employees.size());
        return employees;
    }

    public void populateTableView(List<Employee> employees, Connection con) {
    	NumeCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("NumeCol"));
        PrenumeCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("PrenumeCol"));
        CNPCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("CNPCol"));
        OrasCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("OrasCol"));
        JudetCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("JudetCol"));
        SalariuCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("SalariuCol"));
        FilialaCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("FilialaCol"));
        DepartamentCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("DepartamentCol"));
        SexCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("SexCol"));
        StradaCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("StradaCol"));
        NrCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("NrCol"));
        MailCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("MailCol"));

        
        ObservableList<Employee> e = FXCollections.observableArrayList(employees);
        System.out.println(e.get(0).getNumeCol());
        employeeTableView.setItems(e);
    }
    
	@Override
    public void initialize(URL arg0, ResourceBundle arg1) {
//        Connection con = ConnectToDB();
        Department.getItems().addAll(getAllDepartments(Main.con.con));
        List<Employee> employees = getAllEmployees(Main.con.con);
        populateTableView(employees, Main.con.con);
//        NumeCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("NumeCol"));
//        PrenumeCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("PrenumeCol"));
//        CNPCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("CNPCol"));
//        OrasCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("OrasCol"));
//        JudetCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("JudetCol"));
//        SalariuCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("SalariuCol"));
//        FilialaCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("FilialaCol"));
//        DepartamentCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("DepartamentCol"));
//        SexCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("SexCol"));
//        StradaCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("StradaCol"));
//        NrCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("NrCol"));
//        MailCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("MailCol"));
//
//        List<Employee> employees = getAllEmployees(con);
//        ObservableList<Employee> e = FXCollections.observableArrayList(employees);
//        System.out.println(e.get(0).getNumeCol());
//        employeeTableView.setItems(e);
    }
	
	public void rowSelected(MouseEvent event) throws IOException {
		selectedEmployee = employeeTableView.getSelectionModel().getSelectedItems();
        if(selectedEmployee.size() > 0)
        	System.out.println(selectedEmployee.get(0).getNumeCol());
	}

    public void updateEmployeeTable() throws IOException {
        //String[] names = selectedEmployee.split(" ");
        String lastName = selectedEmployee.get(0).getNumeCol();
        String firstName = selectedEmployee.get(0).getPrenumeCol();
        String cnp = selectedEmployee.get(0).getCNPCol();

        getFirstAndLastName(lastName, firstName, cnp);
    }
    
    public String createDeleteQuery(String lastName, String firstName, String cnp) {
		System.out.println("miau" + lastName + firstName);
		String deleteQuery = "DELETE FROM Angajat WHERE Nume = '" + lastName + "' AND Prenume = '" + firstName + "' AND CNP = '" + cnp + "'";
		
		return deleteQuery;
	}
    
    public void deleteEmployee(ActionEvent event) throws IOException {
//		Connection con = ConnectToDB();
		//String[] names;
		//String firstName, lastName, cnp;
		
		//names = selectedEmployee.split(" ");
    	
    	if (selectedEmployee != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("CONFIRMATION");
            alert.setTitle("Stergere angajat");
            alert.setContentText("Inregistrarea va fi stearsa din baza de date. Doriti sa continuati?");
            alert.showAndWait();
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
				String lastName = selectedEmployee.get(0).getNumeCol();
		        String firstName = selectedEmployee.get(0).getPrenumeCol();
		        String cnp = selectedEmployee.get(0).getCNPCol();
				
				String deleteQuery = createDeleteQuery(lastName, firstName, cnp);
				System.out.println(deleteQuery);
				Statement stm;
				try {
					stm = (Statement) Main.con.con.createStatement();
					stm.execute(deleteQuery);
					populateTableView(getAllEmployees(Main.con.con), Main.con.con);
				} catch (SQLException e) {
					e.printStackTrace();
					Alert alert1 = new Alert(AlertType.WARNING);
		       		alert1.setHeaderText("WARNING");
	                alert1.setTitle("Nu se poate sterge angajatul.");
	                alert1.setContentText("Pentru executarea operatiunii de stergere trebuie sa schimbati Managerul/Directorul departamentului/Filialei la care angajatul selectat lucreaza.");
	                alert1.showAndWait();
				}
			}
    	}
            else {
            	Alert alert1 = new Alert(AlertType.WARNING);
	       		alert1.setHeaderText("WARNING");
                alert1.setTitle("Zero selectie");
                alert1.setContentText("Pentru executarea operatiunii de stergere trebuie sa selectati un angajat.");
                alert1.showAndWait();
            }
    }
    
    public List<Employee> SalaryFilter(String filterSalary, Connection con) {
    	String SelectQuery = "SELECT A.Nume, A.Prenume, A.CNP, A.DataNasterii, A.DataAngajarii, A.Oras, A.Judet, A.Salariu, B.Oras AS Filiala, C.Nume AS Departament, A.Sex, A.Strada, A.Nr, A.Mail " + 
				"FROM Angajat A LEFT JOIN Filiala B ON A.FilialaID = B.FilialaID LEFT JOIN Departamente C ON A.DepartamentID = C.DepartamentID WHERE A.Salariu >= " + filterSalary;
		Statement stm;
		List<Employee> employees = new ArrayList<Employee>();
		
		try {
		stm = (Statement) con.createStatement();
		ResultSet rs = stm.executeQuery(SelectQuery);
		while (rs.next()) {
		    String lastName = rs.getString("Nume");
		    String firstName = rs.getString("Prenume");
		    String cnp = rs.getString("CNP");
		    String city_department = rs.getString("Oras");
		    String city = rs.getString("Judet");
		    int salary = rs.getInt("Salariu");
		    String headquarterName = rs.getString("Filiala");
		    String departmentName = rs.getString("Departament");
		    String sex = rs.getString("Sex");
		    String street = rs.getString("Strada");
		    int nr = rs.getInt("Nr");
		    String mail = rs.getString("Mail");
		  
		    System.out.println(headquarterName + " " + departmentName);
		    Employee employee = new Employee(lastName, firstName, cnp, city_department, city, Integer.toString(salary), headquarterName, departmentName, sex, street, Integer.toString(nr), mail);
		    employees.add(employee);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		
		System.out.println("Interogare simpla -> JOIN 1");
		System.out.println(employees.size());
		return employees;
	}
    
    public List<Employee> DepartmentFilter(String filterDepartment, Connection con) {
    	String SelectQuery = "SELECT A.Nume, A.Prenume, A.CNP, A.DataNasterii, A.DataAngajarii, A.Oras, A.Judet, A.Salariu, B.Oras AS Filiala, C.Nume AS Departament, A.Sex, A.Strada, A.Nr, A.Mail " + 
				"FROM Angajat A LEFT JOIN Filiala B ON A.FilialaID = B.FilialaID LEFT JOIN Departamente C ON A.DepartamentID = C.DepartamentID WHERE C.Nume = '" + filterDepartment + "'";
		Statement stm;
		List<Employee> employees = new ArrayList<Employee>();
		
		try {
		stm = (Statement) con.createStatement();
		ResultSet rs = stm.executeQuery(SelectQuery);
		while (rs.next()) {
		    String lastName = rs.getString("Nume");
		    String firstName = rs.getString("Prenume");
		    String cnp = rs.getString("CNP");
		    String city_department = rs.getString("Oras");
		    String city = rs.getString("Judet");
		    int salary = rs.getInt("Salariu");
		    String headquarterName = rs.getString("Filiala");
		    String departmentName = rs.getString("Departament");
		    String sex = rs.getString("Sex");
		    String street = rs.getString("Strada");
		    int nr = rs.getInt("Nr");
		    String mail = rs.getString("Mail");
		  
		    System.out.println(headquarterName + " " + departmentName);
		    Employee employee = new Employee(lastName, firstName, cnp, city_department, city, Integer.toString(salary), headquarterName, departmentName, sex, street, Integer.toString(nr), mail);
		    employees.add(employee);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		
		System.out.println("Interogare simpla -> JOIN 1");
		System.out.println(employees.size());
		return employees;
	}
    
    public List<Employee> SalaryAndDepartmentFilter(String filterSalary, String filterDepartment, Connection con) {
    	String SelectQuery = "SELECT A.Nume, A.Prenume, A.CNP, A.DataNasterii, A.DataAngajarii, A.Oras, A.Judet, A.Salariu, B.Oras AS Filiala, C.Nume AS Departament, A.Sex, A.Strada, A.Nr, A.Mail " + 
				"FROM Angajat A LEFT JOIN Filiala B ON A.FilialaID = B.FilialaID LEFT JOIN Departamente C ON A.DepartamentID = C.DepartamentID WHERE A.Salariu >= " + filterSalary + " AND C.Nume = '" + filterDepartment + "'";
		Statement stm;
		List<Employee> employees = new ArrayList<Employee>();
		
		try {
		stm = (Statement) con.createStatement();
		ResultSet rs = stm.executeQuery(SelectQuery);
		while (rs.next()) {
		    String lastName = rs.getString("Nume");
		    String firstName = rs.getString("Prenume");
		    String cnp = rs.getString("CNP");
		    String city_department = rs.getString("Oras");
		    String city = rs.getString("Judet");
		    int salary = rs.getInt("Salariu");
		    String headquarterName = rs.getString("Filiala");
		    String departmentName = rs.getString("Departament");
		    String sex = rs.getString("Sex");
		    String street = rs.getString("Strada");
		    int nr = rs.getInt("Nr");
		    String mail = rs.getString("Mail");
		    
		    System.out.println(headquarterName + " " + departmentName);
		    Employee employee = new Employee(lastName, firstName, cnp, city_department, city, Integer.toString(salary), headquarterName, departmentName, sex, street, Integer.toString(nr), mail);
		    employees.add(employee);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		
		System.out.println("Interogare simpla -> JOIN 1");
		System.out.println(employees.size());
		return employees;
	}
	
	public void filterEmployee() {
		String salary = "";
		String department = "";
		int option = 0;
		salary = Salary.getText();
		if(Department.getValue() != null) {
			department = Department.getValue();
		}
		
		if(salary.length() > 0 && department.length() >= 1) {
			option = 1;
		}
		else {
			if(salary.length() > 0) {
				option = 2;
			}
			else option = 3;
		}
		
		System.out.println("opt = " + option);
		switch(option) {
			case 1:
				List<Employee> e1 = SalaryAndDepartmentFilter(salary, department, Main.con.con);
				if(e1.size() > 0) {
					populateTableView(e1, Main.con.con);
				}
				else {
					Alert alert1 = new Alert(AlertType.WARNING);
		       		alert1.setHeaderText("WARNING");
	                alert1.setTitle("Zero potrivire");
	                alert1.setContentText("Nu s-a gasit niciun angajat corespunzator cu filtrele alese.");
	                alert1.showAndWait();
				}
				break;
			case 2:
				List<Employee> e2 = SalaryFilter(salary, Main.con.con);
				if(e2.size() > 0) {
					populateTableView(e2, Main.con.con);
				}
				else {
					Alert alert1 = new Alert(AlertType.WARNING);
		       		alert1.setHeaderText("WARNING");
	                alert1.setTitle("Zero potrivire");
	                alert1.setContentText("Nu s-a gasit niciun angajat corespunzator cu filtrele alese.");
	                alert1.showAndWait();
				}
				break;
			case 3:
				List<Employee> e3 = DepartmentFilter(department, Main.con.con);
				if(e3.size() > 0) {
					populateTableView(e3, Main.con.con);
				}
				else {
					Alert alert1 = new Alert(AlertType.WARNING);
		       		alert1.setHeaderText("WARNING");
	                alert1.setTitle("Zero potrivire");
	                alert1.setContentText("Nu s-a gasit niciun angajat corespunzator cu filtrele alese.");
	                alert1.showAndWait();
				}
				break;
			default: 
				populateTableView(getAllEmployees(Main.con.con), Main.con.con);
				break;
		}
	}
}
