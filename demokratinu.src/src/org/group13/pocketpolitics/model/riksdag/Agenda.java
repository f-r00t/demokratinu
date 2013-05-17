package org.group13.pocketpolitics.model.riksdag;

import java.util.List;

public class Agenda extends Document{

	private final String summary;
	
	private final int traffnummer;
	private final String datum;
	private final String id;
	private final String relaterat_id;
	private final double score;
	private final String notisrubrik;
	private final String beslutsdag;
	private final int beslutad;
	
	private List<CommitteeProposal> fors;
	
	/**
	 * Ett betänkande
	 * 
	 * @param title
	 * @param beteckning	beteckning: "UbU5"
	 * @param traffnummer
	 * @param datum
	 * @param id	fullt id: "H001UbU5"
	 * @param rm	"2012/13"
	 * @param relaterat_id
	 * @param score	3.6777
	 * @param notisrubrik
	 * @param beslutsdag
	 * @param beslutad
	 */
	public Agenda(String summary, String title, String beteckning, int traffnummer,
			String datum, String id, String rm, String relaterat_id,
			double score, String notisrubrik, String beslutsdag, int beslutad,
			List<CommitteeProposal> fors) {
		super(beteckning, rm, title);
		
		this.summary = summary;
		this.traffnummer = traffnummer;
		this.datum = datum;
		this.id = id;
		this.relaterat_id =relaterat_id;
		this.score=score;
		this.notisrubrik=notisrubrik;
		this.beslutsdag=beslutsdag;
		this.beslutad=beslutad;
		
		this.fors=fors;
	}
	
	public void setFors(List<CommitteeProposal> fors) {
		this.fors = fors;
	}

	public int getTraffnummer() {
		return traffnummer;
	}

	public String getDatum() {
		return datum;
	}

	public String getId() {
		return id;
	}
	public String getRelaterat_id() {
		return relaterat_id;
	}

	public double getScore() {
		return score;
	}

	public String getNotisrubrik() {
		return notisrubrik;
	}

	public String getBeslutsdag() {
		return beslutsdag;
	}

	public int getBeslutad() {
		return beslutad;
	}

	public List<CommitteeProposal> getFors() {
		return fors;
	}


	public String getSummary() {
		return summary;
	}
}
