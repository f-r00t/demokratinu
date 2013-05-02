package org.group13.pocketpolitics.net;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class MotionAsyncTask<OutClass> extends XmlAsyncTask< Void, Integer, OutClass> {

	private final ActivityNetInterface<OutClass> act;
	
	/**
	 * 
	 * @param act
	 * @param year	"2012/13"
	 * @param code	"Ub354" or "73"
	 */
	MotionAsyncTask(ActivityNetInterface<OutClass> act, String year, String docnum){
		this.act=act;
		
	}
	
	
	@Override
	protected OutClass readFeed(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected OutClass doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
