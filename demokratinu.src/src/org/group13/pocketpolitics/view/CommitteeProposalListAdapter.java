package org.group13.pocketpolitics.view;

import java.util.List;

import org.group13.pocketpolitics.R;
import org.group13.pocketpolitics.model.riksdag.CommitteeProposal;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommitteeProposalListAdapter extends BaseAdapter {

	LayoutInflater inflater;
	List<CommitteeProposal> items;

	public CommitteeProposalListAdapter(Context context, List<CommitteeProposal> items) {
		super();
		Log.i("Viking","super");
		
		this.items = items;
		Log.i("Viking","items");
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.i("Viking","inflater");
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.i("Viking","getVeiw");
		CommitteeProposal committeProposal = items.get(position);
		View vi = convertView;

		Log.i("Viking","if");
		if (convertView == null) {
			vi = inflater.inflate(R.layout.committee_proposal_list_item, null);
		}

		Log.i("Viking","title");
		// Sets the title text
		TextView committeProposalTitle = (TextView) vi.findViewById(R.id.committeeProposalListItemTitle);
		committeProposalTitle.setText(committeProposal.getTitle());
		
		Log.i("Viking","text");
		//Set text
		TextView committeProposalText = (TextView) vi.findViewById(R.id.committeeProposalListItemText);
		committeProposalText.setText(committeProposal.getForslag());

		return vi;
	}	
	
	@Override
	public int getCount() {
		Log.i("Viking","getCount()");
		return items.size();
		
	}

	@Override
	public Object getItem(int arg0) {
		Log.i("Viking","getItem(int arg0)");
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		Log.i("Viking","getItemId(int arg0)");
		return 0;
	}

}
