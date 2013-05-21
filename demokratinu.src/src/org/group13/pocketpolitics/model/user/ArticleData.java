package org.group13.pocketpolitics.model.user;

import java.util.List;
import java.util.Map;

public class ArticleData {

	//private int interest;
	
	private final List<Comment> replies;
	private Map<String, UserOpinion> cpmap;
	
	
	ArticleData(List<Comment> replies){
		this.replies = replies;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	public Map<String, UserOpinion> getCpmap() {
		return cpmap;
	}

	public void setCpmap(Map<String, UserOpinion> cpmap) {
		this.cpmap = cpmap;
	}
}
