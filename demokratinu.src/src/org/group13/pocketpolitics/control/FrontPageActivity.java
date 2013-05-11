package org.group13.pocketpolitics.control;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.R.id;
import org.group13.pocketpolitics.R.layout;
import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.model.Committee;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.QueryResult;
import org.group13.pocketpolitics.net.Retriever;
import org.group13.pocketpolitics.view.ArticleListAdapter;

public class FrontPageActivity extends Activity implements ActivityNetInterface<QueryResult> {

	private ListView listViewArticles;
	private ArrayList<Article> articleList = new ArrayList<Article>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_front_page);
		
		
		
		listViewArticles = (ListView) findViewById(R.id.article_list);
		listViewArticles.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Code goes here
				Toast.makeText(getApplicationContext(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();

			}
		});

		// Replace this with the factory later, really ugly code incoming:
		/*
		Article a = new Article();
		a.setTitle("Artikel A");
		a.setNbrOfLikes(1000);
		a.setNbrOfDislikes(100);
		articleList.add(a);

		Article b = new Article();
		b.setTitle("Artikel B");
		b.setNbrOfLikes(2000);
		b.setNbrOfDislikes(200);
		articleList.add(b);

		Article c = new Article();
		c.setTitle("Artikel C");
		c.setNbrOfLikes(3000);
		c.setNbrOfDislikes(300);
		articleList.add(c);
		*/
		
		orderPage(1);

		setAdapter();
		
	}
	
	private void orderPage(int p){
		if(Retriever.isConnected(this)){
			Retriever.retrieveArticles(this, "", "", p, -1, Committee.Arbetsmarknad);
		}
	}

	private void setAdapter() {
		listViewArticles.setAdapter(new ArticleListAdapter(this, articleList));
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
		articleList.addAll(result.getArts());
		setAdapter();
	}

	@Override
	public void onFailure(String message) {
		Log.w(this.getClass().getSimpleName(),"PocketDebug: Recieve articles failed: "+message);
		
	}

}
