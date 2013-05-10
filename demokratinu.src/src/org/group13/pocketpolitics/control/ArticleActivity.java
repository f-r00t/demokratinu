package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ArticleActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		TextView title = (TextView)findViewById(R.id.activityArticleTitle);
		title.setText("Bazinga!");
	}
}
