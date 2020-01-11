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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.model.Links;

import java.util.ArrayList;

/**
 * Created by welcome on 11/14/2017.
 */
//Old Class
public class FragmentResources extends Fragment {
    View rootview;
    ArrayList<Links> UrlList;
    ListView list;
    TextView txtName;
    ImageView imgDrawer, imgHelp;

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
        LinkAdapter adapter = new LinkAdapter(getActivity(), UrlList);
        list.setAdapter(adapter);
    }

    /**
     * Function: Fetch all resources
     */
    private void getData() {
        UrlList = new ArrayList<>();

        Links l4 = new Links();
        l4.setName("AARP links to Advance Directive Forms by State (PDF)");
        l4.setUrl("https://www.aarp.org/caregiving/financial-legal/free-printable-advance-directives/");
        l4.setImage(R.drawable.link_one);

        Links l5 = new Links();
        l5.setName("Aging with Dignity, Five Wishes");
        l5.setUrl("https://www.agingwithdignity.org/");
        l5.setImage(R.drawable.aging);

        Links l11 = new Links();
        l11.setName("ABA-American Bar Association, Commission on Law and Aging");
        l11.setUrl("https://www.americanbar.org/groups/law_aging/resources/health_care_decision_making/consumer_s_toolkit_for_health_care_advance_planning/");
        l11.setImage(R.drawable.aba_new);

        Links l7 = new Links();
        l7.setName("American Hospital Association, Put It In Writing");
        l7.setUrl("http://www.aha.org/advocacy-issues/initiatives/piiw/index.shtml");
        l7.setImage(R.drawable.link_three);

        Links l8 = new Links();
        l8.setName("Caring Connections links to Advance Directive Forms by State (PDF)");
        l8.setUrl("http://www.caringinfo.org/i4a/pages/index.cfm?pageid=3289");
        l8.setImage(R.drawable.care);

        Links l9 = new Links();
        l9.setName("Center for Practical Bioethics, Caring Conversations");
        l9.setUrl("https://www.practicalbioethics.org/resources/caring-conversations.html");
        l9.setImage(R.drawable.link_five);

        Links l10 = new Links();
        l10.setName("National Healthcare Decisions Day (NHDD),  Advance Care Planning");
        l10.setUrl("https://www.nhdd.org/public-resources/#where-can-i-get-an-advance-directive");
        l10.setImage(R.drawable.link_six);


        UrlList.add(l11);
        UrlList.add(l4);
        UrlList.add(l5);
        UrlList.add(l7);
        UrlList.add(l8);
        UrlList.add(l9);
        UrlList.add(l10);

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
        imgHelp = getActivity().findViewById(R.id.imgRight);
        imgHelp.setVisibility(View.GONE);

        imgDrawer = getActivity().findViewById(R.id.imgDrawer);
        imgDrawer.setImageResource(R.drawable.back_new);

        imgDrawer.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        txtName = getActivity().findViewById(R.id.txtTitle);
        txtName.setVisibility(View.VISIBLE);
        txtName.setText("Advance Directives Informaton");
        txtName.setGravity(View.TEXT_ALIGNMENT_CENTER);

        list = rootview.findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(UrlList.get(position).getUrl()));
                startActivity(intent);
            }
        });

    }

}
