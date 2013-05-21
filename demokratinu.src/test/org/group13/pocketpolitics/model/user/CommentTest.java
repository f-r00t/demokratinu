package org.group13.pocketpolitics.model.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.Gson;

public class CommentTest extends AndroidTestCase{
	
	public CommentTest(){
		super();
	}
	
	public void atestArticleBuilder(){
		
		List<Comment> listc = new ArrayList<Comment>();
		
		for(int i=0; i<5; i++){
			listc.add(recur(i%2+1, 2,"top "+i));
		}
		ArticleData d = new ArticleData(listc);
		Map<String, UserOpinion> m = new HashMap<String, UserOpinion>();
		
		for(int i=0; i<5; i++){
			m.put("H001UbU5#"+(i+1), new UserOpinion(337-2*i, 152+i, 2*(i%2)-1));
		}
		
		d.setCpmap(m);
		
		//printArticleData(d);
		
		Gson g = new Gson();
		String json = g.toJson(d.getReplies().get(0));
		Log.w(this.getClass().getSimpleName(), "PocketDebug: Gson from ArticleData: "+json);
	}
	
	public void testCommentBuilder(){
		int level = 2;
		int width = 3;
		
		Comment tree = recur(level, width, "Top of the Tree!");
		
		//print(tree);
		Log.w(this.getClass().getSimpleName(), "PocketDebug: gson: "+gson(tree));
	}
	
	private String gson(Comment tree){
		Gson gs = new Gson();
		return gs.toJson(tree);
	}
	
	/**
	 * Recursively creates comment trees
	 * @param level levels of the tree
	 * @param width width per level
	 * @return
	 */
	private Comment recur(int level, int width, String content){
		if(level==0){
			return null;
		}
		
		Comment ret = new Comment();
		ret.setAuthor("Level "+level);
		ret.setContent(content);
		
		for(int i=0; i<width; i++){
			Comment sub = recur(level-1, width, "Sibling no "+i);
			if(sub!=null){
				ret.reply(sub);
			}
		}
		
		return ret;
	}
	
	/**
	 * recursive print
	 * @param head
	 */
	private void print(Comment head){
		
		Log.i(this.getClass().getSimpleName(), "PocketDebug: comment author "+head.getAuthor());
		Log.i(this.getClass().getSimpleName(), "PocketDebug: comment content "+head.getContent());
		
		List<Comment> children = head.getReplies();
		for(Comment c : children){
			print(c);
		}
	}
	
	private void printArticleData(ArticleData d){
		Log.w(this.getClass().getSimpleName(), "PocketDebug: ArticleData");
		for(Comment c : d.getReplies()){
			print(c);
		}
		
	}

}
