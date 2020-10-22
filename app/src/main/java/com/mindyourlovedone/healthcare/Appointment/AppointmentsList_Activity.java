package com.mindyourlovedone.healthcare.Appointment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.Appointment.Adapter.AppointmentListAdapter;
import com.mindyourlovedone.healthcare.Appointment.Class.Doctor_list;
import com.mindyourlovedone.healthcare.Appointment.Model.AppointmentListPojo;
import com.mindyourlovedone.healthcare.HomeActivity.R;

import java.util.ArrayList;

public class AppointmentsList_Activity extends AppCompatActivity {
    private ImageView imgBackAppoint,imgHomeBack,imgInform;
    private TextView userName_self;
    private RadioGroup radioGrup;
    private RadioButton radiobtnTest,radiobtnAll,radiobtnSpecialist;
    private AutoCompleteTextView aCTV_SearchBar;
    private ListView appointmentList;
    private Context context;
    private ArrayList<AppointmentListPojo> appointmentListPojo = new ArrayList<>();
    private AppointmentListAdapter appointmentListAdapter;
    private String[] searchItems = {"Search by","Doctor"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_list_);

        initUI();
        initListener();
        setData();
        setAdapter();
    }

    private void setData() {

// static data has been given to the list by add()ing in Array<>list.
        appointmentListPojo = new ArrayList<>();
        AppointmentListPojo a1 = new AppointmentListPojo();
        a1.setMonth("Jan");
        a1.setDay("01");
        a1.setYear("2020");
        a1.setDoctorName("Dr.M.K.Baks");
        a1.setStatus("Completed");
        a1.setDiagnosisType("Diabetes");
        a1.setVisitingStatus("Daily");
        AppointmentListPojo a2 = new AppointmentListPojo();
        a2.setMonth("feb");
        a2.setDay("02");
        a2.setYear("2020");
        a2.setDoctorName("Dr Brick Wall");
        a2.setStatus("Completed");
        a2.setDiagnosisType("Hypertension");
        a2.setVisitingStatus("Daily");
        AppointmentListPojo a3 = new AppointmentListPojo();
        a3.setMonth("mar");
        a3.setDay("03");
        a3.setYear("2020");
        a3.setDoctorName("Dr Aikenhead");
        a3.setStatus("follow up");
        a3.setDiagnosisType("Hyperlipidemia");
        a3.setVisitingStatus("weekly");
        AppointmentListPojo a4 = new AppointmentListPojo();
        a4.setMonth("apr");
        a4.setDay("04");
        a4.setYear("2020");
        a4.setDoctorName(" Dr C. Surgeon Beavers");
        a4.setStatus("Completed");
        a4.setDiagnosisType("Back pain");
        a4.setVisitingStatus("Daily");
        AppointmentListPojo a5 = new AppointmentListPojo();
        a5.setMonth("may");
        a5.setDay("05");
        a5.setYear("2020");
        a5.setDoctorName("Dr Luke Whitesell ");
        a5.setStatus("new");
        a5.setDiagnosisType("Obesity");
        a5.setVisitingStatus("yearly");
        AppointmentListPojo a6 = new AppointmentListPojo();
        a6.setMonth("jun");
        a6.setDay("06");
        a6.setYear("2020");
        a6.setDoctorName("Dr Splettstoesser");
        a6.setStatus("follow up");
        a6.setDiagnosisType("Allergic rhinitis");
        a6.setVisitingStatus("weekly");
        AppointmentListPojo a7 = new AppointmentListPojo();
        a7.setMonth("jul");
        a7.setDay("07");
        a7.setYear("2020");
        a7.setDoctorName("Dr. Mary E. Jensen");
        a7.setStatus("Completed");
        a7.setDiagnosisType("Reflux esophagitis");
        a7.setVisitingStatus("Daily");
        AppointmentListPojo a8 = new AppointmentListPojo();
        a8.setMonth("aug");
        a8.setDay("08");
        a8.setYear("2020");
        a8.setDoctorName("Dr. Bradley M. Rodgers");
        a8.setStatus("new");
        a8.setDiagnosisType("Anxiety");
        a8.setVisitingStatus("yearly");
        AppointmentListPojo a9 = new AppointmentListPojo();
        a9.setMonth("sep");
        a9.setDay("00");
        a9.setYear("2020");
        a9.setDoctorName("Dr. Randolph J. Canterbury II");
        a9.setStatus("follow up");
        a9.setDiagnosisType("Osteoarthritis");
        a9.setVisitingStatus("weekly");
        AppointmentListPojo a10 = new AppointmentListPojo();
        a10.setMonth("oct");
        a10.setDay("10");
        a10.setYear("2020");
        a10.setDoctorName("Dr. William D. Steers");
        a10.setStatus("Completed");
        a10.setDiagnosisType("Major depressive disorder");
        a10.setVisitingStatus("Daily");
        AppointmentListPojo a11 = new AppointmentListPojo();
        a11.setMonth("nov");
        a11.setDay("11");
        a11.setYear("2020");
        a11.setDoctorName("Dr. William D. Steers");
        a11.setStatus("new");
        a11.setDiagnosisType("Depressive disorder");
        a11.setVisitingStatus("yearly");
        AppointmentListPojo a12 = new AppointmentListPojo();
        a12.setMonth("dec");
        a12.setDay("12");
        a12.setYear("2020");
        a12.setDoctorName("Dr.Baks");
        a12.setStatus("follow up");
        a12.setDiagnosisType("Urinary tract infection");
        a12.setVisitingStatus("Daily");
        appointmentListPojo.add(a1);
        appointmentListPojo.add(a2);
        appointmentListPojo.add(a3);
        appointmentListPojo.add(a4);
        appointmentListPojo.add(a5);
        appointmentListPojo.add(a6);
        appointmentListPojo.add(a7);
        appointmentListPojo.add(a8);
        appointmentListPojo.add(a9);
        appointmentListPojo.add(a10);
        appointmentListPojo.add(a11);
        appointmentListPojo.add(a12);



    }

    @SuppressLint("ClickableViewAccessibility")
    private void setAdapter(){
//listview adapter set
       AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(this,appointmentListPojo);
        appointmentList.setAdapter(appointmentListAdapter);
        appointmentList.deferNotifyDataSetChanged();
        //for dynamic hight of a list
        appointmentList.setDivider(null);
       // ListUtils.setDynamicHeight(appointmentList);




// aCTV set adapter
        final ArrayAdapter<String> adapterSearchBar = new ArrayAdapter<String>(this, R.layout.autocompletetextview_dropdown_list,
                R.id.textList, searchItems);
       // aCTV_SearchBar.setThreshold(1);
        aCTV_SearchBar.setDropDownAnchor(R.id.aCTV_SearchBar);
        aCTV_SearchBar.setAdapter(adapterSearchBar);

//set onTouchListener to show all the dropdown list by a touch on search bar
        aCTV_SearchBar.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

              /* //to blur the forground
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) v.getLayoutParams();
                // add flag
                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.3f;
                wm.updateViewLayout(v, p);
*/
                if (aCTV_SearchBar.length() >= 0) {
                    if (!aCTV_SearchBar.getText().toString().equals(""))
                        adapterSearchBar.getFilter().filter(null);
                    aCTV_SearchBar.showDropDown();

                }return false;
            }
        });
        //setting onClickListener on actv list and adding a static item to it as a Header.
        aCTV_SearchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = searchItems[i];

                if (s.equalsIgnoreCase("Search by")) {

                    Toast.makeText(context, "It is header of the list", Toast.LENGTH_SHORT).show();
                } else if (s.equalsIgnoreCase("Doctor")) {
                    Intent intent = new Intent(AppointmentsList_Activity.this, Doctor_list.class);
                    AppointmentsList_Activity.this.startActivity(intent);
                    finish();
                }
            }
        });


    }
    private void initListener() {

        appointmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "working", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        imgBackAppoint = findViewById(R.id.imgBacks);
        imgHomeBack = findViewById(R.id.imgHome);
        imgInform = findViewById(R.id.imgInform);
        radiobtnTest = findViewById(R.id.radiobtnTest);
        radiobtnAll = findViewById(R.id.radiobtnAll);
        radiobtnSpecialist = findViewById(R.id.radiobtnSpecialist);
        radioGrup = findViewById(R.id.radioGrup);
        appointmentList = findViewById(R.id.apponintmentList);
        aCTV_SearchBar = findViewById(R.id.aCTV_SearchBar);
        userName_self = findViewById(R.id.userName_self);

    }

    // this class is to set dynamic hight of multiple list in single Activity/Fragment/List_page.
   /* private static class ListUtils {
        private static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }


    }*/
}