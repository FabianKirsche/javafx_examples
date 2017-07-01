package org.krlib.dashboard.clock;

import java.io.IOException;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Clock extends VBox{
	@FXML private Label lbl_clock;
	
	
	/***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
	public Clock() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clock.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
    /**
     * The text to display in the label.
     */
	public final String getText() {return lbl_clock.getText();}
	public final void setText(String pValue) {lbl_clock.setText(pValue);}
	public StringProperty textProperty() {return lbl_clock.textProperty();}
	
	
	/***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
	public void btn_demo_Clicked() {
		System.out.println("Clock's btn_demo was clicked! Hoorayy");
	}
}
