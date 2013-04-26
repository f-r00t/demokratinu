package com.example.pocketpolitics.net;

import android.util.Log;

public class QueryParam {

	protected String dateFrom;
	protected String dateTo;
	protected int page;
	
	QueryParam(String dateFrom, String dateTo, int page){
		this.dateFrom = dateFrom;
		this.dateTo= dateTo;
		
		if(page <1){
			Log.w(this.getClass().getSimpleName(), "Leif: bad page number: "+page);
			page=1;
		}
		this.page = page;
	}
}
