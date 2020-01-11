package com.mindyourlovedone.healthcare.Connections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.DashBoard.FragmentMedicalInfo;
import com.mindyourlovedone.healthcare.HomeActivity.R;

import java.util.ArrayList;

/**
 * Created by Nikita on 26/6/2018.
 */
/**
 * Class: MedAdapter
 * Screen: Medical Information Subsection List
 * A class that manages listing of medical informations
 */
public class MedAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> medArraylist;
    LayoutInflater lf;
    Holder holder;
    String Type;
    FragmentMedicalInfo fr;

    public MedAdapter(Context context, ArrayList<String> medArraylist, String Type, FragmentMedicalInfo fr) {
        this.fr = fr;
        this.context = context;
        this.medArraylist = medArraylist;
        this.Type = Type;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return medArraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return medArraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_medicalinfo, parent, false);
            holder = new Holder();
            holder.txtInfo1 = convertView.findViewById(R.id.txtInfo1);
            holder.txtInfo2 = convertView.findViewById(R.id.txtInfo2);
            holder.txtInfo3 = convertView.findViewById(R.id.txtInfo3);
            holder.txtInfo4 = convertView.findViewById(R.id.txtInfo4);
            holder.txtInfo5 = convertView.findViewById(R.id.txtInfo5);

            holder.txtsub1 = convertView.findViewById(R.id.txtsub1);
            holder.txtsub2 = convertView.findViewById(R.id.txtsub2);
            holder.txtsub3 = convertView.findViewById(R.id.txtsub3);
            holder.txtsub4 = convertView.findViewById(R.id.txtsub4);
            holder.txtsub5 = convertView.findViewById(R.id.txtsub5);

            holder.imgEdit = convertView.findViewById(R.id.imgEdit);
            holder.imgDelete = convertView.findViewById(R.id.imgDelete);
            holder.rlEdit = convertView.findViewById(R.id.rlEdit);
            holder.rlDelete = convertView.findViewById(R.id.rlDelete);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtInfo1.setVisibility(View.GONE);
        holder.txtsub1.setVisibility(View.GONE);
        holder.txtInfo2.setVisibility(View.GONE);
        holder.txtsub2.setVisibility(View.GONE);
        holder.txtInfo3.setVisibility(View.GONE);
        holder.txtsub3.setVisibility(View.GONE);
        holder.txtInfo4.setVisibility(View.GONE);
        holder.txtsub4.setVisibility(View.GONE);
        holder.txtInfo5.setVisibility(View.GONE);
        holder.txtsub5.setVisibility(View.GONE);

        String[] str = medArraylist.get(position).split("]");

        if(str!=null){
            if(str.length == 2){
                holder.txtInfo1.setVisibility(View.VISIBLE);
                holder.txtsub1.setVisibility(View.VISIBLE);

                holder.txtsub1.setText(str[0]);
                holder.txtInfo1.setText(str[1]);
            }else if(str.length==4){
                holder.txtInfo1.setVisibility(View.VISIBLE);
                holder.txtsub1.setVisibility(View.VISIBLE);
                holder.txtInfo2.setVisibility(View.VISIBLE);
                holder.txtsub2.setVisibility(View.VISIBLE);

                holder.txtsub1.setText(str[0]);
                holder.txtInfo1.setText(str[1]);
                holder.txtsub2.setText(str[2]);
                holder.txtInfo2.setText(str[3]);
            }else if(str.length==6){
                holder.txtInfo1.setVisibility(View.VISIBLE);
                holder.txtsub1.setVisibility(View.VISIBLE);
                holder.txtInfo2.setVisibility(View.VISIBLE);
                holder.txtsub2.setVisibility(View.VISIBLE);
                holder.txtInfo3.setVisibility(View.VISIBLE);
                holder.txtsub3.setVisibility(View.VISIBLE);

                holder.txtsub1.setText(str[0]);
                holder.txtInfo1.setText(str[1]);
                holder.txtsub2.setText(str[2]);
                holder.txtInfo2.setText(str[3]);
                holder.txtsub3.setText(str[4]);
                holder.txtInfo3.setText(str[5]);
            }else if(str.length==8){
                holder.txtInfo1.setVisibility(View.VISIBLE);
                holder.txtsub1.setVisibility(View.VISIBLE);
                holder.txtInfo2.setVisibility(View.VISIBLE);
                holder.txtsub2.setVisibility(View.VISIBLE);
                holder.txtInfo3.setVisibility(View.VISIBLE);
                holder.txtsub3.setVisibility(View.VISIBLE);
                holder.txtInfo4.setVisibility(View.VISIBLE);
                holder.txtsub4.setVisibility(View.VISIBLE);

                holder.txtsub1.setText(str[0]);
                holder.txtInfo1.setText(str[1]);
                holder.txtsub2.setText(str[2]);
                holder.txtInfo2.setText(str[3]);
                holder.txtsub3.setText(str[4]);
                holder.txtInfo3.setText(str[5]);
                holder.txtsub4.setText(str[6]);
                holder.txtInfo4.setText(str[7]);
            }else if(str.length==10){
                holder.txtInfo1.setVisibility(View.VISIBLE);
                holder.txtsub1.setVisibility(View.VISIBLE);
                holder.txtInfo2.setVisibility(View.VISIBLE);
                holder.txtsub2.setVisibility(View.VISIBLE);
                holder.txtInfo3.setVisibility(View.VISIBLE);
                holder.txtsub3.setVisibility(View.VISIBLE);
                holder.txtInfo4.setVisibility(View.VISIBLE);
                holder.txtsub4.setVisibility(View.VISIBLE);
                holder.txtInfo5.setVisibility(View.VISIBLE);
                holder.txtsub5.setVisibility(View.VISIBLE);

                holder.txtsub1.setText(str[0]);
                holder.txtInfo1.setText(str[1]);
                holder.txtsub2.setText(str[2]);
                holder.txtInfo2.setText(str[3]);
                holder.txtsub3.setText(str[4]);
                holder.txtInfo3.setText(str[5]);
                holder.txtsub4.setText(str[6]);
                holder.txtInfo4.setText(str[7]);
                holder.txtsub5.setText(str[8]);
                holder.txtInfo5.setText(str[9]);
            }else{
                holder.txtInfo1.setVisibility(View.VISIBLE);
                holder.txtInfo1.setText(medArraylist.get(position));
            }
        }else{
            holder.txtInfo1.setVisibility(View.VISIBLE);
            holder.txtInfo1.setText(medArraylist.get(position));
        }


        holder.rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fr != null) {
                    if (Type.equalsIgnoreCase("vaccine")) {//2
                        fr.vaccineEditDelete(position, 1);
                    } else if (Type.equalsIgnoreCase("allergy")) {//3
                        fr.allergyEditDelete(position, 1);
                    } else if (Type.equalsIgnoreCase("hospital")) {//1
                        fr.hospitalEditDelete(position, 1);
                    } else if (Type.equalsIgnoreCase("history")) {//4
                        fr.historyEditDelete(position, 1);
                    } else if (Type.equalsIgnoreCase("condition")) {//1
                        fr.conditionEditDelete(position, 1);
                    } else if (Type.equalsIgnoreCase("implants")) {//5
                        fr.implantsEditDelete(position, 1);
                    }
                }
            }
        });

        holder.rlEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fr != null) {
                    if (Type.equalsIgnoreCase("vaccine")) {
                        fr.vaccineEditDelete(position, 0);
                    } else if (Type.equalsIgnoreCase("allergy")) {
                        fr.allergyEditDelete(position, 0);
                    } else if (Type.equalsIgnoreCase("hospital")) {
                        fr.hospitalEditDelete(position, 0);
                    } else if (Type.equalsIgnoreCase("history")) {
                        fr.historyEditDelete(position, 0);
                    } else if (Type.equalsIgnoreCase("condition")) {
                        fr.conditionEditDelete(position, 0);
                    } else if (Type.equalsIgnoreCase("implants")) {
                        fr.implantsEditDelete(position, 0);
                    }
                }
            }
        });

        return convertView;
    }

    private class Holder {
        TextView txtInfo1, txtInfo2, txtInfo3, txtInfo4, txtInfo5;
        TextView txtsub1, txtsub2, txtsub3, txtsub4, txtsub5;
        ImageView imgEdit, imgDelete;
        RelativeLayout rlEdit, rlDelete;
    }

}
