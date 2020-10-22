package com.mindyourlovedone.healthcare.Prescription.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.Prescription.Prescription_List_Activity;
import com.mindyourlovedone.healthcare.model.Prescription;

import java.util.ArrayList;

public class currentPrescription_ListAdapter extends BaseAdapter {
    private Holder holder;
    private Context context;
    private LayoutInflater lf;
    private ArrayList<Prescription> currentList_prescription = new ArrayList<>();


    public currentPrescription_ListAdapter(Context context, ArrayList<Prescription> currentList_prescription) {
        this.context = context;
        this.currentList_prescription = currentList_prescription;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @Override
    public int getCount() {
        return currentList_prescription.size();
    }

    @Override
    public Object getItem(int position) {
        return currentList_prescription.size();
    }

    @Override
    public long getItemId(int position) {
        currentList_prescription.get(position);
        return position;
        
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d(TAG, "getView: set current adapter");
        if (convertView == null){
            convertView = lf.inflate(R.layout.row_current_itemlist,parent,false);
            holder = new Holder();
            holder.tv_maladyName = convertView.findViewById(R.id.tv_maladyName);
            holder.tv_medicineName = convertView.findViewById(R.id.tv_medicineName);
           // holder.tv_currentDate = convertView.findViewById(R.id.tv_currentDate);
            convertView.setTag(holder);
        }else {
            holder = (currentPrescription_ListAdapter.Holder) convertView.getTag();
        }
        Prescription prescription = currentList_prescription.get(position);
        holder.tv_medicineName.setText(prescription.getMedicine());
        holder.tv_maladyName.setText(prescription.getMalady());
        //holder.tv_currentDate.setVisibility(View.GONE);
        return convertView;
    }
    private class Holder{
        TextView tv_medicineName,tv_maladyName,tv_currentDate;
        public Holder() {
            
        }
    }
}
