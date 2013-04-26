package com.example.pocketpolitics.net;

import android.util.Log;

public class QueryParam {

	protected String date;
	protected int page;
	
	QueryParam(String date, int page){
		this.date = date;
		if(page <1){
			Log.w(this.getClass().getSimpleName(), "Leif: bad page number: "+page);
			page=1;
		}
		this.page = page;
	}
}
