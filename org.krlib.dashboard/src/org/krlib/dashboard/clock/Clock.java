package org.krlib.dashboard.clock;

import java.io.IOException;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class Clock extends GridPane{
	@FXML private Label lbl_curDate_WeekDay;
	@FXML private Label lbl_curDate_divider;
	@FXML private Label lbl_curDate_longDate;
	@FXML private Label lbl_mainClock;
	@FXML private Label lbl_alarmClock1;
	@FXML private Label lbl_alarmClock2;
	@FXML private Label lbl_alarmSound;
	
	@FXML private HBox hb_alarmClock1;
	
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
		InitializeTimer();
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
	private void InitializeTimer() {
		ScheduledService<ClockModel> ss = new ScheduledService<ClockModel>() {
			@Override
			protected Task<ClockModel> createTask() {
				return new Task<ClockModel>() {
					@Override
					protected ClockModel call() throws Exception {
						return new ClockModel();
					}
				};
			}
		};
		
		ss.setPeriod(Duration.seconds(1));
		ss.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				cm = ss.getValue();
				
				lbl_curDate_WeekDay.setText(cm.getWeekDay());
				lbl_curDate_longDate.setText(cm.getDispDate());
				lbl_mainClock.setText(cm.getDispTime());
			}
		});
		ss.start();
	}
	
	public void hb_alarmClock1_OnMouseClicked() {
		System.out.println("hb_alarmClock1 Clicked");
	}
}
