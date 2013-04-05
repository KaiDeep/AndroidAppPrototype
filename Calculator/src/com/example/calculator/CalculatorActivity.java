package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

public class CalculatorActivity extends Activity 
{
	EditText m_InputEditText;
	
	Button button_0, button_1, button_2, button_3, button_4,
		   button_5, button_6, button_7, button_8, button_9;
	
	Button button_Plus, button_Minus, button_Multiply, button_Divide,
		   button_Equal, button_Point, button_Del, button_Reset;
	
	Button button_Sin, button_Cos, button_Tan, button_Squared_2,
		   button_Root, button_Dec, button_Bin, button_Pi;
	
	Button button_Exit;
	
	String str_Sum = "", str_Pi = "3.1416";
	
	String str_Zero, str_One, str_Two, str_Three, str_Four, 
		   str_Five, str_Six, str_Seven, str_Eight, str_Nine;
	
	Float m_fResult = 0f, m_fResult_Mul = 1f, m_fResult_Div = 1f;
	
	char m_charPressed;
	
	int m_charCount;

	
//	String str_One_One = "", str_Two_Two, str_Three_Three, str_Four_Four, str_Five_Five,
//		   str_Six_Six, str_Seven_Seven, str_Eight_Eight, str_Nine_Nine;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        
        m_InputEditText = (EditText) findViewById(R.id.inputEditText);
        
        button_0 = (Button) findViewById(R.id.button_0);
        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);
        button_3 = (Button) findViewById(R.id.button_3);
        button_4 = (Button) findViewById(R.id.button_4);
        button_5 = (Button) findViewById(R.id.button_5);
        button_6 = (Button) findViewById(R.id.button_6);
        button_7 = (Button) findViewById(R.id.button_7);
        button_8 = (Button) findViewById(R.id.button_8);
        button_9 = (Button) findViewById(R.id.button_9);
        
        button_Plus = (Button) findViewById(R.id.button_Plus);
        button_Minus = (Button) findViewById(R.id.button_Minus);
        button_Multiply = (Button) findViewById(R.id.button_Multiply);
        button_Divide = (Button) findViewById(R.id.button_Divide);        
        button_Equal = (Button) findViewById(R.id.button_Equal);
        button_Point = (Button) findViewById(R.id.button_Point);
        button_Del = (Button) findViewById(R.id.button_Del);
        button_Reset = (Button) findViewById(R.id.button_Reset);
    
	
        button_Sin = (Button) findViewById(R.id.button_Sin);
        button_Cos = (Button) findViewById(R.id.button_Cos);
        button_Tan = (Button) findViewById(R.id.button_Tan);
        button_Squared_2 = (Button) findViewById(R.id.button_Squared_2);
        
        button_Root = (Button) findViewById(R.id.button_Root);
        button_Dec = (Button) findViewById(R.id.button_Dec);
        button_Bin = (Button) findViewById(R.id.button_Bin);
        button_Pi = (Button) findViewById(R.id.button_Pi);
        
        button_Exit  = (Button) findViewById(R.id.button_Exit);
        
        m_InputEditText.setText(m_fResult.toString());
    }

    public void onClickListener_0(View v)
    {
    	if ('=' == m_charPressed) {
    		onClickListener_Reset(button_Reset);
    	}
    	
    	if ("" != str_Sum) {    		
    		str_Sum = str_Sum + (String) button_0.getText();    		
    	} else {
    		str_Sum = "0";
    	}
    	
    	m_InputEditText.setText(str_Sum);
    }
        
	public void onClickListener_1(View v)
    {
		if ('=' == m_charPressed) {
    		onClickListener_Reset(button_Reset);
    	}
    	
		str_Sum = str_Sum +  (String) button_1.getText();
		m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_2(View v)
    {
    	if ('=' == m_charPressed) {
            onClickListener_Reset(button_Reset);
        }
        
        if ('=' == m_charPressed) {
            onClickListener_Equal(button_Equal);
        }
                
        str_Sum = str_Sum + (String) button_2.getText();        
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_3(View v)
    {
    	if ('=' == m_charPressed) {
            onClickListener_Reset(button_Reset);
        }

    	str_Sum = str_Sum + (String) button_3.getText();        
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_4(View v)
    {
    	if ('=' == m_charPressed) {
            onClickListener_Reset(button_Reset);
        }
        
    	str_Sum = str_Sum + (String) button_4.getText();        
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_5(View v)
    {
    	if ('=' == m_charPressed) {
            onClickListener_Reset(button_Reset);
        }
        
    	str_Sum = str_Sum + (String) button_5.getText();        
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_6(View v)
    {
    	if ('=' == m_charPressed) {
            onClickListener_Reset(button_Reset);
        }
        
    	str_Sum = str_Sum + (String) button_6.getText();        
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_7(View v)
    {
    	if ('=' == m_charPressed) {
            onClickListener_Reset(button_Reset);
        }
        
    	str_Sum = str_Sum + (String) button_7.getText();        
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_8(View v)
    {
    	if ('=' == m_charPressed) {
            onClickListener_Reset(button_Reset);
        }
        
    	str_Sum = str_Sum + (String) button_8.getText();        
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_9(View v)
    {
    	if ('=' == m_charPressed) {
            onClickListener_Reset(button_Reset);
        }
        
    	str_Sum = str_Sum + (String) button_9.getText();        
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_Plus(View view)
    {
    	if ('-' == m_charPressed) {
            onClickListener_Equal(button_Minus);
        } 
    	else if ('*' == m_charPressed) {        
            onClickListener_Equal(button_Multiply);
        }        
        else if ('/' == m_charPressed) {
            onClickListener_Equal(button_Divide);
        }
        
        
    	m_charPressed = '+';
            	
        if ("" != str_Sum) {
        	m_fResult = m_fResult + Float.parseFloat(m_InputEditText.getText().toString());
        } 
        
        m_InputEditText.setText(m_fResult.toString());            
        m_fResult_Mul = m_fResult;
        m_fResult_Div = m_fResult;
        str_Sum = "";
    }
    
    public void onClickListener_Minus(View v)
    {
        
        if ('+' == m_charPressed) {       
            onClickListener_Equal(button_Plus);
        }
        else if ('*' == m_charPressed) {        
            onClickListener_Equal(button_Multiply);
        }
        else if ('/' == m_charPressed) {
            onClickListener_Equal(button_Divide);
        }
       
        
        m_charPressed = '-';
        
        //String EditTextMsg = m_InputEditText.getText().toString(); 
        //Float floatEditTextMsg = Float.parseFloat(EditTextMsg);
        
        if ("" == str_Sum && 0 == m_fResult) {
        	str_Sum = str_Sum + '-';
            //Log.d("sum=","minus press");
        }
        else if ("" != str_Sum) {
            if (0 == m_fResult) {
            	m_fResult = Float.parseFloat(str_Sum) - m_fResult;
            }            
            else {
            	m_fResult = m_fResult - Float.parseFloat(str_Sum);
            }
            
            m_InputEditText.setText(m_fResult.toString());                
            m_fResult_Mul = m_fResult;                
            m_fResult_Div = m_fResult;                
            str_Sum = "";
        }
        
    }
    
    public void onClickListener_Multiply(View view)
    {
    	if ('/' == m_charPressed) {        
            onClickListener_Equal(button_Divide);
        }
        else if ('+' == m_charPressed) {
            onClickListener_Equal(button_Plus);
        }        
        else if ('-' == m_charPressed) {
            onClickListener_Equal(button_Minus);
        }
        
        m_charPressed = '*';
        
        String editTextMsg = m_InputEditText.getText().toString(); 
        Float floatEditTextMsg = Float.parseFloat(editTextMsg);
        
        if ("" != str_Sum) {
            m_fResult_Mul = m_fResult_Mul * floatEditTextMsg;             
            m_fResult = m_fResult_Mul;            
            m_fResult_Div = m_fResult_Mul;            
            m_InputEditText.setText(m_fResult_Mul.toString());            
        }
        else {
            m_InputEditText.setText(editTextMsg);            
            //result_mul=result_mul * Float.parseFloat(sum);            
            //result=result_mul;
        }
        
        str_Sum = "";
    }    
    
    public void onClickListener_Divide(View v)
    {    
        if ('+' == m_charPressed) {
            onClickListener_Equal(button_Plus);
        }
        
        else if ('-' == m_charPressed) {
            onClickListener_Equal(button_Minus);
        }        
        else if ('*' == m_charPressed) {
            onClickListener_Equal(button_Multiply);
        }
        
        m_charPressed = '/';
        
        String editTextMsg = m_InputEditText.getText().toString(); 
        Float floatEditTextMsg = Float.parseFloat(editTextMsg);
        
        if ("" != str_Sum && 1 == m_fResult_Div) {
            if (0 == m_charCount) {

            	m_fResult_Div = floatEditTextMsg / m_fResult_Div;  
                //Log.d("if if result_div=", result_div.toString());
            	m_charCount++;
            }
            else {
            	m_fResult_Div = m_fResult_Div / floatEditTextMsg; 
                //Log.d("if else result_div=", result_div.toString());
            }
                        
            m_fResult = m_fResult_Div;
            m_fResult_Mul = m_fResult_Div;            
            m_InputEditText.setText(m_fResult_Div.toString());            
            str_Sum = "";
        }
        else if ("" != str_Sum && 1 != m_fResult_Div) {
        	m_fResult_Div = m_fResult_Div / floatEditTextMsg;             
            Log.d("else if result_div=", m_fResult_Div.toString());            
            m_fResult = m_fResult_Div;            
            m_fResult_Mul = m_fResult_Div;            
            m_InputEditText.setText(m_fResult_Div.toString());                        
        }
        else {
            m_InputEditText.setText(editTextMsg);            
        }
        
        str_Sum = "";
    }
    
    public void onClickListenerPoint(View v)
    {
          
    	int error=0;        
        if (null != str_Sum) {
            for (int i = 0; i < str_Sum.length(); ++i) {
                if(str_Sum.charAt(i)=='.') {
                    error = 1;
                    break;
                }
            }
        }
        
        if (error==0) {
            if (null == str_Sum) {
            	str_Sum = str_Sum + "0.";
            }
            else {
            	str_Sum = str_Sum + ".";
            }
        }
       
        m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_Equal(View view)
    {
    	switch (m_charPressed) {
    	case '+':
    		onClickListener_Plus(button_Plus);
    		break;
    	case '-':
    		onClickListener_Minus(button_Minus);
    		break;
    	case '*':
    		onClickListener_Multiply(button_Multiply);
    		break;
    	case '/':
    		onClickListener_Divide(button_Divide);
    		break;
    	}
    	        
        m_charPressed = '=';        
    }
    
    public void onClickListener_Reset(View view) 
    {		
    	str_Sum = "";
        //countOne = 0;//result=0;
        
        m_fResult = 0f;
        m_fResult_Mul = 1f;
        m_fResult_Div = 1f;
        m_charPressed = ' ';
        m_charCount = 0;
        
        m_InputEditText.setText(m_fResult.toString());
	}
    
    public void onClickListener_Point(View view)
    {      
    	int error = 0;
    	if (null != str_Sum) {
    		
			for (int i = 0; i < str_Sum.length(); ++i) {
			    if (str_Sum.charAt(i)=='.') {
			        error = 1;
			        break;
			    }
			}

    	}
         
    	if (0 == error) {
    		if (null == str_Sum) {
    			str_Sum = str_Sum + "0.";
    		} else {
    			str_Sum = str_Sum + ".";
    		}
    	}
        
    	m_InputEditText.setText(str_Sum);
    }
    
    public void onClickListener_Sin(View v)
    {        
    	mathFunCalculation(Angle.SIN);
    }
    
    public void onClickListener_Cos(View v)
    {        
    	mathFunCalculation(Angle.COS);
    }
    
    public void onClickListener_Tan(View v)
    {        
    	mathFunCalculation(Angle.TAN);
    }
    
    public void onClickListener_Squared_2(View v)
    {
    	mathFunCalculation(Angle.SQU_2);
    }
    
    public void onClickListener_Qube(View v)
    {
    	mathFunCalculation(Angle.CUBE);
    }
    
    public void onClickListener_Root(View v)
    {
    	mathFunCalculation(Angle.ROOT);
    }
    
    private void mathFunCalculation(Angle angle)
    {
        String editTextMsg = m_InputEditText.getText().toString(); 
        Double doubleEditTextMsg = Double.parseDouble(editTextMsg);//degree
    	Double dValue = 0.0;
    	switch (angle) {
    	case SIN:
    		dValue = Math.sin(Math.toRadians(doubleEditTextMsg));
    		break;
    	case COS:
    		dValue = Math.cos(Math.toRadians(doubleEditTextMsg));
    		break;
    	case TAN:
    		dValue = Math.tan(Math.toRadians(doubleEditTextMsg));
    		break;
    	case SQU_2:
    		dValue = Math.pow(doubleEditTextMsg, 2);
    		break;
    	case CUBE:
    		dValue = Math.pow(doubleEditTextMsg, 3);
    		break;
    	case ROOT:
    		dValue = Math.sqrt(doubleEditTextMsg);
    		break;
    	}
    	
        m_InputEditText.setText(dValue.toString());        
        editTextMsg = m_InputEditText.getText().toString();
        m_fResult = Float.parseFloat(editTextMsg);
        
        m_fResult_Mul = Float.parseFloat(editTextMsg);        
        m_fResult_Div = Float.parseFloat(editTextMsg);                
        str_Sum = "";        
    }
    
    public void onClickListener_Pi(View v)
    {        
         if ('=' == m_charPressed) {
             onClickListener_Reset(button_Reset);
         }
         
         str_Sum = str_Pi;        
         m_InputEditText.setText(str_Pi);     
    } 
    
    public void onClickListener_Del(View v)
    {        
        if ("" != str_Sum) {
            StringBuilder stringBuilder = new StringBuilder(80);            
            stringBuilder.append(str_Sum);            
            str_Sum = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();            
            m_InputEditText.setText(str_Sum);
        }
        else {
        	onClickListener_Reset(button_Reset);
        }
    }
    
    public void onClickListener_Dec(View v)
    {       
        String editTextMsg = m_InputEditText.getText().toString();
        Integer unicode_value = 0, dec_num;
        int dec_flag = 0;
        
        for (int i = 0; i <= editTextMsg.length() - 1; ++i) {
            unicode_value = editTextMsg.codePointAt(i);
            if (unicode_value > 49 || unicode_value < 48) {
                dec_flag = 1;
                Log.d("uni", unicode_value.toString());
                break;
            }            
        }
                
        if (0 == dec_flag) {
            dec_num = Integer.parseInt(editTextMsg, 2);                        
            m_InputEditText.setText(dec_num.toString());            
            //editTextMsg = editText.getText().toString();
        } else {                
        	m_InputEditText.setText("input error");
        }
        
        str_Sum = ""; 
    } 
   
    public void onClickListener_Bin(View v)
    {        
    	numberSystenConversion(NumberSystem.BIN);
    }
    
    public void onClickListener_Oct(View v)
    {        
    	numberSystenConversion(NumberSystem.OCT);
    }
    
    public void onClickListener_Hex(View v)
    {        
    	numberSystenConversion(NumberSystem.HEX);
    }
    
    private void numberSystenConversion(NumberSystem numSys)
    {
    	String editTextMsg = m_InputEditText.getText().toString();
        String numsys_string = "";
        for (int i = 0; i < editTextMsg.length(); ++i) {
            if (editTextMsg.charAt(i)=='.') {
                break;
            } else {
            	numsys_string = numsys_string + editTextMsg.charAt(i);
            }
        }
        
        Integer dec_num = Integer.parseInt(numsys_string);
        
    	switch (numSys) {
    	case BIN:
    		m_InputEditText.setText(Integer.toBinaryString(dec_num));
    		break;
    	case OCT:
    		m_InputEditText.setText(Integer.toOctalString(dec_num));
    		break;
    	case HEX:
    		m_InputEditText.setText(Integer.toHexString(dec_num));
    		break;
    	}
    	
    	str_Sum = "";
    }
    
    public void onClickListener_Exit(View v)
    {     
        finish();
    }
}
