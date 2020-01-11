package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mindyourlovedone.healthcare.Activity.AddVitalSignsActivity;
import com.mindyourlovedone.healthcare.DashBoard.AddAppointmentActivity;
import com.mindyourlovedone.healthcare.DashBoard.MedicalAppointActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.RecyclerSwipeAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.SimpleSwipeListener;
import com.mindyourlovedone.healthcare.SwipeCode.SwipeLayout;
import com.mindyourlovedone.healthcare.model.Appoint;
import com.mindyourlovedone.healthcare.model.VitalSigns;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Class: VitalAdpater
 * Screen: Vital Sign List Screen
 * A class that manages Adpater for Vital Sign List
 */
public class VitalAdpater extends RecyclerSwipeAdapter<VitalAdpater.ViewHolder> {
    Context context;
    ArrayList<VitalSigns> vitalList;
    LayoutInflater lf;
    Preferences preferences;
    ImageLoader imageLoaderProfile, imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile, displayImageOptionsCard;
    FragmentVitalSigns fr;
    boolean flagDrop = false;


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
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        holder.lintrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fr != null) {
                    fr.deleteVital(vitalList.get(position));
                }
            }
        });
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

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagDrop == false) {
                    holder.llSubVital.setVisibility(View.VISIBLE);
                    holder.imgDropDown.setImageResource(R.drawable.dropup);
                    holder.txtEdit.setVisibility(View.VISIBLE);
                    holder.viewVital.setVisibility(View.VISIBLE);
                    holder.view2.setVisibility(View.GONE);
                    flagDrop = true;
                } else if (flagDrop == true) {
                    holder.llSubVital.setVisibility(View.GONE);
                    holder.imgDropDown.setImageResource(R.drawable.drop_down);
                    holder.txtEdit.setVisibility(View.GONE);
                    holder.viewVital.setVisibility(View.GONE);
                    holder.view2.setVisibility(View.VISIBLE);
                    flagDrop = false;
                }
            }
        });

        if (vitalList.get(position).getPulseRate().equals("")) {
            holder.txtPulseData.setVisibility(View.GONE);
        } else {
            holder.txtPulseData.setVisibility(View.VISIBLE);
            holder.txtPulseData.setText(vitalList.get(position).getPulseRate());
        }
        if (vitalList.get(position).getOther().equals("")) {
            holder.txtOter.setVisibility(View.GONE);
        } else {
            holder.txtOter.setVisibility(View.VISIBLE);
            holder.txtOter.setText(vitalList.get(position).getOther());
        }
        if (vitalList.get(position).getCol().equals("")) {
            holder.txtCol.setVisibility(View.GONE);
        } else {
            holder.txtCol.setVisibility(View.VISIBLE);
            holder.txtCol.setText(vitalList.get(position).getCol());
        }

        if (vitalList.get(position).getRespRate().equals("")) {
            holder.txtRespData.setVisibility(View.GONE);
        } else {
            holder.txtRespData.setVisibility(View.VISIBLE);
            holder.txtRespData.setText(vitalList.get(position).getRespRate());
        }

        if (vitalList.get(position).getLocation().equals("")) {
            holder.txtLocData.setVisibility(View.GONE);
        } else {
            holder.txtLocData.setVisibility(View.VISIBLE);
            holder.txtLocData.setText(vitalList.get(position).getLocation());
        }

        if (vitalList.get(position).getNote().equals("")) {
            holder.txtNoteData.setVisibility(View.GONE);
        } else {
            holder.txtNoteData.setVisibility(View.VISIBLE);
            holder.txtNoteData.setText(vitalList.get(position).getNote());
        }

        holder.txtBPValue.setText(vitalList.get(position).getBp() + ",");
        holder.txtHRValue.setText(vitalList.get(position).getHeartRate() + ",");
        holder.txtTempValue.setText(vitalList.get(position).getTemperature() + ",");
        holder.txtDate.setText(vitalList.get(position).getDate());
        holder.txtTime.setText(vitalList.get(position).getTime());


        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VitalSigns vi = vitalList.get(position);
                Intent i = new Intent(context, AddVitalSignsActivity.class);
                i.putExtra("VitalEdit", vi);
                i.putExtra("IsUpdate", true);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEdit, txtBP, txtBPValue, txtHR, txtHRValue, txtTemp, txtTempValue, txtDate, txtTime,txtOter,txtCol, txtPulseData, txtRespData, txtLocData, txtNoteData;
        ImageView imgDropDown;

        SwipeLayout swipeLayout;
        RelativeLayout rlVital, rlMain;
        LinearLayout llSubVital;
        View viewVital, view2;
        LinearLayout lintrash;

        public ViewHolder(View convertView) {
            super(convertView);
            txtEdit = convertView.findViewById(R.id.txtEdit);
            txtBP = convertView.findViewById(R.id.txtBP);
            txtBPValue = convertView.findViewById(R.id.txtBpValue);
            txtHR = convertView.findViewById(R.id.txtHR);
            txtHRValue = convertView.findViewById(R.id.txtHRValue);
            txtTemp = convertView.findViewById(R.id.txtTemp);
            txtTempValue = convertView.findViewById(R.id.txtTempValue);
            txtDate = convertView.findViewById(R.id.txtDate);
            txtTime = convertView.findViewById(R.id.txtTime);

            txtPulseData = convertView.findViewById(R.id.txtPulseData);
            txtOter = convertView.findViewById(R.id.txtOter);
            txtCol = convertView.findViewById(R.id.txtCol);
            txtRespData = convertView.findViewById(R.id.txtRespData);
            txtLocData = convertView.findViewById(R.id.txtLocData);
            txtNoteData = convertView.findViewById(R.id.txtNoteData);


            rlVital = convertView.findViewById(R.id.rlVital);
            rlMain = convertView.findViewById(R.id.rlMain);
            llSubVital = convertView.findViewById(R.id.llSubVital);

            imgDropDown = convertView.findViewById(R.id.imgDropDown);
            viewVital = convertView.findViewById(R.id.viewVital);
            view2 = convertView.findViewById(R.id.view2);
            lintrash = convertView.findViewById(R.id.lintrash);
            swipeLayout = convertView.findViewById(R.id.swipe);

        }
    }

}

