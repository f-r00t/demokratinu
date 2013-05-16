package org.group13.pocketpolitics.model.riksdag;

import java.util.List;

public class Motion extends Moprosition {
	
	private final List<Proposer> intressenter;
	private final String subtype;
	private final String subtitle;
	private final String kammaren;
	private final String utskottet;

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

	public List<Proposer> getIntressenter() {
		return intressenter;
	}

	public String getSubtype() {
		return subtype;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getKammaren() {
		return kammaren;
	}

	public String getUtskottet() {
		return utskottet;
	}
}
