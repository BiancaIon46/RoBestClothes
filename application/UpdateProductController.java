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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateProductController implements Initializable {
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
    
    static String updatedProductName;
    static String updatedProductCode;
    
    public void ShowProductsScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowProducts.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    
    public void getNameAndProductCode(String productName, String productCode) {
    	updatedProductName = productName;
    	updatedProductCode = productCode;
    }
    
    public String createUpdateQuery(String updateCell, String updateVal) {
		String updateQuery = "UPDATE Produs SET " + updateCell + " = '" + updateVal + "' WHERE Nume = '" + updatedProductName + "' AND CodProdus = '" + updatedProductCode + "'";
		
		return updateQuery;
	}
    
    public void updateProductName(ActionEvent event)  throws IOException {
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
    
    public void updateProductColor(ActionEvent event)  throws IOException {
		String newVal = Culoare.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Culoare", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public void updateProductSize(ActionEvent event)  throws IOException {
		String newVal = Marime.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Marime", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public void updateProductNrOfPieces(ActionEvent event)  throws IOException {
		String newVal = NrBucati.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("NrBucati", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public void updateProductProductCode(ActionEvent event)  throws IOException {
		String newVal = CodProdus.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("CodProdus", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public void updateProductProductPrice(ActionEvent event)  throws IOException {
		String newVal = Pret.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Pret", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
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
    
	public void deleteProductFromProductMaterialTable(String productName, String productCode) {
    	String deleteProductFromProductMaterialTableQuery = "DELETE FROM ProdusMaterial WHERE ProdusID = (SELECT ProdusID FROM Produs WHERE Nume  = '" + productName + "' AND CodProdus = '" + productCode + "')";
    
    	System.out.println(deleteProductFromProductMaterialTableQuery);
    	System.out.println("Subcerere 3 -> Interogare complexa");
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(deleteProductFromProductMaterialTableQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
    public void updateProductMaterials(ActionEvent event)  throws IOException {
    	int nrOfDeleteQueries = -1;
    	String getNrOfDeleteQueries = "SELECT COUNT(*) AS NrOfDeleteQueries FROM ProdusMaterial WHERE ProdusID = (SELECT ProdusID FROM Produs WHERE Nume = '" + updatedProductName + "' AND CodProdus = '" + updatedProductCode + "')";
    	
    	Statement stm; 
		
		try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs=stm.executeQuery(getNrOfDeleteQueries);
			while(rs.next())
				nrOfDeleteQueries = rs.getInt("NrOfDeleteQueries");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i <= nrOfDeleteQueries; i++) {
			deleteProductFromProductMaterialTable(updatedProductName, updatedProductCode);
		}
    	
    	List<String> materials = getCheckedMaterials();
		
    	String getProductIDQuery = "SELECT ProdusID FROM Produs WHERE Nume = '" + updatedProductName + "' AND CodProdus = '" + updatedProductCode + "'";
    	int productID = -1;
    	try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs=stm.executeQuery(getProductIDQuery);
			while(rs.next())
				productID = rs.getInt("ProdusID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	insertProductWithMaterialIntoDB(createInsertProductAndMaterialQuery(materials, productID), Main.con.con);
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		addCheckBoxes(getAllMaterials());
	}
}
