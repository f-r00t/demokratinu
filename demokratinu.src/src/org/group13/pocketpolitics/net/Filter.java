package org.group13.pocketpolitics.net;

import org.group13.pocketpolitics.model.Committee;

public class Filter {

	protected final String dateFrom;
	protected final String dateTo;
	protected final String sort;
	protected final int sortint;
	
	protected final Committee utskott;
	
	/**
	 * Class for making a query to data.riksdagen.se/sok/.
	 * 
	 * Date format: "yyyy-mm-dd"
	 * 
	 * @param dateFrom  Search for articles after this date, leave empty "" for no restriction
	 * @param dateTo	Search for articles up to this date, leave empty "" for no restriction
	 * @param sort		Sort results: default = sort after date (only closed issues);
	 * 0 = sort after date (all issues);  
	 */

	Filter(String dateFrom, String dateTo, int sort, Committee utskott){
		this.dateFrom = dateFrom;
		this.dateTo= dateTo;
		this.sortint = sort;

		if(utskott==null){
			this.utskott = Committee.NULL;
		} else {
			this.utskott = utskott;
		}
		
		if(sort==0){
			this.sort="datum";
		}
		else{
			this.sort="beslutsdag";
		}
	}
	
	@Override
	public boolean equals(Object o){
		Filter f = (Filter) o;
		if(!f.dateFrom.equals(this.dateFrom)){
			return false;
		} else if(!f.dateTo.equals(this.dateTo)){
			return false;
		} else if(f.sortint!=this.sortint){
			return false;
		} else if(!f.utskott.equals(this.utskott)){
			return false;
		}
		
		return true;
	}
}
