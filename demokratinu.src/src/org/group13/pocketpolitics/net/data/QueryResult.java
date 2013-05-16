package org.group13.pocketpolitics.net.data;

import java.util.List;

import org.group13.pocketpolitics.model.riksdag.Article;


public class QueryResult {
	private final int thisPage;
	private final int totalPages;
	private final int totalTraffar;
	private final List<Article> arts;
	
	/**
	 * 
	 * @param arts
	 * @param totalPages
	 * @param thisPage
	 * @param totalTraffar 
	 */
	public QueryResult(List<Article> arts, int totalPages, int thisPage, int totalTraffar){
		this.arts = arts;
		this.totalPages = totalPages;
		this.thisPage = thisPage;
		this.totalTraffar = totalTraffar;
	}

	public int getThisPage() {
		return thisPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalTraffar() {
		return totalTraffar;
	}

	public List<Article> getArts() {
		return arts;
	}

}
