package org.group13.pocketpolitics.test.model;

import java.util.List;

import org.group13.pocketpolitics.model.Comment;

import android.test.AndroidTestCase;
import android.util.Log;

public class CommentTest extends AndroidTestCase{
	
	public CommentTest(){
		super();
	}
	
	public void testCommentBuilder(){
		Comment tree = recur(4, 3, "Top of the Tree!");
		
		print(tree);
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
		
		Log.i(this.getClass().getSimpleName(), "Leif: comment au "+head.getAuthor());
		Log.i(this.getClass().getSimpleName(), "Leif: comment cont "+head.getContent());
		
		List<Comment> children = head.getReplies();
		for(Comment c : children){
			print(c);
		}
	}

}
