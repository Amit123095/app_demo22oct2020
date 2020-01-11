package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.RecyclerSwipeAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.SimpleSwipeListener;
import com.mindyourlovedone.healthcare.SwipeCode.SwipeLayout;
import com.mindyourlovedone.healthcare.model.Form;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017. Changes done by nikita on 20/6/18. Changes done by shradha on 1/2/19
 */

/**
 * Class: PresDocumentsAdapter
 * Screen: Prescription upload list
 * A class that manages to Prescription upload list
 * implements OnclickListener for onClick event on views
 */
public class PresDocumentsAdapter extends RecyclerSwipeAdapter<PresDocumentsAdapter.ViewHolder> {
    Context context;
    ArrayList<Form> documentList;
    LayoutInflater lf;

    FragmentPrescriptionUpload fr;
    Preferences preferences;

    public PresDocumentsAdapter(Context context, ArrayList<Form> documentList) {
        this.context = context;
        this.documentList = documentList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences = new Preferences(context);
    }

    public PresDocumentsAdapter(Context context, ArrayList<Form> documentList, FragmentPrescriptionUpload fr) {
        this.fr = fr;
        this.context = context;
        this.documentList = documentList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences = new Preferences(context);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public PresDocumentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_care_plan_new, parent, false);
        return new PresDocumentsAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    @Override
    public void onBindViewHolder(final PresDocumentsAdapter.ViewHolder holder, final int position) {
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });

        holder.lintrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fr != null) {
                    fr.deleteDocument(documentList.get(position));
                }
            }
        });

        holder.txtDocHeader.setText(documentList.get(position).getName());
        holder.txtDocTime.setText("Date: " + documentList.get(position).getDate());
        String extension = FilenameUtils.getExtension(documentList.get(position).getName());
        showDocIcon(extension, preferences.getString(PrefConstants.CONNECTED_PATH) + documentList.get(position).getDocument(), holder);
        holder.rlFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PrescriptionUploadActivity.class);
                i.putExtra("GoTo", "Edit");
                i.putExtra("Path", "Yes");
                i.putExtra("FormObject", documentList.get(position));
                context.startActivity(i);
            }
        });
        holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PrescriptionUploadActivity.class);
                i.putExtra("GoTo", "Edit");
                i.putExtra("Path", "Yes");
                i.putExtra("FormObject", documentList.get(position));
                context.startActivity(i);
            }
        });
    }

    private void showDocIcon(String extension, String originPath, ViewHolder holder) {
        //  Toast.makeText(context,extension,Toast.LENGTH_SHORT).show();
        switch (extension) {
            case "pdf":
                holder.imgDocType.setImageResource(R.drawable.pdf);
                break;
            case "txt":
                holder.imgDocType.setImageResource(R.drawable.docx);
                break;
            case "docx":
                holder.imgDocType.setImageResource(R.drawable.docx);
                break;
            case "xlsx":
                holder.imgDocType.setImageResource(R.drawable.excel);
                break;
            case "doc":
                holder.imgDocType.setImageResource(R.drawable.docx);
                break;
            case "xls":
                holder.imgDocType.setImageResource(R.drawable.excel);
                break;
            case "png":
                holder.imgDocType.setImageURI(Uri.parse(originPath));
                ;
                break;
            case "PNG":
                holder.imgDocType.setImageURI(Uri.parse(originPath));
                break;
            case "jpg":
                holder.imgDocType.setImageURI(Uri.parse(originPath));
                break;
            case "jpeg":
                holder.imgDocType.setImageURI(Uri.parse(originPath));
                break;
            case "ppt":
                holder.imgDocType.setImageResource(R.drawable.ppt);
                break;
            case "pptx":
                holder.imgDocType.setImageResource(R.drawable.ppt);
                break;
            default:
                holder.imgDocType.setImageResource(R.drawable.pdf);
                break;

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDocHeader, txtDocDate, txtDocTime;
        ImageView imgDocType, imgForword, imgEdit;
        SwipeLayout swipeLayout;
        LinearLayout lintrash;
        RelativeLayout rlFix;

        public ViewHolder(View convertView) {
            super(convertView);
            swipeLayout = itemView.findViewById(R.id.swipe);
            lintrash = itemView.findViewById(R.id.lintrash);
            txtDocHeader = convertView.findViewById(R.id.txtDocHeader);
            txtDocTime = convertView.findViewById(R.id.txtDocTime);
            txtDocDate = convertView.findViewById(R.id.txtDocDate);
            imgDocType = convertView.findViewById(R.id.imgDocType);
            imgForword = convertView.findViewById(R.id.imgNext);
            imgEdit = convertView.findViewById(R.id.imgEdit);
            rlFix = convertView.findViewById(R.id.rlFix);
        }
    }
}
