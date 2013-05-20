package org.group13.pocketpolitics.net.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.group13.pocketpolitics.model.user.Account;

import android.os.AsyncTask;

class PostAsyncTask extends AsyncTask<Void, Integer, String> {
	
	private Account user;
	private String url;
	private ServerInterface act;
	
	PostAsyncTask(ServerInterface act, Account user, ServerUrl surl){
		this.user = user;
		this.url = surl.getUrl();
		this.act = act;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		HttpResponse r = post(new ArrayList<NameValuePair>());
		return r.getEntity().toString();
	}
	
	@Override
	protected void onPostExecute(String msg){
		act.messageReturned(msg);
	}
	
	protected String url(){
		return url;
	}
	
	/**<p>Calls the url specified in method url(). Adds user data to the POST.
	 * <p>Källa: http://www.androidsnippets.com/executing-a-http-post-request-with-httpclient
	 * @param data List of data posted in the request other than user data
	 * @return
	 */
	protected HttpResponse post(List<NameValuePair> data){
		data.add(new BasicNameValuePair("email",user.getEmail()));
		data.add(new BasicNameValuePair("user",user.getUsername()));
		data.add(new BasicNameValuePair("pass",user.getPassword()));
		
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
