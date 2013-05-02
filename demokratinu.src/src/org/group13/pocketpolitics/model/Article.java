package org.group13.pocketpolitics.model;

import java.util.Date;
import java.util.List;

public class Article extends LikeableItem {

	private String title;
	private String dokid;
	
	private int traffnummer;
	private String datum;
	private String id;
	private String rm;
	private String relaterat_id;
	private double score;
	private String notisrubrik;
	private String beslutsdag;
	private int beslutad;
	
	/**
	 * Ett betänkande
	 * 
	 * @param content
	 * @param date
	 * @param isHidden
	 * @param opinion
	 * @param nbrOfLikes
	 * @param nbrOfDislikes
	 * @param replies
	 * @param title
	 * @param dokid	beteckning: "UbU5"
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
	public Article(String content, Date date, boolean isHidden, int opinion,
			int nbrOfLikes, int nbrOfDislikes, List<Comment> replies, 
			String title, String dokid,
			int traffnummer,
			String datum,
			String id,
			String rm,
			String relaterat_id,
			double score,
			String notisrubrik,
			String beslutsdag,
			int beslutad) {
		
		super(content, date, isHidden, opinion, nbrOfLikes, nbrOfDislikes, replies);
		
		this.title = title;
		this.dokid = dokid;
		
		this.traffnummer = traffnummer;
		this.datum = datum;
		this.id = id;
		this.rm = rm;
		this.relaterat_id =relaterat_id;
		this.score=score;
		this.notisrubrik=notisrubrik;
		this.beslutsdag=beslutsdag;
		this.beslutad=beslutad;
	}
	
	// Temporary constructor
	public Article() {
		super();
	}
	
	public int getTraffnummer() {
		return traffnummer;
	}

	public void setTraffnummer(int traffnummer) {
		this.traffnummer = traffnummer;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getRelaterat_id() {
		return relaterat_id;
	}

	public void setRelaterat_id(String relaterat_id) {
		this.relaterat_id = relaterat_id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getNotisrubrik() {
		return notisrubrik;
	}

	public void setNotisrubrik(String notisrubrik) {
		this.notisrubrik = notisrubrik;
	}

	public String getBeslutsdag() {
		return beslutsdag;
	}

	public void setBeslutsdag(String beslutsdag) {
		this.beslutsdag = beslutsdag;
	}

	public int getBeslutad() {
		return beslutad;
	}

	public void setBeslutad(int beslutad) {
		this.beslutad = beslutad;
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
