package com.mindyourlovedone.healthcare.Connections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Contact;

import java.util.ArrayList;

/**
 * Class: RelationAdapter
 * Screen: List of cateories like relation,priority,network,insurance type etc
 * A class that manages listing Categories for list
 */
public class RelationsAdapter extends BaseAdapter implements Filterable {

    Context context;
    String[] relationship;
    String selected="";
    LayoutInflater lf;
    ViewHolder holder;
    int pos;
    String[] mOriginalValues;

    public RelationsAdapter(Context context, String[] relationship, String selected) {
        this.mOriginalValues=relationship;
        this.context=context;
        this.relationship=relationship;
        this.selected=selected;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return relationship.length;
    }

    @Override
    public Object getItem(int position) {
        return relationship[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
  pos=position;
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_relations, parent, false);
            holder = new ViewHolder();

            holder.txtRel=  convertView.findViewById(R.id.txtRel);
            holder.imgCheck=  convertView.findViewById(R.id.imgCheck);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtRel.setText(relationship[position]);
        holder.imgCheck.getTag();
        if (selected.equalsIgnoreCase(relationship[pos]))
        {
            holder.imgCheck.setVisibility(View.VISIBLE);
            holder.imgCheck.setTag(position);
        }
        else
        {
            holder.imgCheck.setVisibility(View.GONE);
            holder.imgCheck.setTag(position);
        }
        return convertView;
    }

    public class ViewHolder {
        TextView txtRel;
        ImageView imgCheck;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                relationship = (String[]) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<String> FilteredArrList = new ArrayList<String>();

                if (mOriginalValues == null) {
                    mOriginalValues = relationship; // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.length;
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.length; i++) {
                        String data = mOriginalValues[i];
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(mOriginalValues[i]);
                        }
                    }

                    String[] filterAr=new String[FilteredArrList.size()];
                    filterAr=FilteredArrList.toArray(filterAr);
                    // set the Filtered result to return
                    results.count = filterAr.length;
                    results.values = filterAr;
                }
                return results;
            }
        };
        return filter;
    }

}
