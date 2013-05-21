package org.group13.pocketpolitics.model.user;

import java.util.Date;
import java.util.List;

import org.group13.pocketpolitics.model.riksdag.Agenda;

public class Article {

	private Agenda agenda;
	private ArticleReplies areplies;
	
	Article(Agenda agenda, List<Comment> replies){
		this.areplies = new ArticleReplies(replies);
		this.agenda = agenda;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	
	public List<Comment> getReplies(){
		return this.areplies.replies;
	}
	
	private static class ArticleReplies{
		private List<Comment> replies;
		//private int opinion;
		
		ArticleReplies(List<Comment> replies){
			this.replies = replies;
		}
	}
}
