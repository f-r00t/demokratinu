package com.example.pocketpolitics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;

public class MainActivity extends Activity {

	SharedPreferences prefs;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prefs = this.getSharedPreferences("com.example.pocketpolitics", 0);
		
		//Editor editor = prefs.edit();
		//editor.putBoolean("com.example.pocketpolitics.stayloggedin", false);
		//editor.apply();
		
		if (prefs.getBoolean("com.example.pocketpolitics.stayloggedin", false)) {// if StayLoggedIn
			/*
			String uname = prefs.getString("com.example.politics.username", "");
			String pass = prefs.getString("com.example.pocketpolitics.password", "");
			
			if (SomeNetClass.getInstance.authenticate(this, uname, pass)) {
			*/
				intent = new Intent(getApplicationContext(), FrontPageActivity.class);
			/*}
			else {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			}*/
		}
		else {
			intent = new Intent(getApplicationContext(), LoginActivity.class);
		}
		startActivity(intent);
		finish();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
