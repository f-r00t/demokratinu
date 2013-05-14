package org.group13.pocketpolitics.control;

import java.util.ArrayList;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.Retriever;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleActivity extends Activity implements ActivityNetInterface<String>{
	private TextView titleTextView;
	private ListView listViewMotions;
	//private ArrayList<Motion> motionList = new ArrayList<Motion>();
	
	private Article article;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		orderVotings();
		
		titleTextView = (TextView)findViewById(R.id.activityArticleTitle);
		titleTextView.setText("Bazinga!");
	}

	public void setTitle(String title){
		titleTextView.setText(title);
	}

	/*private void setAdapter() {
		listViewMotions.setAdapter(new MotionListAdapter(this, motionList));
	}*/

	private void orderVotings(){
		if(Retriever.isConnected(this)){
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
