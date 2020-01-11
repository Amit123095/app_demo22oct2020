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
import com.mindyourlovedone.healthcare.model.Document;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017. Changes done by nikita on 20/6/18.
 * Changes done by shradha on 1/2/19
 */
/**
 * Class: DocumentAdapter
 * Screen: Document list
 * A class that manages to document list
 * implements OnclickListener for onClick event on views
 */
public class DocumentAdapter extends RecyclerSwipeAdapter<DocumentAdapter.ViewHolder> {
    Context context;
    ArrayList<Document> documentList;
    LayoutInflater lf;
    Preferences preferences;

    public DocumentAdapter(Context context, ArrayList<Document> documentList) {
        this.context = context;
        this.documentList = documentList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences=new Preferences(context);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_care_plan_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public void onBindViewHolder(final DocumentAdapter.ViewHolder holder, final int position) {
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });

        holder.lintrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof CarePlanListActivity) {
                    ((CarePlanListActivity) context).deleteDocument(documentList.get(position));
                }
            }
        });

        holder.txtDocDate.setVisibility(View.VISIBLE);


        if (documentList.get(position).getType().equals("Other")) {
            if (documentList.get(position).getOtherDoc().equalsIgnoreCase(""))
            {
                holder.txtDocHeader.setText(documentList.get(position).getType());

            }else{
                holder.txtDocHeader.setText(documentList.get(position).getType() + " - " + documentList.get(position).getOtherDoc());
            }
        } else {
            holder.txtDocHeader.setText(documentList.get(position).getType());
        }
        if (documentList.get(position).getPerson().equals("")) {
            holder.txtDocTime.setVisibility(View.GONE);
        } else {
            holder.txtDocTime.setText("Dated:");
            holder.txtDocTime.setVisibility(View.GONE);
        }

        if (documentList.get(position).getDate().equals("")) {
            holder.txtDocDate.setVisibility(View.GONE);
        } else {
            holder.txtDocDate.setVisibility(View.VISIBLE);
            holder.txtDocTime.setVisibility(View.GONE);
            holder.txtDocDate.setText("Dated: "+documentList.get(position).getDate());
        }

        String extension = FilenameUtils.getExtension(documentList.get(position).getName());
        showDocIcon(extension, preferences.getString(PrefConstants.CONNECTED_PATH)+ documentList.get(position).getDocument(),holder);
        
        holder.rlFix.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                Intent i = new Intent(context, AddDocumentActivity.class);
                i.putExtra("GoTo", "Edit");
                i.putExtra("Path", "Yes");
                i.putExtra("DocumentObject", documentList.get(position));
                context.startActivity(i);

            }
        });
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
    private void showDocIcon(String extension, String originPath, ViewHolder holder) {
        switch (extension)
        {
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
                holder.imgDocType.setImageURI(Uri.parse(originPath));;
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
}
