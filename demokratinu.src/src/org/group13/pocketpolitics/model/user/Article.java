package org.group13.pocketpolitics.model.user;

import java.util.Date;
import java.util.List;

import org.group13.pocketpolitics.model.riksdag.Agenda;

public class Article extends LikeableItem {

	private Agenda agenda;
	
	Article(Agenda agenda, Date date, boolean isHidden, int opinion,
			int nbrOfLikes, int nbrOfDislikes, List<Comment> replies){
		super(date, isHidden, opinion, nbrOfLikes, nbrOfDislikes, replies);
		
		this.agenda = agenda;
	}
}
