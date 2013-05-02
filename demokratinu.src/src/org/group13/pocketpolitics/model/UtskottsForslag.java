package org.group13.pocketpolitics.model;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.net.PartyVote;

public class UtskottsForslag {
	
	public final List<String> motion;
	
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
		{
			List<String> mots = new ArrayList<String>();
			String temp;
			// TODO
			// parse string this.forslag -> motion codes
			this.motion = mots;
		}
	}
	
	
}
