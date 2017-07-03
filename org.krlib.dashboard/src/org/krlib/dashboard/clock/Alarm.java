package org.krlib.dashboard.clock;

import java.util.*;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

interface IAlarmListener {
	void startedRinging();
	void stoppedRinging();
}

public class Alarm {
	private List<IAlarmListener> listeners = new ArrayList<IAlarmListener>(); //List of all objects subscribed to the event
	
	
	/***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
	public Alarm(Integer pHour, Integer pMinute, Boolean pActive) {
		setHour(pHour);
		setMinute(pMinute);
		setActive(pActive);
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
	
	public void notifyStartedRinging() {
		for (IAlarmListener al : listeners) {
			al.startedRinging();
		}
	}
	
	public void notifyStoppedRinging() {
		for (IAlarmListener al : listeners) {
			al.stoppedRinging();
		}
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
