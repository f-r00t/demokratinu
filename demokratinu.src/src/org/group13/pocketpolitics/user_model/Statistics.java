package org.group13.pocketpolitics.user_model;

import java.util.HashMap;

import org.group13.pocketpolitics.model.riksdag.Party;

public class Statistics {

	private HashMap<Party, Integer> statistics;
	private int NbrOfOpinions;
	
	public void addOpinion(Party party, int opinion) {
		// TODO
	}
	
	public void removeOpinion(Party party, int opinion) {
		// TODO
	}
	
	public HashMap<Party, Integer> getStatistics() {
		return statistics;
	}
	
	public void setStatistics(HashMap<Party, Integer> statistics) {
		this.statistics = statistics;
	}
	
	public int getNbrOfOpinions() {
		return NbrOfOpinions;
	}
	
	public void setNbrOfOpinions(int nbrOfOpinions) {
		NbrOfOpinions = nbrOfOpinions;
	}
}
