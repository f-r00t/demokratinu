package org.group13.pocketpolitics.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.CommitteeProposal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

//Code based on code from this page http://numberedmountain.blogspot.se/p/code-example-overriding.html
public class CommitteeProposalListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> listTopics;
	private List<List<CommitteeProposal>> listNotes;

	public CommitteeProposalListAdapter(Context context,
			List<CommitteeProposal> items) {
		super();

		this.context = context;
		
		listTopics = new ArrayList<String>(items.size());
		listNotes = new ArrayList<List<CommitteeProposal>>();
		
		for (CommitteeProposal element : items) {
			listTopics.add(element.getTitle());
			ArrayList<CommitteeProposal> elementList = new ArrayList<CommitteeProposal>();
			elementList.add(element);
			listNotes.add(elementList);
		}
		
		Log.i("Viking","Size dim 1: "+listNotes.size());
		Log.i("Viking","Size dim 2: "+listNotes.get(0).size());
	}

	@Override
	public CommitteeProposal getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return getGroup(groupPosition).get(childPosition); //mNotes[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView row = (TextView) convertView;

		if (row == null) {
			row = new TextView(context);
		}

		// Assign the note text
		row.setText(getChild(groupPosition, childPosition).getForslag());

		// Make the note italic
		row.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);

		// indent the child element a bit
		row.setPadding(20, 0, 0, 0);
		//notifyDataSetChanged();
		
		//Log.i("Viking","In child: "+getChild(groupPosition, childPosition).getForslag());
		return row;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return getGroup(groupPosition).size();
	}

	@Override
	public List<CommitteeProposal> getGroup(int groupPosition) {
		return listNotes.get(groupPosition);
	}



	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return listNotes.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView row = (TextView) convertView;
		if (row == null) {
			row = new TextView(context);
		}
		row.setTypeface(Typeface.DEFAULT_BOLD);
		row.setTextSize(16);
		row.setText(listTopics.get(groupPosition));
		row.setPadding(45, 0, 0, 0);
		//notifyDataSetChanged();
		//Log.i("Viking","In parent: "+listTopics.get(groupPosition));
		return row;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}	
	
/*	private LayoutInflater inflater;
	private List<CommitteeProposal> items;
	private Context context;
	private List<String> parentList;
	private List<List<CommitteeProposal>> childList;
	
	public CommitteeProposalListAdapter(Context context,
			List<CommitteeProposal> items) {
		super();
		
		parentList = new ArrayList<String>();
		childList = new ArrayList<List<CommitteeProposal>>();
		
		//This might not be needed and should probably be removed.
		this.items = items;
		Log.i("Viking","childList");
		childList.add(this.items);
		Log.i("Viking","after childList");
		
		for (int i=0;i<items.size();i++){
			Log.i("Viking","for "+i);
			parentList.add(items.get(i).getTitle());
		}
		
		//Not sure if it is a good idea to both have an inflater and a context
		this.context = context;
		this.inflater = (LayoutInflater) context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.i("Viking","In parent: "+parentList.size());
		Log.i("Viking","In child: "+childList.get(0).size());
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return (childList.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Log.i("Viking","Child text: "+((CommitteeProposal)this.getChild(groupPosition, childPosition)).getForslag());
		TextView tv = new TextView(context);
		tv.setText(((CommitteeProposal)this.getChild(groupPosition, childPosition)).getForslag());
		return tv;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		Log.i("Viking","In getChildrenCount "+(childList.get(groupPosition)).size());
		return (childList.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupPosition; //parentList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return parentList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView tv = new TextView(context);
		tv.setText(parentList.get(groupPosition));
		Log.i("Viking","Parent text: "+parentList.get(groupPosition)+" : "+isExpanded);
		return tv;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}

/*
 * 	LayoutInflater inflater;
	List<CommitteeProposal> items;

	public CommitteeProposalListAdapter(Context context, List<CommitteeProposal> items) {
		super();
		
		this.items = items;

		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.i("Viking","In adapter: "+items.size());
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		CommitteeProposal committeProposal = items.get(position);
		View vi = convertView;


		if (convertView == null) {
			vi = inflater.inflate(R.layout.committee_proposal_list_item, null);
		}

		// Sets the title text
		TextView committeProposalTitle = (TextView) vi.findViewById(R.id.committeeProposalListItemTitle);
		committeProposalTitle.setText(committeProposal.getTitle());
		
		//Set text
		TextView committeProposalText = (TextView) vi.findViewById(R.id.committeeProposalListItemText);
		committeProposalText.setText(committeProposal.getForslag());

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

 */
