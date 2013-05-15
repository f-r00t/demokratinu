package org.group13.pocketpolitics.net;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.net.data.QueryParam;
import org.group13.pocketpolitics.net.data.QueryResult;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;


class ArticlesAsyncTask extends XmlAsyncTask<QueryParam, QueryResult>{
	//private static final String QUERY = "http://data.riksdagen.se/sok/?doktyp=bet&avd=dokument&sort=datum&utformat=&a=s&datum=2012-11-11&tom=2013-01-01&p=1&sz=3";
	private static final String QUERY = "http://data.riksdagen.se/sok/?doktyp=bet&avd=dokument&utformat=&a=s"; //"&datum=2012-11-11&tom=2013-01-01&p=1&sz=3";
	
	private final String xmlns = null;
	

	private static int ARTICLES_PER_PAGE = 15;

	//private ActivityNetInterface<QueryResult> acti;

	ArticlesAsyncTask(ActivityNetInterface<QueryResult> act){
		super(act);
		//this.acti=act;
	}

	@Override
	protected QueryResult doInBackground(QueryParam... params) {
		if(params==null || params[0] == null){
			return null;
		}
		
		QueryParam qpar = params[0];
		
		String url = QUERY + "&datum=" + qpar.filter.dateFrom + "&tom=" + qpar.filter.dateTo + "&p=" + qpar.page + "&sz=" + ARTICLES_PER_PAGE + "&sort="+qpar.filter.sort + "&org="+qpar.filter.utskott.getQueryName();
		return retrieve(url, qpar);
	}
	
	@Override
	protected QueryResult readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
		List<Article> traffarList = new ArrayList<Article>();
		
		parser.require(XmlPullParser.START_TAG, xmlns, "sok");
		int thisPage = Integer.parseInt(parser.getAttributeValue(xmlns, "sida"));
		int totalPages = Integer.parseInt(parser.getAttributeValue(xmlns, "sidor"));
		int totalTraffar = Integer.parseInt(parser.getAttributeValue(xmlns, "traffar"));
		
		Log.i(this.getClass().getSimpleName(), "PocketDebug: attributes gathered. sida="+thisPage+" sidor="+totalPages+" traffar="+totalTraffar);
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if (name.equals("traff")){
				//Log.i(this.getClass().getSimpleName(), "PocketDebug: in readFeed(Xml...): entering <traff>");
				traffarList.add(readTraff(parser));
			}
			else if(name.equals("trafflista")){
				//Log.i(this.getClass().getSimpleName(), "PocketDebug: in readFeed(Xml...): entering <trafflista>");
				continue;
			}
			else{
				skip(parser);
			}
		}
		
		QueryResult qres = new QueryResult(traffarList, totalPages, thisPage, totalTraffar);

		return qres;
	}
	
	private Article readTraff(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "traff");
		
		Article art = new Article();
		
		while(parser.next()!= XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			//Log.i(this.getClass().getSimpleName(), "PocketDebug: in readTraff: looking at <"+name+">");
			
			if(name.equals("traffnummer")){ 
				art.setTraffnummer(Integer.parseInt(readString(parser, "traffnummer", xmlns)));
			} else if(name.equals("datum")){ 
				art.setDatum(readString(parser, "datum", xmlns));
			} else if(name.equals("id")){ 
				art.setId(readString(parser, "id", xmlns));
			} else if(name.equals("titel")){ 
				art.setTitle(readString(parser, "titel", xmlns));
			} else if(name.equals("rm")){ 
				art.setRm(readString(parser, "rm", xmlns));
			} else if(name.equals("relaterat_id")){ 
				
				//Log.i(this.getClass().getSimpleName(), "PocketDebug: relaterat_id l�ses...");
				art.setRelaterat_id(readString(parser, "relaterat_id", xmlns));
				//Log.w(this.getClass().getSimpleName(), "PocketDebug: relaterat_id l�st: "+art.getRelaterat_id());
				
			} else if(name.equals("beteckning")){ 
				art.setBeteckning(readString(parser, "beteckning", xmlns));
			} else if(name.equals("score")){
				
				//K�lla: http://stackoverflow.com/questions/4323599/best-way-to-parsedouble-with-comma-as-decimal-separator
				NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
				double d = -1;
				String read = readString(parser, "score", xmlns);
				try {
					d=  format.parse(read).doubleValue();
				} catch (ParseException e) {
					Log.e(this.getClass().getSimpleName(), "PocketDebug: reading <score>, Format exception: "+read);
					e.printStackTrace();
				}
				art.setScore(d);
			} else if(name.equals("notisrubrik")){ 
				art.setNotisrubrik(readString(parser, "notisrubrik", xmlns));
			} else if(name.equals("notis")){ 
				art.setContent((readString(parser, "notis", xmlns)));
			} else if(name.equals("beslutsdag")){ 
				art.setBeslutsdag(readString(parser, "beslutsdag", xmlns));
			} else if(name.equals("beslutad")){
				art.setBeslutad(Integer.parseInt(readString(parser, "beslutad", xmlns)));
			} else{
				skip(parser);
			}
		}
		
		return art;
	}
	
	
	
}
