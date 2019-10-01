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
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_card);
        getData();
        initUI();
        initListener();
        setCardData();
    }

    private void getData() {
        CardList = new ArrayList<>();

       /* Card c1=new Card();
        c1.setName("Jackson Montana");
        c1.setType("Supplemental Insurance");
        c1.setImgFront(R.drawable.front);
        c1.setImgBack(R.drawable.back);

        Card c2=new Card();
        c2.setName("James Johnson");
        c2.setType("Medicare");
        c2.setImgFront(R.drawable.fronts);
        c2.setImgBack(R.drawable.backs);

        CardList.add(c1);
        CardList.add(c2);*/
    }

    private void initListener() {
        floatAdd.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        imgRight = findViewById(R.id.imgRight);
        imgBack = findViewById(R.id.imgBack);
        llAddCard = findViewById(R.id.llAddCard);
        lvCard = findViewById(R.id.lvCard);
        txtView = findViewById(R.id.txtView);
        floatAdd=findViewById(R.id.floatAdd);
    }

    private void setCardData() {
       /* if (CardList.size()!=0)
        {
            lvCard.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.GONE);
        }else{
            txtView.setVisibility(View.VISIBLE);
            lvCard.setVisibility(View.GONE);
        }*/
        lvCard.setVisibility(View.VISIBLE);
        CardAdapter adapter = new CardAdapter(context, CardList);
        lvCard.setAdapter(adapter);

       /* lvCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final ImageView imgFront= (ImageView) view.findViewById(R.id.imgFront);
                final ImageView imgPre= (ImageView) view.findViewById(R.id.imgBack);
                final ImageView imgCard= (ImageView) view.findViewById(R.id.imgCard);
                ImageView imgForword= (ImageView) view.findViewById(R.id.imgForword);
                imgFront.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgCard.setImageResource(CardList.get(position).getImgFront());
                        imgPre.setImageResource(R.drawable.white_dot);
                        imgFront.setImageResource(R.drawable.blue_dot);
                    }
                });
                imgPre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgCard.setImageResource(CardList.get(position).getImgBack());
                        imgPre.setImageResource(R.drawable.blue_dot);
                        imgFront.setImageResource(R.drawable.white_dot);
                    }
                });

                imgForword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(context,ViewCardActivity.class);
                        i.putExtra("Card",CardList.get(position));
                        startActivity(i);
                    }
                });
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.floatAdd:
                // preferences.putString(PrefConstants.SOURCE,"Card");
                Intent i = new Intent(context, AddCardActivity.class);
                startActivityForResult(i, REQUEST_PRES);
                break;
            case R.id.imgRight:

                break;
        }
    }
/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PRES && data != null) {

      *//*  Card p=new Card();
        p.setDates(data.getExtras().getString("Date"));
        p.setDoctor(data.getExtras().getString("Name"));
        p.setDosageList((ArrayList<Dosage>)data.getExtras().getSerializable("Dosage"));
        p.setCardImageList((ArrayList<PrescribeImage>) data.getExtras().getSerializable("Image"));*//*

            CardList.add((Card) data.getSerializableExtra("PrObj"));
            setCardData();
        }

    }*/


}