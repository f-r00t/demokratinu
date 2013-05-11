package org.group13.pocketpolitics.model;

/**
 * Intressent
 * @author Leif
 *
 */
public class Proposer {

	private final String personId;
	private final String name;
	private final String party;
	private final String role;
	
	public Proposer(String name, String party, String role, String intressentId){
		this.name=name;
		this.party=party;
		this.role = role;
		this.personId = intressentId;
	}

	public String getPersonId() {
		return personId;
	}

	public String getName() {
		return name;
	}

	public String getParty() {
		return party;
	}

	public String getRole() {
		return role;
	}
}
