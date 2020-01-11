package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.R;

import java.util.ArrayList;
/**
 * Class: ReactionAdapter
 * Screen: Reaction list
 * A class that manages to multichoice of reaction
 * implements OnclickListener for onClick event on views
 */
public class ReactionAdapter extends BaseAdapter {

    Context context;
    String[] relationship;
    ArrayList selected;
    LayoutInflater lf;
    ViewHolder holder;
    int pos;

    public ReactionAdapter(Context context, String[] relationship, ArrayList selected) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
  pos=position;
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_reaction, parent, false);
            holder = new ViewHolder();

            holder.txtRel=  convertView.findViewById(R.id.txtRel);
            holder.imgCheck=  convertView.findViewById(R.id.imgCheck);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtRel.setText(relationship[position]);
        for (int i=0;i<selected.size();i++)
        {
            if (relationship[position].equals(selected.get(i).toString()))
            {
                holder.imgCheck.setChecked(true);
            }
        }

        holder.imgCheck.getTag();
        holder.imgCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    ((ReactionListActivity)context).addList(relationship[position],true);
                }else{
                    ((ReactionListActivity)context).addList(relationship[position],false);
                }
                holder.imgCheck.setTag(position);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView txtRel;
        CheckBox imgCheck;
    }
}
