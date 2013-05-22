package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.R.id;
import org.group13.pocketpolitics.R.layout;
import org.group13.pocketpolitics.R.menu;
import org.group13.pocketpolitics.model.user.Account;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	// Values for email and password at the time of the login attempt.
	private String email;
	private String password;

	// UI references.
	private EditText emailView;
	private EditText passwordView;
	private CheckBox stayLoggedInBox;
	
	private SharedPreferences prefs;
	private Intent intent;
	private Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*requestWindowFeature(Window.FEATURE_NO_TITLE);*/
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_login);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.pp_titlebar);
		
		prefs = this.getSharedPreferences("org.group13.pocketpolitics", 0);
		editor = prefs.edit();
		

		if (prefs.getBoolean("org.group13.pocketpolitics.stayloggedin", false)) {// if StayLoggedIn
			
			email = prefs.getString("org.group13.pocketpolitics.email", "");
			password = prefs.getString("org.group13.pocketpolitics.password", "");
			
			//if (SomeNetClass.getInstance.authenticate(this, email, pass)) {
			
				intent = new Intent(getApplicationContext(), FrontPageActivity.class);
				startActivity(intent);
				finish();
			//}
		}
		


		// Set up the login form.
		emailView = (EditText) findViewById(R.id.email);

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
		
		// Goes to registration form.
		
		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
						startActivity(intent);
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
		emailView.setError(null);
		passwordView.setError(null);

		// Store values at the time of the login attempt.
		email = emailView.getText().toString();
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
		if (TextUtils.isEmpty(email)) {
			emailView.setError(getString(R.string.error_field_required));
			focusView = emailView;
			cancel = true;
		} else if (email.length() < 3) {
			emailView.setError(getString(R.string.error_invalid_email));
			focusView = emailView;
			cancel = true;
		}
*/
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			//authenticate();
			
			stayLoggedInBox = (CheckBox) this.findViewById(R.id.stay_logged_in_checkbox);
			
			if (stayLoggedInBox.isChecked()) {
				editor.putBoolean("org.group13.pocketpolitics.stayloggedin", true);
				editor.putString("org.group13.pocketpolitics.email", email);
				editor.putString("org.group13.pocketpolitics.password", password);
			}
			
			Intent intent = new Intent(getApplicationContext(), FrontPageActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
