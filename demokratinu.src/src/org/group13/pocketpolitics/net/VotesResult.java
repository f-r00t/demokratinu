package org.group13.pocketpolitics.net;

import java.util.List;

public class VotesResult {

	public final List<UtskottsForslag> fors;
	
	VotesResult(List<UtskottsForslag> fors){
		this.fors = fors;
	}
}
