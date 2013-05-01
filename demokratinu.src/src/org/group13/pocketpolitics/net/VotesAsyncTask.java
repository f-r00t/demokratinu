package org.group13.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.net.PartyVote;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class VotesAsyncTask<Input> extends XmlAsyncTask<Input, Integer, VotesResult> {

	private final String URL = "data.riksdagen.se/utskottsforslag/";
	
	private final String xmlns = null;
	
	private final String dokCode;
	private final String motionCode;
	
	VotesAsyncTask(String dokCode, String motionCode){
		this.dokCode = dokCode;
		this.motionCode = motionCode;
	}
	
	@Override
	protected VotesResult doInBackground(Input... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private VotesResult retrieveVotes(){
		String url = URL+dokCode;
		
		InputStream instr = retrieveStream(url);
		
		try {
			VotesResult result = this.parseXml(instr);
			
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
	protected VotesResult readFeed(XmlPullParser parser) throws XmlPullParserException,IOException {

		parser.require(XmlPullParser.START_TAG, xmlns, "utskottsforslag");
		
		parser.next();
		if(parser.getName()=="dokument"){
			if (!parseDokument(parser)){
				Log.w(this.getClass().getSimpleName(), "Leif: Wrong dokument retrieved!");
				return null;
			}
		} else {
			Log.e(this.getClass().getSimpleName(), "Leif: Error, tag <dokument> expected, got <" + parser.getName()+">");
			return null;
		}
		
		VotesResult out=null;
		parser.next();
		if(parser.getName()=="dokutskottsforslag"){
			
			while(parser.next()!=XmlPullParser.END_TAG){
				if(out==null){
					out = parseForslag(parser);
				} else {
					this.skip(parser);
				}
				
			}
		} else {
			Log.e(this.getClass().getSimpleName(), "Leif: Error, tag <dokutskottsforslag> expected, got <" + parser.getName()+">");
			return null;
		}
		
		
		
		return out;
	}
	
	protected VotesResult parseForslag(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "utskottsforslag");
		
		boolean found = false;
		
		String voteXmlUrl = null;
		String motParti = null;
		String motForslag = null;
		String vinnare = null;
		List<PartyVote> partyVotes = null;
		
		while(parser.next()!=XmlPullParser.END_TAG){
			String name = parser.getName();
			
			if(name.equals("rubrik")){
				// TODO
			} else if(name.equals("forslag")){
				found = name.contains(this.motionCode);
			} else if(found){
				if(name.equals("motforslag_nummer")){
					motForslag = this.readString(parser, "motforslag_nummer", xmlns);
				} else if(name.equals("motforslag_partier")){
					motParti = this.readString(parser, "motforslag_partier", xmlns);
				} else if(name.equals("votering_url_xml")){
					voteXmlUrl = this.readString(parser, "votering_url_xml", xmlns);
				} else if(name.equals("vinnare")){
					vinnare = this.readString(parser, "vinnare", xmlns);
				} else if(name.equals("votering_sammanfattning_html")){
					partyVotes = this.parseVotering(parser);
				} else {
					this.skip(parser);
				}
			}
			else {
				this.skip(parser);
			}
		}
		
		if(found){
			VotesResult res = new VotesResult(this.motionCode, voteXmlUrl, motParti, motForslag, vinnare, partyVotes);
			return res;
		}
		return null;
	}
	
	protected List<PartyVote> parseVotering(XmlPullParser parser) throws XmlPullParserException, IOException{
		
		parser.require(XmlPullParser.START_TAG, xmlns, "votering_sammanfattning_html");
		parser.nextTag();
		parser.require(XmlPullParser.START_TAG, xmlns, "table");
		parser.nextTag();
		
		parser.require(XmlPullParser.START_TAG, xmlns, "tr");
		String attr = parser.getAttributeValue(xmlns, "class");
		if(!"sakfragan".equals(attr)){
			Log.w(this.getClass().getSimpleName(), "Leif: in .parseVotering(): <tr class=\"sakfragan\"> expected, found class="+attr);
		}
		this.skip(parser);
		
		parser.require(XmlPullParser.START_TAG, xmlns, "tr");
		attr = parser.getAttributeValue(xmlns, "class");
		if(!"sakfragan".equals(attr)){
			Log.w(this.getClass().getSimpleName(), "Leif: in .parseVotering(): <tr class=\"vottabellrubrik\"> expected, found class="+attr);
		}
		this.skip(parser);
		
		List<PartyVote> partyVotes = new ArrayList<PartyVote>();
		
		while(parser.next()!=XmlPullParser.END_TAG){
			parser.require(XmlPullParser.START_TAG, xmlns, "tr");
			String party=null;
			int yes = -1, no = -1, neutral = -1, absent = -1;
			
			while(parser.next()!=XmlPullParser.END_TAG){
				parser.require(XmlPullParser.START_TAG, xmlns, "td");
				attr = parser.getAttributeValue(xmlns, "class");
				if (attr.equals("parti")){
					party = this.readString(parser, "td", xmlns);
				} else if(attr.equals("rost_ja")){
					yes = Integer.parseInt(this.readString(parser, "td", xmlns));
				} else if(attr.equals("rost_nej")){
					no = Integer.parseInt(this.readString(parser, "td", xmlns));
				} else if(attr.equals("rost_avstar")){
					neutral = Integer.parseInt(this.readString(parser, "td", xmlns));
				} else if (attr.equals("rost_franvarande")){
					absent = Integer.parseInt(this.readString(parser, "td", xmlns));
				} else {
					Log.w(this.getClass().getSimpleName(), "Leif: in .parseVotering(): attribute not recognized: <td class=\""+attr+"\">");
				}
			}
			
			parser.require(XmlPullParser.END_TAG, xmlns, "tr");
			partyVotes.add(new PartyVote(party, yes, no, neutral, absent));
		}
		parser.require(XmlPullParser.END_TAG, xmlns, "table");
		parser.nextTag();
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, xmlns, "votering_sammanfattning_html");
		parser.nextTag();
		parser.require(XmlPullParser.START_TAG, xmlns, "votering_url_xml");
		return partyVotes;
	}
	
	protected boolean parseDokument(XmlPullParser parser) throws XmlPullParserException, IOException{
		
		boolean correct = false;
		parser.require(XmlPullParser.START_TAG, xmlns, "dokument");
		while(parser.next()!=XmlPullParser.END_TAG){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals("dok_id")){
				correct = this.readString(parser, "dok_id", xmlns).equals(this.dokCode);
			} else {
				skip(parser);
			}
		}
		
		return correct;
	}
}
