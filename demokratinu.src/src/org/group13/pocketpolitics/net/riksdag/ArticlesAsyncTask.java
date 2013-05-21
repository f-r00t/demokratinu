package org.group13.pocketpolitics.net.riksdag;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.group13.pocketpolitics.model.riksdag.Agenda;
import org.group13.pocketpolitics.net.riksdag.data.QueryParam;
import org.group13.pocketpolitics.net.riksdag.data.QueryResult;
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
		
		String url = QUERY + "&datum=" + qpar.getFilter().getDateFrom() + "&tom=" + qpar.getFilter().getDateTo() + "&p=" + qpar.getPage() + "&sz=" + ARTICLES_PER_PAGE + "&sort="+qpar.getFilter().getSort() + "&org="+qpar.getFilter().getUtskott().getQueryName();
		return retrieve(url, qpar);
	}
	
	@Override
	protected QueryResult readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
		List<Agenda> traffarList = new ArrayList<Agenda>();
		
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
	
	private Agenda readTraff(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "traff");
		
		//Agenda art = new Agenda();
		String beteckning="";
		String rm="";
		String title="";
		
		String summary="";
		int traffnummer=-1;
		String datum="";
		String id="";
		String relaterat_id="";
		double score=-1;
		String notisrubrik="";
		String beslutsdag="";
		int beslutad=-1;
		
		while(parser.next()!= XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			//Log.i(this.getClass().getSimpleName(), "PocketDebug: in readTraff: looking at <"+name+">");
			
			if(name.equals("traffnummer")){ 
				traffnummer=(Integer.parseInt(readString(parser, "traffnummer", xmlns)));
			} else if(name.equals("datum")){ 
				datum=(readString(parser, "datum", xmlns));
			} else if(name.equals("id")){ 
				id=(readString(parser, "id", xmlns));
			} else if(name.equals("titel")){ 
				title=(readString(parser, "titel", xmlns));
			} else if(name.equals("rm")){ 
				rm=(readString(parser, "rm", xmlns));
			} else if(name.equals("relaterat_id")){ 
				
				//Log.i(this.getClass().getSimpleName(), "PocketDebug: relaterat_id läses...");
				relaterat_id=(readString(parser, "relaterat_id", xmlns));
				//Log.w(this.getClass().getSimpleName(), "PocketDebug: relaterat_id läst: "+art.getRelaterat_id());
				
			} else if(name.equals("beteckning")){ 
				beteckning=(readString(parser, "beteckning", xmlns));
			} else if(name.equals("score")){
				
				//Källa: http://stackoverflow.com/questions/4323599/best-way-to-parsedouble-with-comma-as-decimal-separator
				NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
				double d = -1;
				String read = readString(parser, "score", xmlns);
				try {
					d=  format.parse(read).doubleValue();
				} catch (ParseException e) {
					Log.e(this.getClass().getSimpleName(), "PocketDebug: reading <score>, Format exception: "+read);
					e.printStackTrace();
				}
				score=d;
			} else if(name.equals("notisrubrik")){ 
				notisrubrik=(readString(parser, "notisrubrik", xmlns));
			} else if(name.equals("notis")){ 
				summary=((readString(parser, "notis", xmlns)));
			} else if(name.equals("beslutsdag")){ 
				beslutsdag=(readString(parser, "beslutsdag", xmlns));
			} else if(name.equals("beslutad")){
				beslutad=(Integer.parseInt(readString(parser, "beslutad", xmlns)));
			} else{
				skip(parser);
			}
		}
		
		return new Agenda(summary, title, beteckning, traffnummer, 
				datum, id, rm, relaterat_id,
				score, notisrubrik, beslutsdag, beslutad, null);
	}
	
	
	
}
