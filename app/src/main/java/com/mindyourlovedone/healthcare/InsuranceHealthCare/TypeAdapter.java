package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.Activity.RelationshipActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
/**
 * Class: TypeAdapter
 * Screen: Specialist or test  List Screen
 * A class that manages Adpater for Specialist or test  List
 */
public class TypeAdapter extends BaseAdapter {

    String[] relationship;
    LayoutInflater lf;
    Holders holder;
    Context context;

    public TypeAdapter(Context context, String[] relationship) {
        this.context = context;
        this.relationship = relationship;
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
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_type, parent, false);
            holder = new Holders();
            holder.txtType = convertView.findViewById(R.id.txtType);
            holder.imgType = convertView.findViewById(R.id.imgType);
            convertView.setTag(holder);
        } else {
            holder = (Holders) convertView.getTag();
        }
        holder.txtType.setText(relationship[position]);
        if (relationship[position].equalsIgnoreCase("" + RelationshipActivity.selected)) {
            holder.imgType.setVisibility(View.VISIBLE);
        } else {
            holder.imgType.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class Holders {
        TextView txtType;
        ImageView imgType;
    }
}
