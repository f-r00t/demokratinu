package org.group13.pocketpolitics.model;

public class Intressent {

	public final String personId;
	public final String name;
	public final String party;
	public final String role;
	
	public Intressent(String name, String party, String role, String intressentId){
		this.name=name;
		this.party=party;
		this.role = role;
		this.personId = intressentId;
	}
}
