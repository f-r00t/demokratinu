package org.group13.pocketpolitics.user_model;

import java.util.Date;
import java.util.List;


public class Comment extends LikeableItem {
	
	private String author;
	
	public Comment(String content, Date date, boolean isHidden, int opinion,
			int nbrOfLikes, int nbrOfDislikes, List<Comment> replies, String author) {
		super(content, date, isHidden, opinion, nbrOfLikes, nbrOfDislikes, replies);
		this.author = author;
		
	}
	
	// Temporary constructor
	public Comment() {
		super();
	}

	/**
	 * @return The author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author The author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
}
