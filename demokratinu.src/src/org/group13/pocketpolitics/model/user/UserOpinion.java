package org.group13.pocketpolitics.model.user;

public class UserOpinion {

	private int totalDislike;
	private int totalLike;
	private int myOpinion;
	
	UserOpinion(int totalDislike, int totalLike, int myOpinion){
		this.totalDislike = totalDislike;
		this.totalLike = totalLike;
		this.myOpinion = myOpinion;
	}

	public int getTotalDislike() {
		return totalDislike;
	}

	public void setTotalDislike(int totalDislike) {
		this.totalDislike = totalDislike;
	}

	public int getTotalLike() {
		return totalLike;
	}

	public void setTotalLike(int totalLike) {
		this.totalLike = totalLike;
	}

	public int getMyOpinion() {
		return myOpinion;
	}

	public void setMyOpinion(int myOpinion) {
		this.myOpinion = myOpinion;
	}
}
