package com.example.alarmclock;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AlarmListAdapter extends ArrayAdapter<AlarmItem> 
{
	private List<AlarmItem> mAlarmList;
	private Context mContext;
	int mResourceId;
	
	public AlarmListAdapter(Context context, int textViewResourceId, List<AlarmItem> alarms) 
	{
		super(context, textViewResourceId, alarms);
		mContext = context;
		mAlarmList = alarms;
		mResourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
	    View row = convertView;
	    ViewHolder viewHolder = null;
	    
	    if (row == null) {	    	
	    	LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
	    	row = inflater.inflate(mResourceId, parent, false);
	    	
	    	viewHolder = new ViewHolder();
	    	
	    	viewHolder.sAlarmMsg = (TextView) row.findViewById(R.id.alarmMsg_txtView);
	    	viewHolder.sAlarmCal = (TextView) row.findViewById(R.id.alarmCal_txtView);
	    	
	    	row.setTag(viewHolder);
	    	
	    } else {
	    	viewHolder = (ViewHolder) row.getTag();
	    }
	    
	    AlarmItem alarm = mAlarmList.get(position);
	    if (alarm != null) {    	
	    	try {
			    viewHolder.sAlarmMsg.setText(alarm.getAlarmMessage());
			    viewHolder.sAlarmCal.setText(alarm.getAlarmTime());			    			   
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
    	
	    return row;
	}
	
	@Override
	public int getCount() 
	{
		return mAlarmList.size();
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	@Override
	public AlarmItem getItem(int position)
	{
		return mAlarmList.get(position);
	}
	
	public static class ViewHolder 
	{
	    public TextView sAlarmMsg;
	    public TextView sAlarmCal;
	}
}
