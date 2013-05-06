package org.group13.pocketpolitics.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.model.UtskottsForslag;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class VotesAsyncTask extends XmlAsyncTask<Article, Integer, String> {

	private final String URL = "http://data.riksdagen.se/utskottsforslag/";
	
	private final String xmlns = null;
	
	private final Article article;
	
	//private final String dokCode;	//H001...
	//private final String motionId;
	private final ActivityNetInterface<String> act;
	
	/**
	 * 
	 * @param act	Interface updating the GUI
	 * @param article The article to find votes for. It is updated dynamically
	 */
	VotesAsyncTask(ActivityNetInterface<String> act, Article article){
		this.article = article;
		this.act = act;
	}
	
	@Override
	protected void onPreExecute(){
		if(act!=null){
			act.onPreExecute();
		} else {
			Log.w(this.getClass().getSimpleName(), "Leif: in @.onPreExecute Activity is null");
			this.cancel(true);
		}
		
		if(article == null || article.getId() == null){
			Log.e(this.getClass().getSimpleName(), "Leif: in @.onPreExecute: Article is null or has no id!");
			this.cancel(true);
		}
	}
	
	@Override
	protected String doInBackground(Article... params) {
		
		String url = URL+article.getId();
		return retrieve(url, article);
	}
	
	@Override
	protected void onPostExecute(String res){
		Retriever.threadFinished();
		if(act!=null){
			act.onSuccess(res);
		} else {
			Log.w(this.getClass().getSimpleName(), "Leif: in @.onPostExecute Activity is null");
			act.onFailure("! Null returned!");
		}
	}
	
	@Override
	protected void onCancelled(String res){
		Retriever.threadFinished();
		if(act!=null){
			act.onFailure("! Cancelled!");
		} else {
			Log.w(this.getClass().getSimpleName(), "Leif: in @.onCancelled Activity is null");
		}
		
	}
	
	@Override
	protected String readFeed(XmlPullParser parser) throws XmlPullParserException,IOException {

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
		
		
		
		while(parser.next()!=XmlPullParser.START_TAG && !this.isCancelled()){
			//Log.w(this.getClass().getSimpleName(), "Leif: in readFeed: skipped a tag of type: "+parser.getEventType());
			if(parser.getEventType()==XmlPullParser.END_TAG){
				Log.w(this.getClass().getSimpleName(), "Leif: in readFeed: skipped an END_TAG!");
			}
		}
		
		parser.require(XmlPullParser.START_TAG, xmlns, "dokutskottsforslag");
		List<UtskottsForslag> lForslag = new ArrayList<UtskottsForslag>();
		
		if(parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals("dokutskottsforslag")){
			
			// Log.i(this.getClass().getSimpleName(), "Leif: Entered <dokutskottsforslag>");
			while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
				if(parser.getEventType()!=XmlPullParser.START_TAG) {
					continue;
				}
				
				if(parser.getName().equals("utskottsforslag")){
					// Log.i(this.getClass().getSimpleName(), "Leif: Entering <utskottsforslag>");
					UtskottsForslag out = parseForslag(parser);
					if(out!=null){
						lForslag.add(out);
					} else {
						Log.w(this.getClass().getSimpleName(), "Leif: in .readFeed(): UtskottsForslag null!");
					}
				} else {
					Log.w(this.getClass().getSimpleName(), "Leif: in .readFeed: expected <utskottsforslag>, found <"+parser.getName() +">");
					skip(parser);
				}
			}
		} else {
			Log.e(this.getClass().getSimpleName(), "Leif: Error, tag <dokutskottsforslag> expected, got <" + parser.getName()+">");
			return null;
		}
		
		article.setFors(lForslag);
		return article.getId();
	}
	
	private UtskottsForslag parseForslag(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "utskottsforslag");
		
		int punkt = -1;
		String rubrik = null;
		String forslag = null;
		String voteXmlUrl = null;
		String motParti = null;
		int motForslag = -1;
		String vinnare = null;
		List<PartyVote> partyVotes = null;
		
		while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}
			
			String name = parser.getName();
			
			if("punkt".equals(name)){
				punkt = Integer.parseInt(this.readString(parser, "punkt", xmlns));
			} else if("rubrik".equals(name)){
				rubrik = this.readString(parser, "rubrik", xmlns);
			} else if("forslag".equals(name)){
				forslag = this.readString(parser, "forslag", xmlns);
			} else if("motforslag_nummer".equals(name)){
				motForslag = Integer.parseInt(this.readString(parser, "motforslag_nummer", xmlns));
			} else if("motforslag_partier".equals(name)){
				motParti = this.readString(parser, "motforslag_partier", xmlns);
			} else if("votering_url_xml".equals(name)){
				voteXmlUrl = this.readString(parser, "votering_url_xml", xmlns);
				
				//Log.i(this.getClass().getSimpleName(), "Leif: in .parseForslag(): Found url xml:"+voteXmlUrl);
				
			} else if("vinnare".equals(name)){
				vinnare = this.readString(parser, "vinnare", xmlns);
			} else if("votering_sammanfattning_html".equals(name)){
				partyVotes = this.parseVotering(parser);
				
				//Log.i(this.getClass().getSimpleName(), "Leif: in .parseForslag(): Finished parsing the html votes table.");
				
			} else {
				this.skip(parser);
			}
			
		}
		// Log.i(this.getClass().getSimpleName(), "Leif: in .parseForslag: event type " + parser.getEventType());
		// Log.i(this.getClass().getSimpleName(), "Leif: in .parseForslag: tag name <" + parser.getName() + ">");
		
		parser.require(XmlPullParser.END_TAG, xmlns, "utskottsforslag");
		
		if(forslag!=null){
			UtskottsForslag res = new UtskottsForslag(punkt, rubrik, forslag, voteXmlUrl, motParti, motForslag, vinnare, partyVotes);
			return res;
		}
		return null;
	}
	
	private List<PartyVote> parseVotering(XmlPullParser parser) throws XmlPullParserException, IOException{
		
		parser.require(XmlPullParser.START_TAG, xmlns, "votering_sammanfattning_html");
		
		//Log.i(this.getClass().getSimpleName(), "Leif: Entering <votering_sammanfattning_html>");
		
		while(parser.nextTag() !=XmlPullParser.START_TAG){
			if(parser.getEventType()==XmlPullParser.END_TAG && "votering_sammanfattning_html".equals(parser.getName())){
				return null;
			}
		}
		
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
		while(parser.nextTag() !=XmlPullParser.START_TAG && !this.isCancelled());
		
		parser.require(XmlPullParser.START_TAG, xmlns, "tr");
		// Log.i(this.getClass().getSimpleName(), "Leif: Entering <tr class=\"vottabellrubrik\">");
		attr = parser.getAttributeValue(xmlns, "class");
		if(!"vottabellrubik".equals(attr)){
			Log.w(this.getClass().getSimpleName(), "Leif: in .parseVotering(): <tr class=\"vottabellrubik\"> expected, found class=\""+attr+"\"");
		}
		this.skip(parser);
		
		List<PartyVote> partyVotes = new ArrayList<PartyVote>();
		
		while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
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
		while(parser.next()!=XmlPullParser.END_TAG && !this.isCancelled()){
			if(parser.getEventType()!=XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			//Log.i(this.getClass().getSimpleName(), "Leif: in .parseDokument(): looking at <" + name + ">");
			
			if(name.equals("dok_id")){
				correct = this.readString(parser, "dok_id", xmlns).equals(this.article.getId());
			} else {
				skip(parser);
			}
		}
		parser.require(XmlPullParser.END_TAG, xmlns, "dokument");
		//Log.i(this.getClass().getSimpleName(), "Leif: in .parseDokument(): looking at </"+parser.getName()+">");
		
		return correct;
	}
}
