package org.group13.pocketpolitics.net;

public class PartyVote {

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
