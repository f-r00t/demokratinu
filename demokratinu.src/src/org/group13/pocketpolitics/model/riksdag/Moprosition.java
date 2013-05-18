package org.group13.pocketpolitics.model.riksdag;

/**
 * Class for both Motion and Proposition, hence the name.
 * @author Leif
 *
 */
public abstract class Moprosition extends Document{
	
	private final String textURL;
	
	private final boolean motion;

	private final Committee uts;
	
	private String text;	//this String is set afterwards
	

	protected Moprosition(String textURL, String rm, String beteckning, String title, Committee uts, boolean motion){
		super(beteckning, rm, title);
		this.motion = motion;
		this.textURL = textURL;
		this.uts = uts;
		
		text = null;
	}

	public String getTextURL() {
		return textURL;
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
