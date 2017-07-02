package org.krlib.dashboard.clock;

import java.util.Calendar;
import java.util.Locale;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClockModel {
	private Calendar cal;
	
	
	/***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
	public ClockModel() {
		UpdateTime();
	}
	
	/***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
	/*
	 * The current hour
	 */
    private IntegerProperty hour = new SimpleIntegerProperty();
    public Integer getHour() { return hour.get();}
    private void setHour(Integer pValue) {hour.set(pValue); dispTime.set(getDispTime());}
    public IntegerProperty getHourProperty() { return hour;}
    
    /*
     * The current Minute
     */
    private IntegerProperty minute = new SimpleIntegerProperty();
    public Integer getMinute() { return minute.get();}
    private void setMinute(Integer pValue) {minute.set(pValue); dispTime.set(getDispTime());}
    public IntegerProperty getMinuteProperty() { return minute;}
    
    /*
     * The current Second
     */
    private IntegerProperty second = new SimpleIntegerProperty();
    public Integer getSecond() { return second.get();}
    private void setSecond(Integer pValue) {second.set(pValue);}
    public IntegerProperty getSecondProperty() { return second;}
    
    /*
     * The current time as a string to display
     */
    private StringProperty dispTime = new SimpleStringProperty();
    public String getDispTime() { return getHour() + ":" + getMinute(); }
    public StringProperty getDispTimeProperty() {return dispTime; }
    
    /*
     * The current year
     */
    private IntegerProperty year = new SimpleIntegerProperty();
    public Integer getYear() { return year.get(); }
    private void setYear(Integer pValue) {year.set(pValue); dispDate.set(getDispDate());}
    public IntegerProperty getYearProperty() {return year; }
    
    /*
     * The current month
     */
    private StringProperty month = new SimpleStringProperty();
    public String getMonth() { return month.get(); }
    private void setMonth(String pValue) {month.set(pValue); dispDate.set(getDispDate());}
    public StringProperty getMonthProperty() {return month; }
    
    /*
     * The current day of the month
     */
    private IntegerProperty day = new SimpleIntegerProperty();
    public Integer getDay() { return day.get();}
    private void setDay(Integer pValue) {day.set(pValue); dispDate.set(getDispDate());}
    public IntegerProperty getDayProperty() {return day; }
    
    
    /*
     * The current day of the week
     */
    private StringProperty weekDay = new SimpleStringProperty();
    public String getWeekDay() { return weekDay.get(); }
    private void setWeekDay(String pValue) {weekDay.set(pValue);}
    public StringProperty getWeekDayProperty() {return weekDay; }
    
    /*
     * The current date as a string to display
     */
    private StringProperty dispDate = new SimpleStringProperty();
    public String getDispDate() { return getMonth() + " " + getDay() + " " + getYear();}
    public StringProperty getDispDateProperty() {return dispDate;}
    
    
	
	/***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
	
	
	private void UpdateTime() {
		//Get current time/date
		cal = null;
		cal = Calendar.getInstance();
		
		this.setHour(cal.get(Calendar.HOUR_OF_DAY));
		this.setMinute(cal.get(Calendar.MINUTE));
		this.setSecond(cal.get(Calendar.SECOND));
		
		this.setYear(cal.get(Calendar.YEAR));
		this.setMonth(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US));
		this.setDay(cal.get(Calendar.DAY_OF_MONTH));
		this.setWeekDay(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
		System.out.println("updated: " + this.getHour() + ":" + this.getMinute() + "." + this.getSecond());
	}
	
	
}
