package com.engineerinme.pankaj.autoreply;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	
	Button btnPrevReplies, btnNewReply;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindElements();
		
	}
	
	private void bindElements(){
		btnPrevReplies = (Button)findViewById(R.id.btnPrevReplies);
		btnNewReply = (Button)findViewById(R.id.btnNewReply);
		
		btnPrevReplies.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				Intent prevRepliesIntent = new Intent();
				System.out.print(getString(R.string.prevRepliesActivity));
				prevRepliesIntent.setClassName(getPackageName(), getPackageName()+"."+getString(R.string.prevRepliesActivity));
				startActivity(prevRepliesIntent);
			}
		});
		
		btnNewReply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newReplyIntent = new Intent();
				newReplyIntent.setClassName(getPackageName(), getPackageName()+"."+getString(R.string.newReplyActivity));
				startActivity(newReplyIntent);
			}
		});
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/

}
