package com.mindyourlovedone.healthcare.HomeActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.model.Links;

import java.util.ArrayList;

/**
 * Created by V@iBh@V on 11/18/2017.
 * Adapterb for set video link to list
 */
public class LinkAdapter extends BaseAdapter {
    Context context;
    ArrayList<Links> urlList;
    LayoutInflater lf;
    Holder holder;

    public LinkAdapter(Context context, ArrayList<Links> urlList) {
        this.context = context;
        this.urlList = urlList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object getItem(int position) {
        return urlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_resources, parent, false);
            holder = new Holder();
            holder.txtName = convertView.findViewById(R.id.txtName);
            holder.imgLogo = convertView.findViewById(R.id.imgLogo);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtName.setText(urlList.get(position).getName());
        holder.imgLogo.setImageResource(urlList.get(position).getImage());

        return convertView;
    }

    private class Holder {
        TextView txtName;
        ImageView imgLogo;

    }
}
