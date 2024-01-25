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
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddProviderController implements Initializable {
	@FXML TextField Nume;
    @FXML TextField CodFurnizor;
    @FXML TextField NrContract;
    @FXML DatePicker DataAchizitie;
    @FXML VBox VBoxCol1;
    @FXML VBox VBoxCol2;
    
    private Stage stage;
    private Scene scene;
    
    public void MenuPageScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    
//    public Connection ConnectToDB() {
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
				e.printStackTrace();
			}
			
		}
    }
    
    public String createInsertProviderQuery(String name, String providerCode) {
		String insertProviderQuery = "INSERT INTO Furnizori (CodFurnizor, Nume) VALUES ('" + providerCode + "', '"  + name + "');";
		
		return insertProviderQuery;
	}
	
	public int insertProviderIntoDB(String insertQuery, Connection con) {
		Statement stm;
		int providerID = -1;
		try {
			stm = (Statement) con.createStatement();
			stm.execute(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stm.getGeneratedKeys();
			while(rs.next()) {
				providerID = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return providerID;
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
	
	public List<String> createInsertProviderAndMaterialQuery(List<String> materials, int providerID, String contractNr, String acquisitionDate) {
		String insertQuery;
		List<String> insertQueries = new ArrayList<String>();
		
		for(int i = 0; i < materials.size(); i = i + 2) {
			insertQuery = "INSERT INTO ContractFurnizori (FurnizorID, MaterialID, Cantitate, NrContract, DataAchizitie) VALUES (" + providerID + ", " + "(SELECT MaterialID FROM Materiale WHERE Nume = '" + materials.get(i) + "'), " + Integer.parseInt(materials.get(i+1)) + ", '" + contractNr + "', '" + acquisitionDate + "');";
			insertQueries.add(insertQuery);
			System.out.println(insertQuery);
		}
		System.out.println("Subcerere 3 (Interogare complexa)");
		return insertQueries;
	}
	
	public void insertProviderWithMaterialIntoDB(List<String> insertQueries, Connection con) {
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
	
	public void extractProviderInfo(ActionEvent event) throws IOException {
		String name = Nume.getText();
		String providerCode = CodFurnizor.getText();
		String contractNr = NrContract.getText();
		String acquisitionDate = "";
		if(DataAchizitie.getValue() != null)
			acquisitionDate = DataAchizitie.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
//		System.out.println("Nume = " + name);
//		System.out.println("ManagerName = " + managerName);
//		System.out.println("departmentCode = " + departmentCode);
		
		List<String> materials = getCheckedMaterials();
		
		if(name.length() <= 0 || providerCode.length() <= 0 || contractNr.length() <= 0 || acquisitionDate.length() <= 0 || materials.size() <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campuri necompletate");
            alert.setHeaderText("Completati toate campurile pentru a putea adauga un furnizor.");
            alert.showAndWait();
		} else {
			int newID = insertProviderIntoDB(createInsertProviderQuery(name, providerCode), Main.con.con);
			if(newID != -1) {
				insertProviderWithMaterialIntoDB(createInsertProviderAndMaterialQuery(materials, newID, contractNr, acquisitionDate), Main.con.con);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addCheckBoxes(getAllMaterials());
	}
}
