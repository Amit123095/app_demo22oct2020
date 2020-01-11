package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.mindyourlovedone.healthcare.model.Note;

import java.util.ArrayList;

/**
 * Created by welcome on 9/18/2017. Changes done by nikita on 20/6/18
 */
/**
 * Class: NoteAdapter
 * Screen: Event note list
 * A class that manages to Event note list
 * implements OnclickListener for onClick event on views
 */
public class NoteAdapter extends RecyclerSwipeAdapter<NoteAdapter.Holder> {
    Context context;
    ArrayList<Note> noteList;
    LayoutInflater lf;
    boolean flagDrop = false;

    public NoteAdapter(Context context, ArrayList noteList) {
        this.context = context;
        this.noteList = noteList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note, parent, false);
        return new Holder(view);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void onBindViewHolder(final NoteAdapter.Holder holder, final int position) {

        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });

        holder.lintrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof EventNoteActivity) {
                    ((EventNoteActivity) context).deleteNote(noteList.get(position));
                }
            }
        });

        if (noteList.get(position).getTxtNote().equals("")) {
            holder.txtNote.setVisibility(View.GONE);
        } else {
            holder.txtNote.setVisibility(View.VISIBLE);
            holder.txtNote.setText(noteList.get(position).getTxtNote());
        }

        holder.txtNote.setSingleLine(true);
        holder.txtNote.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtNote.setText(noteList.get(position).getTxtNote());

        holder.txtDateTime.setText(noteList.get(position).getTxtDate());

        holder.txtEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (context instanceof EventNoteActivity) {
                    ((EventNoteActivity) context).imgFrowardClick(noteList.get(position));
                }
            }
        });

        holder.rlTop.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                if (flagDrop == false) {
                    holder.llNote.setVisibility(View.VISIBLE);
                    holder.imgForward.setImageResource(R.drawable.dropup);
                    holder.txtNote.setVisibility(View.VISIBLE);
                    holder.txtNote.setSingleLine(false);
                    holder.txtNote.setText(noteList.get(position).getTxtNote());
                    holder.rlView.setVisibility(View.GONE);
                    flagDrop = true;
                } else if (flagDrop == true) {
                    holder.llNote.setVisibility(View.GONE);
                    holder.imgForward.setImageResource(R.drawable.drop_down);
                    holder.txtNote.setVisibility(View.VISIBLE);
                    holder.txtNote.setSingleLine(true);
                    holder.txtNote.setEllipsize(TextUtils.TruncateAt.END);
                    holder.txtNote.setText(noteList.get(position).getTxtNote());
                    holder.rlView.setVisibility(View.VISIBLE);
                    flagDrop = false;
                }
            }
        });
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtNote, txtNoteData, txtDateTime, txtEditNote, txtTime;
        ImageView imgForward, imgDrop;
        LinearLayout lintrash, llNote;
        SwipeLayout swipeLayout;
        RelativeLayout rlTop, rlView;

        public Holder(View convertView) {
            super(convertView);
            swipeLayout = itemView.findViewById(R.id.swipe);
            lintrash = itemView.findViewById(R.id.lintrash);
            llNote = itemView.findViewById(R.id.llNote);
            txtNote = convertView.findViewById(R.id.txtNote);
            txtNoteData = convertView.findViewById(R.id.txtNoteData);
            txtDateTime = convertView.findViewById(R.id.txtDateTime);
            txtEditNote = convertView.findViewById(R.id.txtEditNote);
            imgForward = convertView.findViewById(R.id.imgForword);
            imgDrop = convertView.findViewById(R.id.imgDrop);
            rlTop = convertView.findViewById(R.id.rlTop);
            rlView = convertView.findViewById(R.id.rlView);

        }
    }
}