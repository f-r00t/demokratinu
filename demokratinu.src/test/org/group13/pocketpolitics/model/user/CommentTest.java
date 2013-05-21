package org.group13.pocketpolitics.model.user;

import java.util.List;

import org.group13.pocketpolitics.model.user.Comment;

import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.Gson;

public class CommentTest extends AndroidTestCase{
	
	public CommentTest(){
		super();
	}
	
	public void testArticleBuilder(){
		
		Article a = new Article(null, null);
		
		for(int i=1; i<8; i++){
			
		}
	}
	
	public void testCommentBuilder(){
		int level = 2;
		int width = 3;
		
		Comment tree = recur(level, width, "Top of the Tree!");
		
		//print(tree);
		Log.w(this.getClass().getSimpleName(), "Leif: gson: "+gson(tree));
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
	@SuppressWarnings("unused")
	private void print(Comment head){
		
		Log.i(this.getClass().getSimpleName(), "Leif: comment au "+head.getAuthor());
		Log.i(this.getClass().getSimpleName(), "Leif: comment cont "+head.getContent());
		
		List<Comment> children = head.getReplies();
		for(Comment c : children){
			print(c);
		}
	}

}
