package org.group13.pocketpolitics.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.model.Intressent;
import org.group13.pocketpolitics.model.Moprosition;
import org.group13.pocketpolitics.model.Motion;
import org.group13.pocketpolitics.model.Proposition;
import org.group13.pocketpolitics.model.Utskott;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

class MotionAsyncTask extends XmlAsyncTask< Void, Integer, Moprosition> {

	private final static String URL = "http://data.riksdagen.se/dokumentstatus/";
	private final static String xmlns = null;
	
	
	//private final ActivityNetInterface<Moprosition> act;
	private final String dokId;
	private final String year;
	private final String docNum;
	
	/**
	 * 
	 * @param act
	 * @param year	"2012/13"
	 * @param code	"Ub354" or "73"
	 */
	MotionAsyncTask(ActivityNetInterface<Moprosition> act, String year, String docNum){
		super(act);
		//this.act=act;
		this.dokId = translate(year, docNum);
		
		this.year = year;
		this.docNum = docNum;
	}
	
	
	@Override
	protected Moprosition doInBackground(Void... arg0) {
		String url = URL+this.dokId;
		return retrieve(url, null);
	}

	@Override
	protected Moprosition readFeed(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		
		parser.require(XmlPullParser.START_TAG, xmlns, "dokumentstatus");
		
		String beteckning = null;
		String rm = null;
		List<Intressent> intressenter = null;
		String textURL = null;
		boolean motion = true;
		
		String subtype = null;
		String title = null;
		String subtitle = null;
		Utskott uts = Utskott.NULL;
		
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
				if(!motion){
					org+="U";
				}
				uts = Utskott.findUtskott(org);
			} else if( "titel".equals(parser.getName())){
				title = this.readString(parser, "titel", xmlns);
			} else if( "subtitel".equals(parser.getName())){
				subtitle = this.readString(parser, "subtitel", xmlns);
			} else if( "dokument_url_text".equals(parser.getName())){
				textURL = this.readString(parser, "dokument_url_text", xmlns);
			} else {
				skip(parser);
			}
		}
		
		while(parser.next()!=XmlPullParser.START_TAG);
		parser.require(XmlPullParser.START_TAG, xmlns, "dokaktivitet");
		skip(parser);
		
		while(parser.next()!=XmlPullParser.START_TAG);
		parser.require(XmlPullParser.START_TAG, xmlns, "dokintressent");
		
		if(motion){
			intressenter = this.parseIntressenter(parser);
		} else {
			skip(parser);
		}
		
		String utskottet = null;
		String kammaren = null;
		
		while(parser.next()!=XmlPullParser.START_TAG);
		parser.require(XmlPullParser.START_TAG, xmlns, "dokforslag");
		
		if(motion){
			while(parser.next()!=XmlPullParser.START_TAG);
			parser.require(XmlPullParser.START_TAG, xmlns, "forslag");
			while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
				if(parser.getEventType()!=XmlPullParser.START_TAG){
					continue;
				}
				
				if("utskottet".equals(parser.getName())){
					utskottet = this.readString(parser, "utskottet", xmlns);
				} else if("kammaren".equals(parser.getName())){
					kammaren = this.readString(parser, "kammaren", xmlns);
				} else {
					skip(parser);
				}
			}
			parser.require(XmlPullParser.END_TAG, xmlns, "forslag");
			
			while(parser.next()!=XmlPullParser.END_TAG);
			
			parser.require(XmlPullParser.END_TAG, xmlns, "dokforslag");
		} else {
			skip(parser);
		}
		
		while(parser.next()!=XmlPullParser.START_TAG);
		parser.require(XmlPullParser.START_TAG, xmlns, "dokuppgift");
		
		
		if(motion){
			return new Motion(intressenter, textURL, rm, beteckning, subtype, title, subtitle, uts, kammaren, utskottet);
		}
		return new Proposition(textURL, rm, beteckning, title, uts);
	}
	
	private List<Intressent> parseIntressenter(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "dokintressent");
		
		List<Intressent> listr = new ArrayList<Intressent>();
		
		while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}
			
			parser.require(XmlPullParser.START_TAG, xmlns, "intressent");
			//Log.i(this.getClass().getSimpleName(), "Leif: in parseIntressenter: entering <intressent>");
			
			String personId = null;
			String name = null;
			String party = null;
			String role = null;
			
			while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
				if(parser.getEventType()!=XmlPullParser.START_TAG){
					continue;
				}
				
				if("roll".equals(parser.getName())){
					role = this.readString(parser, "roll", xmlns);
				} else if("namn".equals(parser.getName())){
					name = this.readString(parser, "namn", xmlns);
				} else if("partibet".equals(parser.getName())){
					party = this.readString(parser, "partibet", xmlns);
				} else if("intressent_id".equals(parser.getName())){
					personId = this.readString(parser, "intressent_id", xmlns);
				} else {
					skip(parser);
				}
			}
			
			//Log.i(this.getClass().getSimpleName(), "Leif: in parseIntr(): "+name);
			
			listr.add(new Intressent(name, party, role, personId));
		}
		
		parser.require(XmlPullParser.END_TAG, xmlns, "dokintressent");
		return listr;
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
