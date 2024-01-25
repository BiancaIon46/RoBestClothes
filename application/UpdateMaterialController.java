package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateMaterialController implements Initializable {
	
	@FXML TextField Nume;
    @FXML TextField Culoare;
    @FXML TextField Pret;
    @FXML TextField Calitate;
	
	private Stage stage;
	private Scene scene;
	
	public static String updatedMaterialName, updatedMaterialColour;
	
	public void ShowMaterialScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ShowMaterials.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
	
	public void getMaterialInfo(String materialName, String materialColour) {
		updatedMaterialName = materialName;
		updatedMaterialColour = materialColour;
	}
	
	public String createUpdateQuery(String updateCell, String updateVal) {
		String updateQuery = "UPDATE Materiale SET " + updateCell + " = '" + updateVal + "' WHERE Nume = '" + updatedMaterialName + "' AND Culoare = '" + updatedMaterialColour + "'";
		
		return updateQuery;
	}

	public void updateMaterialName(ActionEvent event)  throws IOException {
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
	
	public void updateMaterialColour(ActionEvent event)  throws IOException {
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
	
	public void updateMaterialPrice(ActionEvent event)  throws IOException {
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
	
	public void updateMaterialQuality(ActionEvent event)  throws IOException {
		String newVal = Calitate.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Calitate", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
}
