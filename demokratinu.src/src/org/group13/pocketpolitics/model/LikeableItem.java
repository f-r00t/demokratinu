package org.group13.pocketpolitics.model;

import java.util.Date;
import java.util.List;

public abstract class LikeableItem {

	private int opinion;
	private int nbrOfLikes;
	private int nbrOfDislikes;
	private boolean isHidden;
	private String content;
	private Date date;
	private List<Comment> replies;

	public LikeableItem(String content, Date date, boolean isHidden,
			int opinion, int nbrOfLikes, int nbrOfDislikes, List<Comment> replies) {
		this.content = content;
		this.date = date;
		this.isHidden = isHidden;
		this.opinion = opinion;
		this.nbrOfLikes = nbrOfLikes;
		this.nbrOfDislikes = nbrOfDislikes;
		this.replies = replies;
	}
	
	// Temporary cunstructor
	public LikeableItem() {
		super();
	}

	/**
	 * Sets this item to be liked for the current user (If it was previously liked it will now be neutral)
	 */
	public void like() {
		switch(opinion) {
			case -1:
				nbrOfDislikes--;
				nbrOfLikes++;
				opinion = 1;
				break;
			case 1:
				nbrOfLikes--;
				opinion = 0;
				break;
			default:
				nbrOfLikes++;
				opinion = 1;
				break;
		}
	}
	
	/**
	 * Sets this item to be disliked for the current user (If it was previously disliked it will now be neutral)
	 */
	public void dislike() {
		switch(opinion) {
			case -1:
				nbrOfDislikes--;
				opinion = 0;
				break;
			case 1:
				nbrOfLikes--;
				nbrOfDislikes++;
				opinion = -1;
				break;
			default:
				nbrOfDislikes++;
				opinion = -1;
				break;
		}
	}
	
	/**
	 * Hides/Collapses this item from the user
	 * 
	 * @param isHidden True if this item shall be hidden, false otherwise
	 */
	public void hide(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	
	/**
	 * @return True if this item is hidden, false otherwise
	 */
	public Boolean isHidden() {
		return isHidden;
	}
	
	/**
	 * @param comment The reply to this item
	 */
	public void reply(Comment comment) {
		this.replies.add(comment);
	}
	
	/**
	 * @return An array of all the replies this item has
	 */
	public Comment[] getReplies() {
		return (Comment[]) this.replies.toArray();
	}
	
	/**
	 * @return The users opinion of this item (Like = 1, Dislike = -1, Default = 0)
	 */
	public int getOpinion() {
		return opinion;
	}

	/**
	 * @param opinion The users opinion of this item (Like = 1, Dislike = -1, Default = 0)
	 */
	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}

	/**
	 * @return Total number of likes this item has
	 */
	public int getNbrOfLikes() {
		return nbrOfLikes;
	}

	/**
	 * @param nbrOfLikes Total number of likes this item has
	 */
	public void setNbrOfLikes(int nbrOfLikes) {
		this.nbrOfLikes = nbrOfLikes;
	}

	/**
	 * @return Total number of dislikes this item has
	 */
	public int getNbrOfDislikes() {
		return nbrOfDislikes;
	}

	/**
	 * @param nbrOfDislikes Total number of dislikes this item has
	 */
	public void setNbrOfDislikes(int nbrOfDislikes) {
		this.nbrOfDislikes = nbrOfDislikes;
	}

	/**
	 * @return The content text of this item
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content text of this item
	 */
	public void setContent(String content) {
		this.content = content;
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
}
