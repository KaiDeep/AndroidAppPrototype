package com.example.alarmclock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmMainActivity extends Activity 
{
	private static final int DIALOG_SET_ALERT = 10;
	private AlertDialog setAlarmDialog = null;
	
    Button 		mButtonSetAlarm;
    Button 		mButtonViewAlarm;
    CheckBox	mRepeatCheckbox;
    TextView 	mTextAlarmPrompt;
    DatePicker 	mDatePicker;
    TimePicker 	mTimePicker;    
    EditText	mAlarmMsgEditText;    
    String 		mAlarmMessage;
        
    public static Context mBaseContext;
    
    private static List<AlarmItem> mAlarmList = null;
    
    final static int RQS_1 = 1;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_main);

		mBaseContext = getBaseContext();		
		mAlarmList = new ArrayList<AlarmItem>();		
		mTextAlarmPrompt = (TextView) findViewById(R.id.alarmPrompt);        
		mButtonSetAlarm = (Button) findViewById(R.id.startAlarm);
		mButtonSetAlarm.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) 
			{
				mTextAlarmPrompt.setText("");
				showDialog(DIALOG_SET_ALERT);
			}		
		});		
		
		mButtonViewAlarm = (Button) findViewById(R.id.viewAlarms);
		mButtonViewAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				showAlarmList();
			}		
		});
		
		loadSavedAlarmsFromPref();
	}

	@Override 
	public void onBackPressed() 
	{
		saveAlarmsInPref();
		finish();
        return;
    } 
	
	@Override
	protected Dialog onCreateDialog(int id) 
	{
	    switch (id) {
	    case DIALOG_SET_ALERT:
	      // Create out AlterDialog
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setCancelable(true);
			setAlarmDialog = builder.create();
			Calendar calendar = Calendar.getInstance();
			setAlarmDialog.setTitle(calendar.getTime().toString());	
			View v = getLayoutInflater().inflate(R.layout.alarm_setalarm, null);
			setAlarmDialog.setView(v);
			
			mDatePicker = (DatePicker) v.findViewById(R.id.dp_date); 
			mTimePicker = (TimePicker) v.findViewById(R.id.timePicker1);
			mRepeatCheckbox = (CheckBox) v.findViewById(R.id.repeatCheckbox);
			mAlarmMsgEditText = (EditText) v.findViewById(R.id.alarm_msg_editText);
			
			Button saveBtn = (Button) v.findViewById(R.id.saveBtn);
			saveBtn.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) 
				{
					int year = mDatePicker.getYear();
	                int month = mDatePicker.getMonth();
	                int day = mDatePicker.getDayOfMonth();
	                int hour = mTimePicker.getCurrentHour();
	                int minute = mTimePicker.getCurrentMinute();
	                
	                /** Creating a calendar object corresponding to the date and time set by the user */
	                GregorianCalendar calendar = new GregorianCalendar(year,month,day, hour, minute);
	                GregorianCalendar calNow = new GregorianCalendar();		            		            	            
		            if (calendar.compareTo(calNow) <= 0) {
		                // Today Set time passed, count to tomorrow
		            	calendar.add(Calendar.DATE, 1);
		            }
		           
		            mAlarmMessage = mAlarmMsgEditText.getText().toString();
		            if (mAlarmMessage.isEmpty()) {
		            	mAlarmMessage = "Wake up !";
		            }
		            
		            setAlarm(calendar, mRepeatCheckbox.isChecked());		            
		            setAlarmDialog.dismiss();		            
		            showAlarmList();
				}
			});
			setAlarmDialog.show();	      
	    }
	    return super.onCreateDialog(id);
	}
	
	private void showAlarmList()
	{        
        Intent intent = new Intent(AlarmMainActivity.this, AlarmsListActivity.class);
        startActivity(intent);
	}
	
	public static List<AlarmItem> getAlarms()
	{
		return mAlarmList;
	}
	
	public static void removeAlarmFromList(AlarmItem alarm)
	{
		mAlarmList.remove(alarm);
	}
	
	private void saveAlarm(Calendar cal, String msg, int alarmId)
	{
		mAlarmList.add(new AlarmItem(cal, msg, alarmId));
	}
			
	private void setAlarm(Calendar cal, boolean bRepeat)
	{	
		int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
		
        // create new Pending intent and add to the alarm manager
        Intent invokeAlarmIntent = new Intent(this, InvokeAlarmActivity.class);
        invokeAlarmIntent.putExtra("alarm_message", mAlarmMessage);
        invokeAlarmIntent.setData(Uri.parse("custom://" + iUniqueId));
        invokeAlarmIntent.setAction(String.valueOf(iUniqueId));
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, iUniqueId, invokeAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        if (bRepeat) {
        	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 5*1000, pendingIntent);
        } else {
        	alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
        
        saveAlarm(cal, mAlarmMessage, iUniqueId);
        
        String msg = "\n\n***\n" + "Alarm is set " + cal.getTime() + "\n" + "***\n";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
		
	public static void removeAlarm(int iUniqueId)
	{
//		Intent alarmIntent  = new Intent(mBaseContext, AlarmMainActivity.class);
//		alarmIntent .setData(Uri.parse("custom://" + iUniqueId));
//		alarmIntent .setAction(String.valueOf(iUniqueId));
//		AlarmManager alarmManager = (AlarmManager) mBaseContext.getSystemService(ALARM_SERVICE);		
//		PendingIntent displayIntent = PendingIntent.getBroadcast(mBaseContext, iUniqueId, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//		alarmManager.cancel(displayIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.alarm_main, menu);
		return true;
	}

	@Override
    public boolean dispatchTouchEvent(MotionEvent event) 
    {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View v = getCurrentFocus();
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + v.getLeft() - scrcoords[0];
            float y = event.getRawY() + v.getTop() - scrcoords[1];
            
            if (event.getAction() == MotionEvent.ACTION_UP 
				&& (x < v.getLeft() || x >= v.getRight() 
				|| y < v.getTop() || y > v.getBottom()) ) { 
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
	
	private void loadSavedAlarmsFromPref() 
	{
		mAlarmList.clear();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mBaseContext);
        
        int itemCount = sharedPrefs.getInt("ALARM_COUNT", 0);
        String key = "KEY";
        for (int i = 0; i < itemCount; ++i) {        	        	
        	String alarmString = sharedPrefs.getString(key + "_" + i, "0");
        	if ("0" != alarmString) {
        		List<String> alarms = Arrays.asList(alarmString.split(","));
        		long miliSec = Long.parseLong(alarms.get(0));
        		Calendar cal = Calendar.getInstance();
        		cal.setTimeInMillis(miliSec);
        		mAlarmList.add(new AlarmItem(cal, alarms.get(1), Integer.parseInt(alarms.get(2))));
        	}
        }
	}
	
	private void saveAlarmsInPref()
	{
		int itemCount = mAlarmList.size();
		
		List<StringBuilder> builderList = new ArrayList<StringBuilder>();
		
		for (int i = 0; i < itemCount; ++i) {
			StringBuilder sb = new StringBuilder();
			Calendar cal = mAlarmList.get(i).getAlarmCal();
			long longTime = cal.getTimeInMillis();
			sb.append(String.valueOf(longTime)).append(",");
			sb.append(mAlarmList.get(i).getAlarmMessage()).append(",");
			sb.append(String.valueOf(mAlarmList.get(i).getAlarmId())).append(",");
			builderList.add(sb);
		}
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mBaseContext);
		SharedPreferences.Editor prefEditor = sharedPrefs.edit();

		prefEditor.clear(); // clear old data
		prefEditor.putInt("ALARM_COUNT", itemCount);
		String key = "KEY";
		for (int i = 0; i < itemCount; ++i) {
			prefEditor.putString(key + "_" + i, builderList.get(i).toString());
		}
		
		prefEditor.commit();		
	}
}

/*
 * public void AddAlarm(int requestCode,MutableDateTime dueDate,int repeat) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Constants.RECORD_ID, requestCode);
        intent.putExtra("REPEAT", repeat);
        PendingIntent operation = PendingIntent.getBroadcast(context, requestCode, intent,  PendingIntent.FLAG_ONE_SHOT );
        MutableDateTime due = dueDate.toMutableDateTime();
        switch(repeat){
        case NO_REPEAT:
            due.addMinutes(0);
            break;
        case DAILY:

            due.addDays(1); 
            break;
        case WEEKLY:
            due.addWeeks(1);
            break;
        case MONTHLY:
            due.addMonths(1);
            break;
        case MONTHLY_2:
            due.addWeeks(5);            
            break;
        case YEARLY:
            due.addYears(1);
            break;
        }
        due.add(-(dueDate.getMillis()));
        due.setSecondOfMinute(0);
        dueDate.setSecondOfMinute(0);
        alarm.cancel(operation);
        alarm.set(AlarmManager.RTC_WAKEUP, dueDate.getMillis(), operation);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, dueDate.getMillis(), due.getMillis(), operation);
}
 */
