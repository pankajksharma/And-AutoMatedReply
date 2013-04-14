package com.engineerinme.pankaj.autoreply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewReplyActivity extends Activity{
	
	Button btnSubmitReply;
	EditText edtKeywords, edtNewMessage;
	DatePicker expDate;
	TimePicker expTime;
	
	String expDateTimeStr;
	Date expDateTime;
	
	boolean proceedWithOutKeywords = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reply);
        bindItems();
    }
	
	private void bindItems(){
		btnSubmitReply = (Button) findViewById(R.id.btnSubmitReply);
		edtKeywords = (EditText) findViewById(R.id.edtTextKeywords);
		edtNewMessage = (EditText) findViewById(R.id.editTextNewReply);
		expDate = (DatePicker) findViewById(R.id.datePicker1);
		expTime = (TimePicker) findViewById(R.id.timePicker1);
		
		btnSubmitReply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String toastText = validateItems();
				if (toastText.equalsIgnoreCase("all_clear")){
					String keywords = edtKeywords.getText().toString();
					keywords = keywords.replace("\n", "");
					MySQLiteOpenHelper db = new MySQLiteOpenHelper(getApplicationContext());
					MySQLiteOpenHelper.insertIntoDB(db.getWritableDatabase(), keywords, edtNewMessage.getText().toString(), expDateTimeStr );
					Toast.makeText(getApplicationContext(), "Message has been added!", Toast.LENGTH_LONG).show();
					NewReplyActivity.this.finish();
				}
				else if(!toastText.equalsIgnoreCase("")){
					Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	private String validateItems(){
		//String combinedKeywords = edtKeywords.getText().toString();
		String message = edtNewMessage.getText().toString();
		expDateTimeStr = expDate.getYear()+"-"+(expDate.getMonth()+1)+"-"+expDate.getDayOfMonth()+" "
				+ expTime.getCurrentHour()+":"+expTime.getCurrentMinute();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		expDateTime = new Date();
		try {
			expDateTime = format.parse(expDateTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date now = new Date();
		try {
			now = format.parse(new Date().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(message.equalsIgnoreCase(""))
			return getString(R.string.toastNoMessage);
		else if(expDateTime.compareTo(now) <= 0)
			return getString(R.string.toastDateTimeNotValid);
		/*else if(combinedKeywords.equalsIgnoreCase("")){
			AlertDialog.Builder emptyKeywordsAlert = new AlertDialog.Builder(NewReplyActivity.this);
			emptyKeywordsAlert
			.setMessage(R.string.dialogNoKeyword)
			.setCancelable(false)
			.setPositiveButton(R.string.dialogNoKeywordYes, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					proceedWithOutKeywords = true;
					dialog.cancel();
				}
			})
			.setNegativeButton(R.string.dialogNoKeywordNo, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					proceedWithOutKeywords = false;
					dialog.cancel();
					edtKeywords.requestFocus();
				}
			});
			
			emptyKeywordsAlert.create().show();
			
			if(proceedWithOutKeywords == true)
				return "all_clear";
			else
				return "";
		}*/
		else
			return "all_clear";
	}
	
}