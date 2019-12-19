package com.mindyourlovedone.healthcare.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.TypeAdapter;

import java.util.ArrayList;

public class FragmentType extends Fragment {
    View rootView;
    ListView lvType;
    ArrayList<String> typeList = new ArrayList<>();
    String[] Relationship = {"Aunt", "Brother", "Brother-in-law", "Client", "Cousin", "Dad", "Daughter","Daughter-in-law", "Father-in-law", "Friend", "Granddaughter", "Grandmother", "Grandfather", "Grandson", "Husband", "Mom", "Mother-in-law", "Neighbor", "Nephew", "Niece", "Patient", "Roommate", "Significant Other", "Sister", "Sister-in-law", "Son","Son-in-law", "Uncle", "Wife", "Other"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lvType = getActivity().findViewById(R.id.lvType);
        Toast.makeText(getActivity(), "Hi u are in Type Fragment", Toast.LENGTH_SHORT).show();
        if (getArguments() != null) {
            String category = getArguments().getString("Category");
            if (category.equalsIgnoreCase("TypeAppointment")) {
                TypeAdapter rd = new TypeAdapter(getActivity(), Relationship);
                lvType.setAdapter(rd);
            } else {
                Toast.makeText(getActivity(), "Dude m not here..!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Dude m not here as well..!!", Toast.LENGTH_SHORT).show();
        }
        // initUi();
        return inflater.inflate(R.layout.fragment_type, container, false);
        //return rootView;
    }


    private void initUi() {
        lvType = getActivity().findViewById(R.id.lvType);
    }
}

