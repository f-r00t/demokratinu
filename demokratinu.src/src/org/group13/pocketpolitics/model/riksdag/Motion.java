package org.group13.pocketpolitics.model.riksdag;

import java.util.List;

public class Motion extends Moprosition {
	
	private final List<Proposer> intressenter;
	private final String subtype;
	private final String subtitle;

	public Motion(List<Proposer> intressenter, String textURL, String rm, String beteckning, 
			String subtype, String title, String subtitle, 
			Committee uts){
		
		super(textURL, rm, beteckning, title, uts, true);
		this.intressenter = intressenter;
		this.subtype = subtype;
		this.subtitle = subtitle;
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
}
