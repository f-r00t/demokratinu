package org.group13.pocketpolitics.control;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.Agenda;
import org.group13.pocketpolitics.model.user.Account;
import org.group13.pocketpolitics.net.Connected;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;
import org.group13.pocketpolitics.net.riksdag.data.QueryParam;
import org.group13.pocketpolitics.net.riksdag.data.QueryResult;
import org.group13.pocketpolitics.view.ArticleListAdapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FrontPageActivity extends Activity implements ActivityNetInterface<QueryResult> {

	public static final String ARTICLE_NUM_SENT = "org.group13.pocketpolitics.control.FrontPage.ARTICLE_NUM";

	private ListView listViewArticles;
	private List<Agenda> articleList = new ArrayList<Agenda>();

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("testing", "1");
		if(ArticleMemoryController.articles().isEmpty()){
			orderNextPage();
			setAdapter();
			Log.e("testing", "2");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e("testing", "3");
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_front_page);
		
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
				//TODO 
				// fungerar det?
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
		editor.apply();
		
		ArticleMemoryController.flush();
		
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
