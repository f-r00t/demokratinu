package org.group13.pocketpolitics.control;

import java.util.List;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.CommitteeProposal;
import org.group13.pocketpolitics.model.user.Article;
import org.group13.pocketpolitics.net.Connected;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;
import org.group13.pocketpolitics.view.CommitteeProposalListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ArticleActivity extends Activity implements ActivityNetInterface<String>{
	
	public static final String ARTICLE_NUM_SENT = "org.group13.pocketpolitics.control.ArticleActivity.ARTICLE_NUM";
	
	private TextView titleTextView;
	private TextView textTextView;
	
	private ExpandableListView listViewCommitteeProposal;
	private View articleHeader;
	private View progressBar;
	
	private Article article;


	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		this.progressBar = findViewById(R.id.progressBarContainer);
		
		int ix = getIntent().getIntExtra(ArticleActivity.ARTICLE_NUM_SENT, -1);
		article = ArticleMemoryController.article(ix);
		if(article == null){
			Log.w(this.getClass().getSimpleName(), "PocketDebug: in onCreate(): article null at position "+ix);
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		orderVotings();
		
		listViewCommitteeProposal = (ExpandableListView) findViewById(R.id.detailViewVotesList);
		LayoutInflater inflater = LayoutInflater.from(this);
	    articleHeader = inflater.inflate(R.layout.article_header, null);
	    listViewCommitteeProposal.addHeaderView(articleHeader);
		
		titleTextView = (TextView)findViewById(R.id.activityArticleTitle);
		titleTextView.setText(article.getAgenda().getTitle());
			
		textTextView = (TextView)findViewById(R.id.activityArticleText);
		textTextView.setText(article.getAgenda().getSummary());
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 * 
	 * This is for the Up button to work
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Retrives the CommitteeProposals for the article and their respective votes
	 */
	private void orderVotings(){
		if(Connected.isConnected(this)){
			Retriever.retrieveVotes(this, article.getAgenda());
		}
	}
	
	@Override
	public void onPreExecute() {
		this.progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// ignore, anropas i nuläget aldrig
	}

	@Override
	public void onFailure(String message) {
		this.progressBar.setVisibility(View.GONE);
		Log.w(this.getClass().getSimpleName(),"PocketDebug: Recieve articles failed: "+message);
		
	}

	@Override
	public void onSuccess(String result) {
		this.progressBar.setVisibility(View.GONE);
		Log.i(this.getClass().getSimpleName(), "PocketDebug: Votes retrieved for article "+result);
		//listComPro =  article.getAgenda().getFors();
		
		setAdapter();
	}
	
	private void setAdapter() {
		List<CommitteeProposal> listComPro = article.getAgenda().getFors();
		Log.i("Viking","Before setAdapter: "+listComPro.size());
		listViewCommitteeProposal.setAdapter(new CommitteeProposalListAdapter(this, listComPro));
	}
}
