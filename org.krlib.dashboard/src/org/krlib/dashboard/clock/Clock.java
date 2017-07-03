package org.krlib.dashboard.clock;

import java.io.IOException;
import java.util.Calendar;

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
	private Integer DEFAULT_ALARM1_HOUR = 2;
	private Integer DEFAULT_ALARM1_MINUTE = 42;
	private Boolean DEFAULT_ALARM1_ACTIVE = true;
	private Integer DEFAULT_ALARM2_HOUR = 2;
	private Integer DEFAULT_ALARM2_MINUTE = 38;
	private Boolean DEFAULT_ALARM2_ACTIVE = true;
	
	@FXML private Label lbl_curDate_WeekDay;
	@FXML private Label lbl_curDate_divider;
	@FXML private Label lbl_curDate_longDate;
	@FXML private Label lbl_mainClock;
	@FXML private Label lbl_alarmClock1;
	@FXML private Label lbl_alarmClock2;
	@FXML private Label lbl_alarmSound;
	@FXML private HBox hb_alarmClock1;

	private Integer prevMinute;
	private TimeModel tm;
	
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
		
		setDefaults();
		setListeners();
		initializeTimer();
	}
	
	/***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
	
	public Alarm alarm1 = new Alarm(DEFAULT_ALARM1_HOUR, DEFAULT_ALARM1_MINUTE, DEFAULT_ALARM1_ACTIVE);
	public Alarm alarm2 = new Alarm(DEFAULT_ALARM2_HOUR, DEFAULT_ALARM2_MINUTE, DEFAULT_ALARM2_ACTIVE);
		
	/***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
	private void setDefaults() {
		prevMinute = Calendar.getInstance().get(Calendar.MINUTE);
		lbl_alarmClock1.setText(alarm1.getHour() + ":" + alarm1.getMinute());
		lbl_alarmClock2.setText(alarm2.getHour() + ":" + alarm2.getMinute());
	}
	
	private void setListeners() {
		alarm1.addListener(new IAlarmListener() {
			
			@Override
			public void stoppedRinging() { alarmStoppedRinging(); }
			
			@Override
			public void startedRinging() { alarmStartedRinging(); }
		});
		
		alarm2.addListener(new IAlarmListener() {
			
			@Override
			public void stoppedRinging() { alarmStoppedRinging(); }
			
			@Override
			public void startedRinging() { alarmStartedRinging(); }
		});
	}
	
	private void initializeTimer() {
		ScheduledService<TimeModel> ss = new ScheduledService<TimeModel>() {
			@Override
			protected Task<TimeModel> createTask() {
				return new Task<TimeModel>() {
					@Override
					protected TimeModel call() throws Exception {
						return new TimeModel();
					}
				};
			}
		};
		
		ss.setPeriod(Duration.seconds(1));
		ss.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				tm = ss.getValue();
				
				lbl_curDate_WeekDay.setText(tm.getWeekDay());
				lbl_curDate_longDate.setText(tm.getDispDate());
				lbl_mainClock.setText(tm.getDispTime());
				
				//Execute once a minute
				if (prevMinute != tm.getMinute()) {
					System.out.println("\tprevMinute: " + prevMinute + "\n\ttm.getMinute(): " + tm.getMinute());
					
					CheckForAlarm(tm);
				}
				prevMinute = tm.getMinute();
			}
		});
		ss.start();
	}
	
	private void CheckForAlarm(TimeModel pTm) {
		if (alarm1.getActive() && tm.getHour() == alarm1.getHour() && tm.getMinute() == alarm1.getMinute()) {
			alarm1.ring();
			System.out.println("alarm1.ring()");
		}
		else if (alarm2.getActive() && tm.getHour() == alarm2.getHour() && tm.getMinute() == alarm2.getMinute()) {
			alarm2.ring();
			System.out.println("alarm2.ring()");
		}
	}
	
	protected void alarmStoppedRinging() {
		lbl_mainClock.setStyle("-fx-text-fill: black;");
	}
	
	protected void alarmStartedRinging() {
		lbl_mainClock.setStyle("-fx-text-fill: red;");
	}
	
	
	/***************************************************************************
     *                                                                         *
     * UI Callback Methods                                                     *
     *                                                                         *
     **************************************************************************/
	public void hb_alarmClock1_OnMouseClicked() {
		if(alarm1.getRinging()) {
			alarm1.stop();			
		}
		
		if (alarm2.getRinging()) {
			alarm2.stop();
		}
	}
}
