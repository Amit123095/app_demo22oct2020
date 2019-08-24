package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.ResourcesNew;

import java.util.ArrayList;

/*Created by shradha 12/02/2019*/
public class ResourceAdapter extends BaseAdapter {
    ArrayList<ResourcesNew> resourcesNewList;
    ListView lvResources;
    Holders holder;
    LayoutInflater lf;
    Context context;

    public ResourceAdapter(Activity activity, ArrayList<ResourcesNew> resourcesList) {
        this.context = activity;
        this.resourcesNewList = resourcesList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resourcesNewList.size();
    }

    @Override
    public Object getItem(int position) {
        return resourcesNewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_resources_new, parent, false);
            holder = new Holders();
            holder.txtName = convertView.findViewById(R.id.txtName);
            holder.imgResources = convertView.findViewById(R.id.imgResources);
            holder.imgForword = convertView.findViewById(R.id.imgForword);

            convertView.setTag(holder);
        }
        /*if (convertView.equals(position == 0)) {
            Intent intentContactUs = new Intent(context, BaseNewActivity.class);
            intentContactUs.putExtra("Home", 5);
            context.startActivity(intentContactUs);
        } else {
            Toast.makeText(context, "Wrong dude..!!", Toast.LENGTH_SHORT).show();
        }*/
        else {
            holder = (Holders) convertView.getTag();
        }
        ResourcesNew rn = resourcesNewList.get(position);
        holder.txtName.setText(rn.getName());
        holder.imgResources.setImageResource(rn.getResImage());

        return convertView;
    }

    private class Holders {
        TextView txtName;
        ImageView imgResources, imgForword;
    }
}
