package com.mindyourlovedone.healthcare.Connections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.R;
//import com.mindyourlovedone.healthcare.utility.AppConstants;

import java.util.ArrayList;

/**
 * Created by varsha on 9/4/2017.
 */

public class RelationAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> relationArraylist;
    LayoutInflater lf;
    int count;
    View previousView;


    public RelationAdapter(Context context, ArrayList<String> relationArraylist) {
        this.context = context;
        this.relationArraylist = relationArraylist;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return relationArraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return relationArraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.row_relation, null);
        try {
            view.setText(relationArraylist.get(position));
            if (position == 0) {
                previousView = view;
            }
            view.setOnClickListener(new View.OnClickListener() {
                /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                    if (v != previousView) {
                        v.setBackgroundResource(R.drawable.darkgray_gray_border);
                        previousView.setBackgroundResource(R.drawable.lightgray_gray_border);
                        previousView = v;
                    } else {
                        v.setBackgroundResource(R.drawable.darkgray_gray_border);
                    }
                    FragmentNewContact fragmentNewContact = new FragmentNewContact();
                    fragmentNewContact.callRelation(((TextView) v).getText().toString());
                   // AppConstants.RELATION = ((TextView) v).getText().toString();
                    Toast.makeText(context, ((TextView) v).getText().toString(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


}
