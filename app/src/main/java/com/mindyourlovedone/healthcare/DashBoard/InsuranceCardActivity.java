package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Card;

import java.util.ArrayList;

/**
 * Class: InsuranceCardActivity
 * Screen: Insurance Card List Screen
 * A class that manages to display list of event notes
 * implements OnclickListener for onClick event on views
 */
public class InsuranceCardActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_PRES = 100;
    Context context = this;
    RecyclerView lvCard;
    ImageView imgBack, imgRight;
    ArrayList<Card> CardList;
    RelativeLayout llAddCard;
    ImageView floatAdd;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_card);
        getData();
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();
        setCardData();
    }

    private void getData() {
        CardList = new ArrayList<>();
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        floatAdd.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        imgRight = findViewById(R.id.imgRight);
        imgBack = findViewById(R.id.imgBack);
        llAddCard = findViewById(R.id.llAddCard);
        lvCard = findViewById(R.id.lvCard);
        txtView = findViewById(R.id.txtView);
        floatAdd = findViewById(R.id.floatAdd);
    }

    private void setCardData() {
        lvCard.setVisibility(View.VISIBLE);
        CardAdapter adapter = new CardAdapter(context, CardList);
        lvCard.setAdapter(adapter);
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.floatAdd://Add New Contact
                Intent i = new Intent(context, AddCardActivity.class);
                startActivityForResult(i, REQUEST_PRES);
                break;
            case R.id.imgRight:

                break;
        }
    }
}