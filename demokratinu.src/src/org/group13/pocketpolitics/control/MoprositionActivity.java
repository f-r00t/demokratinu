package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.model.riksdag.Moprosition;
import org.group13.pocketpolitics.net.Connected;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MoprositionActivity extends Activity implements ActivityNetInterface<Moprosition>{
	public static final String MOPR_YEAR_SENT = "org.group13.pocketpolitics.control.MoprositionActivity.sent_year";
	public static final String MOPR_ID_SENT = "org.group13.pocketpolitics.control.MoprositionActivity.sent_id";
	
	private String year;
	private String id;
	private Moprosition mopr;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.mopr = null;
		this.year = getIntent().getStringExtra(MoprositionActivity.MOPR_YEAR_SENT);
		this.id = getIntent().getStringExtra(MoprositionActivity.MOPR_ID_SENT);
		
		orderMoprosition();
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
		// TODO snurrande hjul
		
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// ignore
		
	}

	@Override
	public void onSuccess(Moprosition result) {
		this.mopr = result;
		// TODO snurrande hjul
		
	}

	@Override
	public void onFailure(String message) {
		Log.w(this.getClass().getSimpleName(), "PocketDebug: Retrieve moprosition failed: "+message);
		// TODO snurrande hjul
		
	}

}
