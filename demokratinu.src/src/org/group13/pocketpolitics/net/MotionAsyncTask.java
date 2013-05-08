package org.group13.pocketpolitics.net;

import java.io.IOException;

import org.group13.pocketpolitics.model.Intressent;
import org.group13.pocketpolitics.model.Utskott;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

class MotionAsyncTask<OutClass> extends XmlAsyncTask< Void, Integer, OutClass> {

	private final static String URL = "http:/data.riksdagen.se/dokumentstatus/";
	private final static String xmlns = null;
	
	
	//private final ActivityNetInterface<OutClass> act;
	private final String dokId;
	private final String year;
	private final String docNum;
	
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
		
		this.year = year;
		this.docNum = docNum;
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
		
		String beteckning;
		String rm;
		Intressent[] intressenter;
		String textURL;
		boolean motion = true;
		
		String subtype;
		String title;
		String subtitle;
		Utskott uts;
		
		parser.next();
		parser.require(XmlPullParser.START_TAG, xmlns, "dokument");
		while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}
			
			if( "rm".equals(parser.getName())){
				rm = this.readString(parser, "rm", xmlns);
				if(!rm.equals(this.year)){
					Log.w(this.getClass().getSimpleName(), "Leif: Expected year "+year+", found "+rm);
					return null;
				}
				
			} else if( "beteckning".equals(parser.getName())){
				beteckning = this.readString(parser, "beteckning", xmlns);
				if(!beteckning.equals(this.docNum)){
					Log.w(this.getClass().getSimpleName(), "Leif: Expected beteckning "+docNum+", found "+beteckning);
					return null;
				}
			} else if( "typ".equals(parser.getName())){
				if("prop".equals( this.readString(parser, "typ", xmlns))){
					motion = false;
				} else {
					motion = true;
				}
			} else if( "subtyp".equals(parser.getName())){
				subtype = this.readString(parser, "subtyp", xmlns);
			} else if( "organ".equals(parser.getName())){
				String org = this.readString(parser, "organ", xmlns);
				uts = Utskott.valueOf(org);
			} else if( "titel".equals(parser.getName())){
				title = this.readString(parser, "titel", xmlns);
			} else if( "subtitel".equals(parser.getName())){
				subtitle = this.readString(parser, "subtitel", xmlns);
			} else if( "dokument_url_text".equals(parser.getName())){
				textURL = this.readString(parser, "dokument_url_text", xmlns);
			}
		}
		
		parser.require(XmlPullParser.START_TAG, xmlns, "dokaktivitet");
		skip(parser);
		
		parser.require(XmlPullParser.START_TAG, xmlns, "dokintressent");
		if(motion){
			intressenter = this.parseIntressenter(parser);
		} else {
			intressenter = null;
			skip(parser);
		}
		
		String utskottet;
		String kammaren;
		
		parser.require(XmlPullParser.START_TAG, xmlns, "dokforslag");
		if(motion){
			parser.next();
			parser.require(XmlPullParser.START_TAG, xmlns, "forslag");
			while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
				if(parser.getEventType()!=XmlPullParser.START_TAG){
					continue;
				}
				
				if("utskottet".equals(parser.getName())){
					utskottet = this.readString(parser, "utskottet", xmlns);
				} else if("kammaren".equals(parser.getName())){
					kammaren = this.readString(parser, "kammaren", xmlns);
				}
			}
			parser.require(XmlPullParser.END_TAG, xmlns, "forslag");
			
			parser.next();
			while(parser.getEventType() != XmlPullParser.END_TAG){
				String name = "";
				if(parser.getEventType()==XmlPullParser.START_TAG){
					name = parser.getName();
				}
				Log.w(this.getClass().getSimpleName(), "Leif: in @.readFeed(): Skipped a tag <"+name+">");
				skip(parser);
				parser.next();
			}
			
			parser.require(XmlPullParser.END_TAG, xmlns, "dokforslag");
		} else {
			skip(parser);
		}
		
		parser.require(XmlPullParser.START_TAG, xmlns, "dokuppgift");
		
		// TODO Auto-generated method stub
		return null;
	}
	
	private Intressent[] parseIntressenter(XmlPullParser parser){
		//TODO
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
		// ska bli 0 för år 2012
		
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
