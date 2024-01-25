package application;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ProductMaterialController {
	
	@FXML CheckBox MaterialName;
	@FXML TextField MaterialQuantity;
	
	public void init(String materialName) {
		MaterialName.setText(materialName);
	}
}
