package com.example.pocketpolitics.net;

import android.util.Log;

import com.example.pocketpolitics.model.Utskott;

/**
 * Class for making a query to data.riksdagen.se/sok/.
 * @author Leif
 *
 */
class QueryParam {

	protected final String dateFrom;
	protected final String dateTo;
	protected final String sort;
	protected final int page;
	protected final Utskott utskott;
	
	/**
	 * Class for making a query to data.riksdagen.se/sok/.
	 * 
	 * Date format: "yyyy-mm-dd"
	 * 
	 * @param dateFrom  Search for articles after this date, leave empty "" for no restriction
	 * @param dateTo	Search for articles up to this date, leave empty "" for no restriction
	 * @param page		Get result page, default 1
	 * @param sort		Sort results: default = sort after date (only closed issues);
	 * 0 = sort after date (all issues); 
	 * 1 = sort after relevance (all issues). Relevance is determined by data.Riksdagen.se 
	 */
	QueryParam(String dateFrom, String dateTo, int page, int sort, Utskott utskott){
		this.dateFrom = dateFrom;
		this.dateTo= dateTo;
		
		if(utskott==null){
			this.utskott = Utskott.NULL;
		} else {
			this.utskott = utskott;
		}
		
		
		
		if(page <1){
			Log.w(this.getClass().getSimpleName(), "Leif: bad page number: "+page);
			page=1;
		}
		this.page = page;
		
		if(sort==1){
			this.sort="rel";
		}
		else if(sort==0){
			this.sort="datum";
		}
		else{
			this.sort="beslutsdag";
			
		}
	}
}
