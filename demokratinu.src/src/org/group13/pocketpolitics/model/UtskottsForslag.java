package org.group13.pocketpolitics.model;


import java.util.List;



import android.util.Log;

public class UtskottsForslag {
	
	public final String moprositionParsed;	// "2012/13:Ub354" -> "[[2012%2F13]:[Ub354]]"
	
	public final int nr;
	public final int motForslag;
	
	public final String title;
	public final String forslag;
	public final String voteXmlUrl;
	public final String motParti;
	
	public final String vinnare;
	
	public final List<PartyVote> voteItems;
	
	public UtskottsForslag(int punkt, String rubrik, String forslag, String xmlUrl, 
			String motParti, int motForslag, String vinnare, List<PartyVote> voteItems){
		
		this.nr = punkt;
		this.title = rubrik;
		this.forslag = forslag;
		
		this.voteXmlUrl=xmlUrl;
		this.motForslag = motForslag;
		this.motParti = motParti;
		this.vinnare = vinnare;
		
		this.voteItems=voteItems;
		
		//parse motions
		// parse string this.forslag -> motion codes
		{
			
			String build="";
			String temp=forslag;
			int slash =0;
			
			try{
				slash = temp.indexOf("/");
				while(slash!=-1){
					
					String primitiveYear = temp.substring(slash-4, slash+3);
					
					//String webYear = primitiveYear.replace("/", "%2F");
					build+=temp.substring(0, slash-4)+"[[" + primitiveYear+"]:[";
					
					temp=temp.substring(slash+4);
					
					String[] strs = temp.split("[.,\\s]",2);
					
					String number = strs[0];
					temp = " "+strs[1];
					
					build+=number+"]]";
					
					slash = temp.indexOf("/");
				}
			} catch (StringIndexOutOfBoundsException e){
				Log.e(this.getClass().getSimpleName(), "Leif: String index out of bounds Exception!!!");
			}
			
			//Log.i(this.getClass().getSimpleName(), "Leif: temp: "+temp);
			//Log.w(this.getClass().getSimpleName(), "Leif: build: "+build);
			//Log.i(this.getClass().getSimpleName(), "Leif: forslag: "+forslag);
			
			this.moprositionParsed = build;
		}
	}
	
	
}
