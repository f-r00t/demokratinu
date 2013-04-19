package com.example.pocketpolitics.model;

import java.util.Date;
import java.util.List;

public class Article extends LikeableItem {

	private String title;
	
	public Article(String content, Date date, boolean isHidden, int opinion,
			int nbrOfLikes, int nbrOfDislikes, List<Comment> replies, String title) {
		super(content, date, isHidden, opinion, nbrOfLikes, nbrOfDislikes, replies);
		this.title = title;
	}
	
	// Temporary constructor
	public Article() {
		super();
	}


	/**
	 * @return The title (shorter description) of the article
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title (shorter description) of the article
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
