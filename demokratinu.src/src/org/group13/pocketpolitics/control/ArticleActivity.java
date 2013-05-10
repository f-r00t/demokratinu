package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ArticleActivity extends Activity {
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
}
