package org.group13.pocketpolitics.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.group13.pocketpolitics.model.user.Account;

import android.os.AsyncTask;
import android.util.Log;

class PostAsyncTask extends AsyncTask<Void, Integer, HttpEntity> {
	
	private final Account user;
	private final ServerOperation oper;
	private final ServerInterface act;
	
	PostAsyncTask(ServerInterface act, Account user, ServerOperation surl){
		this.user = user;
		this.oper = surl;
		this.act = act;
	}
	
	@Override
	protected HttpEntity doInBackground(Void... params) {
		HttpResponse r = post(new ArrayList<NameValuePair>());
		return r.getEntity();
	}
	
	@Override
	protected void onPostExecute(HttpEntity msg){
		List<String> listr = new ArrayList<String>();
		BufferedReader brd=null;
		try {
			InputStream instr = msg.getContent();
			brd = new BufferedReader(new InputStreamReader(instr));
			
			String line;
			while((line = brd.readLine())!=null){
				listr.add(line);
			}
			
		} catch (IllegalStateException e) {
			Log.w(this.getClass().getSimpleName(), "PocketDebug: in onPostExecute(): Illegal state exception"+e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.w(this.getClass().getSimpleName(), "PocketDebug: in onPostExecute(): IOexception"+e);
			e.printStackTrace();
		} finally {
			if(brd!=null){
				try {
					brd.close();
				} catch (IOException e) {
					Log.w(this.getClass().getSimpleName(), "PocketDebug: in onPostExecute(): Failed at closing reader! Possible resource leak");
					e.printStackTrace();
				}
			}
		}
		respond(listr);
	}
	
	private void respond(List<String> listr){
		switch(this.oper){
		case Register:
			act.messageReturned(listr);
			break;
		case Authenticate:
			act.messageReturned(listr);
			break;
		}
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
		HttpPost hpost = new HttpPost( this.oper.getUrl() );
		
		try {
			hpost.setEntity(new UrlEncodedFormEntity(data));

			HttpResponse response = hclient.execute(hpost);
			final int statusCode = response.getStatusLine().getStatusCode();

			if(statusCode != HttpStatus.SC_OK){
				Log.w(this.getClass().getSimpleName(), "PocketDebug: in .post(): Error "+statusCode+" for URL "+this.oper.getUrl());
				return null;
			}
			
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
