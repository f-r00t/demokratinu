package org.group13.pocketpolitics;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.control.ArticleListAdapter;
import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.net.ArtActivityInterface;
import org.group13.pocketpolitics.net.QueryResult;
import org.group13.pocketpolitics.net.Retriever;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.group13.pocketpolitics.R;


public class FrontPageActivity extends Activity implements ArtActivityInterface {


	private ListView listViewArticles;
	private ArrayList<Article> articleList = new ArrayList<Article>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front_page);
		listViewArticles =  ( ListView ) findViewById(R.id.article_list);
		listViewArticles.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				//Code goes here
			}
		});

		//Replace this with the factory later, really ugly code incoming:

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

		setAdapter();

		getMoreArticles();
	}


	@Override
	public void addArticles(List<Article> arts) {
		articleList.addAll(arts);
		setAdapter();
	}

	private void getMoreArticles(){
		/*ConnectivityManager conMgr = (ConnectivityManager)
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netwInfo = conMgr.getActiveNetworkInfo();
		
		if(netwInfo != null && netwInfo.isConnected()){*/
		if(Retriever.isConnected(this)){
			//new ArticleFromFeed().execute("");
			Retriever.retrieveRssArticleTitles(this);
		}
	}

	private void setAdapter(){
		listViewArticles.setAdapter(new ArticleListAdapter(this, articleList));
	}


	@Override
	public void addArticles(QueryResult qres) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void wasCancelled(QueryResult qres) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

}
