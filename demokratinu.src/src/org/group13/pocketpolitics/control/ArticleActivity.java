package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.QueryResult;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ArticleActivity extends Activity implements ActivityNetInterface<QueryResult>{
	private TextView titleTextView;
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		titleTextView = (TextView)findViewById(R.id.activityArticleTitle);
		titleTextView.setText("Bazinga!");
	}

	public void setTitle(String title){
		titleTextView.setText(title);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(String message) {
		// TODO Auto-generated method stub
		
	}
}
