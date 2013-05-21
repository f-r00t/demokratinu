package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.Agenda;
import org.group13.pocketpolitics.net.Connected;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleActivity extends Activity implements ActivityNetInterface<String>{
	private TextView titleTextView;
	private TextView textTextView;
	private ListView listViewMotions;
	//private ArrayList<Motion> motionList = new ArrayList<Motion>();
	
	private Agenda article;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		//article = (Article) getIntent().getSerializableExtra("Article");
		
		int ix = getIntent().getIntExtra(FrontPageActivity.ARTICLE_NUM_SENT, -1);
		article = ArticleMemoryController.article(ix);
		if(article == null){
			Log.w(this.getClass().getSimpleName(), "PocketDebug: in onCreate(): article null at position "+ix);
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		orderVotings();
		
		titleTextView = (TextView)findViewById(R.id.activityArticleTitle);
		titleTextView.setText(article.getTitle());
		
		textTextView = (TextView)findViewById(R.id.activityArticleText);
		textTextView.setText(article.getSummary());
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
	

	public void setTitle(String title){
		titleTextView.setText(title);
	}

	/*private void setAdapter() {
		listViewMotions.setAdapter(new MotionListAdapter(this, motionList));
	}*/

	/**
	 * Retrives the CommitteeProposals for the article and their respective votes
	 */
	private void orderVotings(){
		if(Connected.isConnected(this)){
			Retriever.retrieveVotes(this, article);
		}
	}
	
	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(String message) {
		Log.w(this.getClass().getSimpleName(),"PocketDebug: Recieve articles failed: "+message);
		
	}

	@Override
	public void onSuccess(String result) {
		Log.i(this.getClass().getSimpleName(), "PocketDebug: Votes retrieved for article "+result);
		
	}

}
