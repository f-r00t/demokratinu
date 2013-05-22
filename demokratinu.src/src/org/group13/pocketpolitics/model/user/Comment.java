package org.group13.pocketpolitics.model.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Comment extends LikeableItem {
	
	private String author;
	private String content;
	private String commentId;
	private Date date;
	private List<Comment> replies;
	
	public Comment(String content, Date date, boolean isHidden, int opinion,
			int nbrOfLikes, int nbrOfDislikes, List<Comment> replies, String author) {
		super(isHidden, opinion, nbrOfLikes, nbrOfDislikes);
		this.author = author;
		this.content = content;
		this.date = date;
		this.replies = replies;
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
	
	/**
	 * @param comment The reply to this item
	 */
	public void reply(Comment comment) {
		if(replies == null){
			replies = new ArrayList<Comment>();
		}
		this.replies.add(comment);
	}
	
	/**
	 * @return An array of all the replies this item has
	 */
	public List<Comment> getReplies(){
	//public Comment[] getReplies() {
		//return (Comment[]) this.replies.toArray();
		return replies;
	}
	
	/**
	 * @return The date this item was first created
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date The date this item was first created
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
}
