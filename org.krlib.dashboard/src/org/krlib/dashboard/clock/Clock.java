package org.krlib.dashboard.clock;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class Clock extends VBox{
	
	public Clock() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clock.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(new ClockController());
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
