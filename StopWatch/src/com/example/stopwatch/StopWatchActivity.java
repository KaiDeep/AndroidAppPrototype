package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StopWatchActivity extends Activity implements OnClickListener
{	
	private Button button_START;
	private Button button_STOP;
	private Button button_RESET;
	private TextView	m_timeTextView;
	
	private static long DELAY = 100;
 
	private String m_applicationState;	
	private long m_startTimePoint;	
 
	private Handler tasksHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
    	if (android.os.Build.VERSION.SDK_INT > 9) {
		      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		      StrictMode.setThreadPolicy(policy);
	    }
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stop_watch);

		button_START = (Button) findViewById(R.id.start_button);
		button_STOP = (Button) findViewById(R.id.stop_button);
		button_RESET = (Button) findViewById(R.id.reset_button);

		button_START.setOnClickListener(this);
		button_STOP.setOnClickListener(this);
		button_RESET.setOnClickListener(this);
		
		afterInit();
	}

	private void afterInit () 
	{
		m_timeTextView = (TextView) findViewById(R.id.timeTextView);
		setLabelText("00:00:00:00");
		m_startTimePoint = Long.valueOf(0);
		stopCounting();
	}
		
	public void startCounting() 
	{
		m_applicationState = StopWatchStates.IN_COUNTING;

		tasksHandler.removeCallbacks(timeTickRunnable);
		tasksHandler.postDelayed(timeTickRunnable, DELAY);
		
		m_startTimePoint = System.currentTimeMillis();
	}
	
	public void stopCounting() 
	{
		m_applicationState = StopWatchStates.IN_WAITING;
	}
	
	public String currentTimeString() 
	{
		long interval = System.currentTimeMillis() - m_startTimePoint;
		return formatElapsedTime(interval);
	}
	
	public void setLabelText (String string) 
	{
		m_timeTextView.setText(string);
	}

	private Runnable timeTickRunnable = new Runnable() {
		public void run() 
		{
			if (m_applicationState == StopWatchStates.IN_COUNTING) {
				setLabelText(currentTimeString());
				tasksHandler.postDelayed(timeTickRunnable, DELAY);
			}
		}
	};
	
	public void stopButtonClick (View button) 
	{
		stopCounting();
	}
	
	public void onClick(View view) 
	{
		if (button_START == view) {
			startCounting();
		}
		else if (button_STOP == view) {
			stopCounting();
		}
		else if (button_RESET == view) {
			afterInit ();
			setLabelText("00:00:00");
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.stop_watch, menu);
		return true;
	}

	private String formatElapsedTime(long now) 
	{
		long hours = 0, minutes = 0, seconds = 0, tenths = 0;
		StringBuilder sb = new StringBuilder();
		
		if (now < 1000) {
			tenths = now / 100;
		} else if (now < 60000) {
			seconds = now / 1000;
			now -= seconds * 1000;
			tenths = (now / 100);
		} else if (now < 3600000) {
			hours = now / 3600000;
			now -= hours * 3600000;
			minutes = now / 60000;
			now -= minutes * 60000;
			seconds = now / 1000;
			now -= seconds * 1000;
			tenths = (now / 100);
		}

		if (hours > 0) {
			sb.append(hours).append(":")
			.append(formatDigits(minutes)).append(":")
			.append(formatDigits(seconds)).append(".")
			.append(formatDigits(tenths));
		} else {
			sb.append(formatDigits(minutes)).append(":")
			.append(formatDigits(seconds)).append(".")
			.append(formatDigits(tenths));
		}

		return sb.toString();
	}

	@SuppressLint("UseValueOf")
	private String formatDigits(long num) 
	{
		return (num < 10) ? "0" + num : new Long(num).toString();
	}
}
