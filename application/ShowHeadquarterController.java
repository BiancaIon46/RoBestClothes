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

public class ShowHeadquarterController extends UpdateHeadquarterController {
	@FXML TableView<Headquarter> headquarterTableView;
    @FXML TableColumn<Headquarter, String> OrasCol;
    @FXML TableColumn<Headquarter, String> CodFilialaCol;
    @FXML TableColumn<Headquarter, String> NumeDirectorCol;
    @FXML TableColumn<Headquarter, String> PrenumeCol;
    @FXML TableColumn<Headquarter, String> CNPCol;
    @FXML TableColumn<Headquarter, String> FurnizoriCol;
    
    ObservableList<Headquarter> selectedHeadquarter;

    private Stage stage;
    private Scene scene;

    public void MenuPageScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public String getHeadquartersProviders(int headquarterID, Connection con) {
    	String SelectQuery = "SELECT B.FurnizorID, A.Nume FROM Furnizori A JOIN FilialaFurnizor B ON A.FurnizorID = B.FurnizorID WHERE B.FilialaID = '" + headquarterID + "'";
		Statement stm;
		String providers = "";
		
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs = stm.executeQuery(SelectQuery);
		while (rs.next()) {
			
			String providerName = rs.getString("Nume");
			
			if(providers.length() == 0){
				providers = providerName;
				//System.out.println(materialInfo);
			} else {
				providers = providers + ", " + providerName;
			//System.out.println(materialInfo);
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Interogare simpla -> JOIN 3");

		return providers;
    }
    
    
    public List<Headquarter> getAllHeadquarters(Connection con) {
        String SelectQuery = "SELECT A.FilialaID, A.Oras, A.CodFiliala, A.DirectorID, B.Nume, B.Prenume, B.CNP FROM Filiala A JOIN Angajat B ON A.DirectorID = B.AngajatID";
        Statement stm;
        List<Headquarter> headquarters = new ArrayList<Headquarter>();
        String providers = "";
        
        try {
            stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(SelectQuery);
            while (rs.next()) {
            	int headquarterID = rs.getInt("FilialaID");
                String name = rs.getString("Oras");
                String headquarterCode = rs.getString("CodFiliala");
                int directorID = rs.getInt("DirectorID");
                String directorLastName = rs.getString("Nume");
                String directorFirstName = rs.getString("Prenume");
                String directorCNP = rs.getString("CNP");
                
                providers = getHeadquartersProviders(headquarterID, con);
                //System.out.println(materialInfo);
                Headquarter SingleHeadquarter = new Headquarter(name, headquarterCode, directorLastName, directorFirstName, directorCNP, providers);
                headquarters.add(SingleHeadquarter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(headquarters.size());
        return headquarters;
    }
    
    public void populateTableView(Connection con) {
    	OrasCol.setCellValueFactory(new PropertyValueFactory<Headquarter, String>("OrasCol"));
    	CodFilialaCol.setCellValueFactory(new PropertyValueFactory<Headquarter, String>("CodFilialaCol"));
    	NumeDirectorCol.setCellValueFactory(new PropertyValueFactory<Headquarter, String>("NumeDirectorCol"));
    	PrenumeCol.setCellValueFactory(new PropertyValueFactory<Headquarter, String>("PrenumeCol"));
    	CNPCol.setCellValueFactory(new PropertyValueFactory<Headquarter, String>("CNPCol"));
    	FurnizoriCol.setCellValueFactory(new PropertyValueFactory<Headquarter, String>("FurnizoriCol"));

        List<Headquarter> headquarters = getAllHeadquarters(con);
        ObservableList<Headquarter> h = FXCollections.observableArrayList(headquarters);
        System.out.println(h.get(0).getOrasCol());
        headquarterTableView.setItems(h);
    }
    
    public void rowSelected(MouseEvent event) throws IOException {
    	selectedHeadquarter = headquarterTableView.getSelectionModel().getSelectedItems();
        if(selectedHeadquarter.size() > 0)
        	System.out.println(selectedHeadquarter.get(0).getOrasCol());
	}
    
    public void updateHeadquarterTable() throws IOException {
        //String[] names = selectedEmployee.split(" ");
        String headquarterName = selectedHeadquarter.get(0).getOrasCol();
        String headquarterCode = selectedHeadquarter.get(0).getCodFilialaCol();

        getHeadquarterNameAndCode(headquarterName, headquarterCode);
    }
    
    public void UpdateHeadquarterScene(ActionEvent event) throws IOException {
        if (selectedHeadquarter != null) {
            Parent root = FXMLLoader.load(getClass().getResource("UpdateHeadquarter.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            updateHeadquarterTable();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Zero selectie");
            alert.setHeaderText("WARNING");
            alert.setContentText("Selectati o filiala pentru a putea face update.");
            alert.showAndWait();
        }
    }
    
    public void deleteProvidersFromHeadquarterProviderTable(int headquarterID) {
		String deleteQuery = "DELETE FROM FilialaFurnizor WHERE FilialaID = " + headquarterID;
		Statement stm;
		
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(deleteQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(deleteQuery);
	}
    
    public void deleteHeadquarter(ActionEvent event) throws IOException {
    	if (selectedHeadquarter != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("CONFIRMATION");
            alert.setTitle("Stergere produs");
            alert.setContentText("Inregistrarea va fi stearsa din baza de date. Doriti sa continuati?");
            alert.showAndWait();
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
		    	//List<String> providers = getCheckedProviders();
		    	String headquarterName = selectedHeadquarter.get(0).getOrasCol();
                String headquarterCode = selectedHeadquarter.get(0).getCodFilialaCol();
                int nrOfQueries = -1;
        		int headquarterID = -1;
        		String getNrOfQueries = "SELECT COUNT(*) AS NrOfQueries, B.FilialaID FROM FilialaFurnizor A JOIN Filiala B ON A.FilialaID = B.FilialaID WHERE B.FilialaID = (SELECT FilialaID FROM Filiala WHERE Oras = '" + headquarterName + "' AND CodFiliala = '" + headquarterCode + "') GROUP BY B.FilialaID";
        		Statement stm;
        		
        		try {
        			stm = (Statement) Main.con.con.createStatement();
        			ResultSet rs=stm.executeQuery(getNrOfQueries);
        			while(rs.next()) {
        				nrOfQueries = rs.getInt("NrOfQueries");
        				headquarterID = rs.getInt("FilialaID");
        			}
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
        		
        		System.out.println(nrOfQueries);
        		for(int i = 0; i <= nrOfQueries; i++) {
        			deleteProvidersFromHeadquarterProviderTable(headquarterID);
        		}
				
				String deleteProductFromProductTableQuery = "DELETE FROM Filiala WHERE Oras = '" + headquarterName + "' AND CodFiliala = '" + headquarterCode + "'";
        		System.out.println(deleteProductFromProductTableQuery);
        		//Statement stm;
        		try {
        			stm = (Statement) Main.con.con.createStatement();
        			stm.execute(deleteProductFromProductTableQuery);
        			populateTableView(Main.con.con);
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
            }
            
    	} else {
    		 Alert alert = new Alert(AlertType.WARNING);
    		 alert.setHeaderText("WARNING");
             alert.setTitle("Zero selectie");
             alert.setContentText("Pentru executarea operatiunii de stergere trebuie sa selectati o filiala.");
             alert.showAndWait();
    	}
		
		
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		populateTableView(Main.con.con);
	}
    
}
