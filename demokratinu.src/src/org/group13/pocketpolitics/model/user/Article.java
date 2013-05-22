package org.group13.pocketpolitics.model.user;

import org.group13.pocketpolitics.model.riksdag.Agenda;

public class Article {

	private Agenda agenda;
	private ArticleData data;
	
	Article(Agenda agenda, ArticleData data){
		this.data = data;
		this.agenda = agenda;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public ArticleData getData() {
		return data;
	}

	public void setData(ArticleData data) {
		this.data = data;
	}
	
	
}
