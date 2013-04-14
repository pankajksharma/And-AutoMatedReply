package com.engineerinme.pankaj.autoreply;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle extras = intent.getExtras();
		
		if ( extras != null )
        {
            Object[] smsextras = (Object[]) extras.get( "pdus" );
            
            String msgText = "", address = "";

            for ( int i = 0; i < smsextras.length; i++ )
            {
                SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);

                String strMsgBody = smsmsg.getMessageBody().toString();
                
                address = smsmsg.getOriginatingAddress();
                
                msgText += strMsgBody;
            }
            
            sendReplies(context, msgText, address);

        }
		
	}
	
	private void sendReplies(Context context, String msgText, String number){
		MySQLiteOpenHelper db = new MySQLiteOpenHelper(context);
		Cursor cr = MySQLiteOpenHelper.getAllEntries(db.getReadableDatabase());
		if(cr.isAfterLast())
			cr.moveToFirst();
		cr.moveToNext();
//		Toast.makeText(context, ""+cr.getCount(), Toast.LENGTH_LONG).show();
		while(!cr.isAfterLast()){
			String[] Keywords = cr.getString(cr.getColumnIndex("keywords")).split(",");
			String message = cr.getString(cr.getColumnIndex("message"));
			for(int i=0;i<Keywords.length;i++){
				String keyword = Keywords[i].replace("\n", "").toLowerCase();
				if((!keyword.equalsIgnoreCase(""))&& msgText.toLowerCase().contains(keyword)){
					sendMessage(context, message, number);
					break;
				}
			}
			cr.moveToNext();
		}
	}

	private void sendMessage(Context context, String reply, String number) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(number, null, reply, null, null);
		Toast.makeText(context, "Sent message \""+reply+"\" to "+number+".", Toast.LENGTH_SHORT).show();
	}
	
}