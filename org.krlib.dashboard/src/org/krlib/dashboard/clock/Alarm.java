package org.krlib.dashboard.clock;

import java.util.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;


/***************************************************************************
 * Event Interface                                                         *
 **************************************************************************/
interface IAlarmListener {
	void startedRinging();
	void stoppedRinging();
}


/***************************************************************************
 *                                                                         *
 * Class declaration & global variables                                    *
 *                                                                         *
 **************************************************************************/
public class Alarm {
	private List<IAlarmListener> listeners = new ArrayList<IAlarmListener>(); //List of all objects subscribed to the event	
	private int previousMinute;
	
	/***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
	public Alarm(Clock pClock, Integer pHour, Integer pMinute, Boolean pActive) {
		setHour(pHour);
		setMinute(pMinute);
		setActive(pActive);
		
		addClockTickListener(pClock);

		/*
		 * The previous minute int is used to prevent the alarm to start ringing more than 
		 * once a minute since the check gets performed every second and not every minute.
		 * Here the previous minute is set to the current minute so the alarm doesn't go off
		 * right after you started the application if it's the same time as the alarm is set to.
		 */
		previousMinute = Calendar.getInstance().get(Calendar.MINUTE);
	}
	
	/***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
	
	private IntegerProperty hour = new SimpleIntegerProperty();
	public Integer getHour() { return hour.get();}
	public void setHour(Integer pValue) { hour.set(pValue);}
	public IntegerProperty getHourProperty() { return hour;}
	
	private IntegerProperty minute = new SimpleIntegerProperty();
	public Integer getMinute() {return minute.get();}
	public void setMinute(Integer pValue) {minute.set(pValue);}
	public IntegerProperty getMinuteProperty() {return minute;}
	
	private BooleanProperty active = new SimpleBooleanProperty();
	public Boolean getActive() {return active.get();}
	public void setActive(Boolean pValue) {active.set(pValue);}
	public BooleanProperty getActiveProperty() {return active;}
	
	private BooleanProperty ringing = new SimpleBooleanProperty();
	public Boolean getRinging() {return ringing.get();}
	private void setRinging(Boolean pValue) { ringing.set(pValue);}
	public BooleanProperty getRingingProperty() {return ringing;}

	
	/***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
	public void addListener(IAlarmListener pValue) {
		listeners.add(pValue);
	}
	
	private void notifyStartedRinging() {
		for (IAlarmListener al : listeners) {
			al.startedRinging();
		}
	}
	
	private void notifyStoppedRinging() {
		for (IAlarmListener al : listeners) {
			al.stoppedRinging();
		}
	}
	
	private void addClockTickListener(Clock pClock) {
		pClock.addListener(new IClockListener() {
			
			@Override
			public void tick() {
				Calendar calendar = Calendar.getInstance();
				
				if (getActive() 
					&& calendar.get(Calendar.HOUR) == getHour()
					&& calendar.get(Calendar.MINUTE) == getMinute()
					&& previousMinute != calendar.get(Calendar.MINUTE)) {
					
					ring();
				}
				previousMinute = calendar.get(Calendar.MINUTE);
			}
		});
	}
	
	
	public void ring() {
		if (!getRinging()) {
			setRinging(true);
			notifyStartedRinging();
		}
	}
	
	public void stop() {
		if (getRinging()) {
			setRinging(false);
			notifyStoppedRinging();	
		}
	}
}
