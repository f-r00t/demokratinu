package com.example.pocketpolitics.model;

import java.util.HashMap;

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
