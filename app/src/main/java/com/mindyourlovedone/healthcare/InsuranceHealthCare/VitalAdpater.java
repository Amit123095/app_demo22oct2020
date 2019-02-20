package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.Activity.AddVitalSignsActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.RecyclerSwipeAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.SwipeLayout;
import com.mindyourlovedone.healthcare.model.VitalSigns;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class VitalAdpater extends RecyclerSwipeAdapter<VitalAdpater.ViewHolder> {
    Context context;
    ArrayList<VitalSigns> vitalList;
    LayoutInflater lf;
    Preferences preferences;
    ImageLoader imageLoaderProfile, imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile, displayImageOptionsCard;
    FragmentVitalSigns fr;


    public VitalAdpater(Activity activity, ArrayList<VitalSigns> vitalList, FragmentVitalSigns fragmentVitalSigns) {
        // preferences = new Preferences(context);
        this.context = activity;
        this.vitalList = vitalList;
        this.fr = fragmentVitalSigns;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public VitalAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vital, parent, false);
        return new VitalAdpater.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return vitalList.size();
    }

    @Override
    public void onBindViewHolder(final VitalAdpater.ViewHolder holder, final int position) {
/*
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
*/

        if (vitalList.get(position).getBp().equals("")) {
            holder.txtBPValue.setVisibility(View.GONE);
        } else {
            holder.txtBPValue.setVisibility(View.VISIBLE);
        }
        if (vitalList.get(position).getHeartRate().equals("")) {
            holder.txtHRValue.setVisibility(View.GONE);
        } else {
            holder.txtHRValue.setVisibility(View.VISIBLE);
        }
        if (vitalList.get(position).getTemperature().equals("")) {
            holder.txtTempValue.setVisibility(View.GONE);
        } else {
            holder.txtTempValue.setVisibility(View.VISIBLE);
        }
        if (vitalList.get(position).getDate().equals("")) {
            holder.txtDate.setVisibility(View.GONE);
        } else {
            holder.txtDate.setVisibility(View.VISIBLE);
        }
        if (vitalList.get(position).getTime().equals("")) {
            holder.txtTime.setVisibility(View.GONE);
        } else {
            holder.txtTime.setVisibility(View.VISIBLE);
        }


      /* if (vitalList.get(position).getCategory().equals("")) {
            holder.txtCategory.setVisibility(View.GONE);
        } else {
            holder.txtCategory.setVisibility(View.VISIBLE);
        }*/
        holder.txtBPValue.setText(vitalList.get(position).getBp());
        holder.txtHRValue.setText(vitalList.get(position).getHeartRate());
        holder.txtTempValue.setText(vitalList.get(position).getTemperature());
        holder.txtDate.setText(vitalList.get(position).getDate());
        holder.txtTime.setText(vitalList.get(position).getTime());

      /*  if (vitalList.get(position).getCategory().equals("Other")) {
            holder.txtCategory.setText(vitalList.get(position).getCategory() + " - " + vitalList.get(position).getOtherCategory());
        } else {
            holder.txtCategory.setText(vitalList.get(position).getCategory());
        }*/
        //holder.imgProfile.setImageResource(FinanceList.get(position).getImage());


        holder.rlVital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddVitalSignsActivity.class);
                VitalSigns hospital = vitalList.get(position);
                i.putExtra("IsEdit", true);
                i.putExtra("Date", "Date");
                i.putExtra("Time", "Time");
                // i.putExtra("IsView", true);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBP, txtBPValue, txtHR, txtHRValue, txtTemp, txtTempValue, txtDate, txtTime;
        ImageView imgDropDown;

        SwipeLayout swipeLayout;
        RelativeLayout rlVital;

        public ViewHolder(View convertView) {
            super(convertView);
            txtBP = convertView.findViewById(R.id.txtBP);
            txtBPValue = convertView.findViewById(R.id.txtBpValue);
            txtHR = convertView.findViewById(R.id.txtHR);
            txtHRValue = convertView.findViewById(R.id.txtHRValue);
            txtTemp = convertView.findViewById(R.id.txtTemp);
            txtTempValue = convertView.findViewById(R.id.txtTempValue);
            txtDate = convertView.findViewById(R.id.txtDate);
            txtTime = convertView.findViewById(R.id.txtTime);
            rlVital = convertView.findViewById(R.id.rlVital);

            imgDropDown = convertView.findViewById(R.id.imgDropDown);
        }
    }

}

