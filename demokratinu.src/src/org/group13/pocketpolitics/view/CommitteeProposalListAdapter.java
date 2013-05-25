package org.group13.pocketpolitics.view;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.control.MoprositionActivity;
import org.group13.pocketpolitics.model.riksdag.CommitteeProposal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

//Code based on code from this page http://numberedmountain.blogspot.se/p/code-example-overriding.html
public class CommitteeProposalListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<String> listTopics;
	private List<List<CommitteeProposal>> listNotes;

	public CommitteeProposalListAdapter(Context context,
			List<CommitteeProposal> items) {
		super();

		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		listTopics = new ArrayList<String>(items.size());
		listNotes = new ArrayList<List<CommitteeProposal>>();

		for (CommitteeProposal element : items) {
			listTopics.add(element.getTitle());
			ArrayList<CommitteeProposal> elementList = new ArrayList<CommitteeProposal>();
			elementList.add(element);
			listNotes.add(elementList);
		}
	}

	@Override
	public CommitteeProposal getChild(int groupPosition, int childPosition) {
		return getGroup(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		View vi = convertView;
		List<String> moprIds = getChild(groupPosition, childPosition)
				.getMoprIds();
		List<String> moprYrs = getChild(groupPosition, childPosition)
				.getMoprYrs();

		if (convertView == null) {
			vi = inflater.inflate(R.layout.committee_proposal_list_item, null);

			LinearLayout cpListItemLayout = (LinearLayout) vi
					.findViewById(R.id.committeeProposalLinearLayout);

			for (int i = 0; i < moprIds.size() && i < moprYrs.size(); i++) {
				Button btn = new Button(context);
				btn.setText(moprYrs.get(i) + ":" + moprIds.get(i));

				final String THIS_YEAR = moprYrs.get(i);
				final String THIS_ID = moprIds.get(i);
				
				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								MoprositionActivity.class);
						intent.putExtra(MoprositionActivity.MOPR_YEAR_SENT,
								THIS_YEAR);
						intent.putExtra(MoprositionActivity.MOPR_ID_SENT,
								THIS_ID);
						context.startActivity(intent);
					}

				});
				cpListItemLayout.addView(btn, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}

		// Set text
		TextView committeProposalText = (TextView) vi
				.findViewById(R.id.committeeProposalListItemText);

		// Assign the note text
		committeProposalText.setText(getChild(groupPosition, childPosition)
				.getForslag());

		// Make the note italic
		committeProposalText.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);

		// indent the child element a bit
		committeProposalText.setPadding(20, 0, 0, 0);

		return vi;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return getGroup(groupPosition).size();
	}

	@Override
	public List<CommitteeProposal> getGroup(int groupPosition) {
		return listNotes.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
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
