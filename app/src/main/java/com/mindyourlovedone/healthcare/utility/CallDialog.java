package com.mindyourlovedone.healthcare.utility;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.ContactData;

import java.util.ArrayList;

/**
 * Created by welcome on 11/8/2017.
 */
/**
 * Class: CallDialog
 * Screen: All List Screen
 * A class that manages the phone call
 */
public class CallDialog {
    Context context;

    public void showCallDialog(final Context context, String mobile, String hphone, String wphone) {
        this.context = context;
        try {
            Double.parseDouble(mobile);
            Double.parseDouble(hphone);
            Double.parseDouble(wphone);
        } catch (NumberFormatException ex) {
            System.out.println("Some Mistake");
        }
        String[] num = {mobile, hphone, wphone};
        final ArrayList<String> a = new ArrayList();
        for (int i = 0; i < num.length; i++) {
            if (num[i].length() != 0) {
                a.add(num[i]);
            }

        }
        String value = a.get(0);
        new AlertDialog.Builder(context)
                .setMessage(a.get(0))
                .setPositiveButton("Call",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                onCall(a.get(0));
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setCancelable(true).show();

    }

    /**
     * Function: Make Call on number
     * @param finalMobile
     */
    private void onCall(String finalMobile) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            context.startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + finalMobile)));
        }
    }


    public void showCallDialogs(final Context context, ArrayList<ContactData> phonelist) {
        this.context = context;

        final ArrayList<String> a = new ArrayList();
        for (int i = 0; i < phonelist.size(); i++) {
            if (phonelist.get(i).getValue().length() != 0) {
                if (!phonelist.get(i).getContactType().equalsIgnoreCase("Fax")) ;
                {
                    a.add(phonelist.get(i).getValue());
                }
            }
        }

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_call, null);
        dialog.setContentView(dialogview);

        ListView listCall = dialog.findViewById(R.id.listCall);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, a);
        listCall.setAdapter(ad);

        listCall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCall(a.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
