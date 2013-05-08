package org.group13.pocketpolitics.model;

public class Motion extends Moprosition {
	
	public final Intressent[] intressenter;
	public final String subtype;
	public final String subtitle;
	public final String kammaren;
	public final String utskottet;

	Motion(Intressent[] intressenter, String textURL, String rm, String beteckning, 
			String subtype, boolean motion, String title, String subtitle, 
			Utskott uts, String kammaren, String utskottet){
		
		super(textURL, rm, beteckning, title, uts);
		this.intressenter = intressenter;
		this.subtype = subtype;
		this.subtitle = subtitle;
		this.kammaren = kammaren;
		this.utskottet = utskottet;
	}
}
