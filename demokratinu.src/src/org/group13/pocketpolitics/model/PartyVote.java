package org.group13.pocketpolitics.model;

public class PartyVote {

	private final String party;
	private final int yes;
	private final int no;
	private final int neutral;
	private final int absent;
	
	public PartyVote(String party, int yes, int no, int neutral, int absent){
		this.party = party;
		this.no = no;
		this.yes = yes;
		this.neutral = neutral;
		this.absent = absent;
	}

	public String getParty() {
		return party;
	}

	public int getYes() {
		return yes;
	}

	public int getNo() {
		return no;
	}

	public int getNeutral() {
		return neutral;
	}

	public int getAbsent() {
		return absent;
	}
}
