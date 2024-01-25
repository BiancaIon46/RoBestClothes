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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateHeadquarterController implements Initializable {

	@FXML TextField CodFiliala;
    @FXML TextField Oras;
    @FXML ChoiceBox<String> Director;
    
    @FXML VBox VBoxCol1;
    @FXML VBox VBoxCol2;
    
	public static String updatedHeadquarterName, updatedHeadquarterCode;
	
	private Scene scene;
	private Stage stage;
	
	public void ShowHeadquarterScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowHeadquarters.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void getHeadquarterNameAndCode(String headquarterName, String headquarterCode) {
		updatedHeadquarterName = headquarterName;
		updatedHeadquarterCode = headquarterCode;
	}
	
	public String createUpdateQuery(String updateCell, String updateVal) {
		String updateQuery = "UPDATE Filiala SET " + updateCell + " = '" + updateVal + "' WHERE Oras = '" + updatedHeadquarterName + "' AND CodFiliala = '" + updatedHeadquarterCode + "'";
		
		return updateQuery;
	}
	
	public void updateHeadquarterName(ActionEvent event)  throws IOException {
		String newVal = Oras.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("Oras", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateHeadquarterCode(ActionEvent event)  throws IOException {
		String newVal = CodFiliala.getText();
		System.out.println(newVal);
		String updateQuery = createUpdateQuery("CodFiliala", newVal);
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateHeadquarterDirector(ActionEvent event)  throws IOException {
		String newVal = Director.getValue();
		System.out.println(newVal);
		String[] directorInfo = newVal.split(" ");
		String updateQuery = "UPDATE Filiala SET DirectorID = (SELECT AngajatID FROM Angajat WHERE Nume = '" + directorInfo[0] + "' AND Prenume = '" + directorInfo[1] +"' AND CNP = '" + directorInfo[2] + "')" + " WHERE Oras = '" + updatedHeadquarterName + "' AND CodFiliala = '" + updatedHeadquarterCode + "'";	
		System.out.println(updateQuery);
		Statement stm;
		try {
			stm = (Statement) Main.con.con.createStatement();
			stm.execute(updateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertHeadquarterWithProvidersIntoDB(List<String> insertQueries, Connection con) {
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
	
	public List<String> createInsertHeadquarterWithProvidersQuery(List<String> providers, int headquarterId) {
    	String insertQuery;
		List<String> insertQueries = new ArrayList<String>();
		
		for(int i = 0; i < providers.size(); i++) {
			insertQuery = "INSERT INTO FilialaFurnizor (FilialaID, FurnizorID) VALUES ('" + headquarterId + "', (SELECT FurnizorID FROM Furnizori WHERE Nume = '" + providers.get(i) +"'))";
			insertQueries.add(insertQuery);
			System.out.println(insertQuery);
		}
		//System.out.println("Subcerere 2 (Interogare complexa)");
		return insertQueries;
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
	}
	
	public void updateHeadquartersProviders(ActionEvent event)  throws IOException {
		List<String> providers = getCheckedProviders();
		
		int nrOfQueries = -1;
		int headquarterID = -1;
		String getNrOfQueries = "SELECT COUNT(*) AS NrOfQueries, B.FilialaID FROM FilialaFurnizor A JOIN Filiala B ON A.FilialaID = B.FilialaID WHERE B.FilialaID = (SELECT FilialaID FROM Filiala WHERE Oras = '" + updatedHeadquarterName + "' AND CodFiliala = '" + updatedHeadquarterCode + "') GROUP BY B.FilialaID";
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
		
		for(int i = 0; i <= nrOfQueries; i++) {
			deleteProvidersFromHeadquarterProviderTable(headquarterID);
		}
		
		insertHeadquarterWithProvidersIntoDB(createInsertHeadquarterWithProvidersQuery(providers, headquarterID), Main.con.con);
	}
	
	public List<String> getAllPossibleDirectors(Connection con) {
    	String selectQuery = "SELECT A.AngajatID, A.Nume, A.Prenume, A.CNP FROM Angajat A WHERE A.AngajatID NOT IN (SELECT DirectorID FROM Filiala)";
    	Statement stm;
    	List<String> directors = new ArrayList<String>();
    	String directorInfo = null;
    	
    	try {
			stm = (Statement) con.createStatement();
			ResultSet rs=stm.executeQuery(selectQuery);
			while(rs.next()) {
				String lastName = rs.getString("Nume");
				String firstName = rs.getString("Prenume");
				String cnp = rs.getString("CNP");
				directorInfo = lastName + " " + firstName + " " + cnp;
				directors.add(directorInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		return directors;
    }
	
	public List<String> getAllProviders() {
		String selectProvidersQuery = "SELECT Nume FROM Furnizori";
		List<String> providers = new ArrayList<String>();
		Statement stm; 
		
		try {
			stm = (Statement) Main.con.con.createStatement();
			ResultSet rs=stm.executeQuery(selectProvidersQuery);
			while(rs.next())
				providers.add(rs.getString("Nume"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providers;
	}
	
	public void addCheckBoxes(List<String> providers) {
		int i;
		int len = providers.size();
		int middle;
		if(len % 2 == 0) {
			middle = len / 2 - 1;
		} else {
			middle = len / 2;
		}

		for(i = 0; i <= middle; i++) {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("HeadquarterProvider.fxml"));
			HBox hBox;
			try {
				hBox = fxmlloader.load();
				HeadquarterProviderController prc = fxmlloader.getController();
				prc.init(providers.get(i));
				VBoxCol1.getChildren().add(hBox);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		for(; i < len; i++) {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("HeadquarterProvider.fxml"));
			HBox hBox;
			try {
				hBox = fxmlloader.load();
				HeadquarterProviderController prc = fxmlloader.getController();
				prc.init(providers.get(i));
				VBoxCol2.getChildren().add(hBox);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> getCheckedProviders() {
		ObservableList<Node> providers1 = VBoxCol1.getChildrenUnmodifiable();
		ObservableList<Node> providers2 = VBoxCol2.getChildrenUnmodifiable();
		List<String> providersInfo = new ArrayList<String>();
		
		int len = providers1.size();
		for(int i = 0; i < len; i++) {
			if(((CheckBox)((HBox)providers1.get(i)).getChildrenUnmodifiable().get(0)).isSelected()) {
				providersInfo.add(((CheckBox)((HBox)providers1.get(i)).getChildrenUnmodifiable().get(0)).getText());
			}	
		}
		
		len = providers2.size();
		for(int i = 0; i < len; i++) {
			if(((CheckBox)((HBox)providers2.get(i)).getChildrenUnmodifiable().get(0)).isSelected()) {
				providersInfo.add(((CheckBox)((HBox)providers2.get(i)).getChildrenUnmodifiable().get(0)).getText());
			}	
		}
		
		System.out.println(providersInfo.size());
		return providersInfo;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Director.getItems().addAll(getAllPossibleDirectors(Main.con.con));
		addCheckBoxes(getAllProviders());
	}
	
}
