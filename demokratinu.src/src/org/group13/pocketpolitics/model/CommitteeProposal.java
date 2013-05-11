package org.group13.pocketpolitics.model;

import java.util.List;



import android.util.Log;

/**
 * Utskottsförslag
 * @author Leif
 *
 */
public class CommitteeProposal {
	
	private final String moprositionParsed;	// "2012/13:Ub354" -> "[[2012%2F13]:[Ub354]]"
	private final int nr;
	private final int motForslag;
	
	private final String title;
	private final String forslag;
	private final String voteXmlUrl;
	private final String motParti;
	
	private final String vinnare;
	
	private final List<PartyVote> voteItems;
	
	public CommitteeProposal(int punkt, String rubrik, String forslag, String xmlUrl, 
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
	
	public String getMoprositionParsed() {
		return moprositionParsed;
	}

	public int getNr() {
		return nr;
	}

	public int getMotForslag() {
		return motForslag;
	}

	public String getTitle() {
		return title;
	}

	public String getForslag() {
		return forslag;
	}

	public String getVoteXmlUrl() {
		return voteXmlUrl;
	}

	public String getMotParti() {
		return motParti;
	}

	public String getVinnare() {
		return vinnare;
	}

	public List<PartyVote> getVoteItems() {
		return voteItems;
	}
}
