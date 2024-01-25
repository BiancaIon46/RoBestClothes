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

public class ShowProvidersController extends UpdateProviderController {
	
	@FXML TableView<Provider> providerTableView;
    @FXML TableColumn<Provider, String> NumeCol;
    @FXML TableColumn<Provider, String> CodFurnizorCol;
    @FXML TableColumn<Provider, String> NrContractCol;
    @FXML TableColumn<Provider, String> DataAchizitieCol;
    @FXML TableColumn<Provider, String> MaterialeFurnizateCol;
    
    ObservableList<Provider> selectedProvider;

    private Stage stage;
    private Scene scene;

    public void MenuPageScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public String getMaterialsProvided(int providerID, Connection con) {
    	String SelectQuery = "SELECT B.Nume AS NumeMaterial, A.Cantitate AS CantitatePerMaterial FROM ContractFurnizori A  JOIN Materiale B ON A.MaterialID = B.MaterialID WHERE A.FurnizorID = " + providerID;
		Statement stm;
		String materials = "";
		
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs = stm.executeQuery(SelectQuery);
		while (rs.next()) {
			
			String materialName = rs.getString("NumeMaterial");
			String materialQuantity = rs.getString("CantitatePerMaterial");
			String materialInfo = materialName + "-" + materialQuantity;
			
			if(materials.length() == 0){
				materials = materialInfo;
				System.out.println(materialInfo);
			} else {
			materials = materials + ", " + materialInfo;
			System.out.println(materialInfo);
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Interogare simpla -> JOIN 3");

		return materials;
    }
    
    public List<Provider> getAllProviders(Connection con) {
        String SelectQuery = "SELECT DISTINCT A.FurnizorID, A.Nume, A.CodFurnizor, B.NrContract, B.DataAchizitie FROM Furnizori A JOIN ContractFurnizori B ON A.FurnizorID = B.FurnizorID ORDER BY A.FurnizorID";
        Statement stm;
        List<Provider> providers = new ArrayList<Provider>();
        String materials = "";
        
        try {
            stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(SelectQuery);
            while (rs.next()) {
            	int providerID = rs.getInt("FurnizorID");
                String name = rs.getString("Nume");
                String providerCode = rs.getString("CodFurnizor");
                String[] pCode = providerCode.split(" ");
                String contractNr = rs.getString("NrContract");
                String acquisitionDate = rs.getString("DataAchizitie");
                String[] date = acquisitionDate.split(" ");
                materials = getMaterialsProvided(providerID, con);
                //System.out.println(materialInfo);
                Provider SingleProvider = new Provider(name, pCode[0], contractNr, date[0], materials);
                providers.add(SingleProvider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(providers.size());
        return providers;
    }
    
    public void populateTableView(Connection con) {
    	NumeCol.setCellValueFactory(new PropertyValueFactory<Provider, String>("NumeCol"));
    	CodFurnizorCol.setCellValueFactory(new PropertyValueFactory<Provider, String>("CodFurnizorCol"));
    	NrContractCol.setCellValueFactory(new PropertyValueFactory<Provider, String>("NrContractCol"));
    	DataAchizitieCol.setCellValueFactory(new PropertyValueFactory<Provider, String>("DataAchizitieCol"));
    	MaterialeFurnizateCol.setCellValueFactory(new PropertyValueFactory<Provider, String>("MaterialeFurnizateCol"));

        List<Provider> providers = getAllProviders(con);
        ObservableList<Provider> p = FXCollections.observableArrayList(providers);
        System.out.println(p.get(0).getNumeCol());
        providerTableView.setItems(p);
    }
    
    public void rowSelected(MouseEvent event) throws IOException {
		selectedProvider = providerTableView.getSelectionModel().getSelectedItems();
        if(selectedProvider.size() > 0)
        	System.out.println(selectedProvider.get(0).getNumeCol());
	}
    
    public void updateProviderTable() throws IOException {
        String providerName = selectedProvider.get(0).getNumeCol();
        String providerCode = selectedProvider.get(0).getCodFurnizorCol();

        getNameAndProviderCode(providerName, providerCode);
    }
    
    public void UpdateProviderScene(ActionEvent event) throws IOException {
        if (selectedProvider != null) {
            Parent root = FXMLLoader.load(getClass().getResource("UpdateProvider.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            updateProviderTable();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Zero selectie");
            alert.setHeaderText("WARNING");
            alert.setContentText("Selectati un produs pentru a putea face update.");
            alert.showAndWait();
        }
    }
    
    public void deleteProviderFromProviderContractTable(String providerName, String providerCode) {
    	String deleteProviderFromProviderContractTableQuery = "DELETE FROM ContractFurnizori WHERE FurnizorID = (SELECT FurnizorID FROM Furnizori WHERE Nume  = '" + providerName + "' AND CodFurnizor = '" + providerCode + "')";
    
    	System.out.println(deleteProviderFromProviderContractTableQuery);
    	System.out.println("Subcerere 3 -> Interogare complexa");
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(deleteProviderFromProviderContractTableQuery);
			populateTableView(Main.con.con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void deleteProviderFromProviderTable(ActionEvent event) throws IOException {
    	if (selectedProvider != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("CONFIRMATION");
            alert.setTitle("Stergere furnizor");
            alert.setContentText("Inregistrarea va fi stearsa din baza de date. Doriti sa continuati?");
            alert.showAndWait();
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
            	String providerName = selectedProvider.get(0).getNumeCol();
                String providerCode = selectedProvider.get(0).getCodFurnizorCol();
                int nrOfDeleteQueries = -1;
            	String getNrOfDeleteQueries = "SELECT COUNT(*) AS NrOfDeleteQueries FROM ContractFurnizori WHERE FurnizorID = (SELECT FurnizorID FROM Furnizori WHERE Nume = '" + providerName + "' AND CodFurnizor = '" + providerCode + "')";
            	Statement stm;
            	
            	try {
        			stm = (Statement) Main.con.con.createStatement();
        			ResultSet rs = stm.executeQuery(getNrOfDeleteQueries);
        			while(rs.next())
        				nrOfDeleteQueries = rs.getInt("NrOfDeleteQueries");
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
            	
            	for(int i = 0; i <= nrOfDeleteQueries; i++) {
            		deleteProviderFromProviderContractTable(providerName, providerCode);
            	}
            	
        		String deleteProviderFromProviderTableQuery = "DELETE FROM Furnizori WHERE Nume = '" + providerName + "' AND CodFurnizor = '" + providerCode + "'";
        		System.out.println(deleteProviderFromProviderTableQuery);
        		//Statement stm;
        		try {
        			stm = (Statement) Main.con.con.createStatement();
        			stm.execute(deleteProviderFromProviderTableQuery);
        			populateTableView(Main.con.con);
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
            }
            
    	} else {
    		 Alert alert = new Alert(AlertType.WARNING);
    		 alert.setHeaderText("WARNING");
             alert.setTitle("Zero selectie");
             alert.setContentText("Pentru executarea operatiunii de stergere trebuie sa selectati un furnizor.");
             alert.showAndWait();
    	}
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	populateTableView(Main.con.con);
	}
}
