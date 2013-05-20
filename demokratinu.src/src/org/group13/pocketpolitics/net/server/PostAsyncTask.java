package org.group13.pocketpolitics.net.server;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public abstract class PostAsyncTask<InClass, OutClass> extends AsyncTask<Void, Integer, OutClass> {
	
	protected abstract String url();
	
	/**
	 * Källa: http://www.androidsnippets.com/executing-a-http-post-request-with-httpclient
	 * @param data List of data posted in the request
	 * @return
	 */
	protected HttpResponse post(List<NameValuePair> data){
		HttpClient hclient = new DefaultHttpClient();
		HttpPost hpost = new HttpPost( url() );
		
		try {
			hpost.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response = hclient.execute(hpost);
			return response;
		} catch (ClientProtocolException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
