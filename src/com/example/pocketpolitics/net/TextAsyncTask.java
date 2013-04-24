package com.example.pocketpolitics.net;

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
class TextAsyncTask extends AsyncTask<String, Integer, String> {
	
	TextAsyncTask(){
		super();
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		// eller anropa TextRetriever direkt?
		return Retriever.getInstance().getText(arg0[0], arg0[1]);
	}

	@Override
	protected void onPreExecute(){
		
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress){
		
	}
	
	@Override
	protected void onPostExecute(String result){
		
	}
	
	@Override
	protected void onCancelled(String result){
		
	}
	
	
}
