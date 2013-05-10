package org.group13.pocketpolitics.view;

import java.util.ArrayList;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.model.Motion;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MotionListAdapter extends BaseAdapter {

	LayoutInflater inflater;
	ArrayList<Motion> items;

	public MotionListAdapter(Activity context, ArrayList<Motion> items) {
		super();

		this.items = items;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		Motion motion = items.get(position);
		View vi = convertView;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.article_list_item, null); //Change this once proper layout has been implemented.
		}

		// Sets the title text
		TextView motionTitle = (TextView) vi.findViewById(R.id.articleTitle); //Change this once proper layout has been implemented.
		motionTitle.setText(motion.getTitle());
		
		return vi;
	}


}
