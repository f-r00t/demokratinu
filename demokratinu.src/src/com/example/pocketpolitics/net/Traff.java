package com.example.pocketpolitics.net;

class Traff {

	public final int traffnummer;
	public final String datum;
	public final String id;
	public final String titel;
	public final String rm;
	public final String relaterat_id;
	public final String beteckning;
	public final double score;
	public final String notisrubrik;
	public final String notis;
	public final String beslutsdag;
	public final int beslutad;

	private Traff(int traffnummer,
			String datum,
			String id,
			String titel,
			String rm,
			String relaterat_id,
			String beteckning,
			double score,
			String notisrubrik,
			String notis,
			String beslutsdag,
			int beslutad){

		this.traffnummer = traffnummer;
		this.datum = datum;
		this.id = id;
		this.titel = titel;
		this.rm = rm;
		this.relaterat_id =relaterat_id;
		this.beteckning = beteckning;
		this.score=score;
		this.notisrubrik=notisrubrik;
		this.notis=notis;
		this.beslutsdag=beslutsdag;
		this.beslutad=beslutad;
	}
}
