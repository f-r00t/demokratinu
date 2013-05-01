package org.group13.pocketpolitics.net;

import java.util.List;

public class VotesResult {
	
	public final String motion;
	
	public final String voteXmlUrl;
	public final String motParti;
	public final String motForslag;
	public final String vinnare;
	
	public final List<PartyVote> voteItems;
	
	VotesResult(String motion, String xmlUrl, String motParti, String motForslag, String vinnare, List<PartyVote> voteItems){
		this.motion = motion;
		
		this.voteXmlUrl=xmlUrl;
		this.motForslag = motForslag;
		this.motParti = motParti;
		this.vinnare = vinnare;
		
		this.voteItems=voteItems;
	}
	
}
