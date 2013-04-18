package com.example.pocketpolitics.control;

import java.util.List;

import com.example.pocketpolitics.R;
import com.example.pocketpolitics.model.Article;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArticleListAdapter extends BaseAdapter{

      private int resource;
      private LayoutInflater inflater;
      private Context context;

	public ArticleListAdapter ( Context ctx, int resourceId, List objects) {
            super( );
            resource = resourceId;
            inflater = LayoutInflater.from( ctx );
            context=ctx;
      }

      @Override
      public View getView ( int position, View convertView, ViewGroup parent ) {

            /* create a new view of my layout and inflate it in the row */
            convertView = ( LinearLayout ) inflater.inflate( resource, null );

            /* Extract the article's object to show */
            Article article = (Article) getItem( position );

            /* Take the TextView from layout and set the city's name */
            TextView txtName = (TextView) convertView.findViewById(R.id.articleTitle);
            txtName.setText(article.getTitle());
            
            return convertView;
      }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}