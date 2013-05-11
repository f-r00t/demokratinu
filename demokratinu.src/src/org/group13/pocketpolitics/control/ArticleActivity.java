package org.group13.pocketpolitics.control;

import java.util.ArrayList;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.model.Committee;
import org.group13.pocketpolitics.model.Motion;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.QueryResult;
import org.group13.pocketpolitics.net.Retriever;
import org.group13.pocketpolitics.view.ArticleListAdapter;
import org.group13.pocketpolitics.view.MotionListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleActivity extends Activity implements ActivityNetInterface<QueryResult>{
	private TextView titleTextView;
	private ListView listViewMotions;
	private ArrayList<Motion> motionList = new ArrayList<Motion>();
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		titleTextView = (TextView)findViewById(R.id.activityArticleTitle);
		titleTextView.setText("Bazinga!");
	}

	public void setTitle(String title){
		titleTextView.setText(title);
	}

	private void orderPage(int p){
		if(Retriever.isConnected(this)){
			Retriever.retrieveArticles(this, "", "", p, -1, Committee.Arbetsmarknad);
		}
	}

	private void setAdapter() {
		listViewMotions.setAdapter(new MotionListAdapter(this, motionList));
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
	public void onSuccess(QueryResult result) {
		Log.i(this.getClass().getSimpleName(),"PocketDebug: Recieved page no "+result.getThisPage());
		motionList.addAll(result.getArts());
		setAdapter();
	}

	@Override
	public void onFailure(String message) {
		Log.w(this.getClass().getSimpleName(),"PocketDebug: Recieve articles failed: "+message);
		
	}

}
