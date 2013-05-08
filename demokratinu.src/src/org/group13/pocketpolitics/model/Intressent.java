package org.group13.pocketpolitics.model;

public class Intressent {

	public final int personId;
	public final String name;
	public final String party;
	public final String role;
	
	public Intressent(String name, String party, String role, int intressentId){
		this.name=name;
		this.party=party;
		this.role = role;
		this.personId = intressentId;
	}
}
