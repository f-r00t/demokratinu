package org.group13.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class VotesAsyncTask<Input, Output> extends XmlAsyncTask<Input, Integer, Output> {

	private final String URL = "data.riksdagen.se/utskottsforslag/";
	
	@Override
	protected Output doInBackground(Input... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Output retrieveVotes(String dokCode){
		String url = URL+dokCode;
		
		InputStream instr = retrieveStream(url);
		
		try {
			Output result = this.parseXml(instr);
			
			return result;
		} catch (XmlPullParserException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: Error in .parseXml(): XmlPullParserException",e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: Error in .parseXml(): IOException",e);
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected Output readFeed(XmlPullParser parser) throws XmlPullParserException,IOException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
