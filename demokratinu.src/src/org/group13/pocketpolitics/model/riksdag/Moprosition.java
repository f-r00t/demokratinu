package org.group13.pocketpolitics.model.riksdag;

/**
 * Class for both Motion and Proposition, hence the name.
 * @author Leif
 *
 */
public abstract class Moprosition {
	
	private final String beteckning;
	private final String rm;
	private final String textURL;
	private final String title;
	
	private final boolean motion;

	private final Committee uts;
	
	private String text;	//this String is set afterwards
	

	protected Moprosition(String textURL, String rm, String beteckning, String title, Committee uts, boolean motion){
		this.motion = motion;
		this.textURL = textURL;
		this.beteckning = beteckning;
		this.rm = rm;
		this.title = title;
		this.uts = uts;
		
		text = null;
	}
	
	public String getBeteckning() {
		return beteckning;
	}

	public String getRm() {
		return rm;
	}

	public String getTextURL() {
		return textURL;
	}

	public String getTitle() {
		return title;
	}

	public boolean isMotion() {
		return motion;
	}

	public Committee getUts() {
		return uts;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
