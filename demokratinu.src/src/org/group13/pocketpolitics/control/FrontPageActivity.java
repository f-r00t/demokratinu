package org.group13.pocketpolitics.control;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.Article;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.Retriever;
import org.group13.pocketpolitics.net.data.QueryParam;
import org.group13.pocketpolitics.net.data.QueryResult;
import org.group13.pocketpolitics.view.ArticleListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FrontPageActivity extends Activity implements ActivityNetInterface<QueryResult> {

	public static final String ARTICLE_NUM_SENT = "org.group13.pocketpolitics.control.FrontPage.ARTICLE_NUM";

	private ListView listViewArticles;
	private List<Article> articleList = new ArrayList<Article>();

	@Override
	protected void onResume(){
		super.onResume();
		if(ArticleMemoryController.articles().isEmpty()){
			orderNextPage();
			//setAdapter();
		}
	}

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

				Intent intent = new Intent(getApplicationContext(), 
						ArticleActivity.class).putExtra(ARTICLE_NUM_SENT, articleList.get(position));
				//TODO 
				// fungerar det?
				startActivity(intent);
			}
		});
	}

	private void orderNextPage(){
		if(Retriever.isConnected(this)){
			QueryParam qpar = ArticleMemoryController.nextQuery();
			if(qpar !=null){
				Retriever.retrieveArticles(this, qpar);
			}
			
		}
	}

	private void setAdapter() {
		listViewArticles.setAdapter(new ArticleListAdapter(this, articleList));
	}

	@Override
	public void onPreExecute() {
		// TODO Skapa snurrande hjul någonstans i guit

	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(QueryResult result) {
		Log.i(this.getClass().getSimpleName(),"PocketDebug: Recieved page no "+result.getThisPage());

		ArticleMemoryController.retrievedPage(result);
		articleList=ArticleMemoryController.articles();
		setAdapter();
	}

	@Override
	public void onFailure(String message) {
		Log.w(this.getClass().getSimpleName(),"PocketDebug: Recieve articles failed: "+message);

	}

}
