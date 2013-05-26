package org.group13.pocketpolitics.control;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.Moprosition;
import org.group13.pocketpolitics.model.user.ArticleData;
import org.group13.pocketpolitics.net.Connected;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;
import org.group13.pocketpolitics.net.server.ServerInterface;
import org.group13.pocketpolitics.net.server.Syncer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MoprositionActivity extends Activity implements ActivityNetInterface<Moprosition>, ServerInterface{
	public static final String MOPR_YEAR_SENT = "org.group13.pocketpolitics.control.MoprositionActivity.sent_year";
	public static final String MOPR_ID_SENT = "org.group13.pocketpolitics.control.MoprositionActivity.sent_id";
	
	private String year;
	private String id;
	
	private Moprosition mopr;
	private ToggleButton likeBtn;
	private ToggleButton dislikeBtn;
	private TextView likeDislikeTV;
	
	
	private int totalLikes = -1;
	private int totalDislikes = -1;
	private int myOpinion = 0;
	private int prevOpinion = 0;
	
	private WebView webView;
	
	private View progressBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moprosition);
		
		setupActionBar();
		
		this.progressBar = findViewById(R.id.progressBarContainer);
		this.webView = (WebView) findViewById(R.id.moprosition_web_view);
		
		this.mopr = null;
		this.year = getIntent().getStringExtra(MoprositionActivity.MOPR_YEAR_SENT);
		this.id = getIntent().getStringExtra(MoprositionActivity.MOPR_ID_SENT);
		
		if(isMotion(this.id)){
			setTitle(getString(R.string.label_activity_motion));
		} else {
			setTitle(getString(R.string.label_activity_proposition));
		}
		
		orderMoprosition();
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // The up-button
			// finish() to jump to the previous activity. We can't
			// return to it the normal way because there is more than one
			// possible parent activity as long as the manifest is aware.
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private static boolean isMotion(String id){
		boolean motion = false;
		if(id.matches("\\D+\\d*")){
			motion = true;
		}		
		return motion;
	}
	
	/**
	 * Order Moprosition, ex 2012/13:Ub5
	 * @param year ex above: "2012/13"
	 * @param id ex above: "Ub5"
	 */
	private void orderMoprosition(){
		if(Connected.isConnected(this)){
			Retriever.retrieveMoprosition(this, this.year, this.id);
			Syncer.getOpinions(this, idOnServer());
		}
	}
	
	/**
	 * 
	 * @return code to identify this motion/proposition in the database
	 */
	private String idOnServer(){
		return year+":"+id;
	}
	
	private void showTotals(){
		likeDislikeTV = (TextView) findViewById(R.id.likeDislikeMoproTV);
		
		String likes = ""+totalLikes;
		if(totalLikes == -1){
			likes ="?";
		}
		String dislikes = ""+totalDislikes;
		if(totalDislikes == -1){
			dislikes = "?";
		}
		
	    likeDislikeTV.setText("Likes: "+likes+" Dislikes: "+dislikes);
	}

	@Override
	public void onPreExecute() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// ignore
	}
	
	@Override
	public void onSuccess(Moprosition result) {
		this.mopr = result;
		
		likeBtn = (ToggleButton) findViewById(R.id.likeMoproBtn);
	    dislikeBtn = (ToggleButton) findViewById(R.id.dislikeMoproBtn);
	    showTotals();
	    
	    OnClickListener changeChecker = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v==likeBtn){
					if (dislikeBtn.isChecked()){
						dislikeBtn.setChecked(false);
					}
				} else if (v==dislikeBtn) {
					if (likeBtn.isChecked()){
						likeBtn.setChecked(false);
					}
				}
				
				prevOpinion = myOpinion;
				
				myOpinion = 0;
				if(likeBtn.isChecked()){
					myOpinion = 1;
				} else if(dislikeBtn.isChecked()){
					myOpinion = -1;
				}
				
				if(prevOpinion!=1 && myOpinion==1){
					totalLikes++;
				} else if(prevOpinion==1 && myOpinion!=1){
					totalLikes--;
				}
				
				if(prevOpinion!=-1 && myOpinion==-1){
					totalDislikes++;
				} else if(prevOpinion==-1 && myOpinion!=-1){
					totalDislikes--;
				}
				
				showTotals();
				Syncer.postOpinion(getThisInstance(), idOnServer(), myOpinion);
			}
		};
		
	    likeBtn.setOnClickListener(changeChecker);
	    dislikeBtn.setOnClickListener(changeChecker);
	    

		// this is also a net-operation, show progressindicator until text is displayed
		webView.loadUrl(mopr.getTextURL());
		progressBar.setVisibility(View.GONE);

	}
	
	private MoprositionActivity getThisInstance(){
		return this;
	}
	
	private void updateButtons(){
		likeBtn.setChecked(this.myOpinion>0);
		dislikeBtn.setChecked(this.myOpinion<0);
	}

	@Override
	public void onFailure(String message) {
		Log.w(this.getClass().getSimpleName(), "PocketDebug: Retrieve moprosition failed: "+message);
		progressBar.setVisibility(View.GONE);
		
		webView.loadData("<p>Hämtningen misslyckades.</p>", "text/html", "UTF-8");
	}

	@Override
	public void postOpinionReturned(boolean succeded) {
		if(!succeded){
			Syncer.getOpinions(this, idOnServer());
		}
	}
	
	@Override
	public void getOpinionsReturned(boolean succeded, int myOpinion, 
			int totalLike, int totalDislike) {
		
		if(succeded){
			this.myOpinion = myOpinion;
			this.totalLikes = totalLike;
			this.totalDislikes = totalDislike;
			
			this.updateButtons();
			this.showTotals();
		}
	}
	
	@Override
	public void operationFailed(String oper) {
		Log.e(this.getClass().getSimpleName(), "PocketDebug: server operation failed: "+oper); 
	}
	
	@Override
	public void getArticleDataReturned(ArticleData data) {
		// ignore
	}
	
	
	
	@Override
	public void authenticateReturned(boolean succeded, String username) {
		// ignore
	}

	@Override
	public void registrationReturned(boolean succeded, boolean unameExists,
			boolean emailExists) {
		// ignore
	}

	

	@Override
	public void postCommentReturned(boolean succeded) {
		// ignore
		
	}

	
}


