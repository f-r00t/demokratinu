package org.group13.pocketpolitics.net;

import java.util.ArrayList;
import java.util.List;

public class UtskottsForslag {
	
	public final List<String> motion;
	
	public final int nr;
	
	public final String title;
	public final String forslag;
	public final String voteXmlUrl;
	public final String motParti;
	public final String motForslag;
	public final String vinnare;
	
	public final List<PartyVote> voteItems;
	
	UtskottsForslag(int punkt, String rubrik, String forslag, String xmlUrl, String motParti, String motForslag, String vinnare, List<PartyVote> voteItems){
		
		this.nr = punkt;
		this.title = rubrik;
		this.forslag = forslag;
		
		this.voteXmlUrl=xmlUrl;
		this.motForslag = motForslag;
		this.motParti = motParti;
		this.vinnare = vinnare;
		
		this.voteItems=voteItems;
		
		{
			List<String> mots = new ArrayList<String>();
			String temp;
			// TODO
			// parse string this.forslag -> motion codes
			this.motion = mots;
		}
	}
	
	
}
