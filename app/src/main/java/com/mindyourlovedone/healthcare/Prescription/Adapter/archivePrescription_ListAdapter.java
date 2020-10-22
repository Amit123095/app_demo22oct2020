package com.mindyourlovedone.healthcare.Prescription.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.Prescription.Model.Pojo_list;
import com.mindyourlovedone.healthcare.Prescription.Prescription_List_Activity;
import com.mindyourlovedone.healthcare.model.Prescription;

import java.util.ArrayList;

public class archivePrescription_ListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater lf;
    private Holder holder;

   private ArrayList<Pojo_list> list_prescription = new ArrayList<>();


    public archivePrescription_ListAdapter(Context context, ArrayList<Pojo_list> list_prescription) {
        this.context = context;
        this.list_prescription = list_prescription;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public archivePrescription_ListAdapter(Prescription_List_Activity context, ArrayList<Prescription> aList_archive) {

    }

    @Override
    public int getCount() {
        return list_prescription.size();
    }

    @Override
    public Object getItem(int position) {
        return list_prescription.size();
    }

    @Override
    public long getItemId(int position) {
        list_prescription.get(position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = lf.inflate(R.layout.row_archive_itemlist,parent,false);
            holder = new Holder();
            holder.tv_currentDate = convertView.findViewById(R.id.tv_currentDate);
            holder.tv_maladyName = convertView.findViewById(R.id.tv_maladyName);
            holder.tv_medicineName = convertView.findViewById(R.id.tv_medicineName);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        Pojo_list Pojo_list = list_prescription.get(position);
        holder.tv_medicineName.setText(Pojo_list.getMedicine());
        holder.tv_maladyName.setText(Pojo_list.getMalady());
        holder.tv_currentDate.setText(Pojo_list.getDate());
        return convertView;
    }
    private class Holder{
        public Holder() {}
            TextView tv_currentDate,tv_medicineName,tv_maladyName;
            LinearLayout linear_rowItem;

    }
}
