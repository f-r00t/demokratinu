package com.example.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.example.pocketpolitics.model.Article;

class ArticlesAsyncTask extends XmlAsyncTask<QueryParam, Integer, QueryResult>{
	//private static final String QUERY = "http://data.riksdagen.se/sok/?doktyp=bet&avd=dokument&sort=datum&utformat=&a=s&datum=2012-11-11&tom=2013-01-01&p=1&sz=3";
	private static final String QUERY = "http://data.riksdagen.se/sok/?doktyp=bet&avd=dokument&utformat=&a=s"; //"&datum=2012-11-11&tom=2013-01-01&p=1&sz=3";
	
	private final String xmlns = null;
	

	private static int ARTICLES_PER_PAGE = 15;

	private ArtActivityInterface acti;

	ArticlesAsyncTask(ArtActivityInterface act){
		this.acti=act;
	}

	@Override
	protected QueryResult doInBackground(QueryParam... params) {
		if(params==null || params[0] == null){
			return null;
		}
		
		return createArticles(params[0]);
	}

	@Override
	protected void onPreExecute(){
		acti.onPreExecute();
	}

	@Override
	protected void onPostExecute(QueryResult qres){
		Retriever.threadFinished();
		acti.addArticles(qres);
	}

	@Override
	protected void onCancelled(QueryResult qres){
		Retriever.threadFinished();
		acti.wasCancelled(qres);
	}

	private QueryResult createArticles(QueryParam qpar){

		String url = QUERY + "&datum=" + qpar.dateFrom + "&tom=" + qpar.dateTo + "&p=" + qpar.page + "&sz=" + ARTICLES_PER_PAGE + "&sort="+qpar.sort + "&org="+qpar.utskott.getQueryName();
		InputStream instr = retrieveStream(url);

		try {
			QueryResult parsedXml = parseXml(instr);
			
			return parsedXml;
		} catch (XmlPullParserException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: Error in ArticlesAsyncTask.parseXml(): XmlPullParserException",e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: Error in ArticlesAsyncTask.parseXml(): IOException",e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Källa: http://developer.android.com/training/basics/network-ops/xml.html#analyze
	 * 
	 * @param instr
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private QueryResult parseXml(InputStream instr) throws XmlPullParserException, IOException{
		try{
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(instr, null);
			parser.nextTag();
			return readFeed(parser);
		}
		finally{
			instr.close();
		}

	}

	private QueryResult readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
		List<Article> traffarList = new ArrayList<Article>();
		
		parser.require(XmlPullParser.START_TAG, xmlns, "sok");
		int thisPage = Integer.parseInt(parser.getAttributeValue(xmlns, "sida"));
		int totalPages = Integer.parseInt(parser.getAttributeValue(xmlns, "sidor"));
		int totalTraffar = Integer.parseInt(parser.getAttributeValue(xmlns, "traffar"));
		
		Log.i(this.getClass().getSimpleName(), "Leif: attributes gathered. sida="+thisPage+" sidor="+totalPages+" traffar="+totalTraffar);
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if (name.equals("traff")){
				//Log.i(this.getClass().getSimpleName(), "Leif: in readFeed(Xml...): entering <traff>");
				traffarList.add(readTraff(parser));
			}
			else if(name.equals("trafflista")){
				//Log.i(this.getClass().getSimpleName(), "Leif: in readFeed(Xml...): entering <trafflista>");
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
			//Log.i(this.getClass().getSimpleName(), "Leif: in readTraff: looking at <"+name+">");
			
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
				
				//Log.i(this.getClass().getSimpleName(), "Leif: relaterat_id läses...");
				art.setRelaterat_id(readString(parser, "relaterat_id", xmlns));
				//Log.w(this.getClass().getSimpleName(), "Leif: relaterat_id läst: "+art.getRelaterat_id());
				
			} else if(name.equals("beteckning")){ 
				art.setDokid(readString(parser, "beteckning", xmlns));
			} else if(name.equals("score")){
				
				//Källa: http://stackoverflow.com/questions/4323599/best-way-to-parsedouble-with-comma-as-decimal-separator
				NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
				double d = -1;
				String read = readString(parser, "score", xmlns);
				try {
					d=  format.parse(read).doubleValue();
				} catch (ParseException e) {
					Log.e(this.getClass().getSimpleName(), "Leif: reading <score>, Format exception: "+read);
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
