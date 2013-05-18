package org.group13.pocketpolitics.model.user;

import java.util.Date;
import java.util.List;


public class Comment extends LikeableItem {
	
	private String author;
	private String content;
	
	public Comment(String content, Date date, boolean isHidden, int opinion,
			int nbrOfLikes, int nbrOfDislikes, List<Comment> replies, String author) {
		super(date, isHidden, opinion, nbrOfLikes, nbrOfDislikes, replies);
		this.author = author;
		this.content = content;
		
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
