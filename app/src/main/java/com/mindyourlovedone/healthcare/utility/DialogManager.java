package com.mindyourlovedone.healthcare.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.Connections.FragmentNewContact;
import com.mindyourlovedone.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedone.healthcare.DashBoard.OverviewActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentSpecialist;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by varsha on 8/21/2017.
 */

public class DialogManager {
    FragmentDashboard fragmentDashboard;
    FragmentSpecialist fragmentSpecialist;
    FragmentNewContact fragmentNewContact;

    public DialogManager(FragmentDashboard fragmentDashboard) {
        this.fragmentDashboard = fragmentDashboard;
    }


    public DialogManager(FragmentNewContact fragmentNewContact) {
        this.fragmentNewContact = fragmentNewContact;
    }

    public DialogManager() {

    }

    public DialogManager(FragmentSpecialist fragmentSpecialist) {
        this.fragmentSpecialist = fragmentSpecialist;
    }

    /*public static void showAlert(String msg, Context context, View layout) {
        TextView text = layout.findViewById(R.id.txt_toast);
        text.setText(msg);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }*/

    public static void showAlert(String msg, Context context) {
        if(context!=null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void closeKeyboard(Activity context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void showErrorDialog(String titles, String msg, final Context context, final String flag, final Object o) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogview = lf.inflate(R.layout.dialog_error, null);
        TextView title = dialogview.findViewById(R.id.txtTitle);
        if (titles.equals("")) {
            //title.setText(titles);
            // title.setVisibility(View.GONE);
        } else {
            title.setText(titles);
            title.setVisibility(View.VISIBLE);
        }
        TextView body = dialogview.findViewById(R.id.txtMsg);
        body.setText("" + msg);
        dialog.setContentView(dialogview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btnOk = dialogview
                .findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (flag) {
                    case "DELETE_TASK":
                        break;
                    default:
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void showCommonDialog(String titles, String msg, final Context context, final String flag, final Object o) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogview = lf.inflate(R.layout.dialog_common, null);
        TextView title = dialogview.findViewById(R.id.txtTitle);
        if (titles.equals("")) {
            title.setText(titles);
            title.setVisibility(View.GONE);
        } else {
            title.setText(titles);
            title.setVisibility(View.VISIBLE);
        }
        TextView body = dialogview.findViewById(R.id.txtMsg);
        body.setText("" + msg);
        dialog.setContentView(dialogview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView btnYes = dialogview
                .findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (flag) {
                    case "SHARE_LOCATION":
                        fragmentDashboard.postCommonDialog();
                        break;
                    case "ADD_CONNECTION":
                        fragmentNewContact.postCommonDialog();
                        break;


                    case "SAVE_OVERVIEW":
                        ((OverviewActivity) context).postCommonDialog();
                        break;

                    case "ADD_SPECIALIST":
                        fragmentSpecialist.postCommonDialog();
                        break;

                    default:
                        break;
                }
                dialog.dismiss();
            }
        });

        TextView btnNo = dialogview
                .findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (flag) {
                    case "SHARE_LOCATION":
                        dialog.dismiss();
                        break;
                    case "ADD_CONNECTION":
                        dialog.dismiss();
                        break;
                    case "SEND_REQUEST":
                        dialog.dismiss();
                        break;
                    case "SAVE_OVERVIEW":
                        dialog.dismiss();
                        break;

                    case "ADD_SPECIALIST":
                        fragmentNewContact.postCommonDialog();
                        break;
                    default:
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
