package com.example.currencyconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	public int to;
	public int from;
	public String [] currencyValue;
	public String queryResult;	
	TextView resultTxtView;
	EditText unitTextEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);                
        
		resultTxtView = (TextView) findViewById(R.id.resultTextView);
		unitTextEdit = (EditText) findViewById(R.id.unitEditText);
		
        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        
        currencyValue = getResources().getStringArray(R.array.value);
        
        spin1.setAdapter(adapter);
        spin2.setAdapter(adapter);
        
        spin1.setOnItemSelectedListener(new SpinListner(1));
        spin2.setOnItemSelectedListener(new SpinListner(2));
        
        Button calculateBtn = (Button) findViewById(R.id.button1);
        calculateBtn.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) 
			{			
				String unitString = unitTextEdit.getText().toString();
				if (from ==  to || (unitString.length() <= 0 || Integer.parseInt(unitString) < 0)) {
					Toast.makeText(getApplicationContext(), "Invalid", 4000).show();
				}
				else {
					try {
						queryResult = jsonQuery("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22"+currencyValue[from]+currencyValue[to]+"%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
						JSONObject jObj = new JSONObject(queryResult);											
						Double rate = jObj.getJSONObject("query").getJSONObject("results").getJSONObject("rate").getDouble("Rate");						
						rate = Integer.parseInt(unitString) * rate;				
						resultTxtView.setText(String.format("%.2f", rate));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {					
						e.printStackTrace();
					}
				}
			}
		});        
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
    
    public String jsonQuery(String url) throws ClientProtocolException, IOException 
    {    
		StringBuilder build = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
	    HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream content = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		String con;
		while ((con = reader.readLine()) != null) {
			build.append(con);
		}
		
		return build.toString();
	}
    
    private class SpinListner implements OnItemSelectedListener
    {
    	int m_id;
    	
    	SpinListner(int id)
    	{
    		m_id = id;
    	}
    	
    	public void onItemSelected(AdapterView<?> parent, View view, int index, long id) 
    	{
    		if (m_id == 1) {
    			from = index;
    		}
    		else if (m_id == 2) {
    			to = index;
    		}
    	}

    	public void onNothingSelected(AdapterView<?> arg0) 
    	{
    		// TODO Auto-generated method stub	
    	}
    	
    }
}
//public class MainActivity extends Activity 
//{
//	public int to;
//	public int from;
//	public String [] currencyValue;
//	public String queryResult;
//	public Handler handler;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) 
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        
//        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
//        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
//        
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.name, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
//        
//        currencyValue = getResources().getStringArray(R.array.value);
//        
//        spin1.setAdapter(adapter);
//        spin2.setAdapter(adapter);
//        
//        spin1.setOnItemSelectedListener(new SpinListner(1));
//        spin2.setOnItemSelectedListener(new SpinListner(2));
//        
//        Button calculateBtn = (Button) findViewById(R.id.button1);
//        calculateBtn.setOnClickListener(new OnClickListener() {			
//			public void onClick(View v) 
//			{
//				TextView t = (TextView) findViewById(R.id.textView4);
//				if (from ==  to) {
//					Toast.makeText(getApplicationContext(), "Invalid", 4000).show();
//				}
//				else {
//					try {
//						queryResult = jsonResult("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22"+currencyValue[from]+currencyValue[to]+"%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
//						JSONObject jObj;
//						jObj = new JSONObject(queryResult);
//						String exResult = jObj.getJSONObject("query").getJSONObject("results").getJSONObject("rate").getString("Rate");
//						
//						Double rate = jObj.getJSONObject("query").getJSONObject("results").getJSONObject("rate").getDouble("Rate");
//						rate = 5 * rate;
//						String s = rate.toString();
//						//t.setText(exResult);
//						t.setText(s);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//					catch (ClientProtocolException e) {
//						e.printStackTrace();
//					} catch (IOException e) {					
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//    }
//    
//    public String jsonResult(String url) throws ClientProtocolException, IOException 
//    {    
//		StringBuilder build = new StringBuilder();
//		HttpClient client = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet(url);
//	    HttpResponse response = client.execute(httpGet);
//		HttpEntity entity = response.getEntity();
//		InputStream content = entity.getContent();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//		String con;
//		while ((con = reader.readLine()) != null) {
//			build.append(con);
//		}
//		
//		return build.toString();
//	}
//    
//    private class SpinListner implements OnItemSelectedListener
//    {
//    	int m_id;
//    	
//    	SpinListner(int id)
//    	{
//    		m_id = id;
//    	}
//    	
//    	public void onItemSelected(AdapterView<?> parent, View view, int index, long id) 
//    	{
//    		if (m_id == 1) {
//    			from = index;
//    		}
//    		else if (m_id == 2) {
//    			to = index;
//    		}
//    	}
//
//    	public void onNothingSelected(AdapterView<?> arg0) 
//    	{
//    		// TODO Auto-generated method stub	
//    	}
//    	
//    }
//}
