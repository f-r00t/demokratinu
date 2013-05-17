package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.R.layout;
import org.group13.pocketpolitics.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;

public class RegistrationActivity extends Activity {

	// Registration values
	private String username;
	private String email;
	private String password;
	
	// UI references
	private EditText usernameView;
	private EditText emailView;
	private EditText passwordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		// Show the Up button in the action bar.
		setupActionBar();

		findViewById(R.id.buttonSendRegistration).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptRegister();
					}
				});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void attemptRegister() {
		// Reset errors.
		emailView.setError(null);
		passwordView.setError(null);
		usernameView.setError(null);

		// Store values at the time of the login attempt.
		email = emailView.getText().toString().trim();
		password = passwordView.getText().toString();
		username = usernameView.getText().toString().trim();

		boolean cancel = false;
		View focusView = null;
		
		// Check for valid username.
		if (TextUtils.isEmpty(username)) {
			usernameView.setError(getString(R.string.error_field_required));
			focusView = usernameView;
			cancel = true;
		}
		else if (username.length() < 2) {
			usernameView.setError(getString(R.string.error_invalid_username));
			focusView = usernameView;
			cancel = true;
		}
		
	    // Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			passwordView.setError(getString(R.string.error_field_required));
			focusView = passwordView;
			cancel = true;
		}
		else if (password.length() < 4) {
			passwordView.setError(getString(R.string.error_invalid_password));
			focusView = passwordView;
			cancel = true;
		}
		
		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			emailView.setError(getString(R.string.error_field_required));
			focusView = emailView;
			cancel = true;
		}
		else if (email.length() < 3) {
			emailView.setError(getString(R.string.error_invalid_email));
			focusView = emailView;
			cancel = true;
		}
		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// if (Retriever.register()) {
				Intent intent = new Intent(getApplicationContext(), FrontPageActivity.class);
				startActivity(intent);
				finish();
			//}
		}

	}

}
