package com.example.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pocketpolitics.model.Article;

public class ArticlesAsyncTask extends AsyncTask<QueryParam, Integer, String>{
	
	private static final String QUERY_1 = "http://data.riksdagen.se/sok/?sok=&rm=&typ=bet&doktyp=&subtyp=&titel=&talare=&bet=&tempbet=&datum=&tom=";
	private static final String QUERY_2 = "&nr=&fulltext=&planering=&org=&iid=&avd=&valkrets=&personstatus=&webbtv=&debattgrupp=&sort=rel&utformat=&exakt=&utdrag=&a=s#soktraff";
	private static int ARTICLES_PER_PAGE = 15;
	// QUERY_1 + "yyyy-mm-dd" + "&p=" +pageNo + "&sz="+ARTICLES_PER_PAGE + QUERY_2
	
	private ArtActivityInterface acti;
	
	@Override
	protected String doInBackground(QueryParam... arg0) {
		// TODO Auto-generated method stub
		return null;
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
	
	private List<Article> createArticles(QueryParam qpar){
		
		String url = QUERY_1 + qpar.date + "&p=" +qpar.page + "&sz="+ARTICLES_PER_PAGE + QUERY_2;
		InputStream instr = retrieveStream(url);
		
		
		return null;
	}
	
	private InputStream retrieveStream(String url){

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response;
		try {
			response = client.execute(request);
			final int statusCode = response.getStatusLine().getStatusCode();

			if(statusCode != HttpStatus.SC_OK){
				Log.w(this.getClass().getSimpleName(), "Error "+statusCode+" for URL "+url);
				return null;
			}
			HttpEntity responseEntity = response.getEntity();
			return responseEntity.getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			request.abort();
			Log.w(this.getClass().getSimpleName(), "Error for URL "+url, e);
			e.printStackTrace();
		}

		return null;
	}
}
