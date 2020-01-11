package com.mindyourlovedone.healthcare.Connections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Pet;
import com.mindyourlovedone.healthcare.model.Setting;

import java.util.ArrayList;
/**
 * Class: PetAdapter
 * Screen: Pet List
 * A class that manages listing of pet informations
 */
public class PetAdapter extends BaseAdapter {

    Context context;
    ArrayList<Pet> petList;
    LayoutInflater lf;
    ViewHolder holder;

    public PetAdapter(Context context, ArrayList<Pet> petLists) {
        this.context=context;
        this.petList=petLists;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return petList.size();
    }

    @Override
    public Object getItem(int position) {
        return petList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Pet pet = petList.get(position);
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_pet, parent, false);
            holder = new ViewHolder();

            holder.txtProvider= (TextView) convertView.findViewById(R.id.txtProviderValue);
            holder.txtType= (TextView) convertView.findViewById(R.id.txtTypeValue);


            convertView.setTag(holder);
            view = convertView;
        } else {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtProvider.setText(pet.getName());
        holder.txtType.setText("Breed:"+pet.getBreed());

        return convertView;
    }

    public class ViewHolder {
        TextView txtProvider,txtType;
        ImageView imgCheck;
    }
}
