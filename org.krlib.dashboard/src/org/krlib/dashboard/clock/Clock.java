package org.krlib.dashboard.clock;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Clock extends Pane{
	@FXML public Label lbl_clock;
	
	public Clock() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clock.fxml"));
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public StringProperty textProperty() {
		return lbl_clock.textProperty();
	}
	
	public void setText(String pValue) {
		lbl_clock.textProperty().set(pValue);
	}
	
	public String getText() {
		return lbl_clock.textProperty().get();
	}

}