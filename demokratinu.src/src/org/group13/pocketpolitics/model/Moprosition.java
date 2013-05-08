package org.group13.pocketpolitics.model;

/**
 * Class for both Motion and Proposition, hence the name.
 * @author Leif
 *
 */
public abstract class Moprosition {
	
	public final String beteckning;
	public final String rm;
	public final String textURL;
	public final String title;

	public final Utskott uts;
	
	private String text;
	

	protected Moprosition(String textURL, String rm, String beteckning, String title, Utskott uts){
		
		this.textURL = textURL;
		this.beteckning = beteckning;
		this.rm = rm;
		this.title = title;
		this.uts = uts;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
