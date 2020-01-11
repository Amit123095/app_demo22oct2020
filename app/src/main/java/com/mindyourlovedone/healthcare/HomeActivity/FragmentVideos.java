package com.mindyourlovedone.healthcare.HomeActivity;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.model.Links;

import java.util.ArrayList;

/**
 * Created by welcome on 11/14/2017.
 */
/**
 * Class: FragmentVideos
 * Screen: How-To-Videos Screen
 * A class is commin soon
 */
public class FragmentVideos extends Fragment {

    View rootview;
    ArrayList<String> Datalist;
    ArrayList<Links> UrlList;
    ListView list;
    TextView txtTitle, txtName;
    ImageView imgNoti, imgProfile, imgLogo, imgPdf;

    /**
     * @param inflater           LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view. This value may be null.
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_resources, null);
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();
        getData();
        setData();
        return rootview;
    }

    private void setData() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.row_video, R.id.txtName, Datalist);
        list.setAdapter(adapter);
    }

    /**
     * Function: Fetch all Video Link
     */
    private void getData() {
        UrlList = new ArrayList<>();
        Links l1 = new Links();
        l1.setName("Overview | Aging Matters | NPT Reports");
        l1.setUrl("https://www.youtube.com/watch?v=CyIepl3V4Ro");
        UrlList.add(l1);
        //Fol show
        Datalist = new ArrayList<>();
        Datalist.add("Overview | Aging Matters | NPT Reports");
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {

    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("VIDEOS");
        imgPdf = getActivity().findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgProfile = getActivity().findViewById(R.id.imgProfile);
        txtName = getActivity().findViewById(R.id.txtName);
        txtName.setVisibility(View.GONE);
        imgProfile.setVisibility(View.GONE);
        imgNoti = getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);
        imgLogo = getActivity().findViewById(R.id.imgLogo);
        imgLogo.setVisibility(View.GONE);
        list = rootview.findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Datalist.get(position).equals(UrlList.get(position).getName())) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(UrlList.get(position).getUrl()));
                    startActivity(intent);
                }
            }
        });
    }


}
