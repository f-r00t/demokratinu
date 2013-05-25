package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.user.Account;
import org.group13.pocketpolitics.model.user.ArticleData;
import org.group13.pocketpolitics.net.Connected;
import org.group13.pocketpolitics.net.server.ServerInterface;
import org.group13.pocketpolitics.net.server.Syncer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity implements ServerInterface{

	// Registration values
	private String username;
	private String email;
	private String password;
	private String passwordAgain;
	
	// UI references
	private EditText usernameView;
	private EditText emailView;
	private EditText passwordView;
	private EditText passwordAgainView;
	private View progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		// Show the Up button in the action bar.
		setupActionBar();

		progressBar = findViewById(R.id.progressBarContainer);
		
		usernameView = (EditText) findViewById(R.id.reg_username_field);
		emailView = (EditText) findViewById(R.id.reg_email_field);

		passwordView = (EditText) findViewById(R.id.reg_password_field);
		passwordAgainView = (EditText) findViewById(R.id.reg_password_again_field);
		passwordAgainView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.register || id == EditorInfo.IME_NULL) {
							attemptRegister();
							return true;
						}
						return false;
					}
				});
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Navigates up to the parent (in this case LoginActivity)
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
		passwordAgainView.setError(null);

		// Store values at the time of the login attempt.
		email = emailView.getText().toString().trim();
		password = passwordView.getText().toString();
		username = usernameView.getText().toString().trim();
		passwordAgain = passwordAgainView.getText().toString();

		boolean cancel = false;
		View focusView = null;
		
		// Check if passwords match.
		if (!passwordAgain.equals(password)) {
			passwordAgainView.setError(getString(R.string.passwords_dont_match));
			focusView = passwordAgainView;
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
		
		// Check for a valid email address, needs better check.
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
		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			if(Connected.isConnected(this)){
				progressBar.setVisibility(View.VISIBLE);
				Account.set(email, username, password);
				Syncer.register(this);
			} else {
				Toast.makeText(getApplicationContext(),
						getString(R.string.no_connection_found), Toast.LENGTH_LONG)
						.show();
			}
			
		}
	}

	

	@Override
	public void registrationReturned(boolean succeeded, boolean unameExists,
			boolean emailExists) {
		progressBar.setVisibility(View.GONE);
		
		if(succeeded){
			CheckBox checkbox = (CheckBox) this.findViewById(R.id.reg_stay_logged_in_checkbox);
			
			 if (checkbox.isChecked()) {
				 SharedPreferences prefs = this.getSharedPreferences("org.group13.pocketpolitics", Context.MODE_PRIVATE);
				 Editor editor = prefs.edit();
				 
				 editor.putBoolean("org.group13.pocketpolitics.stayloggedin", true);
				 editor.putString("org.group13.pocketpolitics.email", email);
				 editor.putString("org.group13.pocketpolitics.password", password);
				 editor.commit();
			 }
			
			Intent intent = new Intent(getApplicationContext(), FrontPageActivity.class);
			startActivity(intent);
			finish();
			
			Toast.makeText(getApplicationContext(),
					getString(R.string.successfully_registered), Toast.LENGTH_LONG)
					.show();
		} else {
			View focusView = null;
			if (emailExists) {
				this.emailView.setError(getString(R.string.email_taken));
				focusView = emailView;
			}
			if (unameExists) {
				this.usernameView.setError(getString(R.string.username_taken));
				focusView = usernameView;
			}
			focusView.requestFocus();
		}		
	}
	
	@Override
	public void operationFailed(String oper) {
		progressBar.setVisibility(View.GONE);
		
		Toast.makeText(getApplicationContext(),
				getString(R.string.register_operation_failed), Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void authenticateReturned(boolean succeded, String username) {
		// ignore
	}
	
	@Override
	public void postOpinionReturned(boolean succeded) {
		// ignore
	}

	@Override
	public void postCommentReturned(boolean succeded) {
		// ignore
	}

	@Override
	public void getArticleDataReturned(ArticleData data) {
		// ignore
	}

	@Override
	public void getOpinionsReturned(boolean succeded, int myOpinion,
			int totalLike, int totalDislike) {
		// ignore
		
	}
}
