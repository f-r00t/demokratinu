package org.group13.pocketpolitics.control;

import java.util.ArrayList;

import org.group13.pocketpolitics.model.Article;

import org.group13.pocketpolitics.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author Group 13
 */

public class ArticleListAdapter extends BaseAdapter{

	LayoutInflater inflater;
	ArrayList<Article> items;

      public ArticleListAdapter(Activity context, ArrayList<Article> items) {  
		    super();
			
		    this.items = items;
		    this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }
      
      @Override  
      public View getView(final int position, View convertView, ViewGroup parent) {  
    	  
    	Article article = items.get(position);  
        View vi=convertView;
          
          if(convertView==null){
              vi = inflater.inflate(R.layout.article_list_item, null);
          }
          //Sets the title text
          TextView articleTitle = (TextView) vi.findViewById(R.id.articleTitle);
          articleTitle.setText(article.getTitle());
          
          
          //Sets the number of likes
          TextView textViewLikes = (TextView) vi.findViewById(R.id.TextViewLikes);
          textViewLikes.setText(Integer.toString(article.getNbrOfLikes())); 

          //Sets the number of dislikes
          TextView textViewDislikes = (TextView) vi.findViewById(R.id.TextViewDislikes);
          textViewDislikes.setText(Integer.toString(article.getNbrOfDislikes()));
          
          return vi;  
      }

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
}