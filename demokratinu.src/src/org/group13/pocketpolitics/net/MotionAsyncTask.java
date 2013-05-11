package org.group13.pocketpolitics.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.model.Proposer;
import org.group13.pocketpolitics.model.Moprosition;
import org.group13.pocketpolitics.model.Motion;
import org.group13.pocketpolitics.model.Proposition;
import org.group13.pocketpolitics.model.Committee;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

class MotionAsyncTask extends XmlAsyncTask< Void, Moprosition> {

	private final static String URL = "http://data.riksdagen.se/dokumentstatus/";
	private final static String xmlns = null;


	//private final ActivityNetInterface<Moprosition> act;
	private final String dokId;
	private final String year;
	private final String docNum;

	private class Document{
		String beteckning = null;
		String rm = null;
		String textURL = null;
		String subtype = null;
		String title = null;
		String subtitle = null;
		Committee uts = Committee.NULL;
		boolean motion = true;
	}

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

		parser.next();
		Document ddata = parseDokument(parser);
		if(ddata == null){
			return null;
		}
		
		List<Proposer> intressenter = null;

		while(parser.next()!=XmlPullParser.START_TAG);
		parser.require(XmlPullParser.START_TAG, xmlns, "dokaktivitet");
		skip(parser);

		while(parser.next()!=XmlPullParser.START_TAG);
		parser.require(XmlPullParser.START_TAG, xmlns, "dokintressent");

		if(ddata.motion){
			intressenter = this.parseIntressenter(parser);
		} else {
			skip(parser);
		}

		while(parser.next()!=XmlPullParser.START_TAG);
		String[] strs = parseDokforslag(parser, ddata.motion);
		
		String utskottet = strs[0];
		String kammaren = strs[1];
		

		while(parser.next()!=XmlPullParser.START_TAG);
		parser.require(XmlPullParser.START_TAG, xmlns, "dokuppgift");

		Moprosition ret;
		if(ddata.motion){
			ret = new Motion(intressenter, ddata.textURL, ddata.rm, ddata.beteckning, ddata.subtype, ddata.title, ddata.subtitle, ddata.uts, kammaren, utskottet);
		} else {
			ret = new Proposition(ddata.textURL, ddata.rm, ddata.beteckning, ddata.title, ddata.uts);
		}

		retrieveText(ret);

		return ret;
	}

	/**
	 * 
	 * @param parser
	 * @param motion
	 * @return String [utskottet, kammaren]
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	private String[] parseDokforslag(XmlPullParser parser, boolean motion) throws XmlPullParserException, IOException{
		String[] strs = new String[2];

		parser.require(XmlPullParser.START_TAG, xmlns, "dokforslag");

		if(motion){
			while(parser.next()!=XmlPullParser.START_TAG);
			parser.require(XmlPullParser.START_TAG, xmlns, "forslag");
			while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
				if(parser.getEventType()!=XmlPullParser.START_TAG){
					continue;
				}

				if("utskottet".equals(parser.getName())){
					strs[0] = this.readString(parser, "utskottet", xmlns);
				} else if("kammaren".equals(parser.getName())){
					strs[1] = this.readString(parser, "kammaren", xmlns);
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

		return strs;
	}

	private Document parseDokument(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "dokument");

		Document ddata = new Document();

		while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}

			if( "rm".equals(parser.getName())){
				ddata.rm = this.readString(parser, "rm", xmlns);
				if(!ddata.rm.equals(this.year)){
					Log.w(this.getClass().getSimpleName(), "Leif: Expected year "+year+", found "+ddata.rm);
					return null;
				}

			} else if( "beteckning".equals(parser.getName())){
				ddata.beteckning = this.readString(parser, "beteckning", xmlns);
				if(!ddata.beteckning.equals(this.docNum)){
					Log.w(this.getClass().getSimpleName(), "Leif: Expected beteckning "+docNum+", found "+ddata.beteckning);
					return null;
				}
			} else if( "typ".equals(parser.getName())){
				if("prop".equals( this.readString(parser, "typ", xmlns))){
					ddata.motion = false;
				} else {
					ddata.motion = true;
				}
			} else if( "subtyp".equals(parser.getName())){
				ddata.subtype = this.readString(parser, "subtyp", xmlns);
			} else if( "organ".equals(parser.getName())){
				String org = this.readString(parser, "organ", xmlns);
				if(!ddata.motion){
					org+="U";
				}
				ddata.uts = Committee.findUtskott(org);
			} else if( "titel".equals(parser.getName())){
				ddata.title = this.readString(parser, "titel", xmlns);
			} else if( "subtitel".equals(parser.getName())){
				ddata.subtitle = this.readString(parser, "subtitel", xmlns);
			} else if( "dokument_url_html".equals(parser.getName())){
				ddata.textURL = this.readString(parser, "dokument_url_html", xmlns);
			} else {
				skip(parser);
			}
		}

		return ddata;
	}

	/**<p>Retrieves html-text for a motion or proposition.
	 * <p>Källa: http://www.mkyong.com/java/how-to-convert-inputstream-to-string-in-java/
	 * @param mop
	 * @return
	 */
	private boolean retrieveText(Moprosition mop){

		BufferedReader bred = new BufferedReader( new InputStreamReader( retrieveStream(mop.getTextURL())));
		StringBuilder sbuild = new StringBuilder();

		try {
			String line;
			while( (line = bred.readLine()) != null){
				sbuild.append(line);
			}
		} catch (IOException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: in retrieveText(): Failed while reading!");
			e.printStackTrace();
			return false;
		} finally {
			if(bred!=null){
				try {
					bred.close();
				} catch (IOException e) {
					Log.w(this.getClass().getSimpleName(), "Leif: in retrieveText(): Failed at closing reader! Possible resource leak");
					e.printStackTrace();
					return false;
				}
			}
		}

		mop.setText(sbuild.toString());

		return true;
	}

	private List<Proposer> parseIntressenter(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "dokintressent");

		List<Proposer> listr = new ArrayList<Proposer>();

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

			listr.add(new Proposer(name, party, role, personId));
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
