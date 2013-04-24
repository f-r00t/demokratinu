package com.example.pocketpolitics.net.new_solution;

import android.os.AsyncTask;

/**
 * AsyncTask that retrieves short text of an Article from the Riksdag in the background. 
 * 
 * This class should be extended in an activity:
 * private class ATask extends TextAsyncTask ...
 * 
 * Use like this:
 * new ATask().execute(String year, String artikelId)
 * 
 * Update UI using the abstract methods: onPreExecute, onCancelled and onPostExecute
 * 
 * @author Leif
 *
 */
public abstract class TextAsyncTask extends AsyncTask<String, Integer, String> {
	
	@Override
	protected String doInBackground(String... arg0) {
		// eller anropa TextRetriever direkt?
		return Retriever.getInstance().getText(arg0[0], arg0[1]);
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress){
	}
	
	@Override
	protected abstract void onPreExecute();
	
	@Override
	protected abstract void onPostExecute(String result);
	
	@Override
	protected abstract void onCancelled(String result);
	
	
}
