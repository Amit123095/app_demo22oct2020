package com.mindyourlovedone.healthcare.Prescription;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;

public class Prescription_Edit_Activity extends AppCompatActivity {
    private ImageView imgBacks;
    private LinearLayout linear_copy, linear_delete, linear_DatePicker;
    private Dialog dialog;
    private TextView tv_CancelCopy, tv_CancelDelete, tv_Delete, tv_Copy;
    private TextView txtEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription__edit);


        txtEdit =findViewById(R.id.txtEdit);
        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEdit.setText("Save");
                txtEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Prescription_Edit_Activity.this, Prescription_List_Activity.class);
                        Prescription_Edit_Activity.this.startActivity(intent);
                        finish();
                    }
                });
            }
        });

        imgBacks = findViewById(R.id.imgBacks_s);
        imgBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(Prescription_Edit_Activity.this, Prescription_List_Activity.class);
                Prescription_Edit_Activity.this.startActivity(intent_back);
                finish();
            }
        });
//dialog popup of 2 btn Copy and Delete
        dialog = new Dialog(this);
        linear_copy = findViewById(R.id.linear_copy);
        linear_delete = findViewById(R.id.linear_delete);


        linear_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopup();
            }
        });
        linear_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCopyPopup();
            }
        });


    }

    private void showCopyPopup() {
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setContentView(R.layout.popup_copy);
        tv_CancelCopy = dialog.findViewById(R.id.tv_CancelCopy);
        tv_Copy = dialog.findViewById(R.id.tv_Copy);

        tv_CancelCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Prescription_Edit_Activity.this, "Back to list", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Prescription_Edit_Activity.this, Prescription_List_Activity.class);
                Prescription_Edit_Activity.this.startActivity(intent);
                finish();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void showDeletePopup() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setContentView(R.layout.popup_delete);
        tv_CancelDelete = dialog.findViewById(R.id.tv_CancelDelete);
        tv_Delete = dialog.findViewById(R.id.tv_Delete);

        tv_CancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Prescription_Edit_Activity.this, "Back to list", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Prescription_Edit_Activity.this, Prescription_List_Activity.class);
                Prescription_Edit_Activity.this.startActivity(intent);
                finish();
            }
        });
    }

}