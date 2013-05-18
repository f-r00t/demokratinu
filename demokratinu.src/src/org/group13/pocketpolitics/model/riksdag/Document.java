package org.group13.pocketpolitics.model.riksdag;

public abstract class Document {

	private final String beteckning;
	private final String rm;
	private final String title;
	
	Document(String beteckning, String rm, String title){
		this.beteckning=beteckning;
		this.rm = rm;
		this.title = title;
	}

	public String getBeteckning() {
		return beteckning;
	}

	public String getRm() {
		return rm;
	}

	public String getTitle() {
		return title;
	}
}
