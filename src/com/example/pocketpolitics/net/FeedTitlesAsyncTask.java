package com.example.pocketpolitics.net;

import android.os.AsyncTask;

public abstract class FeedTitlesAsyncTask extends AsyncTask<String, Integer, String> {
	@Override
	protected String doInBackground(String... strings){
		return "Leif was here";
	}
	
	@Override
	protected abstract void onPreExecute();
	
	@Override
	protected abstract void onPostExecute(String result);
	
	@Override
	protected abstract void onCancelled(String result);
	
}
