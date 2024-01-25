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
//import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowProductsController extends UpdateProductController {

	@FXML TableView<Product> productTableView;
    @FXML TableColumn<Product, String> NumeCol;
    @FXML TableColumn<Product, String> CuloareCol;
    @FXML TableColumn<Product, String> MarimeCol;
    @FXML TableColumn<Product, String> NrBucatiCol;
    @FXML TableColumn<Product, String> CodProdusCol;
    @FXML TableColumn<Product, String> PretCol;
    @FXML TableColumn<Product, String> MaterialeFolositeCol;
    
    ObservableList<Product> selectedProduct;

    private Stage stage;
    private Scene scene;

    public void MenuPageScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public String getMaterialsOfProduct(int productID, Connection con) {
    	String SelectQuery = "SELECT B.Nume AS NumeMaterial, A.Cantitate AS CantitatePerMaterial FROM ProdusMaterial A  JOIN Materiale B ON A.MaterialID = B.MaterialID WHERE A.ProdusID = " + productID;
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
				//System.out.println(materialInfo);
			} else {
			materials = materials + ", " + materialInfo;
			//System.out.println(materialInfo);
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Interogare simpla -> JOIN 2");

		return materials;
    }
    
    public List<Product> getAllProducts(Connection con) {
        String SelectQuery = "SELECT A.ProdusID, A.Nume, A.Culoare, A.Marime, A.NrBucati, A.CodProdus, A.Pret FROM Produs A ";
        Statement stm;
        List<Product> products = new ArrayList<Product>();
        String materials = "";
        
        try {
            stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(SelectQuery);
            while (rs.next()) {
            	int productID = rs.getInt("ProdusID");
                String name = rs.getString("Nume");
                String color = rs.getString("Culoare");
                String size = rs.getString("Marime");
                String nrOfPieces = rs.getString("NrBucati");
                String productCode = rs.getString("CodProdus");
                int price = rs.getInt("Pret");
                
                materials = getMaterialsOfProduct(productID, con);
                //System.out.println(materialInfo);
                Product SingleProduct = new Product(name, color, size, nrOfPieces, productCode, Integer.toString(price), materials);
                products.add(SingleProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(products.size());
        return products;
    }
    
    public void populateTableView(Connection con) {
    	NumeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("NumeCol"));
        CuloareCol.setCellValueFactory(new PropertyValueFactory<Product, String>("CuloareCol"));
        MarimeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("MarimeCol"));
        NrBucatiCol.setCellValueFactory(new PropertyValueFactory<Product, String>("NrBucatiCol"));
        CodProdusCol.setCellValueFactory(new PropertyValueFactory<Product, String>("CodProdusCol"));
        PretCol.setCellValueFactory(new PropertyValueFactory<Product, String>("PretCol"));
        MaterialeFolositeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("MaterialeFolositeCol"));

        List<Product> products = getAllProducts(con);
        ObservableList<Product> p = FXCollections.observableArrayList(products);
        System.out.println(p.get(0).getNumeCol());
        productTableView.setItems(p);
    }
    
    public void rowSelected(MouseEvent event) throws IOException {
		selectedProduct = productTableView.getSelectionModel().getSelectedItems();
        if(selectedProduct.size() > 0)
        	System.out.println(selectedProduct.get(0).getNumeCol());
	}
    
    public void deleteProductFromProductMaterialTable(String productName, String productCode) {
    	String deleteProductFromProductMaterialTableQuery = "DELETE FROM ProdusMaterial WHERE ProdusID = (SELECT ProdusID FROM Produs WHERE Nume  = '" + productName + "' AND CodProdus = '" + productCode + "')";
    
    	System.out.println(deleteProductFromProductMaterialTableQuery);
    	System.out.println("Subcerere 3 -> Interogare complexa");
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(deleteProductFromProductMaterialTableQuery);
			populateTableView(Main.con.con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void deleteProductFromProductTable(ActionEvent event) throws IOException {
    	if (selectedProduct != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("CONFIRMATION");
            alert.setTitle("Stergere produs");
            alert.setContentText("Inregistrarea va fi stearsa din baza de date. Doriti sa continuati?");
            alert.showAndWait();
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
            	String productName = selectedProduct.get(0).getNumeCol();
                String productCode = selectedProduct.get(0).getCodProdusCol();
                int nrOfDeleteQueries = -1;
            	String getNrOfDeleteQueries = "SELECT COUNT(*) AS NrOfDeleteQueries FROM ProdusMaterial WHERE ProdusID = (SELECT ProdusID FROM Produs WHERE Nume = '" + updatedProductName + "' AND CodProdus = '" + updatedProductCode + "')";
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
            		deleteProductFromProductMaterialTable(productName, productCode);
            	}
            	
        		String deleteProductFromProductTableQuery = "DELETE FROM Produs WHERE Nume = '" + productName + "' AND CodProdus = '" + productCode + "'";
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
             alert.setContentText("Pentru executarea operatiunii de stergere trebuie sa selectati un produs.");
             alert.showAndWait();
    	}
	}
    
    public void updateProductTable() throws IOException {
        //String[] names = selectedEmployee.split(" ");
        String productName = selectedProduct.get(0).getNumeCol();
        String productCode = selectedProduct.get(0).getCodProdusCol();

        getNameAndProductCode(productName, productCode);
    }
    
    public void UpdateProductScene(ActionEvent event) throws IOException {
        if (selectedProduct != null) {
            Parent root = FXMLLoader.load(getClass().getResource("UpdateProduct.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            updateProductTable();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Zero selectie");
            alert.setHeaderText("WARNING");
            alert.setContentText("Selectati un produs pentru a putea face update.");
            alert.showAndWait();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		populateTableView(Main.con.con);
	}
}
