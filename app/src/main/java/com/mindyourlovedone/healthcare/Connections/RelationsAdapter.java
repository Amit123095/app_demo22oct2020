package com.mindyourlovedone.healthcare.Connections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
/**
 * Class: RelationAdapter
 * Screen: List of cateories like relation,priority,network,insurance type etc
 * A class that manages listing Categories for list
 */
public class RelationsAdapter extends BaseAdapter {

    Context context;
    String[] relationship;
    String selected="";
    LayoutInflater lf;
    ViewHolder holder;
    int pos;
    public RelationsAdapter(Context context, String[] relationship, String selected) {
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
}
