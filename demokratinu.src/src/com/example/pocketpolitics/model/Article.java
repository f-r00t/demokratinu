package com.example.pocketpolitics.model;

import java.util.Date;
import java.util.List;

public class Article extends LikeableItem {

	private String title;
	private String dokid;
	
	private int traffnummer;
	private String datum;
	private String id;
	private String titel;
	private String rm;
	private String relaterat_id;
	private String beteckning;
	private double score;
	private String notisrubrik;
	private String notis;
	private String beslutsdag;
	private int beslutad;
	
	public Article(String content, Date date, boolean isHidden, int opinion,
			int nbrOfLikes, int nbrOfDislikes, List<Comment> replies, 
			String title, String dokid,
			int traffnummer,
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
			int beslutad) {
		
		super(content, date, isHidden, opinion, nbrOfLikes, nbrOfDislikes, replies);
		
		this.title = title;
		this.dokid = dokid;
		
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
	
	// Temporary constructor
	public Article() {
		super();
	}


	/**
	 * @return The title (shorter description) of the article
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title (shorter description) of the article
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @return The document id of this article on the Riksdag database.
	 */
	public String getDokid() {
		return dokid;
	}
	
	/**
	 * 
	 * @param dokid The document id of this article on the Riksdag database.
	 */
	public void setDokid(String dokid) {
		this.dokid = dokid;
	}
}
