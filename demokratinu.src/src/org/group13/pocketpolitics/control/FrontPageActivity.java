package org.group13.pocketpolitics.control;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.user.Account;
import org.group13.pocketpolitics.model.user.Article;
import org.group13.pocketpolitics.net.Connected;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;
import org.group13.pocketpolitics.net.riksdag.data.QueryParam;
import org.group13.pocketpolitics.net.riksdag.data.QueryResult;
import org.group13.pocketpolitics.view.ArticleListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FrontPageActivity extends Activity implements ActivityNetInterface<QueryResult> {

	public static final String ARTICLE_NUM_SENT = "org.group13.pocketpolitics.control.FrontPage.ARTICLE_NUM";

	private ListView listViewArticles;
	private List<Article> articleList = new ArrayList<Article>();
	
	private View progressBar;

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(this.getClass().getSimpleName(), "PocketDebug: testing 1");
		if(ArticleMemoryController.articles().isEmpty()){
			orderNextPage();
			setAdapter();
			Log.i(this.getClass().getSimpleName(), "PocketDebug: testing 2");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(this.getClass().getSimpleName(), "PocketDebug: testing 3");
		
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_front_page);
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.pp_titlebar);
		progressBar = findViewById(R.id.progressBarContainer);
		
		listViewArticles = (ListView) findViewById(R.id.article_list);
		listViewArticles.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Code goes here
				/*Toast.makeText(getApplicationContext(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();
				*/
				Intent intent = new Intent(getApplicationContext(), 
						ArticleActivity.class).putExtra(ARTICLE_NUM_SENT, position);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_logout:
	            logout();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void logout() {
		SharedPreferences prefs = this.getSharedPreferences("org.group13.pocketpolitics", 0);
		Editor editor = prefs.edit();
		editor.putBoolean("org.group13.pocketpolitics.stayloggedin", false);
		editor.putString("org.group13.pocketpolitics.email", "");
		editor.putString("org.group13.pocketpolitics.password", "");
		editor.commit();
		
		Account.set("", "", "");
		
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	private void orderNextPage(){
		if(Connected.isConnected(this)){
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
	public void onDestroy() {
		super.onDestroy();
		ArticleMemoryController.flush();
	}
	
	@Override
	public void onPreExecute() {
		progressBar.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onProgressUpdate(Integer procent) {
	}

	@Override
	public void onSuccess(QueryResult result) {
		Log.i(this.getClass().getSimpleName(),"PocketDebug: Recieved page no "+result.getThisPage());

		progressBar.setVisibility(View.GONE);
		
		ArticleMemoryController.retrievedPage(result);
		articleList=ArticleMemoryController.articles();
		setAdapter();
	}

	@Override
	public void onFailure(String message) {
		Log.w(this.getClass().getSimpleName(),"PocketDebug: Recieve articles failed: "+message);
		progressBar.setVisibility(View.GONE);
	}

}
