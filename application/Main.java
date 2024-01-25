package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private Stage stage;
	private Scene scene; 
	public static connectToDataBase con = new connectToDataBase();
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void AddEmployeeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void ShowEmployeeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowAndFilterEmployees.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void AddDepartmentScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddDepartment.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void AddProviderScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddProvider.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void AddMaterialScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddMaterial.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void AddProductScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void ShowProductScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowProducts.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void ShowProvidersScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowProviders.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void ShowDepartmentsScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowDepartments.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void ShowMaterialsScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowMaterials.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void AddHeadquarterScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddHeadquarter.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void ShowHeadquartersScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowHeadquarters.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
