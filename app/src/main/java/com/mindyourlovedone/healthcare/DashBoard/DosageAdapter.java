package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Dosage;

import java.util.ArrayList;

/**
 * Created by welcome on 9/19/2017.
 */
/**
 * Class: DosageAdapter
 * Screen: Dosage list
 * A class that manages to dosage list
 * implements OnclickListener for onClick event on views
 */
class DosageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Dosage> dosageList;
    LayoutInflater lf;
    Holder holder;

    public DosageAdapter(Context context, ArrayList dosageList) {
        this.context = context;
        this.dosageList = dosageList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dosageList.size();
    }

    @Override
    public Object getItem(int position) {
        return dosageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_dosage, parent, false);
            holder = new Holder();
            holder.txtNote = convertView.findViewById(R.id.txtMedicine);
            holder.txtDateTime = convertView.findViewById(R.id.txtDose);
            holder.txtFrequency = convertView.findViewById(R.id.txtFrequency);
            holder.imgEdit = convertView.findViewById(R.id.imgEdit);
            holder.imgDelete = convertView.findViewById(R.id.imgDelete);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtNote.setText(dosageList.get(position).getMedicine());
        holder.txtDateTime.setText(dosageList.get(position).getDose());
        holder.txtFrequency.setText(": " + dosageList.get(position).getFrequency());
        return convertView;
    }

    private class Holder {
        TextView txtNote, txtDateTime, txtTime, txtFrequency;
        ImageView imgForward, imgEdit, imgDelete;
    }

}
