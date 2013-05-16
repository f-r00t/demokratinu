package org.group13.pocketpolitics.view;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.Article;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author Group 13
 */

public class ArticleListAdapter extends BaseAdapter {

	LayoutInflater inflater;
	List<Article> items;

	public ArticleListAdapter(Activity context, List<Article> items) {
		super();

		this.items = items;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		Article article = items.get(position);
		View vi = convertView;
		
		if (convertView == null) {
			vi = inflater.inflate(R.layout.article_list_item, null);
		}
		// Sets the title text
		TextView articleTitle = (TextView) vi.findViewById(R.id.articleTitle);
		articleTitle.setText(article.getTitle());

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