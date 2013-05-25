package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.Moprosition;
import org.group13.pocketpolitics.net.Connected;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

public class MoprositionActivity extends Activity implements ActivityNetInterface<Moprosition>{
	public static final String MOPR_YEAR_SENT = "org.group13.pocketpolitics.control.MoprositionActivity.sent_year";
	public static final String MOPR_ID_SENT = "org.group13.pocketpolitics.control.MoprositionActivity.sent_id";
	
	private String year;
	private String id;
	private Moprosition mopr;
	
	private WebView webView;
	
	private View progressBar;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moprosition);
		
		setupActionBar();
		
		this.progressBar = findViewById(R.id.progressBarContainer);
		this.webView = (WebView) findViewById(R.id.moprosition_web_view);
		
		this.mopr = null;
		this.year = getIntent().getStringExtra(MoprositionActivity.MOPR_YEAR_SENT);
		this.id = getIntent().getStringExtra(MoprositionActivity.MOPR_ID_SENT);
		
		if(isMotion(this.id)){
			setTitle(getString(R.string.label_activity_motion));
		} else {
			setTitle(getString(R.string.label_activity_proposition));
		}
		
		orderMoprosition();
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
		case android.R.id.home: // The up-button
			// finish() to jump to the previous activity. We can't
			// return to it the normal way because there is more than one
			// possible parent activity as long as the manifest is aware.
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private static boolean isMotion(String id){
		boolean motion = false;
		if(id.matches("\\D+\\d*")){
			motion = true;
		}		
		return motion;
	}
	
	/**
	 * Order Moprosition, ex 2012/13:Ub5
	 * @param year ex above: "2012/13"
	 * @param id ex above: "Ub5"
	 */
	private void orderMoprosition(){
		if(Connected.isConnected(this)){
			Retriever.retrieveMoprosition(this, this.year, this.id);
		}
	}

	@Override
	public void onPreExecute() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// ignore
		
	}

	@Override
	public void onSuccess(Moprosition result) {
		this.mopr = result;
		
		// this is also a net-operation, show progressindicator until text is displayed
		webView.loadUrl(mopr.getTextURL());
		progressBar.setVisibility(View.GONE);
		
	}

	@Override
	public void onFailure(String message) {
		Log.w(this.getClass().getSimpleName(), "PocketDebug: Retrieve moprosition failed: "+message);
		progressBar.setVisibility(View.GONE);
		
		webView.loadData("<p>Hämtningen misslyckades.</p>", "text/html", "UTF-8");
	}

}
