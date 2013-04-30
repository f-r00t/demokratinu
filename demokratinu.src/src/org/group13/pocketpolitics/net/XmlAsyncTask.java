package org.group13.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;

public abstract class XmlAsyncTask<I, U, O> extends AsyncTask<I, U, O> {

	
	protected InputStream retrieveStream(String url){

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
			Log.e(this.getClass().getSimpleName(), "Error ClientProtocolException for URL "+url, e);
			e.printStackTrace();
		} catch (IOException e) {
			request.abort();
			Log.e(this.getClass().getSimpleName(), "Error IOException for URL "+url, e);
			e.printStackTrace();
		}

		return null;
	}
	
	protected String readString(XmlPullParser parser, String tag, String xmlns) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, tag);
		String result="";
		if(parser.next() == XmlPullParser.TEXT){
			result = parser.getText();
			parser.nextTag();
			//Log.i(this.getClass().getSimpleName(), "Leif: in readString(...) reading <"+tag+">, finds "+result);
		}
		else{
			//Log.w(this.getClass().getSimpleName(), "Leif: in readString(): Didn't find text for <"+tag+">");
			//throw new IllegalStateException();
		}
		parser.require(XmlPullParser.END_TAG, xmlns, tag);
		return result;
	}

	protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException{
		if(parser.getEventType() != XmlPullParser.START_TAG){
			throw new IllegalStateException();
		}
		int depth = 1;
		while(depth != 0){
			switch(parser.next()){
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}
