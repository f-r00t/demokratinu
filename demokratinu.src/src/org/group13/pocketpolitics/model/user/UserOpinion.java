package org.group13.pocketpolitics.model.user;

public class UserOpinion extends LikeableItem{
	
	public UserOpinion(int myOpinion, int totalLike, int totalDislike){
		super(false, myOpinion, totalLike, totalDislike);
	}

}
