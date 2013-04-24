package com.example.alarmclock;

import android.net.Uri;
import android.os.Bundle;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmsListActivity extends ListActivity 
{
	private AlarmListAdapter mListAdapter;
	
	final int DELETE_ALARM_MENU_ITEM = 123;
	
    public Context mBaseContext;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	
		mBaseContext = getBaseContext();
		
		mListAdapter = new AlarmListAdapter(this, R.layout.activity_alarms_list, AlarmMainActivity.getAlarms());
		setListAdapter(mListAdapter);
		registerForContextMenu(getListView());
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() 
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

//	@Override
//    protected void onListItemClick(ListView l, View v, int position, long id) 
//	{
//		AlarmItem item = mListAdapter.getItem(position);
//		AlarmMainActivity.removeAlarm(item.getAlarmId());
//    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarms_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	{
		menu.setHeaderTitle("Delete Alarm");
		menu.add(Menu.NONE, DELETE_ALARM_MENU_ITEM, 0, "Delete");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	 
	    switch (item.getItemId()) {
	    case DELETE_ALARM_MENU_ITEM:
	    	AlarmItem alarmItem = mListAdapter.getItem(info.position);
	    	removeAlarm(alarmItem.getAlarmId());
	    	AlarmMainActivity.removeAlarmFromList(alarmItem);
	    	// remove from pref
	    	mListAdapter.remove(alarmItem);	    	
	    	mListAdapter.notifyDataSetChanged();
	    	Toast.makeText(this, "Alarm deleted !!!", Toast.LENGTH_LONG).show();
	        return true;
	    default:
	        return super.onContextItemSelected(item);
	    }
	}
		
	public void removeAlarm(int iUniqueId)
	{
		Intent alarmIntent  = new Intent(mBaseContext, AlarmMainActivity.class);
		alarmIntent .setData(Uri.parse("custom://" + iUniqueId));
		alarmIntent .setAction(String.valueOf(iUniqueId));
		AlarmManager alarmManager = (AlarmManager) mBaseContext.getSystemService(ALARM_SERVICE);		
		PendingIntent displayIntent = PendingIntent.getBroadcast(mBaseContext, iUniqueId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(displayIntent);
	}
}
