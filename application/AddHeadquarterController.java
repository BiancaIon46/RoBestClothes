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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddHeadquarterController implements Initializable {
	@FXML TextField CodFiliala;
    @FXML TextField Oras;
    @FXML ChoiceBox<String> Director;
    
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
    
    public String createInsertHeadquarterQuery(String name, String headquarterCode, String directorInfo) {
    	String[] director = directorInfo.split(" ");
		String insertQuery = "INSERT INTO Filiala (CodFiliala, Oras, DirectorID) VALUES ('" + headquarterCode + "', '" + name + "', (SELECT AngajatID FROM Angajat WHERE Nume = '" + director[0] + "' AND Prenume = '" + director[1] + "' AND CNP = '" + director[2] + "'))";
		System.out.println(insertQuery);
		return insertQuery;
    }
    
    public int insertHeadquarterIntoDB(String insertQuery, Connection con) {
    	Statement stm;
		int headquarterId = -1;
		try {
			stm = (Statement) con.createStatement();
			stm.execute(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stm.getGeneratedKeys();
			while(rs.next()) {
				headquarterId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return headquarterId;
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
    
    public void extractHeadquarterInfo(ActionEvent event) throws IOException {
		String name = Oras.getText();
		String headquarterCode = CodFiliala.getText();
		String directorInfo = Director.getValue();
		int newd = -1;
		List<String> providers = getCheckedProviders();
		
		if(name.length() <= 0 || headquarterCode.length() <= 0 || directorInfo.length() <= 0 || providers.size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campuri necompletate");
            alert.setHeaderText("WARNING");
            alert.setContentText("Completati toate campurile pentru a putea adauga o filiala.");
            alert.showAndWait();
		} else {
			int newId = insertHeadquarterIntoDB(createInsertHeadquarterQuery(name, headquarterCode, directorInfo), Main.con.con);
			if(newId != -1) {
				insertHeadquarterWithProvidersIntoDB(createInsertHeadquarterWithProvidersQuery(providers, newId), Main.con.con);
			}
		}
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Director.getItems().addAll(getAllPossibleDirectors(Main.con.con));
		addCheckBoxes(getAllProviders());
	}
}
