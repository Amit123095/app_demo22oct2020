package com.mindyourlovedone.healthcare.Fragment;

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

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.SettingAdapter;
import com.mindyourlovedone.healthcare.model.Setting;

import java.util.ArrayList;

public class FragmentSetting extends Fragment {
    View rootView;
    ArrayList<Setting> settingList;
    ListView lvSetting;
    ImageView imgHelp;
    TextView txtTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        initUi();
        getData();
        setData();

        return rootView;
    }

    private void setData() {
        SettingAdapter sd = new SettingAdapter(getActivity(), settingList);
        lvSetting.setAdapter(sd);
    }

    private void getData() {
        settingList = new ArrayList<Setting>();

        Setting s1 = new Setting();
        s1.setName("Backup, Restore, Share");
        s1.setResImage(R.drawable.medical_one);

        Setting s2 = new Setting();
        s2.setName("Support FAQs");
        s2.setResImage(R.drawable.insu_one);

        Setting s3 = new Setting();
        s3.setName("User Guide");
        s3.setResImage(R.drawable.drawer_videos);

        Setting s4 = new Setting();
        s4.setName("Privacy Policy");
        s4.setResImage(R.drawable.drawer_videos);

        Setting s5 = new Setting();
        s5.setName("End User License Agreement");
        s5.setResImage(R.drawable.drawer_videos);

      //  settingList.add(s1);
        settingList.add(s2);
        settingList.add(s3);
        settingList.add(s4);
        settingList.add(s5);
    }

    private void initUi() {
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("Settings");
        lvSetting = rootView.findViewById(R.id.lvSetting);
        imgHelp = getActivity().findViewById(R.id.imgRight);
        imgHelp.setVisibility(View.GONE);


        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    /*case 0://BackUp-Section
                        Intent intentContactUs = new Intent(getActivity(), DropboxLoginActivity.class);
                        getActivity().startActivity(intentContactUs);
                        break;*/
                    case 0://Support Faq
                        Intent browserIntent = new Intent(getActivity(),WebViewActivity.class);
                        browserIntent.putExtra("Name","Support FAQs");
                        startActivity(browserIntent);
                        //nikita
                        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mindyour-lovedones.com/MYLO/uploads/Support_Faqs.pdf"));
                       // startActivity(browserIntent);
                        break;
                    case 1://User Guide-Section

                       /* Intent browserIntents = new Intent(getActivity(),WebViewActivity.class);
                        browserIntents.putExtra("Name","User Guide");
                        startActivity(browserIntents);*/
                       //nikita
                        Intent browserIntentD = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mindyour-lovedones.com/MYLO/uploads/User_Guide.pdf"));
                        startActivity(browserIntentD);

                        break;
                    case 2://Privacy Policy-Section

                        /*Intent intentp = new Intent();
                        intentp.setAction(Intent.ACTION_VIEW);
                        intentp.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intentp.setData(Uri.parse("market://details?id=cn.wps.moffice_eng"));//varsa ("market://details?id=com.adobe.reader"));
                        intentp.setType(String.valueOf(Uri.parse("application/pdf")));
                        ((BaseActivity) getActivity()).CopyReadAssetss("Privacy Policy.pdf");
*/

                        break;
                    case 3://End User License Agreement-Section
                        /*Intent intentx = new Intent();
                        intentx.setAction(Intent.ACTION_VIEW);
                        intentx.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intentx.setData(Uri.parse("market://details?id=cn.wps.moffice_eng"));//varsa ("market://details?id=com.adobe.reader"));
                        intentx.setType(String.valueOf(Uri.parse("application/pdf")));
                        ((BaseActivity) getActivity()).CopyReadAssetss("eula_new.pdf");
*/
                        break;
                }
            }
        });

    }


}

