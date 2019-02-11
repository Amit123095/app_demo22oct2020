package com.mindyourlovedone.healthcare.HomeActivity;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.InsuranceHealthCare.ResourceAdapter;
import com.mindyourlovedone.healthcare.model.ResourcesNew;

import java.util.ArrayList;

public class FragmentResourcesNew extends Fragment {
    View rootView;
    ArrayList<ResourcesNew> resourcesList;
    ListView lvResources;
    ImageView imgHelp;
    TextView txtName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_resources_new, container, false);
        initUi();
        getData();
        setData();

        return rootView;
    }

    private void setData() {
        ResourceAdapter rd = new ResourceAdapter(getActivity(), resourcesList);
        lvResources.setAdapter(rd);
    }

    private void getData() {
        resourcesList = new ArrayList<ResourcesNew>();

        ResourcesNew r1 = new ResourcesNew();
        r1.setName("Advance Directive Information");
        r1.setResImage(R.drawable.medical_one);

        ResourcesNew r2 = new ResourcesNew();
        r2.setName("Helpful Forms & Templates");
        r2.setResImage(R.drawable.insu_one);

        ResourcesNew r3 = new ResourcesNew();
        r3.setName("Podcasts & videos");
        r3.setResImage(R.drawable.drawer_videos);

        resourcesList.add(r1);
        resourcesList.add(r2);
        resourcesList.add(r3);
    }

    private void initUi() {
        txtName = getActivity().findViewById(R.id.txtName);
        txtName.setText("Resources");
        lvResources = rootView.findViewById(R.id.lvResources);
        imgHelp = getActivity().findViewById(R.id.imgHelp);
        imgHelp.setVisibility(View.GONE);
    }
}

