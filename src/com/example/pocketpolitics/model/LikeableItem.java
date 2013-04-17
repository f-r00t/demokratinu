package com.example.pocketpolitics.model;

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
	
	public void comment() {
		// TODO
	}
	
	public void hide(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	
	public Boolean isHidden() {
		return isHidden;
	}
	
	public void reply(Comment comment) {
		this.replies.add(comment);
	}
	
	public Comment[] getReplies() {
		return (Comment[]) this.replies.toArray();
	}
	
	public int getOpinion() {
		return opinion;
	}

	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}

	public int getNbrOfLikes() {
		return nbrOfLikes;
	}

	public void setNbrOfLikes(int nbrOfLikes) {
		this.nbrOfLikes = nbrOfLikes;
	}

	public int getNbrOfDislikes() {
		return nbrOfDislikes;
	}

	public void setNbrOfDislikes(int nbrOfDislikes) {
		this.nbrOfDislikes = nbrOfDislikes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
