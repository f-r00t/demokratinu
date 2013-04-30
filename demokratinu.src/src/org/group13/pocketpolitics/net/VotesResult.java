package org.group13.pocketpolitics.net;

import java.util.List;

public class VotesResult {
	
	public final String voteXmlUrl;
	public final String motParti;
	public final String motForslag;
	public final String vinnare;
	
	public final List<PartyVote> voteItems;
	
	VotesResult(String xmlUrl, String motParti, String motForslag, String vinnare, List<PartyVote> voteItems){
		this.voteXmlUrl=xmlUrl;
		this.voteItems=voteItems;
		this.motForslag = motForslag;
		this.motParti = motParti;
		this.vinnare = vinnare;
	}
	
	public class PartyVote{
		public final String party;
		public final int yes;
		public final int no;
		public final int neutral;
		public final int absent;
		
		PartyVote(String party, int yes, int no, int neutral, int absent){
			this.party = party;
			this.no = no;
			this.yes = yes;
			this.neutral = neutral;
			this.absent = absent;
		}
	}
}
