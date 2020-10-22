package com.mindyourlovedone.healthcare.Appointment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.Appointment.Model.AppointmentListPojo;

import com.mindyourlovedone.healthcare.HomeActivity.R;


import java.util.ArrayList;

public class AppointmentListAdapter extends BaseAdapter {
    private LayoutInflater lf;
    private Context context;
    private Holder holder;
    private ArrayList<AppointmentListPojo> listPojoArrayAdapter = new ArrayList<>();

    public AppointmentListAdapter(Context context, ArrayList<AppointmentListPojo> listPojoArrayAdapter) {
        this.listPojoArrayAdapter = listPojoArrayAdapter;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listPojoArrayAdapter.size();
    }

    @Override
    public Object getItem(int i) {
        return listPojoArrayAdapter.size();
    }

    @Override
    public long getItemId(int i) {
        listPojoArrayAdapter.get(i);
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = lf.inflate(R.layout.row_appointment_list,viewGroup,false);
            holder = new Holder();
            holder.tv_monthDate = view.findViewById(R.id.monthDate);
            holder.tv_dayDate = view.findViewById(R.id.dayDate);
            holder.tv_yearDate = view.findViewById(R.id.yearDate);
            holder.tv_DoctorName = view.findViewById(R.id.tv_DoctorName);
            holder.tv_Status = view.findViewById(R.id.tv_Status);
            holder.tv_DiagnosisTypes = view.findViewById(R.id.tv_DiagnosisTypes);
            holder.tv_VisitingStatus = view.findViewById(R.id.tv_VisitingStatus);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
            AppointmentListPojo appointmentListPojo = listPojoArrayAdapter.get(i);
            holder.tv_dayDate.setText(appointmentListPojo.getDay());
            holder.tv_monthDate.setText(appointmentListPojo.getMonth());
            holder.tv_yearDate.setText(appointmentListPojo.getYear());
            holder.tv_DoctorName.setText(appointmentListPojo.getDoctorName());
            holder.tv_Status.setText(appointmentListPojo.getStatus());
            holder.tv_DiagnosisTypes.setText(appointmentListPojo.getDiagnosisType());
            holder.tv_VisitingStatus.setText(appointmentListPojo.getVisitingStatus());

        return view;
    }
    private class Holder{
        public Holder() {}

        private TextView tv_monthDate,tv_dayDate,tv_yearDate,tv_DoctorName,tv_Status,tv_DiagnosisTypes,tv_VisitingStatus;

    }
}
