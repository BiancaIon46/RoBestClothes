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

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateProviderController implements Initializable {

    @FXML TextField Nume;
    @FXML TextField CodFurnizor;
    @FXML TextField NrContract;
    @FXML DatePicker DataAchizitie;
    @FXML VBox VBoxCol1;
    @FXML VBox VBoxCol2;
    
    private Stage stage;
    private Scene scene;
    
    static String updatedProviderName;
    static String updatedProviderCode;

    public void ShowProvidersScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ShowProviders.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void getNameAndProviderCode(String providerName, String providerCode) {
    	updatedProviderName = providerName;
    	updatedProviderCode = providerCode;
    }
    
    public String createUpdateQuery(String updateCell, String updateVal) {
		System.out.println("miau" + updatedProviderName + updatedProviderCode);
		String updateQuery = "UPDATE Furnizori SET " + updateCell + " = '" + updateVal + "' WHERE Nume = '" + updatedProviderName + "' AND CodFurnizor = '" + updatedProviderCode + "'";
		
		return updateQuery;
	}
    
    public String createUpdateAuxQuery(String updateCell, String updateVal) {
		String updateQuery = "UPDATE ContractFurnizori SET " + updateCell + " = '" + updateVal + "' WHERE FurnizorID = (SELECT FurnizorID FROM Furnizori WHERE Nume = '" + updatedProviderName + "' AND CodFurnizor = '" + updatedProviderCode + "')";
		
		return updateQuery;
	}
    
    public void updateProviderName(ActionEvent event)  throws IOException {
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
    
    public void updateProviderCode(ActionEvent event)  throws IOException {
		String newVal = CodFurnizor.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("CodFurnizor", newVal);
		System.out.println(updateQuery);
		Statement stm;
    	
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public void updateAcquisitionDate(ActionEvent event)  throws IOException {
		String newVal = DataAchizitie.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println(newVal);
		String updateQuery = createUpdateAuxQuery("DataAchizitie", newVal);
		System.out.println(updateQuery);
		Statement stm;
		
		int NrOfUpdateQueries = -1;
    	String getNrOfUpdateQueries = "SELECT COUNT(*) AS NrOfUpdateQueries FROM ContractFurnizori WHERE FurnizorID = (SELECT FurnizorID FROM Furnizori WHERE Nume = '" + updatedProviderName + "' AND CodFurnizor = '" + updatedProviderCode + "')";
    	
    	try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs = stm.executeQuery(getNrOfUpdateQueries);
			while(rs.next())
				NrOfUpdateQueries = rs.getInt("NrOfUpdateQueries");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	for(int i = 0; i <= NrOfUpdateQueries; i++) {
    		try {
    			stm = (Statement) Main.con.con.createStatement();
    			stm.execute(updateQuery);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
	}
    
    public void updateContractNr(ActionEvent event)  throws IOException {
		String newVal = NrContract.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateAuxQuery("NrContract", newVal);
		System.out.println(updateQuery);
		Statement stm;
		int NrOfUpdateQueries = -1;
    	String getNrOfUpdateQueries = "SELECT COUNT(*) AS NrOfUpdateQueries FROM ContractFurnizori WHERE FurnizorID = (SELECT FurnizorID FROM Furnizori WHERE Nume = '" + updatedProviderName + "' AND CodFurnizor = '" + updatedProviderCode + "')";
    	
    	try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs = stm.executeQuery(getNrOfUpdateQueries);
			while(rs.next())
				NrOfUpdateQueries = rs.getInt("NrOfUpdateQueries");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	for(int i = 0; i <= NrOfUpdateQueries; i++) {
    		try {
    			stm = (Statement) Main.con.con.createStatement();
    			stm.execute(updateQuery);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
	}
    
    public void addCheckBoxes(List<String> materials) {
		int i;
		int len = materials.size();
		int middle;
		
		if(len % 2 == 0) {
			middle = len / 2 - 1;
		} else {
			middle = len / 2;
		}

		for(i = 0; i <= middle; i++) {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("ProductMaterial.fxml"));
			HBox hBox;
			try {
				hBox = fxmlloader.load();
				ProductMaterialController prc = fxmlloader.getController();
				prc.init(materials.get(i));
				VBoxCol1.getChildren().add(hBox);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		for(; i < len; i++) {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("ProductMaterial.fxml"));
			HBox hBox;
			try {
				hBox = fxmlloader.load();
				ProductMaterialController prc = fxmlloader.getController();
				prc.init(materials.get(i));
				VBoxCol2.getChildren().add(hBox);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public List<String> getAllMaterials() {
		String selectMaterialsQuery = "SELECT Nume FROM Materiale";
		List<String> materials = new ArrayList<String>();
		Statement stm; 
		
		try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs=stm.executeQuery(selectMaterialsQuery);
			while(rs.next())
				materials.add(rs.getString("Nume"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materials;
	}
	
	public List<String> getCheckedMaterials() {
		ObservableList<Node> materials1 = VBoxCol1.getChildrenUnmodifiable();
		ObservableList<Node> materials2 = VBoxCol2.getChildrenUnmodifiable();
		List<String> materialsInfo = new ArrayList<String>();
		
		int len = materials1.size();
		for(int i = 0; i < len; i++) {
			if(((CheckBox)((HBox)materials1.get(i)).getChildrenUnmodifiable().get(0)).isSelected()) {
				materialsInfo.add(((CheckBox)((HBox)materials1.get(i)).getChildrenUnmodifiable().get(0)).getText());
				materialsInfo.add(((TextField)((HBox)materials1.get(i)).getChildrenUnmodifiable().get(2)).getText());
			}	
		}
		
		len = materials2.size();
		for(int i = 0; i < len; i++) {
			if(((CheckBox)((HBox)materials2.get(i)).getChildrenUnmodifiable().get(0)).isSelected()) {
				materialsInfo.add(((CheckBox)((HBox)materials2.get(i)).getChildrenUnmodifiable().get(0)).getText());
				materialsInfo.add(((TextField)((HBox)materials2.get(i)).getChildrenUnmodifiable().get(2)).getText());
			}	
		}
		
		System.out.println(materialsInfo.size());
		return materialsInfo;
	}
    
	public void deleteProviderFromProviderContractTable(String providerName, String providerCode) {
    	String deleteProviderFromProviderContractTableQuery = "DELETE FROM ContractFurnizori WHERE FurnizorID = (SELECT FurnizorID FROM Furnizori WHERE Nume  = '" + providerName + "' AND CodFurnizor = '" + providerCode + "')";
    
    	System.out.println(deleteProviderFromProviderContractTableQuery);
    	System.out.println("Subcerere 3 -> Interogare complexa");
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(deleteProviderFromProviderContractTableQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	public List<String> createInsertProviderInfoQuery(List<String> materials, int providerID, String contractNr, String acquisitionDate) {
		String insertQuery;
		List<String> insertQueries = new ArrayList<String>();
		
		for(int i = 0; i < materials.size(); i = i + 2) {
			insertQuery = "INSERT INTO ContractFurnizori (FurnizorID, MaterialID, Cantitate, NrContract, DataAchizitie) VALUES (" + providerID + ", " + "(SELECT MaterialID FROM Materiale WHERE Nume = '" + materials.get(i) + "'), " + Integer.parseInt(materials.get(i+1)) + ", '" + contractNr + "', '" + acquisitionDate + "');";
			insertQueries.add(insertQuery);
			System.out.println(insertQuery);
		}
		System.out.println("Subcerere 2 (Interogare complexa)");
		return insertQueries;
	}
	
	public void insertProviderInfoIntoDB(List<String> insertQueries, Connection con) {
		Statement stm;
		for(int i=0; i < insertQueries.size(); i++) {
			try {
				stm = (Statement) con.createStatement();
				stm.execute(insertQueries.get(i));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public void updateProviderMaterials(ActionEvent event)  throws IOException {
    	int nrOfDeleteQueries = -1;
    	String getNrOfDeleteQueries = "SELECT COUNT(*) AS NrOfDeleteQueries FROM ContractFurnizori WHERE FurnizorID = (SELECT FurnizorID FROM Furnizori WHERE Nume = '" + updatedProviderName + "' AND CodFurnizor = '" + updatedProviderCode + "')";
    	
    	Statement stm; 
		
		try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs=stm.executeQuery(getNrOfDeleteQueries);
			while(rs.next())
				nrOfDeleteQueries = rs.getInt("NrOfDeleteQueries");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String getContractNrAndAcquisitionDateQuerie = "SELECT NrContract, DataAchizitie FROM ContractFurnizori WHERE FurnizorID = (SELECT FurnizorID FROM Furnizori WHERE Nume = '" + updatedProviderName + "' AND CodFurnizor = '" + updatedProviderCode + "')";
		String contractNr = null, acquisitionDate = null;
		try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs=stm.executeQuery(getContractNrAndAcquisitionDateQuerie);
			while(rs.next()) {
				contractNr = rs.getString("NrContract");
				acquisitionDate = rs.getString("DataAchizitie");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i <= nrOfDeleteQueries; i++) {
			deleteProviderFromProviderContractTable(updatedProviderName, updatedProviderCode);
		}
    	
    	List<String> materials = getCheckedMaterials();
		
    	String getProviderIDQuery = "SELECT FurnizorID FROM Furnizori WHERE Nume = '" + updatedProviderName + "' AND CodFurnizor = '" + updatedProviderCode + "'";
    	int providerID = -1;
    	try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs=stm.executeQuery(getProviderIDQuery);
			while(rs.next())
				providerID = rs.getInt("FurnizorID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	insertProviderInfoIntoDB(createInsertProviderInfoQuery(materials, providerID, contractNr, acquisitionDate), Main.con.con);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addCheckBoxes(getAllMaterials());
	}
}
