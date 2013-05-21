package org.group13.pocketpolitics.net.riksdag;

import java.util.List;

import org.group13.pocketpolitics.model.riksdag.Agenda;
import org.group13.pocketpolitics.net.riksdag.data.QueryResult;

/**
 * @deprecated
 * @author Leif
 *
 */
interface ArtActivityInterface {

	public void onArticlesPreExecute();
	
	/**
	 * @deprecated
	 * @param arts
	 */
	public void addArticles(List<Agenda> arts);
	public void addArticles(QueryResult qres);
	
	public void articlesCancelled(QueryResult qres);
}
