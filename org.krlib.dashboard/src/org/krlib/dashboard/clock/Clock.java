package org.krlib.dashboard.clock;

import org.krlib.musicPlayer.*;
import org.krlib.musicPlayer.api.IMusicPlayer;
import org.krlib.musicPlayer.api.MusicPlayerFactory;
import org.krlib.musicPlayer.localFilePlayer.LocalFilePlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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


/***************************************************************************
 * Event Interface                                                         *
 **************************************************************************/
interface IClockListener{
	public void tick();
}

/***************************************************************************
 *                                                                         *
 * Class declaration & global variables                                    *
 *                                                                         *
 **************************************************************************/
public class Clock extends GridPane{
	private Locale DEFAULT_LOCALE = Locale.GERMANY;
	
	private Integer DEFAULT_ALARM1_HOUR = 11;
	private Integer DEFAULT_ALARM1_MINUTE = 46;
	private Boolean DEFAULT_ALARM1_ACTIVE = true;
	private Integer DEFAULT_ALARM2_HOUR = 9;
	private Integer DEFAULT_ALARM2_MINUTE = 52;
	private Boolean DEFAULT_ALARM2_ACTIVE = true;
	
	@FXML private Label lbl_curDate_WeekDay;
	@FXML private Label lbl_curDate_divider;
	@FXML private Label lbl_curDate_longDate;
	@FXML private Label lbl_mainClock;
	@FXML private Label lbl_alarmClock1;
	@FXML private Label lbl_alarmClock2;
	@FXML private Label lbl_alarmSound;
	@FXML private HBox hb_alarmClock1;

	SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("hh:mm");
	SimpleDateFormat hourMinuteSecondFormat = new SimpleDateFormat("hh:mm:ss");
	
	private List<IClockListener> listeners = new ArrayList<IClockListener>();
	
	private IMusicPlayer musicPlayer;
	
	
	/***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
	public Clock() {
		//FXML default init
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clock.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		//General init
		setDefaults();
		setupListeners();
		initializeTick();
	}
	
	/***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
	
	public Alarm alarm1 = new Alarm(this, DEFAULT_ALARM1_HOUR, DEFAULT_ALARM1_MINUTE, DEFAULT_ALARM1_ACTIVE);
	public Alarm alarm2 = new Alarm(this, DEFAULT_ALARM2_HOUR, DEFAULT_ALARM2_MINUTE, DEFAULT_ALARM2_ACTIVE);
		
	/***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
	public void addListener(IClockListener pValue) {
		listeners.add(pValue);
	}
	
	private void notifyListeners() {
		for (IClockListener icl : listeners) {
			icl.tick();
		}
	}
	
	private void setDefaults() {
		lbl_alarmClock1.setText(alarm1.getHour() + ":" + alarm1.getMinute());
		lbl_alarmClock2.setText(alarm2.getHour() + ":" + alarm2.getMinute());
		
		//Set default alarm sound
		String path = new File(getClass().getResource("sounds/Alarm-tone.mp3").getPath()).getAbsolutePath();
		setAlarmSong(path);
	}
	
	private void setupListeners() {
		addListener(new IClockListener() {
			
			@Override
			public void tick() {
				Calendar calendar = Calendar.getInstance(DEFAULT_LOCALE);
				
				lbl_curDate_WeekDay.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, DEFAULT_LOCALE));
				lbl_curDate_longDate.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, DEFAULT_LOCALE)
												+ " " + calendar.get(Calendar.DAY_OF_MONTH) 
												+ " " + calendar.get(Calendar.YEAR));
				
				lbl_mainClock.setText(hourMinuteFormat.format(calendar.getTime()));
				
				System.out.println("[" + hourMinuteSecondFormat.format(calendar.getTime()) + "]: Tick!");
			}
		});

		alarm1.addListener(new IAlarmListener() {
			
			@Override
			public void stoppedRinging() { 
				alarmStoppedRinging(); 
			}
			
			@Override
			public void startedRinging() { 
				alarmStartedRinging(); 
			}
		});
		
		alarm2.addListener(new IAlarmListener() {
			
			@Override
			public void stoppedRinging() { 
				alarmStoppedRinging(); 
			}
			
			@Override
			public void startedRinging() { 
				alarmStartedRinging(); 
			}
		});
	}
	
	private void initializeTick() {
        ScheduledService<Void> ss = new ScheduledService<Void>() {
        	@Override
        	protected Task<Void> createTask() {
        		return new Task<Void>() {
        			@Override
        			protected Void call() throws Exception {
        				return null;
        			}
				};
        	}
		};
		ss.setPeriod(Duration.seconds(1));
		ss.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				notifyListeners(); //here because here it's executed on the "gui"-thread
				
			}
		});
		ss.start();
	}
	
	public void setAlarmSong(String pValue) {
		musicPlayer = MusicPlayerFactory.createMusicPlayer(pValue);
		lbl_alarmSound.setText(musicPlayer.getSongName());
	}
	
	protected void alarmStartedRinging() {
		lbl_mainClock.setStyle("-fx-text-fill: red;");
		System.out.println("Alarm started ringing!");
		
		//Start playing music...
		musicPlayer.play();
	}
	
	protected void alarmStoppedRinging() {
		lbl_mainClock.setStyle("-fx-text-fill: black;");
		System.out.println("Alarm stopped ringing!");

		//Stop playing music...
		musicPlayer.stop();
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
