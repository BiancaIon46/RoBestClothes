package application;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class HeadquarterProviderController {
	@FXML CheckBox ProviderName;

	public void init(String providerName) {
		ProviderName.setText(providerName);
	}
}
