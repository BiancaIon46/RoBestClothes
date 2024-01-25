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

public class ShowMaterialsController extends UpdateMaterialController {
	@FXML TableView<Material> MaterialTableView;
    @FXML TableColumn<Material, String> NumeCol;
    @FXML TableColumn<Material, String> CuloareCol;
    @FXML TableColumn<Material, String> PretCol;
    @FXML TableColumn<Material, String> CalitateCol;
    
    ObservableList<Material> selectedMaterial;
    
    private Stage stage;
    private Scene scene;
    
    public void MenuPageScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public List<Material> getAllMaterials(Connection con) {
		String SelectQuery = "SELECT Nume, Culoare, Pret, Calitate FROM Materiale";
		Statement stm;
		String name = null, colour = null, quality = null;
		int price = -1;
		List<Material> materials = new ArrayList<Material>();
		
		try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(SelectQuery);
			while(rs.next()) {
				name = rs.getString("Nume");
				colour = rs.getString("Culoare");
				quality = rs.getString("Calitate");
				price = rs.getInt("Pret");
				Material material = new Material(name, colour, Integer.toString(price), quality);
				materials.add(material);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	    
	    return materials;
	}
    
    public void populateTableView(Connection con) {
    	NumeCol.setCellValueFactory(new PropertyValueFactory<Material, String>("NumeCol"));
    	CuloareCol.setCellValueFactory(new PropertyValueFactory<Material, String>("CuloareCol"));
    	PretCol.setCellValueFactory(new PropertyValueFactory<Material, String>("PretCol"));
    	CalitateCol.setCellValueFactory(new PropertyValueFactory<Material, String>("CalitateCol"));

        List<Material> materials = getAllMaterials(con);
        ObservableList<Material> m = FXCollections.observableArrayList(materials);
        System.out.println(m.get(0).getNumeCol());
        System.out.println(materials.size());
        MaterialTableView.setItems(m);
    }
    
    public void rowSelected(MouseEvent event) throws IOException {
    	selectedMaterial = MaterialTableView.getSelectionModel().getSelectedItems();
        if(selectedMaterial.size() > 0)
        	System.out.println(selectedMaterial.get(0).getNumeCol());
	}
    
    public void UpdateMaterialScene(ActionEvent event) throws IOException {
        if (selectedMaterial != null) {
            Parent root = FXMLLoader.load(getClass().getResource("UpdateMaterial.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            updateMaterialTable();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Zero selectie");
            alert.setHeaderText("WARNING");
            alert.setContentText("Selectati un material pentru a putea face update.");
            alert.showAndWait();
        }
    }
    
    public void updateMaterialTable() throws IOException {
        String materialName = selectedMaterial.get(0).getNumeCol();
        String materialColour = selectedMaterial.get(0).getCuloareCol();

        getMaterialInfo(materialName, materialColour);
    }
    
    public String createDeleteQuery(String materialName, String materialColour) {
		String deleteQuery = "DELETE FROM Materiale WHERE Nume = '" + materialName + "' AND Culoare = '" + materialColour + "'";
		System.out.println(deleteQuery);
		return deleteQuery;
	}
    
    public void deleteMaterialsFromProviderContractTable(String materialName, String materialColour) {
    	String deleteQuery = "DELETE FROM ContractFurnizori WHERE MaterialID = (SELECT MaterialID FROM Materiale WHERE Nume = '" + materialName + "' AND Culoare = '" + materialColour + "')";
    	Statement stm;
    	
    	try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(deleteQuery);
			populateTableView(Main.con.con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void deleteMaterial(ActionEvent event) throws IOException {
    	if (selectedMaterial != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("CONFIRMATION");
            alert.setTitle("Stergere materiL");
            alert.setContentText("Inregistrarea va fi stearsa din baza de date. Doriti sa continuati?");
            alert.showAndWait();
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
            	String materialName = selectedMaterial.get(0).getNumeCol();
		        String materialColour = selectedMaterial.get(0).getCuloareCol();
		        
            	int NrOfDeleteQueries = -1;
            	String getNrOfDeleteQueries = "SELECT COUNT(*) AS NrOfDeleteQueries FROM ContractFurnizori WHERE MaterialID = (SELECT MaterialID FROM Materiale WHERE Nume = '" + materialName + "' AND Culoare = '" + materialColour + "')";
				Statement stm; 
				
            	try {
        			stm = (Statement) Main.con.con.createStatement();
        			ResultSet rs=stm.executeQuery(getNrOfDeleteQueries);
        			while(rs.next()) {
        				NrOfDeleteQueries = rs.getInt("NrOfDeleteQueries");
        			}
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}  
            	
            	System.out.println(NrOfDeleteQueries);
//            	for(int i = 0; i <= NrOfDeleteQueries; i++) {
//            		deleteMaterialsFromProviderContractTable(materialName, materialColour);
//            	}
            	
				String deleteQuery = createDeleteQuery(materialName, materialColour);
				System.out.println(deleteQuery);

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
                alert1.setContentText("Pentru executarea operatiunii de stergere trebuie sa selectati un material.");
                alert1.showAndWait();
            }
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	populateTableView(Main.con.con);
	}
}
