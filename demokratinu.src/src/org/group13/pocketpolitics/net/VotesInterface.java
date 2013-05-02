package org.group13.pocketpolitics.net;

/**
 * @deprecated
 * @author Leif
 *
 */
public interface VotesInterface {

	public void onVotesPreExecute();
	public void handleVotes(VotesResult votes);
	public void votesCancelled(VotesResult votes);
}
