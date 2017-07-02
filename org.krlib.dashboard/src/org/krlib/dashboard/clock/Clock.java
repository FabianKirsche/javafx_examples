package org.krlib.dashboard.clock;

import java.io.IOException;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Clock extends GridPane{
	@FXML private Label lbl_curDate_WeekDay;
	@FXML private Label lbl_curDate_divider;
	@FXML private Label lbl_curDate_longDate;
	@FXML private Label lbl_mainClock;
	@FXML private Label lbl_alarmClock1;
	@FXML private Label lbl_alarmClock2;
	@FXML private Label lbl_alarmSound;
	
	private ClockModel cm;
	
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
		
		cm = new ClockModel();
		
		//Bind labels to ClockModels Properties
		lbl_curDate_WeekDay.textProperty().bind(cm.getWeekDayProperty());
		lbl_curDate_longDate.textProperty().bind(cm.getDispDateProperty());
		lbl_mainClock.textProperty().bind(cm.getDispTimeProperty());
	}
	
	
	
	
	
	
	/***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
    /**
     * The text to display in the label.
     */
	
	
	/***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
	
}
