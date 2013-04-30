package org.group13.pocketpolitics;

import org.group13.pocketpolitics.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	// Values for email and password at the time of the login attempt.
	private String username;
	private String password;

	// UI references.
	private EditText usernameView;
	private EditText passwordView;

	// 
	private SharedPreferences prefs;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		
		prefs = this.getSharedPreferences("com.example.pocketpolitics", 0);
		
		//Editor editor = prefs.edit();
		//editor.putBoolean("com.example.pocketpolitics.stayloggedin", false);
		//editor.apply();
/*
		if (prefs.getBoolean("com.example.pocketpolitics.stayloggedin", false)) {// if StayLoggedIn
			
			String uname = prefs.getString("com.example.politics.username", "");
			String pass = prefs.getString("com.example.pocketpolitics.password", "");
			
			if (SomeNetClass.getInstance.authenticate(this, uname, pass)) {
			
				intent = new Intent(getApplicationContext(), FrontPageActivity.class);
			}
		}
		startActivity(intent);
		finish();
*/

		// Set up the login form.
		usernameView = (EditText) findViewById(R.id.email);

		passwordView = (EditText) findViewById(R.id.password);
		passwordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {

		// Reset errors.
		usernameView.setError(null);
		passwordView.setError(null);

		// Store values at the time of the login attempt.
		username = usernameView.getText().toString();
		password = passwordView.getText().toString();

		boolean cancel = false;
		View focusView = null;
/*
		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			passwordView.setError(getString(R.string.error_field_required));
			focusView = passwordView;
			cancel = true;
		} else if (password.length() < 4) {
			passwordView.setError(getString(R.string.error_invalid_password));
			focusView = passwordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(username)) {
			usernameView.setError(getString(R.string.error_field_required));
			focusView = usernameView;
			cancel = true;
		} else /if (username.length() < 3) {
			usernameView.setError(getString(R.string.error_invalid_email));
			focusView = usernameView;
			cancel = true;
		}
*/
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			//authenticate();
			Intent intent = new Intent(getApplicationContext(), FrontPageActivity.class);
			startActivity(intent);
		}
	}
}
