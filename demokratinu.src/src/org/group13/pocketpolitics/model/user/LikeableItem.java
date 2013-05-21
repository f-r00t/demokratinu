package org.group13.pocketpolitics.model.user;

abstract class LikeableItem {

	private int myOpinion;
	private int totalLikes;
	private int totalDislikes;
	private boolean isHidden;
	

	LikeableItem(boolean isHidden, int myOpinion, int totalLikes, int totalDislikes) {
		
		this.isHidden = isHidden;
		this.myOpinion = myOpinion;
		this.totalLikes = totalLikes;
		this.totalDislikes = totalDislikes;
		
	}
	
	/**
	 * Sets this item to be liked for the current user (If it was previously liked it will now be neutral)
	 */
	public void like() {
		switch(myOpinion) {
			case -1:
				totalDislikes--;
				totalLikes++;
				myOpinion = 1;
				break;
			case 1:
				totalLikes--;
				myOpinion = 0;
				break;
			default:
				totalLikes++;
				myOpinion = 1;
				break;
		}
	}
	
	/**
	 * Sets this item to be disliked for the current user (If it was previously disliked it will now be neutral)
	 */
	public void dislike() {
		switch(myOpinion) {
			case -1:
				totalDislikes--;
				myOpinion = 0;
				break;
			case 1:
				totalLikes--;
				totalDislikes++;
				myOpinion = -1;
				break;
			default:
				totalDislikes++;
				myOpinion = -1;
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
	 * @return The users myOpinion of this item (Like = 1, Dislike = -1, Default = 0)
	 */
	public int getmyOpinion() {
		return myOpinion;
	}

	/**
	 * @param myOpinion The users myOpinion of this item (Like = 1, Dislike = -1, Default = 0)
	 */
	public void setmyOpinion(int myOpinion) {
		this.myOpinion = myOpinion;
	}

	/**
	 * @return Total number of likes this item has
	 */
	public int gettotalLikes() {
		return totalLikes;
	}

	/**
	 * @param totalLikes Total number of likes this item has
	 */
	public void settotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}

	/**
	 * @return Total number of dislikes this item has
	 */
	public int gettotalDislikes() {
		return totalDislikes;
	}

	/**
	 * @param totalDislikes Total number of dislikes this item has
	 */
	public void settotalDislikes(int totalDislikes) {
		this.totalDislikes = totalDislikes;
	}

	
}
