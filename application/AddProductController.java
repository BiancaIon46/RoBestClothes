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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddProductController implements Initializable{
	
    @FXML TextField Nume;
    @FXML TextField Culoare;
    @FXML TextField NrBucati;
    @FXML TextField CodProdus;
    @FXML TextField Marime;
    @FXML TextField Pret;
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
	
	public String createInsertProductQuery(String name,String color, String productCode, String size, int nrOfPieces, int price) {
		String insertQuery = "INSERT INTO Produs (Nume, Culoare, Marime, NrBucati, CodProdus, Pret) VALUES ('" + name + "', '" + color + "', '" + size + "', " + nrOfPieces + ", '" + productCode + "', " + price + ");";
		System.out.println(insertQuery);
		return insertQuery;
	}
	
	public List<String> createInsertProductAndMaterialQuery(List<String> materials, int productID) {
		String insertQuery;
		List<String> insertQueries = new ArrayList<String>();
		
		for(int i = 0; i < materials.size(); i = i + 2) {
			insertQuery = "INSERT INTO ProdusMaterial (ProdusID, MaterialID, Cantitate) VALUES (" + productID + ", " + "(SELECT MaterialID FROM Materiale WHERE Nume = '" + materials.get(i) + "'), " + Integer.parseInt(materials.get(i+1)) + ");";
			insertQueries.add(insertQuery);
			System.out.println(insertQuery);
		}
		System.out.println("Subcerere 2 (Interogare complexa)");
		return insertQueries;
	}
	
	public int insertProductIntoDB(String insertQuery, Connection con) {
		Statement stm;
		int productId = -1;
		try {
			stm = (Statement) con.createStatement();
			stm.execute(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stm.getGeneratedKeys();
			while(rs.next()) {
				productId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productId;
	}
	
	public void insertProductWithMaterialIntoDB(List<String> insertQueries, Connection con) {
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
	
	public void extractProductInfo(ActionEvent event) throws IOException {
		String name = Nume.getText();
		String color = Culoare.getText();
		String productCode = CodProdus.getText();
		String size = Marime.getText();
		
		int price = 0;
		if(Pret.getText().length() > 0) {
			price = Integer.parseInt(Pret.getText());
		}
		else price = 0;
		
		int nrOfPieces = 0;
		if(NrBucati.getText().length() > 0) {
			nrOfPieces = Integer.parseInt(NrBucati.getText());
		}
		else nrOfPieces = 0;
		
		List<String> materials = getCheckedMaterials();
		
		if(name.length() <= 0 || color.length() <= 0 || size.length() <= 0 || price == 0 || productCode.length() <= 0 || color.length() <= 0 || materials.size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campuri necompletate");
            alert.setHeaderText("Completati toate campurile pentru a putea adauga un produs.");
            alert.showAndWait();
		} else {
			int newId = insertProductIntoDB(createInsertProductQuery(name, color, productCode, size, nrOfPieces, price), Main.con.con);
			if(newId != -1) {
				insertProductWithMaterialIntoDB(createInsertProductAndMaterialQuery(materials, newId), Main.con.con);
			}
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		addCheckBoxes(getAllMaterials());
	}
	
}
