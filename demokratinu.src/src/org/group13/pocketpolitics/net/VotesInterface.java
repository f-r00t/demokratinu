package org.group13.pocketpolitics.net;

public interface VotesInterface {

	public void onVotesPreExecute();
	public void handleVotes(VotesResult votes);
	public void votesCancelled(VotesResult votes);
}
