package org.group13.pocketpolitics.model.user;

import java.util.List;

public class ArticleData {

	private final List<Comment> replies;
	//private int interest;
	
	ArticleData(List<Comment> replies){
		this.replies = replies;
	}

	public List<Comment> getReplies() {
		return replies;
	}
}
