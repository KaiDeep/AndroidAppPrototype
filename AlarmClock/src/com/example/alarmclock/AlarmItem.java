package com.example.alarmclock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.DateFormat;

public class AlarmItem 
{
	private Calendar mAlarmCal;
	private String mMessage;
	private int mAlarmId;
	
	public AlarmItem(Calendar cal, String msg, int id) 
	{
		mAlarmCal = cal;
		mMessage = msg;
		mAlarmId = id;
	}
	
	Calendar getAlarmCal()
	{
		return mAlarmCal;
	}
	
	String getAlarmTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(mAlarmCal.getTime()); 
	}
	
	String getAlarmMessage()
	{
		return mMessage;
	}
	
	int getAlarmId()
	{
		return mAlarmId;
	}
}
