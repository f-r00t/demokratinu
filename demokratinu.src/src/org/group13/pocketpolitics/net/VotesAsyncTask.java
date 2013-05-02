package org.group13.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class VotesAsyncTask extends XmlAsyncTask<Void, Integer, VotesResult> {

	private final String URL = "http://data.riksdagen.se/utskottsforslag/";
	
	private final String xmlns = null;
	
	private final String dokCode;
	private final String motionId;
	private final ActivityNetInterface<VotesResult> act;
	
	/**
	 * 
	 * @param act
	 * @param dokCode on type "H001UbU5"
	 * @param motionId on type "Ub354"
	 */
	VotesAsyncTask(ActivityNetInterface act, String dokCode, String motionId){
		this.dokCode = dokCode;
		this.motionId = motionId;
		this.act = act;
	}
	
	@Override
	protected void onPreExecute(){
		if(act!=null){
			act.onPreExecute();
		} else {
			Log.w(this.getClass().getSimpleName(), "Leif: in @.onPreExecute Activity is null");
		}
	}
	
	@Override
	protected VotesResult doInBackground(Void... params) {
		
		return retrieveVotes();
	}
	
	@Override
	protected void onPostExecute(VotesResult res){
		Retriever.threadFinished();
		if(act!=null){
			act.onSuccess(res);
		} else {
			Log.w(this.getClass().getSimpleName(), "Leif: in @.onPostExecute Activity is null");
		}
	}
	
	@Override
	protected void onCancelled(VotesResult res){
		Retriever.threadFinished();
		if(act!=null){
			act.onFailure("! Cancelled!");
		} else {
			Log.w(this.getClass().getSimpleName(), "Leif: in @.onCancelled Activity is null");
		}
		
	}
	
	private VotesResult retrieveVotes(){
		String url = URL+dokCode;
		// Log.i(this.getClass().getSimpleName(), "Leif: url = "+url);
		
		InputStream instr = retrieveStream(url);
		
		try {
			// Log.i(this.getClass().getSimpleName(), "Leif: start parsing xml...");
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
		// Log.i(this.getClass().getSimpleName(), "Leif: entering <utskottsforslag>");
		
		parser.next();
		if(parser.getName().equals("dokument")){
			// Log.i(this.getClass().getSimpleName(), "Leif: entering <dokument>");
			if (!parseDokument(parser)){
				Log.w(this.getClass().getSimpleName(), "Leif: Wrong dokument retrieved!");
				return null;
			}
		} else {
			Log.e(this.getClass().getSimpleName(), "Leif: Error, tag <dokument> expected, got <" + parser.getName()+">");
			return null;
		}
		
		VotesResult out=null;
		
		while(parser.next()!=XmlPullParser.START_TAG){
			//Log.w(this.getClass().getSimpleName(), "Leif: in readFeed: skipped a tag of type: "+parser.getEventType());
			if(parser.getEventType()==XmlPullParser.END_TAG){
				Log.w(this.getClass().getSimpleName(), "Leif: in readFeed: skipped an END_TAG!");
			}
		}
		
		parser.require(XmlPullParser.START_TAG, xmlns, "dokutskottsforslag");
		
		if(parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals("dokutskottsforslag")){
			
			// Log.i(this.getClass().getSimpleName(), "Leif: Entered <dokutskottsforslag>");
			while(parser.next()!=XmlPullParser.END_TAG){
				if(parser.getEventType()!=XmlPullParser.START_TAG) {
					continue;
				} else if(out==null) {
					if(parser.getName().equals("utskottsforslag")){
						// Log.i(this.getClass().getSimpleName(), "Leif: Entering <utskottsforslag>");
						out = parseForslag(parser);
					} else {
						Log.w(this.getClass().getSimpleName(), "Leif: in .readFeed: expected <utskottsforslag>, found <"+parser.getName() +">");
						skip(parser);
					}
					
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
	
	private VotesResult parseForslag(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "utskottsforslag");
		
		boolean found = false;
		
		String rubrik = null;
		
		String voteXmlUrl = null;
		String motParti = null;
		String motForslag = null;
		String vinnare = null;
		List<PartyVote> partyVotes = null;
		
		while(parser.next()!=XmlPullParser.END_TAG){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}
			
			String name = parser.getName();
			
			if(name.equals("rubrik")){
				rubrik = this.readString(parser, "rubrik", xmlns);
			} else if(name.equals("forslag")){
				String fors = this.readString(parser, "forslag", xmlns);
				found = fors.contains(this.motionId);
				if(!found){
					Log.i(this.getClass().getSimpleName(), "Leif: hoped to find \""+this.motionId + "\" in: "+fors);
				}
			} else if(found){
				if(name.equals("motforslag_nummer")){
					motForslag = this.readString(parser, "motforslag_nummer", xmlns);
				} else if(name.equals("motforslag_partier")){
					motParti = this.readString(parser, "motforslag_partier", xmlns);
				} else if(name.equals("votering_url_xml")){
					voteXmlUrl = this.readString(parser, "votering_url_xml", xmlns);
					Log.i(this.getClass().getSimpleName(), "Leif: in .parseForslag(): Found url xml:"+voteXmlUrl);
					
				} else if(name.equals("vinnare")){
					vinnare = this.readString(parser, "vinnare", xmlns);
				} else if(name.equals("votering_sammanfattning_html")){
					partyVotes = this.parseVotering(parser);
					Log.i(this.getClass().getSimpleName(), "Leif: in .parseForslag(): Finished parsing the html votes table.");
					
				} else {
					this.skip(parser);
				}
			}
			else {
				this.skip(parser);
			}
		}
		// Log.i(this.getClass().getSimpleName(), "Leif: in .parseForslag: event type " + parser.getEventType());
		// Log.i(this.getClass().getSimpleName(), "Leif: in .parseForslag: tag name <" + parser.getName() + ">");
		
		parser.require(XmlPullParser.END_TAG, xmlns, "utskottsforslag");
		
		if(found){
			VotesResult res = new VotesResult(this.motionId, voteXmlUrl, motParti, motForslag, vinnare, partyVotes);
			return res;
		}
		return null;
	}
	
	private List<PartyVote> parseVotering(XmlPullParser parser) throws XmlPullParserException, IOException{
		
		parser.require(XmlPullParser.START_TAG, xmlns, "votering_sammanfattning_html");
		// Log.i(this.getClass().getSimpleName(), "Leif: Entering <votering_sammanfattning_html>");
		while(parser.nextTag() !=XmlPullParser.START_TAG);
		
		parser.require(XmlPullParser.START_TAG, xmlns, "table");
		// Log.i(this.getClass().getSimpleName(), "Leif: Entering <table>");
		while(parser.nextTag() !=XmlPullParser.START_TAG);
		
		parser.require(XmlPullParser.START_TAG, xmlns, "tr");
		// Log.i(this.getClass().getSimpleName(), "Leif: Entering <tr class=\"sakfragan\">");
		String attr = parser.getAttributeValue(xmlns, "class");
		if(!"sakfragan".equals(attr)){
			Log.w(this.getClass().getSimpleName(), "Leif: in .parseVotering(): <tr class=\"sakfragan\"> expected, found class=\""+attr+"\"");
		}
		this.skip(parser);
		while(parser.nextTag() !=XmlPullParser.START_TAG);
		
		parser.require(XmlPullParser.START_TAG, xmlns, "tr");
		// Log.i(this.getClass().getSimpleName(), "Leif: Entering <tr class=\"vottabellrubrik\">");
		attr = parser.getAttributeValue(xmlns, "class");
		if(!"vottabellrubrik".equals(attr)){
			Log.w(this.getClass().getSimpleName(), "Leif: in .parseVotering(): <tr class=\"vottabellrubrik\"> expected, found class=\""+attr+"\"");
		}
		this.skip(parser);
		
		List<PartyVote> partyVotes = new ArrayList<PartyVote>();
		
		while(parser.next()!=XmlPullParser.END_TAG){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}
			if(!"tr".equals(parser.getName())){
				skip(parser);
			}
			
			parser.require(XmlPullParser.START_TAG, xmlns, "tr");
			String party=null;
			int yes = -1, no = -1, neutral = -1, absent = -1;
			
			while(parser.next()!=XmlPullParser.END_TAG){
				if(parser.getEventType()!=XmlPullParser.START_TAG){
					continue;
				}
				
				parser.require(XmlPullParser.START_TAG, xmlns, "td");
				attr = parser.getAttributeValue(xmlns, "class");
				if(attr == null){
					skip(parser);
				} else if (attr.equals("parti")||attr.equals("totalt")){
					party = this.readString(parser, "td", xmlns);
				} else if(attr.equals("rost_ja")||attr.equals("summa_ja")){
					yes = Integer.parseInt(this.readString(parser, "td", xmlns));
				} else if(attr.equals("rost_nej")||attr.equals("summa_nej")){
					no = Integer.parseInt(this.readString(parser, "td", xmlns));
				} else if(attr.equals("rost_avstar")||attr.equals("summa_avstar")){
					neutral = Integer.parseInt(this.readString(parser, "td", xmlns));
				} else if (attr.equals("rost_franvarande")||attr.equals("summa_franvarande")){
					absent = Integer.parseInt(this.readString(parser, "td", xmlns));
				} else {
					Log.w(this.getClass().getSimpleName(), "Leif: in .parseVotering(): attribute not recognized: <td class=\""+attr+"\">");
					skip(parser);
				}
			}
			
			parser.require(XmlPullParser.END_TAG, xmlns, "tr");
			if(party!=null){
				partyVotes.add(new PartyVote(party, yes, no, neutral, absent));
			}
		}
		parser.require(XmlPullParser.END_TAG, xmlns, "table");
		
		while(parser.nextTag() !=XmlPullParser.START_TAG);
		skip(parser);
		
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, xmlns, "votering_sammanfattning_html");

		return partyVotes;
	}
	
	/**
	 * Found the right document?
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private boolean parseDokument(XmlPullParser parser) throws XmlPullParserException, IOException{
		
		boolean correct = false;
		parser.require(XmlPullParser.START_TAG, xmlns, "dokument");
		while(parser.next()!=XmlPullParser.END_TAG){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			//Log.i(this.getClass().getSimpleName(), "Leif: in .parseDokument(): looking at <" + name + ">");
			
			if(name.equals("dok_id")){
				correct = this.readString(parser, "dok_id", xmlns).equals(this.dokCode);
			} else {
				skip(parser);
			}
		}
		parser.require(XmlPullParser.END_TAG, xmlns, "dokument");
		//Log.i(this.getClass().getSimpleName(), "Leif: in .parseDokument(): looking at </"+parser.getName()+">");
		
		return correct;
	}
}
