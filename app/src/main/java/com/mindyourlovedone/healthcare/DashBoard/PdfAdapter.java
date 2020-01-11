package com.mindyourlovedone.healthcare.DashBoard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;

import java.io.File;
import java.util.List;

/**
 * Created by welcome on 10/17/2017.
 */
/**
 * Class: PdfAdapter
 * Screen: Pdf list from stoarge
 * A class that manages to Device storage pdf list
 * implements OnclickListener for onClick event on views
 */
class PdfAdapter extends BaseAdapter {
    Context context;
    String[] pdfList;
    LayoutInflater lf;
    ViewHolder holder;
    File[] imagelist;

    public PdfAdapter(Context context, String[] pdfList, File[] imagelist) {
        this.context = context;
        this.pdfList = pdfList;
        this.imagelist = imagelist;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pdfList.length;
    }

    @Override
    public Object getItem(int position) {
        return pdfList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_pdf, parent, false);
            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.txtName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(pdfList[position]);

        convertView.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                PackageManager packageManager = context.getPackageManager();
                Intent testIntent = new Intent(Intent.ACTION_VIEW);
                testIntent.setType("application/pdf");
                List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
//                        if (list.size() > 0 && imagelist[position].isFile()) {// commented
                ((DocumentSdCardList) context).getData(pdfList[position], String.valueOf(imagelist[position].getPath()));
                // dialog.dismiss();
                ((DocumentSdCardList) context).finish();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView txtName;

    }
}
