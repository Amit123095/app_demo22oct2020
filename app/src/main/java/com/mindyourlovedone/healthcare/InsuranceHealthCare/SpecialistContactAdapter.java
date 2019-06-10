package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.DashBoard.EmergencyInfoActivity;
import com.mindyourlovedone.healthcare.DashBoard.EventNoteActivity;
import com.mindyourlovedone.healthcare.DashBoard.InsuranceActivity;
import com.mindyourlovedone.healthcare.DashBoard.InsuranceInfoActivity;
import com.mindyourlovedone.healthcare.DashBoard.LivingActivity;
import com.mindyourlovedone.healthcare.DashBoard.MedicalAppointActivity;
import com.mindyourlovedone.healthcare.DashBoard.ProfileActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;

/**
 * Created by welcome on 9/13/2017.
 */

public class SpecialistContactAdapter extends BaseAdapter {
    Context context;
    String[] specialist;
    LayoutInflater lf;
    boolean isEmergency, isInsurance;
    int[] profile;
    String from;

    public SpecialistContactAdapter(Context context, String[] specialist, int[] profile, boolean isEmergency, boolean isInsurance, String from) {
        this.context = context;
        this.specialist = specialist;
        this.isEmergency = isEmergency;
        this.isInsurance = isInsurance;
        this.profile = profile;
        this.from = from;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return specialist.length;
    }

    @Override
    public Object getItem(int position) {
        return specialist[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_specialistsnew, parent, false);
        }
       /* RelativeLayout rlmain3 = convertView.findViewById(R.id.rlMain);
        if (position % 2 == 0) {
            rlmain3.setBackgroundColor(context.getResources().getColor(R.color.colorTwoBar));
        } else {
            rlmain3.setBackgroundColor(context.getResources().getColor(R.color.colorOneBar));
        }*/
        /*switch(from)
        {

            case "Speciality":
                RelativeLayout rlmain3= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                if (position%2==0)
                {
                    rlmain3.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                }
                else{
                    rlmain3.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
                }
                break;
            case "Emergency":
                RelativeLayout rlmain1= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                if (position%2==0)
                {
                    rlmain1.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                }
                else{
                    rlmain1.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
                }
                break;
            case "Insurance":
                RelativeLayout rlmain5= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                if (position%2==0)
                {
                    rlmain5.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                }
                else{
                    rlmain5.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
                }

                break;
            case "Event":
                RelativeLayout rlmain4= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                if (position%2==0)
                {
                    rlmain4.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                }
                else{
                    rlmain4.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
                }
                break;
        }
*/
        TextView txtName = convertView.findViewById(R.id.txtName);
        ImageView imgLogo = convertView.findViewById(R.id.imgLogo);
        imgLogo.setImageResource(profile[position]);
        txtName.setText(specialist[position]);
        txtName.setSelected(true);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fragment = null;
                switch (specialist[position]) {
                    case "INSURANCE":
                        fragment = "Insurance";
                        break;

                    case "Pharmacies & Home Medical Equipment":
                        fragment = "Pharmacies";
                        break;

                    case "Urgent Care, TeleMed, Hospitals, Rehab, Home Care":
                        fragment = "Hospitals";
                        break;

                    case "Activities of Daily Living":
                        fragment = "Functional";
                        break;

                    case "Doctors & Other Health Care Professionals":
                        fragment = "Doctors";
                        break;

                    case "HOME HEALTH SERVICES":
                        fragment = "Home Health Services";
                        break;

                    case "Finance, Legal, Other":
                        fragment = "Finance,Insurance and Legal";
                        break;

                    case "Personal Profile":
                        fragment = "Individual";
                        break;

                    case "Medical Profile":
                        fragment = "Information";
                        break;

                    case "Emergency Contacts & Health Care Proxy Agent":
                        fragment = "Emergency";
                        break;

                    case "Primary Physician":
                        fragment = "Physician";
                        break;

                    case "HEALTH CARE PROXY AGENT":
                        fragment = "Proxy";
                        break;

                    case "Insurance Information":
                        fragment = "Insurance Info";
                        break;

                    case "Insurance Forms":
                        fragment = "Insurance Form";
                        break;

                    case "Insurance Cards":
                        fragment = "INSURANCE CARD";
                        break;

                    case "Event Notes":
                        fragment = "Event Notes";
                        break;

                    case "Appointment Checklist":
                        fragment = "Appointment Tracker";
                        break;

                    case "Prescription Information":
                        fragment = "Prescription Information";
                        break;

                    case "Prescription Upload":
                        fragment = "Prescription Upload";
                        break;

                    case "Vital Signs":
                        fragment = "Vital Signs";
                        break;

                }

                if (isEmergency == false && isInsurance == false) {
                    if (fragment.equals("Event Notes") || fragment.equals("Appointment Tracker") || fragment.equals("Functional") /*|| fragment.equals("Vital Signs")*/) {
                        if (fragment.equals("Event Notes")) {
                            Intent i = new Intent(context, EventNoteActivity.class);
                            i.putExtra("FRAGMENT", fragment);
                            context.startActivity(i);
                        } else if (fragment.equals("Appointment Tracker")) {
                            Intent i = new Intent(context, MedicalAppointActivity.class);
                            i.putExtra("FRAGMENT", fragment);
                            context.startActivity(i);
                        } else if (fragment.equals("Functional")) {
                            Intent i = new Intent(context, LivingActivity.class);
                            i.putExtra("FRAGMENT", fragment);
                            context.startActivity(i);
                        }/* else if (fragment.equals("Vital Signs")) {
                            Intent i = new Intent(context, LivingActivity.class);
                            i.putExtra("FRAGMEMedical ProfileNT", fragment);
                            context.startActivity(i);
                        }*/
                    } else {
                        Intent i = new Intent(context, InsuranceActivity.class);
                        i.putExtra("FRAGMENT", fragment);
                        context.startActivity(i);
                    }
                } else if (isEmergency == true && isInsurance == false) {
                    if (fragment.equals("Individual")) {
                        Intent i = new Intent(context, ProfileActivity.class);
                        context.startActivity(i);
                       /* Intent i = new Intent(context, ProfileActivity.class);
                        i.putExtra("FRAGMENT", fragment);
                        context.startActivity(i);*/
                    } else {
                        Intent i = new Intent(context, EmergencyInfoActivity.class);
                        i.putExtra("FRAGMENT", fragment);
                        context.startActivity(i);
                    }
                } else if (isEmergency == false && isInsurance == true) {
                    Intent i = new Intent(context, InsuranceInfoActivity.class);
                    i.putExtra("FRAGMENT", fragment);
                    context.startActivity(i);
                }
            }
        });

        return convertView;
    }
}
