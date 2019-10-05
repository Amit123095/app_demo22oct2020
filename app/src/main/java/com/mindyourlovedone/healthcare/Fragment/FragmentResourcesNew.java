package com.mindyourlovedone.healthcare.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.ResourceAdapter;
import com.mindyourlovedone.healthcare.model.Links;
import com.mindyourlovedone.healthcare.model.ResourcesNew;
import com.mindyourlovedone.healthcare.model.Setting;
import com.mindyourlovedone.healthcare.utility.WebPDFActivity;
import com.mindyourlovedone.healthcare.webservice.WebService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class FragmentResourcesNew extends Fragment {
    View rootView;
    ArrayList<ResourcesNew> resourcesList, supportList, enduserList;
    ListView lvResources;
    ImageView imgHelp, imgProfile;
    TextView txtTitle;

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
        supportList = new ArrayList<ResourcesNew>();
        enduserList = new ArrayList<ResourcesNew>();

        ResourcesNew r1 = new ResourcesNew();
        r1.setName("Advance Directive Information");
        r1.setResImage(R.drawable.medical_one);

        ResourcesNew l12 = new ResourcesNew();
        l12.setName("App Wallet Card");
        // l12.setUrl("http://mindyour-lovedones.com/MYLO/uploads/MYLO_App_Wallet_Card.pdf");
        //l12.setUrl("wallet_card_new.pdf");
        l12.setResImage(R.drawable.insu_one);

        ResourcesNew s5 = new ResourcesNew();
        s5.setName("End User License Agreement and Privacy Policy");
        s5.setResImage(R.drawable.enduser);

        ResourcesNew r2 = new ResourcesNew();
        r2.setName("Helpful Forms & Templates");
        r2.setResImage(R.drawable.insu_one);

        ResourcesNew r3 = new ResourcesNew();
        r3.setName("Podcasts & Videos");
        r3.setResImage(R.drawable.drawer_videos);


        ResourcesNew s2 = new ResourcesNew();
        s2.setName("Support FAQs and User Guide");
        s2.setResImage(R.drawable.faq);


        resourcesList.add(r1);
        resourcesList.add(l12);
        resourcesList.add(s5);
        resourcesList.add(r2);
        resourcesList.add(r3);
        resourcesList.add(s2);


    }

    private void initUi() {
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("Resources");
        //imgProfile.setVisibility(View.GONE)

        lvResources = rootView.findViewById(R.id.lvResources);

        imgHelp = getActivity().findViewById(R.id.imgRight);
        imgHelp.setVisibility(View.GONE);

        //Shradha
        lvResources.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://Add Info
                        Intent intentf = new Intent(getActivity(), ADInfoActivity.class);
                        getActivity().startActivity(intentf);
                        break;

                    case 1://Wallet Cards
                        // Code for Server side pdf
                     /*   String formatD = "https://drive.google.com/viewerng/viewer?embedded=true&url=%s";
                        String fullPathD = String.format(Locale.ENGLISH, formatD, "http://mindyour-lovedones.com/MYLO/uploads/MYLO_App_Wallet_Card.pdf");
                        Intent browserIntentDs = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPathD));
                        startActivity(browserIntentDs);*/

//                     // New code for local pdf // varsha
//                        Intent intentp = new Intent();
//                        intentp.setAction(Intent.ACTION_VIEW);
//                        intentp.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intentp.setData(Uri.parse("market://details?id=cn.wps.moffice_eng"));//varsa ("market://details?id=com.adobe.reader"));
//                        intentp.setType(String.valueOf(Uri.parse("application/pdf")));
//                        CopyReadAssetss("wallet_card_new.pdf");


                        //nikita
                        Intent browserIntentD2 = new Intent(getActivity(), WebPDFActivity.class);
                        browserIntentD2.putExtra("Name", "Wallet Card");
                        browserIntentD2.putExtra("URL", WebService.WALLET_URL);
                        startActivity(browserIntentD2);

                        break;

                    case 2://End User License Agreement-Section and Privacy
                        Intent i = new Intent(getActivity(), SupportActivity.class);
                        i.putExtra("FROM", "EndUser");
                        startActivity(i);
                        break;
                    case 3: //HelpForm
                        // Toast.makeText(getActivity(), "Screen not provided Yet to come", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), HelpFormActivity.class);
                        getActivity().startActivity(intent);
                        break;

                    case 4://Podcast
                        // Toast.makeText(getActivity(), "Screen not provided Yet to come", Toast.LENGTH_SHORT).show();
                        Intent intentd = new Intent(getActivity(), VideoActivity.class);
                        getActivity().startActivity(intentd);
                        break;

                    case 5://Support Faq and User uide
                        Intent ij = new Intent(getActivity(), SupportActivity.class);
                        ij.putExtra("FROM", "Support");
                        startActivity(ij);
                        break;
                }
            }
        });


    }

    public void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getActivity().getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getActivity().getFilesDir(), documentPath);
        try {
            in = assetManager.open(documentPath);
            outFile = new File(getActivity().getExternalFilesDir(null), documentPath);
            out = new FileOutputStream(outFile);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            /*out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;*/
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri uri = null;
        // Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(getActivity(), "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);

    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }


    }
}

