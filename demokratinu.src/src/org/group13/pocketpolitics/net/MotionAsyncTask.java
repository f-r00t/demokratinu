package org.group13.pocketpolitics.net;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

class MotionAsyncTask<OutClass> extends XmlAsyncTask< Void, Integer, OutClass> {

	private final static String URL = "http:/data.riksdagen.se/dokumentstatus/";
	private final static String xmlns = null;
	
	//private final ActivityNetInterface<OutClass> act;
	private final String dokId;
	
	/**
	 * 
	 * @param act
	 * @param year	"2012/13"
	 * @param code	"Ub354" or "73"
	 */
	MotionAsyncTask(ActivityNetInterface<OutClass> act, String year, String docNum){
		super(act);
		//this.act=act;
		this.dokId = translate(year, docNum);
	}
	
	
	@Override
	protected OutClass doInBackground(Void... arg0) {
		String url = URL+this.dokId;
		return retrieve(url, null);
	}

	@Override
	protected OutClass readFeed(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		
		parser.require(XmlPullParser.START_TAG, xmlns, "dokumentstatus");
		
		// TODO Auto-generated method stub
		return null;
	}
	
	protected static String translate(String year, String docNum){
		String ret = "";
		
		int slash = year.indexOf("/");
		int iyear;
		if(slash<0){
			iyear = Integer.parseInt(year);
		} else {
			String[] temp =year.split("/",2);
			iyear = Integer.parseInt(temp[0]);
			int pyear = Integer.parseInt(temp[1]);
			
			if( pyear != (iyear+1)%100){
				Log.e(MotionAsyncTask.class.getSimpleName(), "Leif: Years entered don't match! "+year);
				return null;
			}
		}
		
		int first = (iyear+4)/36;	//2012 -> 56
		ret+= (char)(first-56+'H');
		
		int sec = (iyear-1976)%36;
		// ska bli 0 f�r �r 2012
		
		if(sec<10){
			ret+= sec;
		} else {
			ret+=(char) ('A' + sec-10);
		}
		
		if( docNum.matches("\\d+") ){
			//proposition
			ret += "03";
		} else {
			//motion
			ret += "02";
		}
		ret+=docNum;
		
		//Log.i(MotionAsyncTask.class.getSimpleName(), "Leif: Returning "+ret+" for "+year+":"+docNum);
		return ret;
	}

}
