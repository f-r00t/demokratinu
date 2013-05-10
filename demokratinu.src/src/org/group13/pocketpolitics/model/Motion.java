package org.group13.pocketpolitics.model;

import java.util.List;

public class Motion extends Moprosition {
	
	public final List<Proposer> intressenter;
	public final String subtype;
	public final String subtitle;
	public final String kammaren;
	public final String utskottet;

	public Motion(List<Proposer> intressenter, String textURL, String rm, String beteckning, 
			String subtype, String title, String subtitle, 
			Committee uts, String kammaren, String utskottet){
		
		super(textURL, rm, beteckning, title, uts, true);
		this.intressenter = intressenter;
		this.subtype = subtype;
		this.subtitle = subtitle;
		this.kammaren = kammaren;
		this.utskottet = utskottet;
	}
}
