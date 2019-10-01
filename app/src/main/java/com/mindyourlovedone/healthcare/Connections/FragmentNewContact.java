package com.mindyourlovedone.healthcare.Connections;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mindyourlovedone.healthcare.DashBoard.AddFormActivity;
import com.mindyourlovedone.healthcare.DashBoard.DateClass;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.database.AideQuery;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DoctorQuery;
import com.mindyourlovedone.healthcare.database.FinanceQuery;
import com.mindyourlovedone.healthcare.database.HospitalHealthQuery;
import com.mindyourlovedone.healthcare.database.InsuranceQuery;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.PharmacyQuery;
import com.mindyourlovedone.healthcare.database.SpecialistQuery;
import com.mindyourlovedone.healthcare.model.Aides;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.model.Finance;
import com.mindyourlovedone.healthcare.model.Hospital;
import com.mindyourlovedone.healthcare.model.Insurance;
import com.mindyourlovedone.healthcare.model.Pharmacy;
import com.mindyourlovedone.healthcare.model.Proxy;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.model.Specialist;
import com.mindyourlovedone.healthcare.utility.AppConstants;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.riontech.staggeredtextgridview.StaggeredTextGridView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.mindyourlovedone.healthcare.database.InsuranceQuery.getLastInsurance;
import static com.mindyourlovedone.healthcare.database.PharmacyQuery.getLastPharmacy;


/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentNewContact extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CARD = 50;
    private static final int RESULT_INSURANCE = 16;
    private static final int RESULT_SPECIALTY_NETWORK = 17;
    private static final int RESULT_HOSPITAL_NETWORK = 18;
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;
    private static int RESULT_CAMERA_IMAGE_CARD = 3;
    private static int RESULT_SELECT_PHOTO_CARD = 4;
    private static int RESULT_RELATION = 10;
    private static int RESULT_PRIORITY = 12;
    private static int RESULT_SPECIALTY = 13;
    private static int RESULT_CATEGORY = 14;
    private static int RESULT_FINANCECAT = 15;
    private static int RESULT_TYPE = 11;
    ImageView imgBack;
    Bitmap ProfileMap = null, CardMap = null;
    ListView listRelation, listPhone;
    boolean backflap;
    ContentValues values;
    static int val = 1;
    Uri imageUriProfile, imageUriCard;
    // byte[] photoCard = null;
    String card = "";
    static String Cname = "";
    static String Cemail = "";
    static String Cphone = "";
    static String CAddress = "";
    static String CHPhone = "";
    static String CWPhone = "";
    Emergency rel;
    TextView txtsave;
    Specialist specialist, specialistDoctor;
    Hospital hospital;
    Pharmacy pharmacy;
    Insurance insurance;
    LinearLayout PhoneLayout, A_PhoneLayout;
    Finance finance;
    RelativeLayout rlCard, rlContact, RlPhone;
    TextView txtCardz, txtDelete;
    ImageView txtCard;
    LayoutInflater layoutInflater;
    String rela = "", aentEmail = "";
    //TextView btnShowMore,btnShowLess,btnSon;
    TextView txtOtherInsurance, txtOtherCategory, txtOtherRelation, txtName, txtEmail, txtMobile, txtHomePhone, txtWorkPhone, txtAdd, txtInsuaranceName, txtInsuarancePhone, txtId, txtGroup, txtMember, txtAddress;
    String contactName = "";
    TextView txtContactName, txtFinanceEmail, txtFinanceLocation, txtAgent, txtPracticeName, txtFax, txtNetwork, txtAffiliation, txtDoctorNote, txtDoctorName, txtDoctorOfficePhone, txtDoctorHourOfficePhone, txtDoctorOtherPhone, txtDoctorFax, txtDoctorWebsite;
    TextView txtDoctorAddress, txtDoctorLastSeen, txtDoctorLocator, txtSubscribe, txtSubscribes, txtInsuaranceFax, txtInsuaranceEmail, txtWebsite, txtInsuaranceNote;
    TextView txtFName, txtFinanceOfficePhone, txtFinanceMobilePhone, txtFinanceOtherPhone, txtFinanceFax, txtFinanceAddress, txtFinanceWebsite, txtFinancePracticeName, txtLastSeen, txtFinanceNote;
    TextView txtAids, txtSchedule, txtOther, txtEmergencyNote;
    TextView txtPharmacyName, txtPharmacyAddress, txtPharmacyLocator, txtPharmacyPhone, txtPharmacyFax, txtPharmacyWebsite, txtPharmacyNote;
    TextView txtAideAddress, txtAideCompName, txtAideOfficePhone, txtHourOfficePhone, txtOtherPhone, txtAideFax, txtAideEmail, txtAideWebsite, txtAideNote;
    TextView txtTitle, txtRelation, txtPriority;
    TextView txtAentEmail, txtAentPhone, txtHospitalLocator, txtOtherCategoryDoctor, txtOtherCategoryHospital, txtFNameHospital, txtHospitalOfficePhone, txtHospitalOtherPhone, txtHospitalFax, txtHospitalAddress, txtHospitalWebsite, txtHospitalLocation, txtHospitalPracticeName, txtHospitalLastSeen, txtHospitalNote;
    TextInputLayout tilFNameHospital, tilOtherCategoryDoctor;
    String otherDoctor = "";
    String agent = "", aentPhone = "";
    ImageView imgEdit, imgProfile, imgCard, imgEditCard;
    View rootview;
    RelativeLayout rlRelation, rlDoctorCategory, rlHospital, rlConnection, rlDoctor, rlInsurance, rlCommon, rlAids, rlFinance, rlProxy, rlTop, llAddConn, rlPharmacy;
    Preferences preferences;
    String source = "";
    TextInputLayout tilOtherCategoryHospital, tilPriority;
    ImageView imgprio;
    FrameLayout flPr;
    String has_card = "NO";
    String location = "";
    String name = "", Email = "", email = "", mobile = "", speciality = "", phone = "", address = "", workphone = "", note = "", member = "", group = "", subscriber = "", type = "";
    String network = "", affil = "", practice_name = "", website = "", lastseen = "", locator = "";
    String fax = "";
    String relation = "";
    String proxy = "";
    String priority = "";
    String otherRelation = "";
    String otherCategory = "";
    String otherInsurance = "";
    int prior;
    int prox = 0;
    int connectionFlag;
    boolean inPrimary;
    MySpinner spinner, spinnerInsuarance, spinnerFinance, spinnerProxy, spinnerRelation, spinnerPriority, spinnerHospital;
    TextInputLayout tilInsutype, tilFCategory, tilSpecialty, tilRelation, tilOtherInsurance, tilOtherCategory, tilOtherRelation, tilName, tilFName, tilEmergencyNote, tilDoctorName, tilPharmacyName, tilAideCompName, tilInsuaranceName;
    TextView txtSpecialty, txtHCategory, txtFCategory, txtInsuType;
    StaggeredTextGridView gridRelation;
    ArrayList<String> relationArraylist;
    RelationAdapter relationAdapter;
    ToggleButton tbCard;
    DialogManager dialogManager;

    String imagepath = "", cardPath = "";

    String[] Relationship = {"Aunt", "Brother", "Brother-in-law", "Client", "Cousin", "Dad", "Daughter", "Father-in-law", "Friend", "GrandDaughter", "GrandMother", "GrandFather", "GrandSon", "Husband", "Mom", "Mother-in-law", "Neighbor", "Nephew", "Niece", "Patient", "Roommate", "Significant Other", "Sister", "Sister-in-law", "Son", "Uncle", "Wife", "Other"};

    //String[] healthSpeciality = {"Acupunture", "Allergy & Immunology", "Anesthesiology", "Audiology", "Cardiology", "Chiropractor", "Cosmetic and Laser Surgeon  ", "Critical Care Medicine ", "Dentist ", "Dermatology", "Diabetes & Metabolism", "Emergency Medicine", "Endocrinology", "Endodontics", "Endovascular Medicine", "Family Medicine", "Foot and Ankle Surgeon ", "Gastroenterology", "Geriatric Medicine", "Gynecology", "Hospice & Palliative Medicine	", "Infectious Disease", "Internal Medicine", "Massage Therapy", "Medical Genetics", "Nephrology", "Neurology", "Obstetrics & Gynecology", "Oncology ", "Ophthalmology", "Optometrist", "Orthodontics", "Orthopadeic ", "Orthopadeic Surgeon ", "Otolaryngology", "Pain Medicine", "Pathology", "Pediatrics", "Periodontics", "Physical Therapist", "Plastic & Reconstructive Surgeon ", "Podiatrist ", "Psychiatry", "Pulmonology", "Radiology", "Rheumatology", "Speech Therapist", "Sports Medicine", "Surgeon  - General ", "Thoracic & Cardiac Surgeon ", "Urology", "Vascular Medicine", "Other"};
    String[] healthSpeciality = {"Acupuncturist", "Allergist (Immunologist)", "Anesthesiologist", "Audiologist", "Cardiologist", "Cardiothoracic Surgeon", "Chiropractor", "Colorectal Surgeon", "Cosmetic Surgeon", "Critical Care Medicine", "Dentist", "Dermatologist", "Dietitian/Nutritionist", "Diabetes & Metabolism", "Ear, Nose & Throat Doctor (ENT, Otolaryngologist)", "Emergency Medicine", "Endocrinologist (incl. Diabetes Specialists)", "Endodontics", "Endovascular Medicine", "Eye Doctor", "Family Medicine", "Gastroenterologist", "Geriatrician", "Gynecologist", "Hearing Specialist", "Hematologist (Blood Specialist)", "Hospice", "Infectious Disease Specialist", "Infertility Specialist", "Internal Medicine", "Midwife", "Naturopathic Doctor", "Nephrologist (Kidney Specialist)", "Neurologist (Inc. Headache Specialist)", "Neurosurgeon", "OB-GYN (Obstetrician-Gynecologist)", "Occupational Therapist", "Oncologist", "Ophthalmologist", "Optometrist", "Oral Surgeon", "Orthodontist", "Orthopedic Surgeon (Orthopedist)", "Osteopath", "Otolaryngologist", "Pain Management Specialist", "Palliative Care Specialist", "Pediatric Dentist", "Pediatrician", "Periodontist", "Physician Assistant", "Physiatrist (Physical Medicine)", "Physical Therapist", "Plastic & Reconstructive Surgeon", "Podiatrist (Foot and Ankle Specialist)", "Primary Care Doctor (PCP)", "Prosthodontist", "Psychiatrist", "Psychologist", "Psychotherapist", "Pulmonologist (Lung Doctor)", "Radiologist", "Rheumatologist", "Sleep Medicine Specialist", "Speech Therapist", "Sports Medicine Specialist", "Surgeon - General", "Therapist / Counselor", "Thoracic & Cardiac Surgery", "Urgent Care Specialist", "Urological Surgeon", "Urologist", "Vascular Surgeon", "Other"};

    //String[] insuaranceType = {"Dental", "Disability", "Life", "Long Term Care", "Medicaid", "Medical", "Medicare Supplement (Medigap)", "Medicare", "Supplemental", "Vision", "Other"};
    String[] insuaranceType = {"Apartment", "Auto", "Dental", "Disability", "Home", "Life (Wholelife or Term)", "Long Term Care", "Medicaid", "Medical", "Medicare", "Medicare Supplemental (Medigap)", "Supplemental", "Umbrella", "Vision", "Other"};

    String[] financeType = {"Accountant", "Attorney", "Broker", "Financial Adviser", "Financial Planner", "Notary", "Other"};

    String[] HospitalType = {"Hospital", "Rehabilitation Center", "Home Health Care Agency", "Home Health Care Aide", "TeleMed", "Urgent Care", "Other"};

    String[] proxyType = {"Primary Health Care Proxy Agent", "Successor - Health Care Proxy Agent"};
    //  String[] priorityType = {"Primary Health Care Proxy Agent", "Primary Emergency Contact", "Secondary Health Care Proxy Agent", "Secondary Emergency Contact"};
    String[] priorityType = {"Primary Emergency Contact", "Primary Health Care Proxy Agent", "Secondary Emergency Contact", "Secondary Health Care Proxy Agent", "Primary Emergency Contact and Health Care Proxy Agent"};

    Boolean isEdit;

    DBHelper dbHelper, dbHelper1;
    int id;
    int isPhysician;

    ImageLoader imageLoaderProfile, imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile, displayImageOptionsCard;
    //new
    boolean isOnActivityResult = false;
    String cardImgPath = "";
    public static boolean fromDevice = false;

    LinearLayout llAddPhone, llAddDrPhone, llAddHospPhone, llAddPharmPhone, llAddFinPhone, llAddInsuPhone, llAddAentPhone;
    ImageView imgAddPhone, imgAddDrPhone, imgAddHospPhone, imgAddPharmPhone, imgAddFinPhone, imgAddInsuPhone, imgAddAentPhone;
    TextView txtType, txtDrType;

    //    NonScrollListView listPrPhone,listDrPhone,listHospPhone,listPharmPhone,listFinPhone,listInsuPhone;
    public ArrayList<ContactData> phonelist = new ArrayList<>();
    public ArrayList<ContactData> Aphonelist = new ArrayList<>();

    FrameLayout flFront;
    RelativeLayout rlInsured;
    ScrollView scroll;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_new_contact, null);
        preferences = new Preferences(getActivity());
        getRelationData();
        initComponent();
        initImageLoader();
        initUI();
        initListener();
        initVariables();

        txtsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backflap=false;
                savedata();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFunction(source);
            }
        });
        return rootview;
    }


    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        this.context = getActivity();
    }


    //Nikita - PH format code ends here
    ArrayList<EditText> mTextViewListValue = new ArrayList<>();
    ArrayList<TextView> mTextViewListType = new ArrayList<>();
    ArrayList<ImageView> mImageViewType = new ArrayList<>();

    ArrayList<EditText> TextViewListValue = new ArrayList<>();
    ArrayList<TextView> TextViewListType = new ArrayList<>();
    ArrayList<ImageView> ImageViewType = new ArrayList<>();

    public class CustomTextWatcher implements TextWatcher {
        EditText et = null;

        CustomTextWatcher(EditText et) {
            this.et = et;
        }

        int prevL = 0;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            prevL = et.getText().toString().length();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int length = editable.length();
//            int poss = Integer.parseInt(et.getTag().toString());
            if ((prevL < length) && (length == 3 || length == 7)) {
                et.setText(editable.toString() + "-");
                et.setSelection(et.getText().length());
            }
//            phonelist.get(poss).setValue(et.getText().toString());
        }

    }

    public void deletePhone(int position) {// Tricky code to delete required item
        try {
            for (int i = 0; i < phonelist.size(); i++) {
                if (phonelist.get(i).getId() == position) {//uses index As it is but matching ids
                    ContactData cc = phonelist.get(i);
                    phonelist.remove(cc);
                    PhoneLayout.removeAllViews();
                    mTextViewListValue.clear();
                    mTextViewListType.clear();
                    mImageViewType.clear();
                    setListPh();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            PhoneLayout.removeAllViews();
            mTextViewListValue.clear();
            mTextViewListType.clear();
            mImageViewType.clear();
            setListPh();
        }
    }

    public void deleteAPhone(int position) {// Tricky code to delete required item
        try {
            for (int i = 0; i < Aphonelist.size(); i++) {
                if (Aphonelist.get(i).getId() == position) {//uses index As it is but matching ids
                    ContactData cc = Aphonelist.get(i);
                    Aphonelist.remove(cc);
                    A_PhoneLayout.removeAllViews();
                    TextViewListValue.clear();
                    TextViewListType.clear();
                    ImageViewType.clear();
                    setAListPh();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            A_PhoneLayout.removeAllViews();
            TextViewListValue.clear();
            TextViewListType.clear();
            ImageViewType.clear();
            setAListPh();
        }
    }

    public void addNewPhone(final int pos) {
        try {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.row_phone, null);

            ImageView imgdeletePhone;
            TextView txtType;
            EditText txtPhoNum;

            imgdeletePhone = view.findViewById(R.id.imgdeletePhone);
            txtPhoNum = view.findViewById(R.id.txtPhoNum);
            txtType = view.findViewById(R.id.txtType);

            //Add the instance to the ArrayList -  to maintian separate tags of views
            mTextViewListValue.add(pos, txtPhoNum);
            mTextViewListType.add(pos, txtType);
            mImageViewType.add(pos, imgdeletePhone);

            if (pos == 0) {
                imgdeletePhone.setImageResource(R.drawable.add_n);
            } else {
                imgdeletePhone.setImageResource(R.drawable.delete_n);
            }

            mImageViewType.get(pos).setTag("" + pos);
            mTextViewListType.get(pos).setTag("" + pos);
            mTextViewListValue.get(pos).setTag("" + pos);

            mTextViewListType.get(pos).setText("" + phonelist.get(pos).getContactType());
            mTextViewListValue.get(pos).setText("" + phonelist.get(pos).getValue());

            mTextViewListValue.get(pos).addTextChangedListener(new CustomTextWatcher(mTextViewListValue.get(pos)));

            mTextViewListValue.get(pos).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        int poss = Integer.parseInt(mTextViewListValue.get(pos).getTag().toString());
                        final TextView Caption = (TextView) view;
                        phonelist.get(poss).setValue(Caption.getText().toString());
                    }
                }
            });

            mImageViewType.get(pos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backflap=true;
                    int poss = Integer.parseInt(mImageViewType.get(pos).getTag().toString());
                    if (poss == 0) {
                        ContactData c = new ContactData();
                        c.setId(phonelist.size());
                        phonelist.add(c);
                        addNewPhone(c.getId());
                    } else {
                        deletePhone(poss);
                    }
                }
            });

            mTextViewListType.get(pos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = Integer.parseInt(mTextViewListType.get(pos).getTag().toString());
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    b.setTitle("Type");
                    final String[] types = {"Fax", "Home", "Mobile", "Office"};
                    b.setItems(types, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            backflap=true;
                            if (types[which].equalsIgnoreCase("None")) {
                                phonelist.get(position).setValue(phonelist.get(position).getValue());
                                phonelist.get(position).setContactType("");
                                mTextViewListType.get(pos).setText(phonelist.get(position).getContactType());
                            } else {
                                phonelist.get(position).setValue(phonelist.get(position).getValue());
                                phonelist.get(position).setContactType(types[which]);
                                mTextViewListType.get(pos).setText(phonelist.get(position).getContactType());
                            }
                            dialog.dismiss();
                        }

                    });
                    b.show();
                }
            });

            PhoneLayout.addView(view, pos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addNewAPhone(final int pos) {
        try {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.row_phone, null);

            ImageView imgdeletePhone;
            TextView txtType;
            EditText txtPhoNum;

            imgdeletePhone = view.findViewById(R.id.imgdeletePhone);
            txtPhoNum = view.findViewById(R.id.txtPhoNum);
            txtType = view.findViewById(R.id.txtType);

            //Add the instance to the ArrayList -  to maintian separate tags of views
            TextViewListValue.add(pos, txtPhoNum);
            TextViewListType.add(pos, txtType);
            ImageViewType.add(pos, imgdeletePhone);

            if (pos == 0) {
                imgdeletePhone.setImageResource(R.drawable.add_n);
            } else {
                imgdeletePhone.setImageResource(R.drawable.delete_n);
            }

            ImageViewType.get(pos).setTag("" + pos);
            TextViewListType.get(pos).setTag("" + pos);
            TextViewListValue.get(pos).setTag("" + pos);

            TextViewListType.get(pos).setText("" + Aphonelist.get(pos).getContactType());
            TextViewListValue.get(pos).setText("" + Aphonelist.get(pos).getValue());

            TextViewListValue.get(pos).addTextChangedListener(new CustomTextWatcher(TextViewListValue.get(pos)));

            TextViewListValue.get(pos).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        int poss = Integer.parseInt(TextViewListValue.get(pos).getTag().toString());
                        final TextView Caption = (TextView) view;
                        Aphonelist.get(poss).setValue(Caption.getText().toString());
                    }
                }
            });

            ImageViewType.get(pos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int poss = Integer.parseInt(ImageViewType.get(pos).getTag().toString());
                    if (poss == 0) {
                        ContactData c = new ContactData();
                        c.setId(Aphonelist.size());
                        Aphonelist.add(c);
                        addNewAPhone(c.getId());
                    } else {
                        deleteAPhone(poss);
                    }
                }
            });

            TextViewListType.get(pos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = Integer.parseInt(TextViewListType.get(pos).getTag().toString());
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    b.setTitle("Type");
                    final String[] types = {"Fax", "Home", "Mobile", "Office"};
                    b.setItems(types, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (types[which].equalsIgnoreCase("None")) {
                                Aphonelist.get(position).setValue(Aphonelist.get(position).getValue());
                                Aphonelist.get(position).setContactType("");
                                TextViewListType.get(pos).setText(Aphonelist.get(position).getContactType());
                            } else {
                                Aphonelist.get(position).setValue(Aphonelist.get(position).getValue());
                                Aphonelist.get(position).setContactType(types[which]);
                                TextViewListType.get(pos).setText(Aphonelist.get(position).getContactType());
                            }
                            dialog.dismiss();
                        }

                    });
                    b.show();
                }
            });

            A_PhoneLayout.addView(view, pos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setListPh() {

        if (phonelist.isEmpty()) {
            ContactData c = new ContactData();
            c.setId(0);
            phonelist.add(c);
            addNewPhone(0);
        } else {
            for (int i = 0; i < phonelist.size(); i++) {
                if (phonelist.get(i) != null && phonelist.get(i).getValue() != null) {
                    phonelist.get(i).setId(i);
                    String input = phonelist.get(i).getValue();

                    if (!input.contains("-")) {
                        if (input.contains("(")) {
                            input = input.replace("(", "");
                        }

                        if (input.contains(")")) {
                            input = input.replace(")", "");
                        }

                        if (input.contains("+")) {
                            if (input.length() == 13) {
                                String str_getMOBILE = input.substring(3);

                                input = str_getMOBILE;
                            } else if (input.length() == 12) {
                                String str_getMOBILE = input.substring(2);
                                input = str_getMOBILE;
                            }
                        }
                        String number = input.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
                        phonelist.get(i).setValue(number);
                        System.out.println(number);
                    }

                    addNewPhone(i);
                }
            }
        }

    }

    //Nikita - PH Format code ends here

    public void setAListPh() {

        if (Aphonelist.isEmpty()) {
            ContactData c = new ContactData();
            c.setId(0);
            Aphonelist.add(c);
            addNewAPhone(0);
        } else {
            for (int i = 0; i < Aphonelist.size(); i++) {
                if (Aphonelist.get(i) != null && Aphonelist.get(i).getValue() != null) {
                    Aphonelist.get(i).setId(i);
                    String input = Aphonelist.get(i).getValue();

                    if (!input.contains("-")) {
                        if (input.contains("(")) {
                            input = input.replace("(", "");
                        }

                        if (input.contains(")")) {
                            input = input.replace(")", "");
                        }

                        if (input.contains("+")) {
                            if (input.length() == 13) {
                                String str_getMOBILE = input.substring(3);

                                input = str_getMOBILE;
                            } else if (input.length() == 12) {
                                String str_getMOBILE = input.substring(2);
                                input = str_getMOBILE;
                            }
                        }
                        String number = input.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
                        Aphonelist.get(i).setValue(number);
                        System.out.println(number);
                    }

                    addNewAPhone(i);
                }
            }
        }

    }

//    public void setListPh(NonScrollListView listv) {
//        // listPhone.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
//        if (phonelist.size() == 0) {
//            ContactData c = new ContactData();
//            phonelist.add(c);
//        }
//        pd = new SpecialPhoneAdapter(getActivity(), phonelist);
//      listv.setAdapter(pd);
//    }

    /*public void addNewPhone(Context context) {
        ContactData c = new ContactData();
        phonelist.add(c);
        pd.notifyDataSetChanged();
      *//*  pd= new SpecialPhoneAdapter(context, phonelist);
        listPrPhone.setAdapter(pd);*//*
    }*/

    public void savedata() {
        try {
            InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //TODO: handle exception
        }
        //  Preferences preferences=new Preferences(context);
        //String source=preferences.getString(PrefConstants.SOURCE);

        for (int i = 0; i < phonelist.size(); i++) {
            ContactData c = phonelist.get(i);
            for (int k = 0; k < mTextViewListValue.size(); k++) {
                if (Integer.parseInt(mTextViewListValue.get(k).getTag().toString()) == c.getId()) {
                    phonelist.get(i).setValue(mTextViewListValue.get(k).getText().toString());
                }
            }
        }

        switch (source) {
            case "Connection":
                if (validate("Connection")) {
                    for (int i = 0; i < phonelist.size(); i++) {
                        if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                            phonelist.remove(phonelist.get(i));
                        }
                        // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                    }
                    Log.d("TERE", "" + phonelist.size());
                           /* if (email.equals("")) {
                                Boolean flag = MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), name, email, address, mobile, phone, workphone, relation, imagepath, "", 1, 2, otherRelation, cardPath);
                                if (flag == true) {

                                    Toast.makeText(getActivity(), "You have added connection successfully", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                }
                                //    Toast.makeText(getActivity(), "Succesas", Toast.LENGTH_SHORT).show();
                            } else {*/
                    DBHelper dbHelper = new DBHelper(getActivity(), "MASTER");
                    MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
                    Boolean flags = MyConnectionsQuery.fetchEmailRecord(email);
                    if (flags == true) {
                        Toast.makeText(getActivity(), "This email address is already registered by another profile, Please add another email address", Toast.LENGTH_SHORT).show();
                        txtEmail.setError("This email address is already registered by another profile, Please add another email address");
                    } else {
                        Boolean flag = MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), name, email, address, mobile, phone, workphone, relation, imagepath, "", 1, 2, otherRelation, cardPath, has_card);
                        if (flag == true) {
                            RelativeConnection connection = MyConnectionsQuery.fetchConnectionRecord(preferences.getInt(PrefConstants.USER_ID), email);
                            String mail = connection.getEmail();
                            mail = mail.replace(".", "_");
                            mail = mail.replace("@", "_");
                            preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                            preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                            storeProfileImage(ProfileMap, "Profile");
                            storeProfileImage(CardMap, "Card");


                            File dir = new File(Environment.getExternalStorageDirectory() + "/MYLO/temp");
                            if (dir.isDirectory()) {
                                String[] children = dir.list();
                                for (int i = 0; i < children.length; i++) {
                                    new File(dir, children[i]).delete();
                                }
                            }
                            Boolean flagr = MyConnectionsQuery.updatePhoto(connection.getId(), imagepath, cardPath);

                            DBHelper dbHelper1 = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
                            MyConnectionsQuery m1 = new MyConnectionsQuery(getActivity(), dbHelper1);
                            Boolean flagg = MyConnectionsQuery.insertMyConnectionsData(connection.getId(), name, email, address, mobile, phone, workphone, relation, imagepath, "", 1, 2, otherRelation, cardPath, has_card);
                            if (flagg == true) {
                                Toast.makeText(getActivity(), "You have added profile successfully", Toast.LENGTH_SHORT).show();
                                RelativeConnection con = MyConnectionsQuery.fetchConnectionRecordforImport(email);
                                ContactDataQuery c = new ContactDataQuery(context, dbHelper1);
                                boolean flagf = ContactDataQuery.deleteRecord("Personal Profile", -1);
                                if (flagf == true) {
                                    //   Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < phonelist.size(); i++) {
                                        if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                            Boolean flagc = ContactDataQuery.insertContactsData(-1, connection.getId(), connection.getEmail(), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Personal Profile");
                                            if (flagc == true) {
                                                //       Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                                getActivity().finish();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        //   Toast.makeText(getActivity(), "Succesas", Toast.LENGTH_SHORT).show();
                    }
                    //  }

                }
                break;

            case "Emergency":
                if (validate("Emergency")) {
                    for (int i = 0; i < phonelist.size(); i++) {
                        if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                            phonelist.remove(phonelist.get(i));
                        }
                        // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                    }

                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();
*/

                    Boolean flag = MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, email, address, mobile, phone, workphone, relation, imagepath, note, 2, prior, otherRelation, cardPath, has_card);
                    RelativeConnection con = MyConnectionsQuery.fetchConnectionRecordforImport(email);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added emergency contact successfully", Toast.LENGTH_SHORT).show();

                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Emergency", con.getId());
                        if (flagf == true) {
                            // Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(con.getId(), preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Emergency");
                                    if (flagc == true) {
                                        //      Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    // Toast.makeText(getActivity(), "Succesas", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            case "EmergencyUpdate":

                if (validate("Emergency")) {
                    for (int i = 0; i < phonelist.size(); i++) {
                        if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                            phonelist.remove(phonelist.get(i));
                        }
                        // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                    }
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/

                    Boolean flag = MyConnectionsQuery.updateMyConnectionsData(id, name, email, address, mobile, phone, workphone, relation, imagepath, note, 2, prior, otherRelation, "", "", "", "", "", "", "", "", "", "", "", "", cardPath, "", "", "", "", "", "", "", "", "", "", "", "", "", has_card, "");
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have updated emergency contact successfully", Toast.LENGTH_SHORT).show();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Emergency", id);
                        if (flagf == true) {
                            //  Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Emergency");
                                    if (flagc == true) {
                                        //    Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    // Toast.makeText(getActivity(), "Succesas", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            case "Proxy":
                if (validate("Proxy")) {

                          /*  Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, email, address, mobile, phone, workphone, relation, imagepath, note, 3, prox, otherRelation, cardPath, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added proxy contact successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;

            case "ProxyUpdate":
                if (validate("Proxy")) {
/*
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = MyConnectionsQuery.updateMyConnectionsData(id, name, email, address, mobile, phone, workphone, relation, imagepath, note, 3, prox, otherRelation, "", "", "", "", "", "", "", "", "", "", "", "", cardPath, "", "", "", "", "", "", "", "", "", "", "", "", "", has_card, "");
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have updated proxy contact successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;

            case "Physician":
                if (validate("Physician")) {
                    for (int i = 0; i < phonelist.size(); i++) {
                        if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                            phonelist.remove(phonelist.get(i));
                        }
                        // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                    }
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = SpecialistQuery.insertPhysicianData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, network, affil, note, 1, lastseen, cardPath, otherDoctor, locator, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added physician contact successfully", Toast.LENGTH_SHORT).show();
                        Specialist con = new Specialist();
                        ArrayList<Specialist> connectionList = SpecialistQuery.getPhysician(preferences.getInt(PrefConstants.CONNECTED_USERID), name, speciality, 1);
                        for (int i = 0; i < connectionList.size(); i++) {
                            if (connectionList.get(i).getName().equalsIgnoreCase(name) && connectionList.get(i).getIsPhysician() == 1)
                                con = connectionList.get(i);
                        }
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Primary", con.getId());
                        if (flagf == true) {
                            //Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(con.getId(), preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Primary");
                                    if (flagc == true) {
                                        // Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;

            case "Speciality":
                if (validate("Physician")) {
                    for (int i = 0; i < phonelist.size(); i++) {
                        if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                            phonelist.remove(phonelist.get(i));
                        }
                        // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                    }
                          /*  Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = SpecialistQuery.insertPhysicianData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, network, affil, note, 2, lastseen, cardPath, otherDoctor, locator, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added doctor contact successfully", Toast.LENGTH_SHORT).show();
                        Specialist con = new Specialist();
                        ArrayList<Specialist> connectionList = SpecialistQuery.getDoctor(preferences.getInt(PrefConstants.CONNECTED_USERID), name, speciality, 2);
                        for (int i = 0; i < connectionList.size(); i++) {
                            if (connectionList.get(i).getName().equalsIgnoreCase(name) && connectionList.get(i).getIsPhysician() == 2)
                                con = connectionList.get(i);
                        }
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Doctor", con.getId());
                        if (flagf == true) {
                            //Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(con.getId(), preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Doctor");
                                    if (flagc == true) {
                                        //   Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            case "SpecialistData":
                if (validate("Physician")) {


                          /*  Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    if (isPhysician == 1) {
                        Boolean flag = SpecialistQuery.updatePhysicianData(id, name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, network, affil, note, 1, lastseen, cardPath, otherDoctor, locator, has_card);
                        if (flag == true) {
                            Toast.makeText(getActivity(), "You have updated physician contact successfully", Toast.LENGTH_SHORT).show();
                            ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                            boolean flagf = ContactDataQuery.deleteRecord("Primary", id);
                            if (flagf == true) {
                                //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < phonelist.size(); i++) {
                                    if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                        Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Primary");
                                        if (flagc == true) {
                                            //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    } else if (isPhysician == 2) {
                        Boolean flag = SpecialistQuery.updatePhysicianData(id, name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, network, affil, note, 2, lastseen, cardPath, otherDoctor, locator, has_card);
                        if (flag == true) {
                            Toast.makeText(getActivity(), "You have updated doctor successfully", Toast.LENGTH_SHORT).show();
                            ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                            boolean flagf = ContactDataQuery.deleteRecord("Doctor", id);
                            if (flagf == true) {
                                //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < phonelist.size(); i++) {
                                    if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                        Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Doctor");
                                        if (flagc == true) {
                                            //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            case "PhysicianData":
                if (validate("Physician")) {
                    for (int i = 0; i < phonelist.size(); i++) {
                        if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                            phonelist.remove(phonelist.get(i));
                        }
                        // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                    }

                          /*  Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    if (isPhysician == 1) {
                        Boolean flag = SpecialistQuery.updatePhysicianData(id, name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, network, affil, note, 1, lastseen, cardPath, otherDoctor, locator, has_card);
                        if (flag == true) {
                            Toast.makeText(getActivity(), "You have updated physician contact successfully", Toast.LENGTH_SHORT).show();
                            ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                            boolean flagf = ContactDataQuery.deleteRecord("Primary", id);
                            if (flagf == true) {
                                //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < phonelist.size(); i++) {
                                    if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                        Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Primary");
                                        if (flagc == true) {
                                            //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    } else if (isPhysician == 2) {
                        Boolean flag = SpecialistQuery.updatePhysicianData(id, name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, network, affil, note, 2, lastseen, cardPath, otherDoctor, locator, has_card);
                        if (flag == true) {
                            ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                            boolean flagf = ContactDataQuery.deleteRecord("Doctor", id);
                            if (flagf == true) {
                                //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < phonelist.size(); i++) {
                                    if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                        Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Doctor");
                                        if (flagc == true) {
                                            //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            case "Pharmacy":

                if (validate("Pharmacy")) {
                          /*  Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = PharmacyQuery.insertPharmacyData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, website, address, phone, imagepath, fax, note, cardPath, locator, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added pharmacy successfully", Toast.LENGTH_SHORT).show();
                        Pharmacy con = new Pharmacy();
                        con = getLastPharmacy();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Pharmacy", con.getId());
                        if (flagf == true) {
                            //   Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(con.getId(), preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Pharmacy");
                                    if (flagc == true) {
                                        //   Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            case "PharmacyData":
                for (int i = 0; i < phonelist.size(); i++) {
                    if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                        phonelist.remove(phonelist.get(i));
                    }
                    // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                }
                if (validate("Pharmacy")) {
                            /*Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = PharmacyQuery.updatePharmacyData(id, name, website, address, phone, imagepath, fax, note, cardPath, locator, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have updated pharmacy successfully", Toast.LENGTH_SHORT).show();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Pharmacy", id);
                        if (flagf == true) {
                            //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Pharmacy");
                                    if (flagc == true) {
                                        //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;

            case "Aides":

                if (validate("Aides")) {
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = AideQuery.insertAidesData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, website, email, mobile, phone, workphone, imagepath, fax, note, address, cardPath);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added Health Service successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            case "AidesData":

                if (validate("Aides")) {
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = AideQuery.updateAideData(id, name, website, email, mobile, phone, workphone, imagepath, fax, note, address, cardPath);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have updated Health Service successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;

            case "Hospital":
                if (validate("Hospital")) {
                          /*  Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = HospitalHealthQuery.insertHospitalHealthData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, note, lastseen, otherCategory, cardPath, location, locator, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added contact successfully", Toast.LENGTH_SHORT).show();
                        Hospital con = new Hospital();
                        con = HospitalHealthQuery.getLastHopital();

                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Hospital", con.getId());
                        if (flagf == true) {
                            // Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(con.getId(), preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Hospital");
                                    if (flagc == true) {
                                        //   Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
                break;

            case "HospitalData":
                for (int i = 0; i < phonelist.size(); i++) {
                    if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                        phonelist.remove(phonelist.get(i));
                    }
                    // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                }
                if (validate("Hospital")) {
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = HospitalHealthQuery.updateHospitalHealthData(id, name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, note, lastseen, otherCategory, cardPath, location, locator, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have updated contact successfully", Toast.LENGTH_SHORT).show();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Hospital", id);
                        if (flagf == true) {
                            //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Hospital");
                                    if (flagc == true) {
                                        //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
                break;
            case "Finance":

                if (validate("Finance")) {
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = FinanceQuery.insertFinanceData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, note, lastseen, otherCategory, cardPath, email, location, contactName, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added contact successfully", Toast.LENGTH_SHORT).show();
                        Finance con = new Finance();
                        con = FinanceQuery.getLastFinance();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Finance", con.getId());
                        if (flagf == true) {
                            //  Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(con.getId(), preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Finance");
                                    if (flagc == true) {
                                        //        Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
                break;
            case "FinanceData":
                for (int i = 0; i < phonelist.size(); i++) {
                    if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                        phonelist.remove(phonelist.get(i));
                    }
                    // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                }
                if (validate("Finance")) {
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = FinanceQuery.updateFinanceData(id, name, website, address, mobile, phone, workphone, speciality, imagepath, fax, practice_name, note, lastseen, otherCategory, cardPath, email, location, contactName, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have updated contact successfully", Toast.LENGTH_SHORT).show();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Finance", id);
                        if (flagf == true) {
                            //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Finance");
                                    if (flagc == true) {
                                        //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
                break;
            case "Insurance":
                for (int i = 0; i < Aphonelist.size(); i++) {
                    ContactData c = Aphonelist.get(i);
                    for (int k = 0; k < TextViewListValue.size(); k++) {
                        if (Integer.parseInt(TextViewListValue.get(k).getTag().toString()) == c.getId()) {
                            Aphonelist.get(i).setValue(TextViewListValue.get(k).getText().toString());
                        }
                    }
                }
                if (validate("Insurance")) {
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = InsuranceQuery.insertInsuranceData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, website, type, phone, imagepath, fax, note, member, group, subscriber, email, otherInsurance, agent, cardPath, aentEmail, aentPhone, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have added insurance information successfully", Toast.LENGTH_SHORT).show();
                        Insurance con = new Insurance();
                        con = getLastInsurance();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Insurance", con.getId());
                        //  boolean flagd = ContactDataQuery.deleteRecord("Agent", con.getId());
                        if (flagf == true) {
                            //  Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(con.getId(), preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Insurance");
                                    if (flagc == true) {
                                        //    Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                       /* if (flagd == true) {
                            //  Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < Aphonelist.size(); i++) {
                                if (!Aphonelist.get(i).getContactType().equalsIgnoreCase("") && !Aphonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(con.getId(), preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), Aphonelist.get(i).getValue(), Aphonelist.get(i).getContactType(), "Agent");
                                    if (flagc == true) {
                                        //    Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }*/
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            case "InsuranceData":
                /*for (int i = 0; i < Aphonelist.size(); i++) {
                    ContactData c = Aphonelist.get(i);
                    for (int k = 0; k < TextViewListValue.size(); k++) {
                        if (Integer.parseInt(TextViewListValue.get(k).getTag().toString()) == c.getId()) {
                            Aphonelist.get(i).setValue(TextViewListValue.get(k).getText().toString());
                        }
                    }
                }*/
                for (int i = 0; i < phonelist.size(); i++) {
                    if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                        phonelist.remove(phonelist.get(i));
                    }
                    // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                }

              /*  for (int i = 0; i < Aphonelist.size(); i++) {
                    if (Aphonelist.get(i).getContactType() == "" && Aphonelist.get(i).getValue() == "") {
                        Aphonelist.remove(Aphonelist.get(i));
                    }
                    // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
                }*/
                if (validate("Insurance")) {
                           /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] photo = baos.toByteArray();*/
                    Boolean flag = InsuranceQuery.updateInsuranceData(id, name, website, type, phone, imagepath, fax, note, member, group, subscriber, email, otherInsurance, agent, cardPath, aentEmail, aentPhone, has_card);
                    if (flag == true) {
                        Toast.makeText(getActivity(), "You have updated insurance information successfully", Toast.LENGTH_SHORT).show();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Insurance", id);
                        //  boolean flagd = ContactDataQuery.deleteRecord("Agent", id);
                        if (flagf == true) {
                            //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Insurance");
                                    if (flagc == true) {
                                        //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        /*if (flagd == true) {
                            //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < Aphonelist.size(); i++) {
                                if (!Aphonelist.get(i).getContactType().equalsIgnoreCase("") && !Aphonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(id, preferences.getInt(PrefConstants.CONNECTED_USERID), preferences.getString(PrefConstants.CONNECTED_USEREMAIL), Aphonelist.get(i).getValue(), Aphonelist.get(i).getContactType(), "Agent");
                                    if (flagc == true) {
                                        //         Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }*/
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    //  dialogManager = new DialogManager(new FragmentNewContact());
                    //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                }
                break;
            // InsuranceObject
        }
    }

    private void initImageLoader() {

        source = preferences.getString(PrefConstants.SOURCE);

        int data = R.drawable.ic_profile_defaults;

        data = getIcon(source);

        //Profile
        displayImageOptionsProfile = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(data)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptionsProfile)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoaderProfile = ImageLoader.getInstance();

        //Card
        displayImageOptionsCard = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.busi_card)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration configs = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptionsCard)
                .build();
        ImageLoader.getInstance().init(configs);
        imageLoaderCard = ImageLoader.getInstance();
    }

    private void initComponent() {
        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        DoctorQuery d = new DoctorQuery(getActivity(), dbHelper);
        SpecialistQuery s = new SpecialistQuery(getActivity(), dbHelper);
        HospitalHealthQuery h = new HospitalHealthQuery(getActivity(), dbHelper);
    }

    private void getRelationData() {
        AppConstants.RELATION = "";
        relationArraylist = new ArrayList<>();
        relationArraylist.add("Mother");
        relationArraylist.add("Father");
        relationArraylist.add("Wife");
        relationArraylist.add("Husband");
        relationArraylist.add("Daughter");
        relationArraylist.add("Son");
        relationArraylist.add("Sister");
        relationArraylist.add("Brother");
        relationArraylist.add("Friend");
        relationArraylist.add("GrandFather");
        relationArraylist.add("GrandMother");
        relationArraylist.add("GrandSon");
        relationArraylist.add("GrandDaughter");
        relationArraylist.add("Other");
    }

    private void initVariables() {
        source = preferences.getString(PrefConstants.SOURCE);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            fromDevice = bundle.getBoolean("FromDevice");
            Cname = bundle.getString("Name");
            Cemail = bundle.getString("Email");
            Cphone = bundle.getString("Phone");
            Cphone = Cphone.replaceAll("[^a-zA-Z0-9]", "");
            CAddress = bundle.getString("Address");
            CHPhone = bundle.getString("HPhone");
            CHPhone = CHPhone.replaceAll("[^a-zA-Z0-9]", "");
            CWPhone = bundle.getString("WPhone");
            CWPhone = CWPhone.replaceAll("[^a-zA-Z0-9]", "");

            byte[] image = bundle.getByteArray("Photo");
          /*  Bitmap photo = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgProfile.setImageBitmap(photo);*/
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(image);
            Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
            if (source.equals("Connection")) {
                storeTempImage(bitmap, "Profile");
                ProfileMap = bitmap;
                if (!imagepath.equals("")) {
                    File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/temp/", imagepath);
                    if (imgFile.exists()) {
                        //Shradha
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                        imgEdit.setVisibility(View.VISIBLE);
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    }
                } else {
                    changeIcon(source);
//                    imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                }
            } else {
                storeImage(bitmap, "Profile");
                if (!imagepath.equals("")) {
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), imagepath);
                    if (imgFile.exists()) {
                        //Shradha
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                        imgEdit.setVisibility(View.VISIBLE);
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    }
                } else {
                    changeIcon(source);
//                    imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                }
            }


            source = preferences.getString(PrefConstants.SOURCE);
            switch (source) {
                case "Connection":
                    getContact();
                    break;
                case "Proxy":
                    getContact();
                    break;
                case "Emergency":
                    getContact();
                    break;
                case "Physician":
                    getSContact();
                    break;
                case "Speciality":
                    getSContact();
                    break;
                case "Hospital":
                    getSContact();
                    txtFNameHospital.setText(Cname);
                    txtHospitalAddress.setText(CAddress);
                    // txtEmail.setText(email);
                    try {
                        String mobile = "";
                        mobile = CWPhone;
                        if (!mobile.equals("")) {
                            mobile = getMobile(mobile);
                            txtHospitalOfficePhone.setText(mobile);
                        } else {
                            String OtherM = "";
                            OtherM = Cphone;
                            OtherM = getMobile(OtherM);
                            txtHospitalOfficePhone.setText(OtherM);
                        }

                        String OtherP = "";
                        OtherP = CHPhone;
                        OtherP = getMobile(OtherP);
                        txtHospitalOtherPhone.setText(OtherP);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Pharmacy":

                    txtPharmacyName.setText(Cname);
                    txtPharmacyAddress.setText(CAddress);

                    getCommonContact();
                  /*   txtPharmacyName.setText(Cname);
                    txtPharmacyAddress.setText(CAddress);
                    // txtEmail.setText(email);
                    try {
                        String mobile = "";
                        mobile = CWPhone;
                        if (!mobile.equals("")) {
                            mobile = getMobile(mobile);
                            txtPharmacyPhone.setText(mobile);
                        } else {
                            String OtherM = "";
                            OtherM = Cphone;
                            OtherM = getMobile(OtherM);
                            txtPharmacyPhone.setText(OtherM);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    break;
                case "Aides":
                    txtAideCompName.setText(Cname);
                    txtAideEmail.setText(Cemail);
                    try {

                        txtAideOfficePhone.setText(mobile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Finance":

                    txtContactName.setText(Cname);
                    txtFinanceEmail.setText(Cemail);
                    txtFinanceAddress.setText(CAddress);
                    getCommonContact();
                   /* try {
                        String mobile = "";
                        mobile = Cphone;
                        if (!mobile.isEmpty()) {//nikita
                            mobile = getMobile(mobile);
                            txtFinanceMobilePhone.setText(mobile);
                        }

                        String hphone = "";
                        hphone = CHPhone;
                        if (!hphone.isEmpty()) {//nikita
                            hphone = getMobile(hphone);
                            txtFinanceOtherPhone.setText(hphone);
                        }

                        String wphone = "";
                        wphone = CWPhone;
                        if (!wphone.isEmpty()) {//nikita
                            wphone = getMobile(wphone);
                            txtFinanceOfficePhone.setText(wphone);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    break;
                case "Insurance":
                    //getSContact();
                    txtInsuaranceName.setText(Cname);
                    txtInsuaranceEmail.setText(Cemail);
                    getCommonContact();
                  /*  try {
                        String mobile = "";
                        mobile = CWPhone;
                        if (!mobile.equals("")) {
                            mobile = getMobile(mobile);
                            txtInsuarancePhone.setText(mobile);
                        } else {
                            String OtherM = "";
                            OtherM = Cphone;
                            OtherM = getMobile(OtherM);
                            txtInsuarancePhone.setText(OtherM);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    break;
            }

        }
      /*  txtName.setText(name);
        txtEmail.setText(email);
        try {
            String mobile = "";
            mobile = phone;
            String code = mobile.substring(0, 3);
            mobile = mobile.substring(3, 6) + "-"+ mobile.substring(6, 9) + "-"+ mobile.substring(9, mobile.length());
            txtMobile.setText(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        source = preferences.getString(PrefConstants.SOURCE);
        switch (source) {
            case "Connection":
                changeIcon(source);
                rlTop.setVisibility(View.GONE);
                rlCommon.setVisibility(View.VISIBLE);
                spinnerRelation.setVisibility(View.GONE);
                rlRelation.setVisibility(View.VISIBLE);
                rlConnection.setVisibility(View.VISIBLE);
                rlDoctor.setVisibility(View.GONE);
                rlInsurance.setVisibility(View.GONE);
                rlAids.setVisibility(View.GONE);
                flPr.setVisibility(View.GONE);
                tilPriority.setVisibility(View.GONE);
                imgprio.setVisibility(View.GONE);
                rlProxy.setVisibility(View.GONE);
                rlFinance.setVisibility(View.GONE);
                txtAdd.setText("Create New Profile");
                tilName.setHint("Name");
                tilName.setHintEnabled(false);
                txtName.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        tilName.setHintEnabled(true);
                        txtName.setFocusable(true);

                        return false;
                    }
                });
                txtTitle.setText("Create New Profile");
                //   txtTitle.setAllCaps(true);

                tilEmergencyNote.setVisibility(View.GONE);
                rlPharmacy.setVisibility(View.GONE);
//               setListPh(listPrPhone);
                PhoneLayout = llAddPhone;
                setListPh();
                break;

            case "Pharmacy":
                changeIcon(source);
                visiPharmacy();
                txtAdd.setText("Add Pharmacies &\nHome Medical Equipment");
                txtTitle.setText("Add Pharmacies &\nHome Medical Equipment");
//                setListPh(listPharmPhone);
                PhoneLayout = llAddPharmPhone;
                setListPh();
                break;

            case "PharmacyData":
                changeIcon(source);
                visiPharmacy();
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Update Pharmacies &\nHome Medical Equipment");
                txtTitle.setText("Update Pharmacies &\nHome Medical Equipment");
                Intent specialistIntents = getActivity().getIntent();
                if (specialistIntents.getExtras() != null) {
                    Pharmacy specialist = (Pharmacy) specialistIntents.getExtras().getSerializable("PharmacyObject");
                    pharmacy = (Pharmacy) specialistIntents.getExtras().getSerializable("PharmacyObject");
                    txtPharmacyName.setText(specialist.getName());
                    txtPharmacyAddress.setText(specialist.getAddress());
                    /*if (Cname.isEmpty()) {//nikita
                        txtPharmacyName.setText(specialist.getName());
                    } else {
                        txtPharmacyName.setText(Cname);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtPharmacyPhone.setText(specialist.getPhone());
                    } else {
                        txtPharmacyPhone.setText(Cphone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtPharmacyAddress.setText(specialist.getAddress());
                    } else {
                        txtPharmacyAddress.setText(CAddress);
                    }
*/
                    txtPharmacyWebsite.setText(specialist.getWebsite());
                    txtPharmacyLocator.setText(specialist.getLocator());
                    txtPharmacyFax.setText(specialist.getFax());
                    txtPharmacyNote.setText(specialist.getNote());

                    id = specialist.getId();
                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), id, "Pharmacy");
//                    setListPh(listPharmPhone);
                    PhoneLayout = llAddPharmPhone;
                    setListPh();
                    String photo;
                    if (imagepath.isEmpty()) {
                        photo = specialist.getPhoto();//nikita
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);

                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    //  imgProfile.setImageResource(R.drawable.ic_profile_defaults);

                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            //   imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                    if (specialist.getHas_card() != null) {
                        if (specialist.getHas_card().equals("YES")) {
                            tbCard.setChecked(true);
                            has_card = "YES";
                            rlCard.setVisibility(View.VISIBLE);
                        } else {
                            tbCard.setChecked(false);
                            has_card = "NO";
                            rlCard.setVisibility(View.GONE);
                            cardPath = "";
                            CardMap = null;
                        }
                    } else {
                        tbCard.setChecked(false);
                        has_card = "NO";
                        rlCard.setVisibility(View.GONE);
                        cardPath = "";
                        CardMap = null;
                    }
                }

                break;

            case "PharmacyDataView":
                changeIcon(source);
                visiPharmacy();
                disablePharmacy();
                txtTitle.setText("Pharmacy");
                txtTitle.setVisibility(View.VISIBLE);
                Intent specialistIntents2 = getActivity().getIntent();
                if (specialistIntents2.getExtras() != null) {
                    Pharmacy specialist = (Pharmacy) specialistIntents2.getExtras().getSerializable("PharmacyObject");
                    txtPharmacyName.setText(specialist.getName());
                    txtPharmacyAddress.setText(specialist.getAddress());
                    txtPharmacyWebsite.setText(specialist.getWebsite());
                    txtPharmacyLocator.setText(specialist.getLocator());
                    txtPharmacyFax.setText(specialist.getFax());
                    txtPharmacyPhone.setText(specialist.getPhone());
                    txtPharmacyNote.setText(specialist.getNote());
                    id = specialist.getId();

                    String photo = specialist.getPhoto();
                    imagepath = specialist.getPhoto();
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }

                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                         /*   Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

//                            imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.GONE);
                        tbCard.setChecked(false);
                    }
                }
                break;

            case "Proxy":
                visiProxy();
                txtAdd.setText("Add Proxy AGENT & SUCCESSOR(S)");
                txtTitle.setText("Add Proxy AGENT & SUCCESSOR(S)");
                break;

            case "ProxyUpdate":
                visiProxy();
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Update Proxy AGENT & SUCCESSOR(S)");
                txtTitle.setText("Update Proxy AGENT & SUCCESSOR(S)");
                Intent ProxyIntent = getActivity().getIntent();
                if (ProxyIntent.getExtras() != null) {

                    Proxy rel = (Proxy) ProxyIntent.getExtras().getSerializable("ProxyObject");
                    if (Cname.isEmpty()) {//nikita
                        txtName.setText(rel.getName());
                    } else {
                        txtName.setText(Cname);
                    }
                    if (Cemail.isEmpty()) {//nikita
                        txtEmail.setText(rel.getEmail());
                    } else {
                        txtEmail.setText(Cemail);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtMobile.setText(rel.getMobile());
                    } else {
                        txtMobile.setText(Cphone);
                    }
                    if (CHPhone.isEmpty()) {//nikita
                        txtHomePhone.setText(rel.getPhone());
                    } else {
                        txtHomePhone.setText(CHPhone);
                    }
                    if (CWPhone.isEmpty()) {//nikita
                        txtWorkPhone.setText(rel.getWorkPhone());
                    } else {
                        txtWorkPhone.setText(CWPhone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtAddress.setText(rel.getAddress());
                    } else {
                        txtAddress.setText(CAddress);
                    }

                    txtEmergencyNote.setText(rel.getNote());
                    txtOtherRelation.setText(rel.getOtherRelation());

                    id = rel.getId();
                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), id, "Emergency");
//                    setListPh(listPharmPhone);
                    PhoneLayout = llAddPharmPhone;
                    setListPh();
                    if (!rel.getRelationType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < Relationship.length; i++) {
                            if (rel.getRelationType().equals(Relationship[i])) {
                                index = i;
                            }
                        }
                        spinnerRelation.setSelection(index + 1);
                    }
                   /* prox=rel.getIsPrimary();
                    if (prox==1) {
                        spinnerProxy.setSelection(0);
                    }else if (prox==2)
                    {
                        spinnerProxy.setSelection(1);
                    }*/
                    spinnerProxy.setSelection(rel.getIsPrimary() + 1);


                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = rel.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    if (imgFile.exists()) {
                       /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imgProfile.setImageBitmap(myBitmap);*/
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                        imgEdit.setVisibility(View.VISIBLE);
                        //imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    }
                   /* Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);*/

                    //Change Class Name
                    cardPath = rel.getPhotoCard();
                    if (!rel.getPhotoCard().equals("")) {
                        String photoCard = rel.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            //   imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                        /*Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                }
                break;

            case "ProxyUpdateView":
                visiProxy();
                disableProxy();
                txtTitle.setText("Proxy");
                txtTitle.setVisibility(View.VISIBLE);
                Intent ProxyIntents = getActivity().getIntent();
                if (ProxyIntents.getExtras() != null) {

                    Proxy rel = (Proxy) ProxyIntents.getExtras().getSerializable("ProxyObject");

                    if (Cname.isEmpty()) {//nikita
                        txtName.setText(rel.getName());
                    } else {
                        txtName.setText(Cname);
                    }
                    if (Cemail.isEmpty()) {//nikita
                        txtEmail.setText(rel.getEmail());
                    } else {
                        txtEmail.setText(Cemail);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtMobile.setText(rel.getMobile());
                    } else {
                        txtMobile.setText(Cphone);
                    }
                    if (CHPhone.isEmpty()) {//nikita
                        txtHomePhone.setText(rel.getPhone());
                    } else {
                        txtHomePhone.setText(CHPhone);
                    }
                    if (CWPhone.isEmpty()) {//nikita
                        txtWorkPhone.setText(rel.getWorkPhone());
                    } else {
                        txtWorkPhone.setText(CWPhone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtAddress.setText(rel.getAddress());
                    } else {
                        txtAddress.setText(CAddress);
                    }

                    txtEmergencyNote.setText(rel.getNote());
                    txtOtherRelation.setText(rel.getOtherRelation());
                    id = rel.getId();
                    if (!rel.getRelationType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < Relationship.length; i++) {
                            if (rel.getRelationType().equals(Relationship[i])) {
                                index = i;
                            }
                        }
                        spinnerRelation.setSelection(index + 1);
                    }
                   /* prox=rel.getIsPrimary();
                    if (prox==1) {
                        spinnerProxy.setSelection(0);
                    }else if (prox==2)
                    {
                        spinnerProxy.setSelection(1);
                    }*/

                    spinnerProxy.setSelection(rel.getIsPrimary() + 1);
                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = rel.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    if (imgFile.exists()) {
                       /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imgProfile.setImageBitmap(myBitmap);*/
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                        imgEdit.setVisibility(View.VISIBLE);
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    }
                    /*Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);*/

                    //Change Class Name
                    cardPath = rel.getPhotoCard();
                    if (!rel.getPhotoCard().equals("")) {
                        String photoCard = rel.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                          /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));
                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        tbCard.setChecked(true);
                    } else {
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                }

                break;

            case "Emergency":
                changeIcon(source);
                visiEmergency();
                // spinnerPriority.setVisibility(View.VISIBLE);
                flPr.setVisibility(View.VISIBLE);
                tilPriority.setVisibility(View.VISIBLE);
                imgprio.setVisibility(View.VISIBLE);
                txtAdd.setText("Add Emergency Contact & Proxy Agent");
                txtTitle.setText("Add Emergency Contact");
                tilName.setHint("Name");
                tilName.setHintEnabled(false);
                txtName.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        tilName.setHintEnabled(true);
                        txtName.setFocusable(true);

                        return false;
                    }
                });
                Intent EmergencyIntent = getActivity().getIntent();
                if (EmergencyIntent.getExtras() != null) {
                    Emergency rel = (Emergency) EmergencyIntent.getExtras().getSerializable("EmergencyObject");
                    /*updated code*/
                    String photo = "";
                    String ph = "";
                    if (imagepath.isEmpty()) {//nikita
                        if (rel != null) {
                            photo = rel.getPhoto();
                        }
                    } else {
                        photo = imagepath;
                        ph = imagepath;

                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                    }

                    if (rel != null) {
                        cardPath = rel.getPhotoCard();
                    }
                    if (rel != null) {
                        if (!rel.getPhotoCard().equals("")) {
                            String photoCard = rel.getPhotoCard();
                            File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                            if (imgFile1.exists()) {
                              /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                                imgCard.setImageBitmap(myBitmap);*/
                                imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));
                                //  imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                            }
                           /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                            imgCard.setImageBitmap(bmpCard);*/

                            imgCard.setVisibility(View.VISIBLE);
                            rlCard.setVisibility(View.VISIBLE);
                            flFront.setVisibility(View.GONE);
                            imgEditCard.setVisibility(View.VISIBLE);
                            tbCard.setChecked(true);

                        } else {
                            imgEditCard.setVisibility(View.GONE);
                            imgCard.setVisibility(View.GONE);
                            rlCard.setVisibility(View.GONE);
                            flFront.setVisibility(View.VISIBLE);
                            tbCard.setChecked(false);

                        }
                    }
                }

//                setListPh(listPrPhone);
                PhoneLayout = llAddPhone;
                setListPh();
                break;

            case "EmergencyUpdate":
                changeIcon(source);
                visiEmergency();
                tilName.setHint("Name");
                tilName.setHintEnabled(true);
                // spinnerPriority.setVisibility(View.VISIBLE);
                //spinnerPriority.setFloatingLabelText("Priority");
                flPr.setVisibility(View.VISIBLE);
                tilPriority.setVisibility(View.VISIBLE);
                imgprio.setVisibility(View.VISIBLE);
                txtPriority.setVisibility(View.VISIBLE);
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Update Emergency Contact & Proxy Agent");
                txtTitle.setText("Update Emergency Contact & Proxy Agent");
                Intent EmergencyIntents = getActivity().getIntent();
                if (EmergencyIntents.getExtras() != null) {
                    rel = (Emergency) EmergencyIntents.getExtras().getSerializable("EmergencyObject");
                    txtName.setText(rel.getName());
                    txtEmail.setText(rel.getEmail());
                    txtMobile.setText(rel.getMobile());
                    txtHomePhone.setText(rel.getPhone());
                    txtWorkPhone.setText(rel.getWorkPhone());
                    txtAddress.setText(rel.getAddress());

                  /*  if (Cname.isEmpty()) {//nikita
                        txtName.setText(rel.getName());
                    } else {
                        txtName.setText(Cname);
                    }
                    if (Cemail.isEmpty()) {//nikita
                        txtEmail.setText(rel.getEmail());
                    } else {
                        txtEmail.setText(Cemail);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtMobile.setText(rel.getMobile());
                    } else {
                        txtMobile.setText(Cphone);
                    }
                    if (CHPhone.isEmpty()) {//nikita
                        txtHomePhone.setText(rel.getPhone());
                    } else {
                        txtHomePhone.setText(CHPhone);
                    }
                    if (CWPhone.isEmpty()) {//nikita
                        txtWorkPhone.setText(rel.getWorkPhone());
                    } else {
                        txtWorkPhone.setText(CWPhone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtAddress.setText(rel.getAddress());
                    } else {
                        txtAddress.setText(CAddress);
                    }*/
                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), rel.getId(), "Emergency");
//                    setListPh(listPrPhone);
                    PhoneLayout = llAddPhone;
                    setListPh();
                    txtEmergencyNote.setText(rel.getNote());
                    id = rel.getId();
                    if (!rel.getRelationType().equals("")) {
                        txtRelation.setText(rel.getRelationType());
                        if (rel.getRelationType().equals("Other")) {
                            tilOtherRelation.setVisibility(View.VISIBLE);
                            txtOtherRelation.setText(rel.getOtherRelation());
                        } else {
                            tilOtherRelation.setVisibility(View.GONE);
                            txtOtherRelation.setText("");
                        }
                        //  spinnerRelation.setSelection(index + 1);
                    }

                    if (rel.getIsPrimary() == 0) {
                        priority = "Primary Emergency Contact";
                        txtPriority.setText(priority);
                    } else if (rel.getIsPrimary() == 1) {

                        priority = "Primary Health Care Proxy Agent";
                        txtPriority.setText(priority);
                    } else if (rel.getIsPrimary() == 2) {

                        priority = "Secondary Emergency Contact";
                        txtPriority.setText(priority);
                    } else if (rel.getIsPrimary() == 3) {

                        priority = "Secondary Health Care Proxy Agent";
                        txtPriority.setText(priority);
                    } else if (rel.getIsPrimary() == 4) {

                        priority = "Primary Emergency Contact and Health Care Proxy Agent";
                        txtPriority.setText(priority);
                    } else {
                        priority = "";
                        txtPriority.setText(priority);
                    }

                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = rel.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita


                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);

                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);

                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }

                    } else {
                        imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                        imgEdit.setVisibility(View.GONE);
                    }


                    //Change Class Name
                    cardPath = rel.getPhotoCard();
                    if (!rel.getPhotoCard().equals("")) {
                        String photoCard = rel.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                          /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            //  imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);

                    }
                    if (rel.getHas_card() != null) {
                        if (rel.getHas_card().equals("YES")) {
                            tbCard.setChecked(true);
                            has_card = "YES";
                            rlCard.setVisibility(View.VISIBLE);
                        } else {
                            tbCard.setChecked(false);
                            has_card = "NO";
                            rlCard.setVisibility(View.GONE);
                            cardPath = "";
                            CardMap = null;
                        }
                    } else {
                        tbCard.setChecked(false);
                        has_card = "NO";
                        rlCard.setVisibility(View.GONE);
                        cardPath = "";
                        CardMap = null;
                    }
                }
                break;

            case "EmergencyView":
                changeIcon(source);
                visiEmergency();
                disableEmergency();
                tilName.setHint("Name");
                tilName.setHintEnabled(true);
                // spinnerPriority.setVisibility(View.VISIBLE);
                // spinnerPriority.setFloatingLabelText("Priority");
                flPr.setVisibility(View.VISIBLE);
                tilPriority.setVisibility(View.VISIBLE);
                imgprio.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Emergency Contact and \nHealth Care Proxy Agent");
                Intent EmergencyIntentss = getActivity().getIntent();
                if (EmergencyIntentss.getExtras() != null) {

                    Emergency rel = (Emergency) EmergencyIntentss.getExtras().getSerializable("EmergencyObject");
                    txtName.setText(rel.getName());
                    txtEmail.setText(rel.getEmail());
                    txtMobile.setText(rel.getMobile());
                    txtHomePhone.setText(rel.getPhone());
                    txtWorkPhone.setText(rel.getWorkPhone());
                    txtAddress.setText(rel.getAddress());
                    txtEmergencyNote.setText(rel.getNote());
                    txtOtherRelation.setText(rel.getOtherRelation());
                    id = rel.getId();
                    if (!rel.getRelationType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < Relationship.length; i++) {
                            if (rel.getRelationType().equals(Relationship[i])) {
                                index = i;
                                txtRelation.setText(Relationship[i]);
                            }
                        }

                        //spinnerRelation.setSelection(index + 1);
                    }
                    if (rel.getIsPrimary() != 4) {
                        for (int i = 0; i < priorityType.length; i++) {
                            if (rel.getIsPrimary() == i) {
                                txtPriority.setText(priorityType[i]);
                            }
                        }
                        //  spinnerPriority.setSelection(rel.getIsPrimary() + 1);
                    }
                    String photo = rel.getPhoto();
                    imagepath = rel.getPhoto();
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);

                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);

                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                    }


                   /* Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);*/

                    //Change Class Name
                    cardPath = rel.getPhotoCard();
                    if (!rel.getPhotoCard().equals("")) {
                        String photoCard = rel.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            //  imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.GONE);
                        tbCard.setChecked(false);
                    }

                }
                break;

            case "Speciality":
                changeIcon(source);
                visiSpecialist();
                txtAdd.setText("Add Doctors & Health Care Professionals");
                txtTitle.setText("Add Doctors & Health Care Professionals");
//                setListPh(listDrPhone);
                PhoneLayout = llAddDrPhone;
                setListPh();
                break;

            case "Physician":
                changeIcon(source);
                visiSpecialist();
                txtAdd.setText("Add Primary Physician");
                txtTitle.setText("Add Primary Physician");
//                setListPh(listDrPhone);
                PhoneLayout = llAddDrPhone;
                setListPh();
                break;

            case "SpecialistData":
                changeIcon(source);
                visiSpecialist();
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Update Doctors & Health Care Professionals");
                txtTitle.setText("Update Doctors & Health Care Professionals");
                Intent specialistIntent = getActivity().getIntent();
                if (specialistIntent.getExtras() != null) {
                    specialist = (Specialist) specialistIntent.getExtras().getSerializable("SpecialistObject");
                    specialistDoctor = (Specialist) specialistIntent.getExtras().getSerializable("SpecialistObject");
                    txtDoctorName.setText(specialist.getName());
                    txtDoctorOtherPhone.setText(specialist.getOtherPhone());
                    txtDoctorHourOfficePhone.setText(specialist.getHourPhone());
                    txtDoctorOfficePhone.setText(specialist.getOfficePhone());
                    txtDoctorAddress.setText(specialist.getAddress());

                    /*if (Cname.isEmpty()) {//nikita
                        txtDoctorName.setText(specialist.getName());
                    } else {
                        txtDoctorName.setText(Cname);
                    }
                    if (CWPhone.isEmpty()) {//nikita
                        txtDoctorOtherPhone.setText(specialist.getOtherPhone());
                    } else {
                        txtDoctorOtherPhone.setText(CWPhone);
                    }
                    if (CHPhone.isEmpty()) {//nikita
                        txtDoctorHourOfficePhone.setText(specialist.getHourPhone());
                    } else {
                        txtDoctorHourOfficePhone.setText(CHPhone);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtDoctorOfficePhone.setText(specialist.getOfficePhone());
                    } else {
                        txtDoctorOfficePhone.setText(Cphone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtDoctorAddress.setText(specialist.getAddress());
                    } else {
                        txtDoctorAddress.setText(CAddress);
                    }*/

                    txtDoctorLastSeen.setText(specialist.getLastseen());
                    txtDoctorLocator.setText(specialist.getLocator());
                    txtDoctorWebsite.setText(specialist.getWebsite());
                    txtDoctorFax.setText(specialist.getFax());
                    txtAffiliation.setText(specialist.getHospAffiliation());
                    txtPracticeName.setText(specialist.getPracticeName());
                    txtOtherCategoryDoctor.setText(specialist.getOtherType());
                    txtNetwork.setText(specialist.getNetwork());
                    txtDoctorNote.setText(specialist.getNote());

                    id = specialist.getId();
                    isPhysician = specialist.getIsPhysician();
                    /*if (!specialist.getType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < healthSpeciality.length; i++) {
                            if (specialist.getType().equals(healthSpeciality[i])) {
                                index = i;
                            }
                        }
                        spinner.setSelection(index + 1);
                    }*/

                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), id, "Doctor");
//                    setListPh(listDrPhone);
                    PhoneLayout = llAddDrPhone;
                    setListPh();
                    txtSpecialty.setText(specialist.getType());
                    if (specialist.getType().equals("Other")) {
                        tilOtherCategoryDoctor.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherCategoryDoctor.setVisibility(View.GONE);
                    }

                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = specialist.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);

                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    // imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                    //   imgEdit.setVisibility(View.VISIBLE);
                   /* if (imgFile.exists()) {
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));

                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    } else
                        imgProfile.setImageResource(R.drawable.ic_profile_defaults);*/

                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        imgEditCard.setVisibility(View.GONE);
                        tbCard.setChecked(false);
                    }

                }
                if (specialist.getHas_card() != null) {
                    if (specialist.getHas_card().equals("YES")) {
                        tbCard.setChecked(true);
                        has_card = "YES";
                        rlCard.setVisibility(View.VISIBLE);
                    } else {
                        tbCard.setChecked(false);
                        has_card = "NO";
                        rlCard.setVisibility(View.GONE);
                        cardPath = "";
                        CardMap = null;
                    }
                } else {
                    tbCard.setChecked(false);
                    has_card = "NO";
                    rlCard.setVisibility(View.GONE);
                    cardPath = "";
                    CardMap = null;
                }
                break;
            case "PhysicianData":
                changeIcon(source);
                visiSpecialist();
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Update Primary Physician");
                txtTitle.setText("Update Primary Physician");
                Intent specialistIntent1 = getActivity().getIntent();
                if (specialistIntent1.getExtras() != null) {
                    specialist = (Specialist) specialistIntent1.getExtras().getSerializable("SpecialistObject");
                    txtDoctorName.setText(specialist.getName());
                    txtDoctorOtherPhone.setText(specialist.getOtherPhone());
                    txtDoctorHourOfficePhone.setText(specialist.getHourPhone());
                    txtDoctorOfficePhone.setText(specialist.getOfficePhone());
                    txtDoctorAddress.setText(specialist.getAddress());
                   /* if (Cname.isEmpty()) {//nikita
                        txtDoctorName.setText(specialist.getName());
                    } else {
                        txtDoctorName.setText(Cname);
                    }
                    if (CWPhone.isEmpty()) {//nikita
                        txtDoctorOtherPhone.setText(specialist.getOtherPhone());
                    } else {
                        txtDoctorOtherPhone.setText(CWPhone);
                    }
                    if (CHPhone.isEmpty()) {//nikita
                        txtDoctorHourOfficePhone.setText(specialist.getHourPhone());
                    } else {
                        txtDoctorHourOfficePhone.setText(CHPhone);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtDoctorOfficePhone.setText(specialist.getOfficePhone());
                    } else {
                        txtDoctorOfficePhone.setText(Cphone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtDoctorAddress.setText(specialist.getAddress());
                    } else {
                        txtDoctorAddress.setText(CAddress);
                    }
*/
                    txtDoctorLastSeen.setText(specialist.getLastseen());
                    txtDoctorLocator.setText(specialist.getLocator());
                    txtDoctorWebsite.setText(specialist.getWebsite());
                    txtDoctorFax.setText(specialist.getFax());
                    txtOtherCategoryDoctor.setText(specialist.getOtherType());
                    txtAffiliation.setText(specialist.getHospAffiliation());
                    txtPracticeName.setText(specialist.getPracticeName());
                    txtNetwork.setText(specialist.getNetwork());
                    txtDoctorNote.setText(specialist.getNote());

                    id = specialist.getId();
                    isPhysician = specialist.getIsPhysician();
                   /* if (!specialist.getType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < healthSpeciality.length; i++) {
                            if (specialist.getType().equals(healthSpeciality[i])) {
                                index = i;
                            }
                        }
                        spinner.setSelection(index + 1);
                    }*/
                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), id, "Primary");
//                    setListPh(listDrPhone);
                    PhoneLayout = llAddDrPhone;
                    setListPh();
                    txtSpecialty.setText(specialist.getType());
                    if (specialist.getType().equals("Other")) {
                        tilOtherCategoryDoctor.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherCategoryDoctor.setVisibility(View.GONE);
                    }
                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = specialist.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    // imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                    // imgEdit.setVisibility(View.GONE);
                    // ContactDataQuery cc = new ContactDataQuery(context, dbHelper);
                    // phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), id, "Physician");
//                    setListPh(listDrPhone);
                    //  setListPh(llAddDrPhone);
                    /*
                    if (imgFile.exists()) {
                       */
/* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imgProfile.setImageBitmap(myBitmap);*//*

                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));

                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    }
*/
                   /* Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);*/

                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                          /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }

                }

                if (specialist.getHas_card() != null) {
                    if (specialist.getHas_card().equals("YES")) {
                        tbCard.setChecked(true);
                        has_card = "YES";
                        rlCard.setVisibility(View.VISIBLE);
                    } else {
                        tbCard.setChecked(false);
                        has_card = "NO";
                        rlCard.setVisibility(View.GONE);
                        cardPath = "";
                        CardMap = null;
                    }
                } else {
                    tbCard.setChecked(false);
                    has_card = "NO";
                    rlCard.setVisibility(View.GONE);
                    cardPath = "";
                    CardMap = null;
                }
                break;


            case "SpecialistViewData":
                changeIcon(source);
                visiSpecialist();
                disableSpecialist();
                txtTitle.setText("Doctor");
                txtTitle.setVisibility(View.VISIBLE);
                Intent specialistIntentss = getActivity().getIntent();
                if (specialistIntentss.getExtras() != null) {
                    Specialist specialist = (Specialist) specialistIntentss.getExtras().getSerializable("SpecialistObject");
                    txtDoctorName.setText(specialist.getName());
                    txtDoctorOtherPhone.setText(specialist.getOtherPhone());
                    txtDoctorLastSeen.setText(specialist.getLastseen());
                    txtDoctorLocator.setText(specialist.getLocator());
                    txtDoctorAddress.setText(specialist.getAddress());
                    txtDoctorWebsite.setText(specialist.getWebsite());
                    txtDoctorFax.setText(specialist.getFax());
                    txtDoctorHourOfficePhone.setText(specialist.getHourPhone());
                    txtDoctorOfficePhone.setText(specialist.getOfficePhone());
                    txtAffiliation.setText(specialist.getHospAffiliation());
                    txtPracticeName.setText(specialist.getPracticeName());
                    txtOtherCategoryDoctor.setText(specialist.getOtherType());
                    txtNetwork.setText(specialist.getNetwork());
                    txtDoctorNote.setText(specialist.getNote());
                    id = specialist.getId();
                    isPhysician = specialist.getIsPhysician();
                   /* if (!specialist.getType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < healthSpeciality.length; i++) {
                            if (specialist.getType().equals(healthSpeciality[i])) {
                                index = i;
                            }
                        }
                        spinner.setSelection(index + 1);
                    }*/
                    txtSpecialty.setText(specialist.getType());
                    String photo = specialist.getPhoto();
                    imagepath = specialist.getPhoto();
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }

                }
                break;
            case "PhysicianViewData":
                changeIcon(source);
                visiSpecialist();
                disableSpecialist();
                txtTitle.setText("Primary Physician");
                txtTitle.setVisibility(View.VISIBLE);
                Intent specialistIntents4 = getActivity().getIntent();
                if (specialistIntents4.getExtras() != null) {
                    Specialist specialist = (Specialist) specialistIntents4.getExtras().getSerializable("SpecialistObject");
                    txtDoctorName.setText(specialist.getName());
                    txtDoctorOtherPhone.setText(specialist.getOtherPhone());
                    txtDoctorLastSeen.setText(specialist.getLastseen());
                    txtDoctorLocator.setText(specialist.getLocator());
                    txtDoctorAddress.setText(specialist.getAddress());
                    txtDoctorWebsite.setText(specialist.getWebsite());
                    txtDoctorFax.setText(specialist.getFax());
                    txtDoctorHourOfficePhone.setText(specialist.getHourPhone());
                    txtOtherCategoryDoctor.setText(specialist.getOtherType());
                    txtDoctorOfficePhone.setText(specialist.getOfficePhone());
                    txtAffiliation.setText(specialist.getHospAffiliation());
                    txtPracticeName.setText(specialist.getPracticeName());
                    txtNetwork.setText(specialist.getNetwork());
                    txtDoctorNote.setText(specialist.getNote());
                    id = specialist.getId();
                    isPhysician = specialist.getIsPhysician();
                    /*if (!specialist.getType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < healthSpeciality.length; i++) {
                            if (specialist.getType().equals(healthSpeciality[i])) {
                                index = i;
                            }
                        }
                        spinner.setSelection(index + 1);
                    }*/
                    txtSpecialty.setText(specialist.getType());
                    String photo = specialist.getPhoto();
                    imagepath = specialist.getPhoto();
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }

                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                          /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            //imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                        /*Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }

                }
                break;


            case "Insurance":
                changeIcon(source);
                //  rlInsured.setVisibility(View.VISIBLE);
                visiInsurance();
                txtAdd.setText("Add Insurance Company");
                txtTitle.setText("Add Insurance Company");
//                setListPh(listInsuPhone);
                PhoneLayout = llAddInsuPhone;
                //    A_PhoneLayout=llAddAentPhone;
                setListPh();
                // setAListPh();
                break;

            case "InsuranceData":
                changeIcon(source);
                visiInsurance();
                // rlInsured.setVisibility(View.VISIBLE);
                tilInsuaranceName.setHintEnabled(true);
                txtInsuaranceName.setFocusable(true);
                txtAdd.setText("Update Insurance Company");
                txtTitle.setText("Update Insurance Company");
                txtDelete.setVisibility(View.GONE);
                Intent insuranceIntent = getActivity().getIntent();
                if (insuranceIntent.getExtras() != null) {
                    insurance = (Insurance) insuranceIntent.getExtras().getSerializable("InsuranceObject");
                   /* if (!insurance.getType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < insuaranceType.length; i++) {
                            if (insurance.getType().equalsIgnoreCase(insuaranceType[i])) {
                                index = i;
                            }
                        }
                        spinnerInsuarance.setSelection(index + 1);
                    }*/
                    txtInsuType.setText(insurance.getType());
                    if (insurance.getType().equals("Other")) {
                        tilOtherInsurance.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherInsurance.setVisibility(View.GONE);
                    }
                    txtInsuaranceName.setText(insurance.getName());
                    txtAentEmail.setText(insurance.getAgent_email());
                    txtAentPhone.setText(insurance.getAgentPhone());
                   /* if (Cname.isEmpty()) {//nikita
                        txtInsuaranceName.setText(insurance.getName());
                    } else {
                        txtInsuaranceName.setText(Cname);
                    }*/
                    txtInsuaranceEmail.setText(insurance.getEmail());
                   /* if (Cemail.isEmpty()) {//nikita
                        txtInsuaranceEmail.setText(insurance.getEmail());
                    } else {
                        txtInsuaranceEmail.setText(Cemail);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtInsuarancePhone.setText(insurance.getPhone());
                    } else {
                        txtInsuarancePhone.setText(Cphone);
                    }*/

                    spinnerInsuarance.setDisabledColor(getActivity().getResources().getColor(R.color.colorBlack));

                    txtId.setText(insurance.getMember());
                    txtGroup.setText(insurance.getGroup());
                    txtInsuaranceFax.setText(insurance.getFax());
                    txtAgent.setText(insurance.getAgent());
                    txtWebsite.setText(insurance.getWebsite());
                    txtInsuaranceNote.setText(insurance.getNote());
                    txtSubscribe.setText(insurance.getSubscriber());
                    txtOtherInsurance.setText(insurance.getOtherInsurance());

                    id = insurance.getId();
                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), id, "Insurance");
//                    setListPh(listInsuPhone);
                    PhoneLayout = llAddInsuPhone;
                    setListPh();

                    /*Aphonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),id, "Agent");
                    A_PhoneLayout = llAddAentPhone;
                    setAListPh();*/

                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = insurance.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    // imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                    //    imgEdit.setVisibility(View.GONE);

                   /* if (imgFile.exists()) {
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));

                        //imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    } else {
                        imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                    }*/

                    //Change Class Name
                    cardPath = insurance.getPhotoCard();
                    if (!insurance.getPhotoCard().equals("")) {
                        String photoCard = insurance.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                    if (insurance.getHas_card() != null) {
                        if (insurance.getHas_card().equals("YES")) {
                            tbCard.setChecked(true);
                            has_card = "YES";
                            rlCard.setVisibility(View.VISIBLE);
                        } else {
                            tbCard.setChecked(false);
                            has_card = "NO";
                            rlCard.setVisibility(View.GONE);
                            cardPath = "";
                            CardMap = null;
                        }
                    } else {
                        tbCard.setChecked(false);
                        has_card = "NO";
                        rlCard.setVisibility(View.GONE);
                        cardPath = "";
                        CardMap = null;
                    }
                }

                break;

            case "InsuranceViewData":
                changeIcon(source);
                visiInsurance();
                disableInsurance();
                tilInsuaranceName.setHintEnabled(true);
                txtInsuaranceName.setFocusable(true);
                txtTitle.setText("Insurance Company");
                txtTitle.setVisibility(View.VISIBLE);
                Intent insuranceIntent2 = getActivity().getIntent();
                if (insuranceIntent2.getExtras() != null) {
                    Insurance insurance = (Insurance) insuranceIntent2.getExtras().getSerializable("InsuranceObject");
                    /*if (!insurance.getType().equals("")) {
                        int index = 0;
                        for (int i = 0; i < insuaranceType.length; i++) {
                            if (insurance.getType().equalsIgnoreCase(insuaranceType[i])) {
                                index = i;
                            }
                        }
                        spinnerInsuarance.setSelection(index + 1);
                    }*/
                    txtInsuType.setText(insurance.getType());
                    if (insurance.getType().equals("Other")) {
                        tilOtherInsurance.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherInsurance.setVisibility(View.GONE);
                    }
                    spinnerInsuarance.setDisabledColor(getActivity().getResources().getColor(R.color.colorBlack));
                    txtInsuarancePhone.setText(insurance.getPhone());
                    txtId.setText(insurance.getMember());
                    txtGroup.setText(insurance.getGroup());
                    txtInsuaranceFax.setText(insurance.getFax());
                    txtInsuaranceEmail.setText(insurance.getEmail());
                    txtAgent.setText(insurance.getAgent());
                    txtWebsite.setText(insurance.getWebsite());
                    txtInsuaranceNote.setText(insurance.getNote());
                    txtInsuaranceName.setText(insurance.getName());
                    txtSubscribe.setText(insurance.getSubscriber());
                    txtOtherInsurance.setText(insurance.getOtherInsurance());
                    id = insurance.getId();
                    String photo = insurance.getPhoto();
                    imagepath = insurance.getPhoto();
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    //Change Class Name
                    cardPath = insurance.getPhotoCard();
                    if (!insurance.getPhotoCard().equals("")) {
                        String photoCard = insurance.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                          /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                        /*Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                }
                break;

            case "Aides":
                visiAides();
                txtAdd.setText("Add Health Service");
                txtTitle.setText("Add Health Service");
                break;

            case "AidesData":
                visiAides();
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Update Health Service");
                txtTitle.setText("Update Health Service");
                Intent aidesIntent = getActivity().getIntent();
                if (aidesIntent.getExtras() != null) {
                    Aides aides = (Aides) aidesIntent.getSerializableExtra("AideObject");

                    if (Cname.isEmpty()) {//nikita
                        txtAideCompName.setText(aides.getAidName());
                    } else {
                        txtAideCompName.setText(Cname);
                    }
                    if (Cemail.isEmpty()) {//nikita
                        txtAideEmail.setText(aides.getEmail());
                    } else {
                        txtAideEmail.setText(Cemail);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtAideOfficePhone.setText(aides.getOfficePhone());
                    } else {
                        txtAideOfficePhone.setText(Cphone);
                    }
                    if (CHPhone.isEmpty()) {//nikita
                        txtHourOfficePhone.setText(aides.getHourPhone());
                    } else {
                        txtHourOfficePhone.setText(CHPhone);
                    }
                    if (CWPhone.isEmpty()) {//nikita
                        txtOtherPhone.setText(aides.getOtherPhone());
                    } else {
                        txtOtherPhone.setText(CWPhone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtAideAddress.setText(aides.getAddress());
                    } else {
                        txtAideAddress.setText(CAddress);
                    }

                    txtAideFax.setText(aides.getFax());
                    txtAideWebsite.setText(aides.getWebsite());
                    txtAideWebsite.setText(aides.getWebsite());
                    txtAideNote.setText(aides.getNote());
                    id = aides.getId();

                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = aides.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    if (imgFile.exists()) {
                       /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imgProfile.setImageBitmap(myBitmap);*/
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                        imgEdit.setVisibility(View.VISIBLE);
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    }
                   /* Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);*/

                    //Change Class Name
                    cardPath = aides.getPhotoCard();
                    if (!aides.getPhotoCard().equals("")) {
                        String photoCard = aides.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                        if (imgFile1.exists()) {
                            /*Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                }

                break;

            case "AidesViewData":
                visiAides();
                disableAides();
                txtTitle.setText("Health Service");
                txtTitle.setVisibility(View.VISIBLE);
                Intent aidesIntent2 = getActivity().getIntent();
                if (aidesIntent2.getExtras() != null) {
                    Aides aides = (Aides) aidesIntent2.getSerializableExtra("AideObject");
                    txtAideCompName.setText(aides.getAidName());
                    txtAideOfficePhone.setText(aides.getOfficePhone());
                    txtHourOfficePhone.setText(aides.getHourPhone());
                    txtOtherPhone.setText(aides.getOtherPhone());
                    txtAideFax.setText(aides.getFax());
                    txtAideEmail.setText(aides.getEmail());
                    txtAideAddress.setText(aides.getAddress());
                    txtAideWebsite.setText(aides.getWebsite());
                    txtAideWebsite.setText(aides.getWebsite());
                    txtAideNote.setText(aides.getNote());
                    id = aides.getId();

                    String photo = aides.getPhoto();
                    imagepath = aides.getPhoto();
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    if (imgFile.exists()) {
                      /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imgProfile.setImageBitmap(myBitmap);*/
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                        imgEdit.setVisibility(View.VISIBLE);
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                    }
                  /*  Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);*/


                    //Change Class Name
                    cardPath = aides.getPhotoCard();
                    if (!aides.getPhotoCard().equals("")) {
                        String photoCard = aides.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            //  imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                      /*  Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                }

                break;

            case "Finance":
                changeIcon(source);
                visiFinance();
                txtAdd.setText("Add Finance, Legal, Other");
                txtTitle.setText("Add Finance, Legal, Other");
//                setListPh(listFinPhone);
                PhoneLayout = llAddFinPhone;
                setListPh();
                break;

            case "Hospital":
                changeIcon(source);
                // visiFinance();
                visiHospital();
                txtAdd.setText("Add Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
                txtTitle.setText("Add Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
//                setListPh(listHospPhone);
                PhoneLayout = llAddHospPhone;
                setListPh();

                break;

            case "HospitalData":
                changeIcon(source);
                visiHospital();
                tilFNameHospital.setHintEnabled(true);
                txtFNameHospital.setFocusable(true);
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Update Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
                txtTitle.setText("Update Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
                Intent hospIntent = getActivity().getIntent();
                if (hospIntent.getExtras() != null) {
                    Hospital specialist = (Hospital) hospIntent.getExtras().getSerializable("HospitalObject");
                    hospital = (Hospital) hospIntent.getExtras().getSerializable("HospitalObject");
                    txtFNameHospital.setText(specialist.getName());
                    txtHospitalAddress.setText(specialist.getAddress());
                   /* if (Cname.isEmpty()) {//nikita
                        txtFNameHospital.setText(specialist.getName());
                    } else {
                        txtFNameHospital.setText(Cname);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtHospitalOfficePhone.setText(specialist.getOfficePhone());
                    } else {
                        txtHospitalOfficePhone.setText(Cphone);
                    }
                    if (CWPhone.isEmpty()) {//nikita
                        txtHospitalOtherPhone.setText(specialist.getOtherPhone());
                    } else {
                        txtHospitalOtherPhone.setText(CHPhone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtHospitalAddress.setText(specialist.getAddress());
                    } else {
                        txtHospitalAddress.setText(CAddress);
                    }*/

                    txtHospitalLastSeen.setText(specialist.getLastseen());
                    txtHospitalLocator.setText(specialist.getLocator());
                    txtHospitalWebsite.setText(specialist.getWebsite());
                    txtHospitalFax.setText(specialist.getFax());
                    txtHospitalPracticeName.setText(specialist.getPracticeName());
                    txtHospitalNote.setText(specialist.getNote());
                    txtOtherCategoryHospital.setText(specialist.getOtherCategory());
                    txtHospitalLocation.setText(specialist.getLocation());

                    id = specialist.getId();

                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), id, "Hospital");
//                    setListPh(listHospPhone);
                    PhoneLayout = llAddHospPhone;
                    setListPh();
                   /* if (!specialist.getCategory().equals("")) {
                        int index = 0;
                        for (int i = 0; i < HospitalType.length; i++) {
                            if (specialist.getCategory().equals(HospitalType[i])) {
                                index = i;
                            }
                        }
                        spinnerHospital.setSelection(index + 1);
                    }*/
                    txtHCategory.setText(specialist.getCategory());
                    if (specialist.getCategory().equals("Other")) {
                        tilOtherCategoryHospital.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherCategoryHospital.setVisibility(View.GONE);
                    }

                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = specialist.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    //  imgProfile.setImageResource(R.drawable.ic_profile_defaults);

                    /*Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);*/

                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                    if (specialist.getHas_card() != null) {
                        if (specialist.getHas_card().equals("YES")) {
                            tbCard.setChecked(true);
                            has_card = "YES";
                            rlCard.setVisibility(View.VISIBLE);
                        } else {
                            tbCard.setChecked(false);
                            has_card = "NO";
                            rlCard.setVisibility(View.GONE);
                            cardPath = "";
                            CardMap = null;
                        }
                    } else {
                        tbCard.setChecked(false);
                        has_card = "NO";
                        rlCard.setVisibility(View.GONE);
                        cardPath = "";
                        CardMap = null;
                    }
                }


                break;
            case "HospitalViewData":
                changeIcon(source);
                visiHospital();
                disableHospital();

                // disableFinance();
                tilFNameHospital.setHintEnabled(true);
                txtFNameHospital.setFocusable(true);
                txtTitle.setText("HOSPITALS & REHABILITATION CENTERS");
                txtTitle.setVisibility(View.VISIBLE);
                Intent financeIntent2 = getActivity().getIntent();
                if (financeIntent2.getExtras() != null) {
                    Hospital specialist = (Hospital) financeIntent2.getExtras().getSerializable("HospitalObject");

                    txtFNameHospital.setText(specialist.getName());
                    txtHospitalOtherPhone.setText(specialist.getOtherPhone());
                    txtHospitalLastSeen.setText(specialist.getLastseen());
                    txtHospitalLocator.setText(specialist.getLocator());
                    txtHospitalAddress.setText(specialist.getAddress());
                    txtHospitalWebsite.setText(specialist.getWebsite());
                    txtHospitalFax.setText(specialist.getFax());
                    txtHospitalOfficePhone.setText(specialist.getOfficePhone());
                    txtHospitalPracticeName.setText(specialist.getPracticeName());
                    txtHospitalNote.setText(specialist.getNote());
                    txtOtherCategoryHospital.setText(specialist.getOtherCategory());
                    txtHospitalLocation.setText(specialist.getLocation());

                    id = specialist.getId();
                   /* if (!specialist.getCategory().equals("")) {
                        int index = 0;
                        for (int i = 0; i < HospitalType.length; i++) {
                            if (specialist.getCategory().equals(HospitalType[i])) {
                                index = i;
                            }
                        }
                        spinnerHospital.setSelection(index + 1);
                    }*/
                    txtHCategory.setText(specialist.getCategory());

                    imagepath = specialist.getPhoto();
                    String photo = specialist.getPhoto();
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                   /* Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);*/

                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                            /*Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                }
                break;


            case "FinanceData":
                changeIcon(source);
                visiFinance();
                tilFName.setHintEnabled(true);
                txtFName.setFocusable(true);
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Update Finance, Legal, Other  ");
                txtTitle.setText("Update Finance, Legal, Other  ");

                Intent financeIntent = getActivity().getIntent();
                if (financeIntent.getExtras() != null) {
                    Finance specialist = (Finance) financeIntent.getExtras().getSerializable("FinanceObject");
                    finance = (Finance) financeIntent.getExtras().getSerializable("FinanceObject");
                    txtContactName.setText(specialist.getContactName());
                    txtFinanceEmail.setText(specialist.getEmail());
                    txtFinanceAddress.setText(specialist.getAddress());
                   /* if (Cname.isEmpty()) {//nikita
                        txtContactName.setText(specialist.getContactName());
                    } else {
                        txtContactName.setText(Cname);
                    }
                    if (Cemail.isEmpty()) {//nikita
                        txtFinanceEmail.setText(specialist.getEmail());
                    } else {
                        txtFinanceEmail.setText(Cemail);
                    }
                    if (Cphone.isEmpty()) {//nikita
                        txtFinanceMobilePhone.setText(specialist.getHourPhone());
                    } else {
                        txtFinanceMobilePhone.setText(Cphone);
                    }
                    if (CHPhone.isEmpty()) {//nikita
                        txtFinanceOtherPhone.setText(specialist.getOtherPhone());
                    } else {
                        txtFinanceOtherPhone.setText(CHPhone);
                    }
                    if (CWPhone.isEmpty()) {//nikita
                        txtFinanceOfficePhone.setText(specialist.getOfficePhone());
                    } else {
                        txtFinanceOfficePhone.setText(CWPhone);
                    }
                    if (CAddress.isEmpty()) {//nikita
                        txtFinanceAddress.setText(specialist.getAddress());
                    } else {
                        txtFinanceAddress.setText(CAddress);
                    }*/

                    txtFinanceLocation.setText(specialist.getLocation());
                    txtFName.setText(specialist.getName());
                    txtLastSeen.setText(specialist.getLastseen());
                    txtFinanceWebsite.setText(specialist.getWebsite());
                    txtFinanceFax.setText(specialist.getFax());
                    txtFinancePracticeName.setText(specialist.getPracticeName());
                    txtFinanceNote.setText(specialist.getNote());

//                    txtOtherCategory.setText(specialist.getOtherCategory());
                    /*if (specialist.getOtherCategory() == null || specialist.getOtherCategory().equals("null") || specialist.getOtherCategory().isEmpty()) {
                        txtOtherCategory.setText(specialist.getCategory());
                    } else {
                        txtOtherCategory.setText(specialist.getOtherCategory());
                    }*/
                    txtFCategory.setText(specialist.getCategory());

                    if (specialist.getCategory().equals("Other")) {
                        txtOtherCategory.setText(specialist.getOtherCategory());
                        tilOtherCategory.setVisibility(View.VISIBLE);
                    } else {
                        txtOtherCategory.setText("");
                        tilOtherCategory.setVisibility(View.GONE);
                    }
                    id = specialist.getId();

                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), id, "Finance");
//                    setListPh(listFinPhone);
                    PhoneLayout = llAddFinPhone;
                    setListPh();
                /*    if (!specialist.getCategory().equals("")) {
                        int index = 0;
                        for (int i = 0; i < financeType.length; i++) {
                            if (specialist.getCategory().equals(financeType[i])) {
                                index = i;
                            }
                        }
                        spinnerFinance.setSelection(index + 1);
                    }*/

                    String photo;
                    if (imagepath.isEmpty()) {//nikita
                        photo = specialist.getPhoto();
                    } else {
                        photo = imagepath;
                    }
                    imagepath = photo;//nikita

                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    //    imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                    // imgEdit.setVisibility(View.GONE);
                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                            /*Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                    if (specialist.getHas_card() != null) {
                        if (specialist.getHas_card().equals("YES")) {
                            tbCard.setChecked(true);
                            has_card = "YES";
                            rlCard.setVisibility(View.VISIBLE);
                        } else {
                            tbCard.setChecked(false);
                            has_card = "NO";
                            rlCard.setVisibility(View.GONE);
                            cardPath = "";
                            CardMap = null;
                        }
                    } else {
                        tbCard.setChecked(false);
                        has_card = "NO";
                        rlCard.setVisibility(View.GONE);
                        cardPath = "";
                        CardMap = null;
                    }
                }


                break;

            case "FinanceViewData":
                changeIcon(source);
                visiFinance();
                disableFinance();
                tilFName.setHintEnabled(true);
                txtFName.setFocusable(true);
                txtTitle.setText("Finance and Legal");
                txtTitle.setVisibility(View.VISIBLE);
                Intent financeIntentd = getActivity().getIntent();
                if (financeIntentd.getExtras() != null) {
                    Finance specialist = (Finance) financeIntentd.getExtras().getSerializable("FinanceObject");

                    txtFName.setText(specialist.getName());
                    txtFinanceOtherPhone.setText(specialist.getOtherPhone());
                    txtFinanceEmail.setText(specialist.getEmail());
                    txtContactName.setText(specialist.getContactName());
                    txtFinanceLocation.setText(specialist.getLocation());
                    txtLastSeen.setText(specialist.getLastseen());
                    txtFinanceAddress.setText(specialist.getAddress());
                    txtFinanceWebsite.setText(specialist.getWebsite());
                    txtFinanceFax.setText(specialist.getFax());
                    txtFinanceMobilePhone.setText(specialist.getHourPhone());
                    txtFinanceOfficePhone.setText(specialist.getOfficePhone());
                    txtFinancePracticeName.setText(specialist.getPracticeName());
                    txtFinanceNote.setText(specialist.getNote());
//                    txtOtherCategory.setText(specialist.getOtherCategory());
                    if (specialist.getOtherCategory() == null || specialist.getOtherCategory().equals("null") || specialist.getOtherCategory().isEmpty()) {
                        txtOtherCategory.setText(specialist.getCategory());
                    } else {
                        txtOtherCategory.setText(specialist.getOtherCategory());
                    }

                    id = specialist.getId();
                    if (!specialist.getCategory().equals("")) {
                        int index = 0;
                        for (int i = 0; i < financeType.length; i++) {
                            if (specialist.getCategory().equals(financeType[i])) {
                                index = i;
                            }
                        }
                        spinnerFinance.setSelection(index + 1);
                    }
                    imagepath = specialist.getPhoto();
                    String photo = specialist.getPhoto();
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    if (imgFile.exists()) {
                        if (imgProfile.getDrawable() == null) {
                            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                            imgEdit.setVisibility(View.GONE);
                        } else {
                            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                            imgEdit.setVisibility(View.VISIBLE);
                        }
                        // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
                    }
                    //Change Class Name
                    cardPath = specialist.getPhotoCard();
                    if (!specialist.getPhotoCard().equals("")) {
                        String photoCard = specialist.getPhotoCard();
                        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photoCard);
                        if (imgFile1.exists()) {
                           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                            imgCard.setImageBitmap(myBitmap);*/
                            imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                            // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                        }
                       /* Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                        imgCard.setImageBitmap(bmpCard);*/
                        imgCard.setVisibility(View.VISIBLE);
                        rlCard.setVisibility(View.VISIBLE);
                        flFront.setVisibility(View.GONE);
                        imgEditCard.setVisibility(View.VISIBLE);
                        tbCard.setChecked(true);
                    } else {
                        imgEditCard.setVisibility(View.GONE);
                        imgCard.setVisibility(View.GONE);
                        rlCard.setVisibility(View.GONE);
                        flFront.setVisibility(View.VISIBLE);
                        tbCard.setChecked(false);
                    }
                }
                break;


        }

    }

    private void getCommonContact() {
        try {
            if (!Cphone.isEmpty()) {
                ContactData phone = new ContactData();
                phone.setValue(Cphone);
                phone.setContactType("Mobile");
                phonelist.add(phone);
            }
            if (!CHPhone.isEmpty()) {
                ContactData phone = new ContactData();
                phone.setValue(CHPhone);
                phone.setContactType("Home");
                phonelist.add(phone);
            }
            if (!CWPhone.isEmpty()) {
                ContactData phone = new ContactData();
                phone.setValue(CWPhone);
                phone.setContactType("Work");
                phonelist.add(phone);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeIcon(String source) {
        imgEdit.setVisibility(View.GONE);
        if (source.equals("Emergency")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("EmergencyView")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("EmergencyUpdate")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("Physician")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("Speciality")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("Pharmacy")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("Hospital")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("Finance")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("Insurance")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("FinanceViewData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("FinanceData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("HospitalViewData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("HospitalData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("InsuranceViewData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("InsuranceData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("PhysicianViewData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("SpecialistViewData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("PhysicianData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("SpecialistData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("PharmacyDataView")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("PharmacyData")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("Connection")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        }
    }


    private int getIcon(String source) {
        if (source.equals("Emergency")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("EmergencyView")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("EmergencyUpdate")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("Physician")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("Speciality")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("Pharmacy")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("Hospital")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("Finance")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("Insurance")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("FinanceViewData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("FinanceData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("HospitalViewData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("HospitalData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("InsuranceViewData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("InsuranceData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("PhysicianViewData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("SpecialistViewData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("PhysicianData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("SpecialistData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("PharmacyDataView")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("PharmacyData")) {
            return (R.drawable.ic_profile_defaults);
        } else if (source.equals("Connection")) {
            return (R.drawable.ic_profile_defaults);
        } else {
            return R.drawable.ic_profile_defaults;
        }
    }

    private void disableHospital() {
        txtFNameHospital.setEnabled(false);
        txtHospitalLocation.setEnabled(false);
        txtHospitalOtherPhone.setEnabled(false);
        txtHospitalLastSeen.setEnabled(false);
        txtHospitalLocator.setEnabled(false);
        txtHospitalAddress.setEnabled(false);
        txtHospitalWebsite.setEnabled(false);
        txtHospitalFax.setEnabled(false);
        txtHospitalOfficePhone.setEnabled(false);
        txtHospitalPracticeName.setEnabled(false);
        txtHospitalNote.setEnabled(false);
        spinnerHospital.setClickable(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        imgEditCard.setVisibility(View.GONE);
        flFront.setVisibility(View.GONE);

    }

    private void visiHospital() {
        rlTop.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
        txtSpecialty.setVisibility(View.GONE);
        rlDoctorCategory.setVisibility(View.GONE);
        rlFinance.setVisibility(View.GONE);
        rlHospital.setVisibility(View.VISIBLE);
        spinnerRelation.setVisibility(View.GONE);
        rlRelation.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);

       /* tilFNameHospital.setHint("Name");
        tilFNameHospital.setHintEnabled(false);
        txtFNameHospital.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilFNameHospital.setHintEnabled(true);
                txtFName.setFocusable(true);

                return false;
            }
        });*/
        rlPharmacy.setVisibility(View.GONE);

    }

    private void getSContact() {
        txtDoctorName.setText(Cname);
        txtDoctorAddress.setText(CAddress);
        try {
            if (!Cphone.isEmpty()) {
                ContactData phone = new ContactData();
                phone.setValue(Cphone);
                phone.setContactType("Mobile");
                phonelist.add(phone);
            }
            if (!CHPhone.isEmpty()) {
                ContactData phone = new ContactData();
                phone.setValue(CHPhone);
                phone.setContactType("Home");
                phonelist.add(phone);
            }
            if (!CWPhone.isEmpty()) {
                ContactData phone = new ContactData();
                phone.setValue(CWPhone);
                phone.setContactType("Work");
                phonelist.add(phone);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getContact() {
        txtName.setText(Cname);
        txtEmail.setText(Cemail);
        txtAddress.setText(CAddress);
        if (!Cphone.isEmpty()) {
            ContactData phone = new ContactData();
            phone.setValue(Cphone);
            phone.setContactType("Mobile");
            phonelist.add(phone);
        }
        if (!CHPhone.isEmpty()) {
            ContactData phone = new ContactData();
            phone.setValue(CHPhone);
            phone.setContactType("Home");
            phonelist.add(phone);
        }
        if (!CWPhone.isEmpty()) {
            ContactData phone = new ContactData();
            phone.setValue(CWPhone);
            phone.setContactType("Work");
            phonelist.add(phone);
        }

       /* txtHomePhone.setText(CHPhone);
        txtWorkPhone.setText(CWPhone);
        try {
            String mobile = "";
            mobile = Cphone;
            if (!mobile.isEmpty()) {//nikita
                mobile = getMobile(mobile);
                txtMobile.setText(mobile);
            }

            String hphone = "";
            hphone = CHPhone;
            if (!hphone.isEmpty()) {//nikita
                hphone = getMobile(hphone);
                txtHomePhone.setText(hphone);
            }

            String wphone = "";
            wphone = CWPhone;
            if (!wphone.isEmpty()) {//nikita
                wphone = getMobile(wphone);
                txtWorkPhone.setText(wphone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private String getMobile(String mobile) {
        mobile = mobile.replace("-", "");
        mobile = mobile.replace("(", "");
        mobile = mobile.replace(")", "");
        mobile = mobile.replace("", "");
        mobile = mobile.replace("+", "").trim();
        int count = mobile.length();
        mobile = mobile.substring(count - 10, count);
        if (!mobile.equals("")) {
            mobile = mobile.substring(0, 3) + "-" + mobile.substring(3, 6) + "-" + mobile.substring(6, mobile.length());
        }
        return mobile;
    }

    private void disablePharmacy() {
        txtPharmacyName.setEnabled(false);
        txtPharmacyAddress.setEnabled(false);
        txtPharmacyWebsite.setEnabled(false);
        txtPharmacyLocator.setEnabled(false);
        txtPharmacyFax.setEnabled(false);
        txtPharmacyPhone.setEnabled(false);
        txtPharmacyNote.setEnabled(false);

        imgEdit.setVisibility(View.GONE);
        flFront.setVisibility(View.GONE);
        llAddConn.setVisibility(View.GONE);
        imgEditCard.setVisibility(View.GONE);
    }

    private void disableProxy() {
        txtName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtMobile.setEnabled(false);
        txtHomePhone.setEnabled(false);
        txtWorkPhone.setEnabled(false);
        txtAddress.setEnabled(false);
        txtEmergencyNote.setEnabled(false);
        spinnerRelation.setClickable(false);

        imgEdit.setVisibility(View.GONE);
        flFront.setVisibility(View.GONE);
        imgEditCard.setVisibility(View.GONE);
        llAddConn.setVisibility(View.GONE);
    }

    private void disableEmergency() {
        txtName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtMobile.setEnabled(false);
        txtHomePhone.setEnabled(false);
        txtWorkPhone.setEnabled(false);
        txtAddress.setEnabled(false);
        txtEmergencyNote.setEnabled(false);
        spinnerRelation.setClickable(false);
        //  spinnerPriority.setClickable(false);
        txtPriority.setClickable(false);
        spinnerRelation.setFocusable(false);
        //  spinnerPriority.setFocusable(false);
        imgEdit.setVisibility(View.GONE);
        flFront.setVisibility(View.GONE);
        imgEditCard.setVisibility(View.GONE);
        llAddConn.setVisibility(View.GONE);
    }

    private void visiPharmacy() {

        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        spinnerRelation.setVisibility(View.GONE);
        rlRelation.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);
        rlFinance.setVisibility(View.GONE);
        tilEmergencyNote.setVisibility(View.GONE);
        rlPharmacy.setVisibility(View.VISIBLE);

        tilPharmacyName.setHintEnabled(false);
        txtPharmacyName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilPharmacyName.setHintEnabled(true);
                txtPharmacyName.setFocusable(true);

                return false;
            }
        });
    }

    private void visiProxy() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.VISIBLE);

        rlConnection.setVisibility(View.VISIBLE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlProxy.setVisibility(View.VISIBLE);
        spinnerProxy.setVisibility(View.VISIBLE);
        rlAids.setVisibility(View.GONE);
        rlFinance.setVisibility(View.GONE);
        tilName.setHint("First Name, Last Name");
        tilName.setHintEnabled(false);
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);

                return false;
            }
        });
        tilEmergencyNote.setVisibility(View.GONE);
        rlPharmacy.setVisibility(View.GONE);
    }

    private void visiEmergency() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.VISIBLE);
        rlConnection.setVisibility(View.VISIBLE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlFinance.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);


        tilEmergencyNote.setVisibility(View.VISIBLE);
        rlPharmacy.setVisibility(View.GONE);
    }

    private void disableFinance() {
        txtFName.setEnabled(false);
        txtFinanceEmail.setEnabled(false);
        txtContactName.setEnabled(false);
        txtFinanceLocation.setEnabled(false);
        txtFinanceOtherPhone.setEnabled(false);
        txtLastSeen.setEnabled(false);
        txtFinanceAddress.setEnabled(false);
        txtFinanceWebsite.setEnabled(false);
        txtFinanceFax.setEnabled(false);
        txtFinanceMobilePhone.setEnabled(false);
        txtFinanceOfficePhone.setEnabled(false);
        txtFinancePracticeName.setEnabled(false);
        txtFinanceNote.setEnabled(false);
        spinnerFinance.setClickable(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        flFront.setVisibility(View.GONE);
        imgEditCard.setVisibility(View.GONE);

    }

    private void disableAides() {
        txtAideCompName.setEnabled(false);
        txtAideOfficePhone.setEnabled(false);
        txtHourOfficePhone.setEnabled(false);
        txtOtherPhone.setEnabled(false);
        txtAideFax.setEnabled(false);
        txtAideEmail.setEnabled(false);
        txtAideAddress.setEnabled(false);
        txtAideWebsite.setEnabled(false);
        txtAideWebsite.setEnabled(false);
        txtAideNote.setEnabled(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        flFront.setVisibility(View.GONE);
        imgEditCard.setVisibility(View.GONE);

    }

    private void disableInsurance() {
        txtInsuarancePhone.setEnabled(false);
        txtId.setEnabled(false);
        txtGroup.setEnabled(false);
        txtInsuaranceFax.setEnabled(false);
        txtInsuaranceEmail.setEnabled(false);
        txtAgent.setEnabled(false);
        txtWebsite.setEnabled(false);
        txtInsuaranceNote.setEnabled(false);
        txtInsuaranceName.setEnabled(false);
        txtSubscribe.setEnabled(false);
        spinnerInsuarance.setClickable(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        flFront.setVisibility(View.GONE);
        imgEditCard.setVisibility(View.GONE);

    }

    private void disableSpecialist() {
        txtDoctorName.setEnabled(false);
        txtDoctorOtherPhone.setEnabled(false);
        txtDoctorLastSeen.setEnabled(false);
        txtDoctorLocator.setEnabled(false);
        txtDoctorAddress.setEnabled(false);
        txtDoctorWebsite.setEnabled(false);
        txtDoctorFax.setEnabled(false);
        txtOtherCategoryDoctor.setEnabled(false);
        txtDoctorHourOfficePhone.setEnabled(false);
        txtDoctorOfficePhone.setEnabled(false);
        txtAffiliation.setEnabled(false);
        txtPracticeName.setEnabled(false);
        txtNetwork.setEnabled(false);
        txtDoctorNote.setEnabled(false);
        spinner.setClickable(false);

        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        flFront.setVisibility(View.GONE);
        imgEditCard.setVisibility(View.GONE);

    }

    private void visiFinance() {
        rlTop.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
        txtSpecialty.setVisibility(View.GONE);
        rlDoctorCategory.setVisibility(View.GONE);
        rlFinance.setVisibility(View.VISIBLE);
        spinnerRelation.setVisibility(View.GONE);
        rlRelation.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlFinance.setVisibility(View.VISIBLE);
        rlProxy.setVisibility(View.GONE);

        tilFName.setHint("Firm Name");
        tilFName.setHintEnabled(false);
        txtFName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilFName.setHintEnabled(true);
                txtFName.setFocusable(true);

                return false;
            }
        });
        rlPharmacy.setVisibility(View.GONE);
    }

    private void visiAides() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        spinnerRelation.setVisibility(View.GONE);
        rlRelation.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.VISIBLE);
        rlFinance.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);

        tilAideCompName.setHintEnabled(false);
        txtAideCompName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilAideCompName.setHintEnabled(true);
                txtAideCompName.setFocusable(true);

                return false;
            }
        });
        rlPharmacy.setVisibility(View.GONE);
    }

    private void visiSpecialist() {
        rlTop.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
        txtSpecialty.setVisibility(View.VISIBLE);
        rlDoctorCategory.setVisibility(View.VISIBLE);
        rlFinance.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        spinnerRelation.setVisibility(View.GONE);
        rlRelation.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.VISIBLE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);

        txtAdd.setText("Add Doctors & Other Health Care Professionals");
        txtTitle.setText("Add Doctors & Other Health Care Professionals");
        // tilDoctorName.setHintEnabled(false);

       /* txtDoctorName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilDoctorName.setHintEnabled(true);
                txtDoctorName.setFocusable(true);

                return false;
            }
        });*/
        rlPharmacy.setVisibility(View.GONE);
    }

    private void visiInsurance() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        spinnerRelation.setVisibility(View.GONE);
        rlRelation.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.VISIBLE);
        rlAids.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);

        tilInsuaranceName.setHintEnabled(false);
        txtInsuaranceName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilInsuaranceName.setHintEnabled(true);
                txtInsuaranceName.setFocusable(true);

                return false;
            }
        });
        rlPharmacy.setVisibility(View.GONE);
    }

    private void initListener() {
        llAddConn.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgEditCard.setOnClickListener(this);
        imgCard.setOnClickListener(this);
        flFront.setOnClickListener(this);
        imgAddPhone.setOnClickListener(this);
        imgAddDrPhone.setOnClickListener(this);
        imgAddHospPhone.setOnClickListener(this);
        imgAddPharmPhone.setOnClickListener(this);
        imgAddFinPhone.setOnClickListener(this);
        imgAddInsuPhone.setOnClickListener(this);
        imgAddAentPhone.setOnClickListener(this);
        txtDelete.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }


    private void initUI() {

        imgBack=getActivity().findViewById(R.id.imgBack);
        scroll = rootview.findViewById(R.id.scroll);
        scroll.smoothScrollTo(0, rootview.getTop());
        layoutInflater = (LayoutInflater) getActivity().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        flFront = rootview.findViewById(R.id.flFront);
        rlInsured = rootview.findViewById(R.id.rlInsured);
//        listPrPhone = rootview.findViewById(R.id.listPrPhone);
//        listDrPhone = rootview.findViewById(R.id.listDrPhone);
//        listHospPhone = rootview.findViewById(R.id.listHospPhone);
//        listPharmPhone = rootview.findViewById(R.id.listPharmPhone);
//        listFinPhone = rootview.findViewById(R.id.listFinPhone);
//        listInsuPhone = rootview.findViewById(R.id.listInsuPhone);
        txtsave = getActivity().findViewById(R.id.txtsave);

        llAddPhone = rootview.findViewById(R.id.llAddPhone);
        llAddHospPhone = rootview.findViewById(R.id.llAddHospPhone);
        llAddPharmPhone = rootview.findViewById(R.id.llAddPharmPhone);
        llAddFinPhone = rootview.findViewById(R.id.llAddFinPhone);
        llAddInsuPhone = rootview.findViewById(R.id.llAddInsPhone);
        llAddDrPhone = rootview.findViewById(R.id.llAddDrPhone);
        llAddAentPhone = rootview.findViewById(R.id.llAddAentPhone);

        RlPhone = rootview.findViewById(R.id.RlPhone);
        imgAddPhone = rootview.findViewById(R.id.imgAddPhone);
        imgAddDrPhone = rootview.findViewById(R.id.imgAddDrPhone);
        imgAddHospPhone = rootview.findViewById(R.id.imgAddHospPhone);
        imgAddPharmPhone = rootview.findViewById(R.id.imgAddPharmPhone);
        imgAddFinPhone = rootview.findViewById(R.id.imgAddFinPhone);
        imgAddInsuPhone = rootview.findViewById(R.id.imgAddInsPhone);
        imgAddAentPhone = rootview.findViewById(R.id.imgAddAentPhone);
        txtDelete = rootview.findViewById(R.id.txtDelete);
        txtPriority = rootview.findViewById(R.id.txtPriority);
        txtPriority.setFocusable(false);
        tilPriority = rootview.findViewById(R.id.tilPriority);
        flPr = rootview.findViewById(R.id.flPr);
        imgprio = rootview.findViewById(R.id.imgprio);
        imgProfile = rootview.findViewById(R.id.imgProfile);
        if (source.equals("Emergency")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        } else if (source.equals("Physician")) {
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        }

        tbCard = rootview.findViewById(R.id.tbCard);
      /*  if (imgProfile.equals(R.color.colorThree)) {
            Resources res = getActivity().getResources();
            final ImageView image = (ImageView) rootview.findViewById(R.id.imgProfile);
            final int newColor = res.getColor(R.color.colorThree);
            image.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        } else if (imgProfile.equals(R.color.colorFive)) {
            Resources res = getActivity().getResources();
            final ImageView image = (ImageView) rootview.findViewById(R.id.imgProfile);
            final int newColor = res.getColor(R.color.colorFive);
            image.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        }*/
        listPhone = rootview.findViewById(R.id.listPhone);
        rlDoctorCategory = rootview.findViewById(R.id.rlDoctorCategory);
        rlContact = rootview.findViewById(R.id.rlContact);
        rlCard = rootview.findViewById(R.id.rlCard);
        txtCard = rootview.findViewById(R.id.txtCard);
        tilOtherCategoryHospital = rootview.findViewById(R.id.tilOtherCategoryHospital);
        tilOtherCategoryDoctor = rootview.findViewById(R.id.tilOtherCategoryDoctor);
        tilOtherCategoryDoctor.setHint("Other");
        tilOtherCategoryHospital.setHint("Other Hospital or Rehabilitation Center");
        tilFNameHospital = rootview.findViewById(R.id.tilFNameHospital);
        //Doctor
        txtOtherCategoryDoctor = rootview.findViewById(R.id.txtOtherCategoryDoctor);
        txtDoctorName = rootview.findViewById(R.id.txtDoctorName);
        txtDoctorOfficePhone = rootview.findViewById(R.id.txtDoctorOfficePhone);
        txtDoctorHourOfficePhone = rootview.findViewById(R.id.txtDoctorHourOfficePhone);
        txtDoctorOtherPhone = rootview.findViewById(R.id.txtDoctorOtherPhone);
        txtDoctorFax = rootview.findViewById(R.id.txtDoctorFax);
        txtAentEmail = rootview.findViewById(R.id.txtAentEmail);
        txtAentPhone = rootview.findViewById(R.id.txtAentPhone);
        tbCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    has_card = "YES";
                    rlCard.setVisibility(View.VISIBLE);
                    imgEditCard.setVisibility(View.VISIBLE);
                    if (flFront.getVisibility() == View.VISIBLE) {
                        imgEditCard.setVisibility(View.GONE);
                    }
                } else {
                    has_card = "NO";
                    // imgCard.setImageResource(R.drawable.busi_card);
                    imgEditCard.setVisibility(View.GONE);
                    flFront.setVisibility(View.VISIBLE);
                    imgCard.setVisibility(View.GONE);
                    cardPath = "";
                    CardMap = null;
                    rlCard.setVisibility(View.GONE);

                }
            }
        });

        rlContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getCurrentFocus() != null) {
                    InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    inm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
            }
        });

        txtDoctorFax.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtDoctorFax.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        txtDoctorWebsite = rootview.findViewById(R.id.txtDoctorWebsite);
        txtDoctorAddress = rootview.findViewById(R.id.txtDoctorAddress);
        txtDoctorLastSeen = rootview.findViewById(R.id.txtDoctorLastSeen);
        txtDoctorLastSeen.setFocusable(false);
        txtDoctorLocator = rootview.findViewById(R.id.txtDoctorLocator);
        txtOtherInsurance = rootview.findViewById(R.id.txtOtherInsurance);

        //Pharmacy
        txtPharmacyName = rootview.findViewById(R.id.txtPharmacyName);
        txtPharmacyAddress = rootview.findViewById(R.id.txtPharmacyAddress);
        txtPharmacyPhone = rootview.findViewById(R.id.txtPharmacyPhone);
        txtPharmacyFax = rootview.findViewById(R.id.txtPharmacyFax);
        txtPharmacyWebsite = rootview.findViewById(R.id.txtPharmacyWebsite);
        txtPharmacyLocator = rootview.findViewById(R.id.txtPharmacyLocator);
        txtPharmacyNote = rootview.findViewById(R.id.txtPharmacyNote);


        //Aides
        txtAideCompName = rootview.findViewById(R.id.txtAideCompName);
        txtAideOfficePhone = rootview.findViewById(R.id.txtAideOfficePhone);
        txtHourOfficePhone = rootview.findViewById(R.id.txtHourOfficePhone);
        txtOtherPhone = rootview.findViewById(R.id.txtOtherPhone);
        txtAideFax = rootview.findViewById(R.id.txtAideFax);
        txtAideEmail = rootview.findViewById(R.id.txtAideEmail);
        txtAideWebsite = rootview.findViewById(R.id.txtAideWebsite);
        txtAideNote = rootview.findViewById(R.id.txtAideNote);
        txtAideAddress = rootview.findViewById(R.id.txtAideAddress);
        txtDoctorLastSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(txtDoctorLastSeen);
            }
        });
        txtAideFax.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtAideFax.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        //Finance
        txtFinanceOfficePhone = rootview.findViewById(R.id.txtFinanceOfficePhone);
        txtFinanceMobilePhone = rootview.findViewById(R.id.txtFinanceMobilePhone);
        txtFinanceOtherPhone = rootview.findViewById(R.id.txtFinanceOtherPhone);
        txtFinanceFax = rootview.findViewById(R.id.txtFinanceFax);
        txtFinanceAddress = rootview.findViewById(R.id.txtFinanceAddress);
        txtFinanceWebsite = rootview.findViewById(R.id.txtFinanceWebsite);
        txtFinancePracticeName = rootview.findViewById(R.id.txtFinancePracticeName);
        txtLastSeen = rootview.findViewById(R.id.txtLastSeen);
        txtFinanceNote = rootview.findViewById(R.id.txtFinanceNote);

        rlHospital = rootview.findViewById(R.id.rlHospital);
        txtOtherCategoryHospital = rootview.findViewById(R.id.txtOtherCategoryHospital);
        txtFNameHospital = rootview.findViewById(R.id.txtFNameHospital);
        txtHospitalOfficePhone = rootview.findViewById(R.id.txtHospitalOfficePhone);
        txtHospitalOtherPhone = rootview.findViewById(R.id.txtHospitalOtherPhone);
        txtHospitalFax = rootview.findViewById(R.id.txtHospitalFax);
        txtHospitalAddress = rootview.findViewById(R.id.txtHospitalAddress);
        txtHospitalWebsite = rootview.findViewById(R.id.txtHospitalWebsite);
        txtHospitalLocation = rootview.findViewById(R.id.txtHospitalLocation);
        txtHospitalLocation.setFocusable(false);
        txtHospitalPracticeName = rootview.findViewById(R.id.txtHospitalPracticeName);
        txtHospitalLastSeen = rootview.findViewById(R.id.txtHospitalLastSeen);
        txtHospitalLastSeen.setFocusable(false);
        txtHospitalLocator = rootview.findViewById(R.id.txtHospitalLocator);
        txtHospitalNote = rootview.findViewById(R.id.txtHospitalNote);
        txtHospitalLastSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(txtHospitalLastSeen);
            }
        });
        txtHospitalOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHospitalOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtHospitalOtherPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHospitalOtherPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtHospitalFax.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHospitalFax.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtFinanceFax.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtFinanceFax.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        // Insurance
        txtSubscribe = rootview.findViewById(R.id.txtSubscribe);
        txtInsuaranceFax = rootview.findViewById(R.id.txtInsuaranceFax);
        txtInsuaranceEmail = rootview.findViewById(R.id.txtInsuaranceEmail);
        txtAgent = rootview.findViewById(R.id.txtAgent);
        txtWebsite = rootview.findViewById(R.id.txtWebsite);
        txtInsuaranceNote = rootview.findViewById(R.id.txtInsuaranceNote);


        txtTitle = getActivity().findViewById(R.id.txtTitle);
        llAddConn = rootview.findViewById(R.id.llAddConn);
        rlTop = rootview.findViewById(R.id.rlTop);

        tilOtherCategory = rootview.findViewById(R.id.tilOtherCategory);
        tilOtherCategory.setHint("Other Category");
        txtOtherCategory = rootview.findViewById(R.id.txtOtherCategory);
        tilOtherInsurance = rootview.findViewById(R.id.tilOtherInsurance);

        txtName = rootview.findViewById(R.id.txtName);
        txtEmail = rootview.findViewById(R.id.txtEmail);
        txtMobile = rootview.findViewById(R.id.txtMobile);
        txtEmergencyNote = rootview.findViewById(R.id.txtEmergencyNote);
        txtMobile.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtMobile.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtHomePhone = rootview.findViewById(R.id.txtPhone);
        txtWorkPhone = rootview.findViewById(R.id.txtOfficePhone);
        rlRelation = rootview.findViewById(R.id.rlRelation);
        txtRelation = rootview.findViewById(R.id.txtRelation);
        txtRelation.setFocusable(false);
        txtType = rootview.findViewById(R.id.txtType);
        txtType.setFocusable(false);
        txtDrType = rootview.findViewById(R.id.txtDrType);
        txtDrType.setFocusable(false);

        tilRelation = rootview.findViewById(R.id.tilRelation);
        tilSpecialty = rootview.findViewById(R.id.tilSpecialty);
        txtSpecialty = rootview.findViewById(R.id.txtSpecialty);
        txtSpecialty.setFocusable(false);
        tilInsutype = rootview.findViewById(R.id.tilInsuType);
        txtInsuType = rootview.findViewById(R.id.txtInsuType);
        txtInsuType.setFocusable(false);
        txtHCategory = rootview.findViewById(R.id.txtHCategory);
        txtHCategory.setFocusable(false);
        tilFCategory = rootview.findViewById(R.id.tilFCategory);
        txtFCategory = rootview.findViewById(R.id.txtFCategory);
        txtFCategory.setFocusable(false);
        rlProxy = rootview.findViewById(R.id.rlProxy);
        txtAdd = rootview.findViewById(R.id.txtAdd);
        imgEdit = rootview.findViewById(R.id.imgEdit);
        imgEditCard = rootview.findViewById(R.id.imgEditCard);
        imgProfile = rootview.findViewById(R.id.imgProfile);
        imgCard = rootview.findViewById(R.id.imgCard);
        spinner = rootview.findViewById(R.id.spinner);
        spinnerInsuarance = rootview.findViewById(R.id.spinnerInsuarance);
        spinnerFinance = rootview.findViewById(R.id.spinnerFinance);
        spinnerHospital = rootview.findViewById(R.id.spinnerHospital);
        spinnerProxy = rootview.findViewById(R.id.spinnerProxy);
        spinnerRelation = rootview.findViewById(R.id.spinnerRelation);
        spinnerPriority = rootview.findViewById(R.id.spinnerPriority);
        txtOtherRelation = rootview.findViewById(R.id.txtOtherRelation);

        txtHomePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHomePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        tilName = rootview.findViewById(R.id.tilName);
        tilPharmacyName = rootview.findViewById(R.id.tilPharmacyName);
        tilFName = rootview.findViewById(R.id.tilFName);
        tilAideCompName = rootview.findViewById(R.id.tilAideCompName);
        tilDoctorName = rootview.findViewById(R.id.tilDoctorName);
        tilInsuaranceName = rootview.findViewById(R.id.tilInsuaranceName);
        tilEmergencyNote = rootview.findViewById(R.id.tilEmergencyNote);
        tilOtherRelation = rootview.findViewById(R.id.tilOtherRelation);
        tilOtherRelation.setHint("Other Relation");
        txtWorkPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtWorkPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtAddress = rootview.findViewById(R.id.txtAddress);
        txtPracticeName = rootview.findViewById(R.id.txtPracticeName);
        txtFax = rootview.findViewById(R.id.txtFax);
        txtNetwork = rootview.findViewById(R.id.txtNetwork);
        txtNetwork.setFocusable(false);
        txtAffiliation = rootview.findViewById(R.id.txtAffiliation);
        txtDoctorNote = rootview.findViewById(R.id.txtDoctorNote);

        txtPharmacyPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtPharmacyPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtAideOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtAideOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtHourOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHourOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtOtherPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtOtherPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtFinanceOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtFinanceOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        txtAentPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtAentPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        txtFinanceMobilePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtFinanceMobilePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtFinanceOtherPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtFinanceOtherPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtFName = rootview.findViewById(R.id.txtFName);
        txtFinanceEmail = rootview.findViewById(R.id.txtFinanceEmail);
        txtContactName = rootview.findViewById(R.id.txtContactName);
        txtFinanceLocation = rootview.findViewById(R.id.txtFinanceLocation);
        txtAids = rootview.findViewById(R.id.txtAids);
        txtSchedule = rootview.findViewById(R.id.txtSchedule);
        txtOther = rootview.findViewById(R.id.txtOther);
        txtDoctorOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtDoctorOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtDoctorHourOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtDoctorHourOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtDoctorOtherPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtDoctorOtherPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        rlConnection = rootview.findViewById(R.id.rlConnection);
        rlDoctor = rootview.findViewById(R.id.rlDoctor);
        rlInsurance = rootview.findViewById(R.id.rlInsurance);
        rlCommon = rootview.findViewById(R.id.rlCommon);
        rlAids = rootview.findViewById(R.id.rlAids);
        rlFinance = rootview.findViewById(R.id.rlFinance);
        rlPharmacy = rootview.findViewById(R.id.rlPharmacy);


        txtInsuaranceName = rootview.findViewById(R.id.txtInsuaranceName);
        txtId = rootview.findViewById(R.id.txtId);
        txtGroup = rootview.findViewById(R.id.txtGroup);
        txtMember = rootview.findViewById(R.id.txtMember);
        txtInsuarancePhone = rootview.findViewById(R.id.txtInsuarancePhone);

        txtInsuarancePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtInsuarancePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtInsuaranceFax.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtInsuaranceFax.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtPharmacyFax.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtPharmacyFax.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        gridRelation = rootview.findViewById(R.id.gridRelation);
        setRelationData();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relationship");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, healthSpeciality);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Specialty");

        String sources = preferences.getString(PrefConstants.SOURCE);
        if (sources.equals("Finance") || sources.equals("FinanceViewData") || sources.equals("FinanceData")) {
            ArrayAdapter<String> financeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, financeType);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFinance.setAdapter(financeadapter);
            spinnerFinance.setHint("Category");
        }

        ArrayAdapter<String> hospitaladapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, HospitalType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHospital.setAdapter(hospitaladapter);
        spinnerHospital.setHint("Category");

        ArrayAdapter<String> adapterInsuarance = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, insuaranceType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInsuarance.setAdapter(adapterInsuarance);
        spinnerInsuarance.setHint("Type of Insurance");

        ArrayAdapter<String> adapterProxy = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, proxyType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProxy.setAdapter(adapterProxy);
        spinnerProxy.setHint("Proxy Agent Priority");
        //    txtTitle.setAllCaps(true);
        txtAdd.setAllCaps(true);

        ArrayAdapter<String> adapterPriority = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priorityType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapterPriority);
        spinnerPriority.setHint("Priority");
        //   txtTitle.setAllCaps(true);
        txtAdd.setAllCaps(true);

        txtRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                i.putExtra("Category", "Relation");
                i.putExtra("Selected", txtRelation.getText().toString());
                startActivityForResult(i, RESULT_RELATION);
            }
        });
        txtSpecialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                if (source.equalsIgnoreCase("PhysicianData") || source.equalsIgnoreCase("Physician")) {
                    i.putExtra("Category", "Physician");
                } else {
                    i.putExtra("Category", "Specialty");
                }

                i.putExtra("Selected", txtSpecialty.getText().toString());
                startActivityForResult(i, RESULT_SPECIALTY);
            }
        });
        txtNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                if (source.equalsIgnoreCase("PhysicianData") || source.equalsIgnoreCase("Physician")) {
                    i.putExtra("Category", "PhysicianNetwork");
                } else {
                    i.putExtra("Category", "SpecialtyNetwork");
                }

                i.putExtra("Selected", txtNetwork.getText().toString());
                startActivityForResult(i, RESULT_SPECIALTY_NETWORK);
            }
        });
        txtHospitalLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                i.putExtra("Category", "HospitalNetwork");
                i.putExtra("Selected", txtHospitalLocation.getText().toString());
                startActivityForResult(i, RESULT_HOSPITAL_NETWORK);
            }
        });
        txtHCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                i.putExtra("Category", "Category");
                i.putExtra("Selected", txtHCategory.getText().toString());
                startActivityForResult(i, RESULT_CATEGORY);
            }
        });
        txtFCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                i.putExtra("Category", "finance");
                i.putExtra("Selected", txtFCategory.getText().toString());
                startActivityForResult(i, RESULT_FINANCECAT);
            }
        });
        txtInsuType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                i.putExtra("Category", "Insurance");
                i.putExtra("Selected", txtInsuType.getText().toString());
                startActivityForResult(i, RESULT_INSURANCE);
            }
        });

        txtPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                i.putExtra("Category", "Priority");
                i.putExtra("Selected", txtPriority.getText().toString());
                startActivityForResult(i, RESULT_PRIORITY);
            }
        });
        txtType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Type");
                final String[] types = {"Mobile", "Office", "Home", "Fax"};
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtType.setText(types[which]);
                        dialog.dismiss();
                    }

                });
                b.show();
            }
        });

        txtDrType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Type");
                final String[] types = {"Mobile", "Office", "Home", "Fax"};
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtDrType.setText(types[which]);
                        dialog.dismiss();
                    }

                });
                b.show();
            }
        });
        spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherRelation.setVisibility(View.VISIBLE);
                } else {
                    tilOtherRelation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerFinance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherCategory.setVisibility(View.VISIBLE);
                } else {
                    tilOtherCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherCategoryHospital.setVisibility(View.VISIBLE);
                    txtOtherCategoryHospital.requestFocus();
                } else {
                    tilOtherCategoryHospital.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherCategoryDoctor.setVisibility(View.VISIBLE);
                } else {
                    tilOtherCategoryDoctor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerInsuarance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherInsurance.setVisibility(View.VISIBLE);
                } else {
                    tilOtherInsurance.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // setListPh(listPrPhone);
    }

    private void showCalendar(final TextView txtDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                long selectedMilli = newDate.getTimeInMillis();

                Date datePickerDate = new Date(selectedMilli);
                String reportDate = new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                DateClass d = new DateClass();
                d.setDate(reportDate);
                txtDate.setText(reportDate);
            }
        }, year, month, day);
        dpd.show();
    }

    public void setRelationData() {
        relationAdapter = new RelationAdapter(getActivity(), relationArraylist);
        gridRelation.setAdapter(relationAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtDelete:
                switch (source) {
                    case "Connection":

                        break;

                    case "EmergencyUpdate":
                        deleteEmergency(rel);
                        break;


                    case "SpecialistData":
                        deleteSpecialist(specialistDoctor, 2);
                        break;
                    case "PhysicianData":
                        deleteSpecialist(specialist, 1);
                        break;

                    case "PharmacyData":
                        deletePharmacy(pharmacy);

                        break;


                    case "HospitalData":
                        deleteHospital(hospital);
                        break;


                    case "FinanceData":
                        deleteFinance(finance);

                        break;

                    case "InsuranceData":
                        deleteInsurance(insurance);
                        break;
                    // InsuranceObject
                }
                break;

            case R.id.llAddConn:
                //  Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
               /* switch (source)
                {
                    case "Connections":
                        if (validate("Connection")) {
                          //  dialogManager = new DialogManager(new FragmentNewContact());
                          //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;

                    case "Speciality":
                        break;
                }
*/
              /*  if (source.equals("Connection")) {
                    if (validate("Connection")) {
                        dialogManager = new DialogManager(new FragmentNewContact());
                        dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                    }
                } else if (source.equals("Speciality")) {
                    if (validate("Speciality")) {
                        dialogManager = new DialogManager(new FragmentNewContact());
                        dialogManager.showCommonDialog("Save?", "Do you want to save Doctor?", getActivity(), "ADD_DOCTOR", null);
                    }
                }*/

                break;
            case R.id.txtAdd:
                //Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_SHORT).show();

                break;

            case R.id.imgAddPhone:

                // addNewPhone(llAddPhone);
                //  Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();

                break;

            case R.id.imgAddDrPhone:
//                addNewPhone(llAddDrPhone);
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgAddHospPhone:
//                addNewPhone(llAddHospPhone);
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgAddPharmPhone:
//                addNewPhone(llAddPharmPhone);
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgAddFinPhone:
//                addNewPhone(llAddFinPhone);
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgAddInsPhone:
//                addNewPhone(llAddInsuPhone);
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgProfile:
                ShowCameraDialog(RESULT_CAMERA_IMAGE, RESULT_SELECT_PHOTO, "Profile");

                break;
            case R.id.imgEditCard:
                ShowCameraDialog(RESULT_CAMERA_IMAGE_CARD, RESULT_SELECT_PHOTO_CARD, "Card");
                break;

            case R.id.flFront:
                ShowCameraDialog(RESULT_CAMERA_IMAGE_CARD, RESULT_SELECT_PHOTO_CARD, "Card");
                break;

            case R.id.imgCard:
                /*Bitmap bitmaps = ((BitmapDrawable) imgCard.getDrawable()).getBitmap();
                ByteArrayOutputStream baoss = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, baoss);
                byte[]  photoCard = baoss.toByteArray();*/
                if (cardPath != "") {
                    Intent i = new Intent(getActivity(), AddFormActivity.class);
                    i.putExtra("Image", cardPath);
                    i.putExtra("IsDelete", true);
                    //new
                    i.putExtra("isOnActivityResult", isOnActivityResult);
                    i.putExtra("cardImgPath", cardImgPath);
                    startActivityForResult(i, REQUEST_CARD);
                }
                break;


        }
    }

    private void deleteSpecialist(final Specialist specialist, final int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = SpecialistQuery.deleteRecord(specialist.getId(), id);
                // boolean flags=SpecialistQuery.deleteRecord(item.getUnique());
                if (flag == true) {//Shradha delete whole record and image
                    ArrayList<Specialist> specialistList = new ArrayList<>();
                    //specialistList = item.getImage();
                    for (int i = 0; i < specialistList.size(); i++) {
                        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH) + specialistList.get(i).getImage());//nikita
                        if (imgFile.exists()) {
                            imgFile.delete();
                        }
                    }
                    // Toast.makeText(getActivity(), "/", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                   /* getData();
                    setListData();*/
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();

    }

//    private void addNewPhone(final LinearLayout layout) {
//      /*  ArrayList phonelist=new ArrayList();
//
//        PhoneAdapter pd=new PhoneAdapter(context,phonelist,val);
//        listPhone.setAdapter(pd);*/
//        final View helperview = layoutInflater.inflate(R.layout.row_phone, null);
//        layout.addView(helperview);//jobt
//
//        TextView txtPhoNum = helperview.findViewById(R.id.txtPhoNum);
//        TextView txtType = helperview.findViewById(R.id.txtType);
//        ImageView imgdeletePhone = helperview.findViewById(R.id.imgdeletePhone);
//        txtType.setFocusable(false);
//        txtType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder b = new AlertDialog.Builder(context);
//                b.setTitle("Type");
//                final String[] types = {"Mobile", "Office", "Home", "Fax"};
//                b.setItems(types, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        TextView txtType = helperview.findViewById(R.id.txtType);
//                        txtType.setText(types[which]);
//                        dialog.dismiss();
//                    }
//
//                });
//
//                b.show();
//                //  Intent i=new Intent(getActivity(),PhoneActivity.class);
//                //  startActivityForResult(i,RESULT_TYPE);
//            }
//        });
//        imgdeletePhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                layout.removeView(helperview);
//            }
//        });
//
//    }

    private void ShowCameraDialog(final int resultCameraImageCard, final int resultSelectPhotoCard, final String profile) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_gender1, null);
        final TextView textOption1 = dialogview.findViewById(R.id.txtOption1);
        final TextView textOption2 = dialogview.findViewById(R.id.txtOption2);
        final TextView textOption3 = dialogview.findViewById(R.id.txtOption3);
        TextView textCancel = dialogview.findViewById(R.id.txtCancel);
        textOption1.setText("Take Picture");
        textOption2.setText("Gallery");
        textOption3.setText("Remove Picture");
        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        textOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dispatchTakePictureIntent(resultCameraImageCard, profile);
                if (profile.equals("Profile")) {
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    imageUriProfile = getActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    //  intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriProfile);
                    startActivityForResult(intent, resultCameraImageCard);
                } else if (profile.equals("Card")) {
                    dialogCameraFront(resultCameraImageCard);

                }

                dialog.dismiss();
            }
        });
        textOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, resultSelectPhotoCard);
                dialog.dismiss();
            }
        });

        textOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profile.equals("Profile")) {
                    //varsha
                    imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                    imagepath = "";
                    ProfileMap = null;
                    imgEdit.setVisibility(View.GONE);
                } else if (profile.equals("Card")) {
                    cardPath = "";
                    imgCard.setImageResource(R.drawable.busi_card);
                    imgEditCard.setVisibility(View.GONE);
                    imgCard.setVisibility(View.GONE);
                    rlCard.setVisibility(View.VISIBLE);
                    flFront.setVisibility(View.VISIBLE);
                    CardMap = null;
                }
                dialog.dismiss();
            }
        });
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void dialogCameraFront(final int resultCameraImage) {
        final Dialog dialogCamera = new Dialog(context);
        dialogCamera.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCamera.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_camera_ins, null);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);


        dialogCamera.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogCamera.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 0, 10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogCamera.getWindow().setAttributes(lp);
        dialogCamera.setCanceledOnTouchOutside(false);
        dialogCamera.show();


        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                imageUriCard = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                // intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriCard);


                startActivityForResult(intent, resultCameraImage);
                dialogCamera.dismiss();
            }
        });
    }

    Context context = getActivity();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private boolean validate(String screen) {
      /*  Bitmap bitmaps = ((BitmapDrawable) imgCard.getDrawable()).getBitmap();
        ByteArrayOutputStream baoss = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.JPEG, 10, baoss);
        if (imgCard.getVisibility() == View.VISIBLE) {
            photoCard = baoss.toByteArray();
        } else if (imgCard.getVisibility() == View.GONE) {
            photoCard = null;
        }*/

        if (tbCard.isChecked() && (CardMap == null && imgEditCard.getVisibility() != View.VISIBLE)) {
            DialogManager.showAlert("Please Add Business Card.", context);
            return false;
        }

        if (!screen.equals("Connection")) {
            storeImage(ProfileMap, "Profile");
            storeImage(CardMap, "Card");
        }

       /* if (fromDevice) {
            name = Cname;
            email = Cemail;

           *//* mobile = Cphone;
            phone = CHPhone;
            workphone = CWPhone;*//*
            address = CAddress;

        } else {*/


        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        mobile = txtMobile.getText().toString().trim();
        phone = txtHomePhone.getText().toString().trim();
        workphone = txtWorkPhone.getText().toString().trim();
        address = txtAddress.getText().toString().trim();


        //}
        int indexValue = spinnerRelation.getSelectedItemPosition();

        for (int i = 0; i < phonelist.size(); i++) {
            if (phonelist.get(i).getValue().isEmpty()&&phonelist.get(i).getContactType().isEmpty())
            {
                // phonelist.remove(phonelist.get(i));
                //  DialogManager.showAlert("Please add Phone number with Type", context);
            }else if (phonelist.get(i).getValue()==""&& phonelist.get(i).getContactType()!="")
            {
                DialogManager.showAlert("Please add Phone number with Type", context);
                return false;
            }else if (phonelist.get(i).getContactType()==""&&phonelist.get(i).getValue()!="")
            {
                DialogManager.showAlert("Please add Phone number with Type", context);
                return false;
            }else if (phonelist.get(i).getValue().length()<12||phonelist.get(i).getValue().length()>12)
            {
                DialogManager.showAlert("Phone number needs to be 10 digits", context);
                return false;
            }
        }

        if (screen.equals("Connection")) {
           /*   if (indexValue != 0) {
                relation = Relationship[indexValue - 1];
            }*/
            relation = txtRelation.getText().toString();
            otherRelation = txtOtherRelation.getText().toString();
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                DialogManager.showAlert("Please Enter Name", context);
            } else if (relation.equals("")) {
                txtRelation.setError("Please Select Relation");
                DialogManager.showAlert("Please Select Relation", context);
            } else if (relation.equals("Other") && otherRelation.equals("")) {
                txtOtherRelation.setError("Please Enter Other Relation");
                DialogManager.showAlert("Please Enter Other Relation", context);
            } else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                DialogManager.showAlert("Please Enter email", context);
            } else if (!email.equals("") && !email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                DialogManager.showAlert("Please enter valid email", context);
            } /*else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", context);
            }*/ else if (mobile.length() != 0 && mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                DialogManager.showAlert("Mobile number should be 10 digits", context);
            } /*else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", context);
            } else if (relation.equals("")) {
               // txtAddress.setError("Please Enter Address");
                showAlert("Please select relation", context);
            }*/ else return true;
        } else if (screen.equals("Speciality")) {
            if (name.equals("")) {
                txtName.setError("Please Enter Doctor Name");
                DialogManager.showAlert("Please Enter Name", context);
            } /*else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", context);
            }*/ else if (mobile.length() != 0 && mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                DialogManager.showAlert("Mobile number should be 10 digits", context);

            } else return true;
        } else if (screen.equals("Emergency")) {
            //  priority=txtPriority.getText().toString();
            relation = txtRelation.getText().toString();
            /*int indexs = spinnerPriority.getSelectedItemPosition();
            if (indexValue != 0) {
                relation = Relationship[indexValue - 1];
            }
            if (indexs != 0) {
                priority = priorityType[indexs - 1];
            }*/
            otherRelation = txtOtherRelation.getText().toString();
            note = txtEmergencyNote.getText().toString().trim();
            /**/
            if (priority.equals("Primary Emergency Contact")) {
                prior = 0;
            } else if (priority.equals("Primary Health Care Proxy Agent")) {
                prior = 1;
            } else if (priority.equals("Secondary Emergency Contact")) {
                prior = 2;
            } else if (priority.equals("Secondary Health Care Proxy Agent")) {
                prior = 3;
            } else if (priority.equals("Primary Emergency Contact and Health Care Proxy Agent")) {

                prior = 4;
            } else {
                prior = 5;
            }
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                DialogManager.showAlert("Please Enter Name", context);
            } /*else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                showAlert("Please Enter email", context);
            } */ else if (!email.equals("") && !email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                DialogManager.showAlert("Please enter valid email", context);
            } /*else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", context);
            }*/ else if (mobile.length() != 0 && mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                DialogManager.showAlert("Mobile number should be 10 digits", context);
            }
           /* else if (phone.equals("")) {
                txtHomePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", context);
            } else if (phone.length() < 10) {
                txtHomePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }else if (workphone.equals("")) {
                txtWorkPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", context);
            } else if (workphone.length() < 10) {
                txtWorkPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", context);
            }*/
            else return true;
        } else if (screen.equals("Proxy")) {
            if (indexValue != 0) {
                relation = Relationship[indexValue - 1];
            }
            otherRelation = txtOtherRelation.getText().toString();
            int indexValues = spinnerProxy.getSelectedItemPosition();
            if (indexValues != 0) {
                proxy = proxyType[indexValues - 1];
            }
            if (proxy.equals("Primary Health Care Proxy Agent")) {
                prox = 0;
            } else if (proxy.equals("Successor - Health Care Proxy Agent")) {
                prox = 1;
            }
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                DialogManager.showAlert("Please Enter Name", context);
            }/* else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                showAlert("Please Enter email", context);
            } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                showAlert("Please enter valid email", context);
            }
            else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", context);
            }*/ else if (mobile.length() != 0 && mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                DialogManager.showAlert("Mobile number should be 10 digits", context);
            } else return true;
           /* else if (phone.equals("")) {
                txtHomePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", context);
            } else if (phone.length() < 10) {
                txtHomePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }
            else if (workphone.equals("")) {
                txtWorkPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", context);
            }else if (workphone.length() < 10) {
                txtWorkPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }
            else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", context);
            }*/
        } else if (screen.equals("Physician")) {
            //  if (!fromDevice) {
            name = txtDoctorName.getText().toString();
            mobile = txtDoctorOfficePhone.getText().toString();
            phone = txtDoctorHourOfficePhone.getText().toString();
            workphone = txtDoctorOtherPhone.getText().toString();
            address = txtDoctorAddress.getText().toString();
            //  }

            fax = txtDoctorFax.getText().toString();

            website = txtDoctorWebsite.getText().toString();
            lastseen = txtDoctorLastSeen.getText().toString();
            locator = txtDoctorLocator.getText().toString();
            //  fax=txtDoctorFax.getText().toString();
            /*int indexValuex = spinner.getSelectedItemPosition();
            if (indexValuex != 0) {
                speciality = healthSpeciality[indexValuex - 1];
            }*/
            speciality = txtSpecialty.getText().toString();
            if (speciality.equals("Other")) {
                tilOtherCategoryDoctor.setVisibility(View.VISIBLE);
            } else {
                tilOtherCategoryDoctor.setVisibility(View.GONE);
            }
            otherDoctor = txtOtherCategoryDoctor.getText().toString();
            practice_name = txtPracticeName.getText().toString();
            network = txtNetwork.getText().toString();
            affil = txtAffiliation.getText().toString();
            note = txtDoctorNote.getText().toString();

            if (name.equals("")) {
                txtDoctorName.setError("Please Doctor Enter Name");
                DialogManager.showAlert("Please Enter Doctor Name", context);
            }
             /* if (mobile.equals("")) {
                  txtDoctorOfficePhone.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", context);
            }*/
            else if (mobile.length() != 0 && mobile.length() < 10) {
                txtDoctorOfficePhone.setError("Mobile number should be 10 digits");
                DialogManager.showAlert("Mobile number should be 10 digits", context);
            } else return true;
           /* else if (phone.equals("")) {
                  txtDoctorHourOfficePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", context);
            } else if (phone.length() < 10) {
                  txtDoctorHourOfficePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }
            else if (workphone.equals("")) {
                  txtDoctorOtherPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", context);
            }else if (workphone.length() < 10) {
                  txtDoctorOtherPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }*/

        } else if (screen.equals("Pharmacy")) {
            //  if (!fromDevice) {
            name = txtPharmacyName.getText().toString();
            phone = txtPharmacyPhone.getText().toString();
            address = txtPharmacyAddress.getText().toString();
            // }

            fax = txtPharmacyFax.getText().toString();

            website = txtPharmacyWebsite.getText().toString();
            locator = txtPharmacyLocator.getText().toString();
            note = txtPharmacyNote.getText().toString();
            if (name.equals("")) {
                txtPharmacyName.setError("Please Enter Name");
                DialogManager.showAlert("Please Enter Name", context);
            }
           /* if (phone.equals("")) {
                txtPharmacyPhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", context);
            } else if (phone.length() < 10) {
                txtPharmacyPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }*/
            else return true;
        } else if (screen.equals("Aides")) {
            // if (!fromDevice) {
            name = txtAideCompName.getText().toString();
            mobile = txtAideOfficePhone.getText().toString();
            phone = txtHourOfficePhone.getText().toString();
            workphone = txtOtherPhone.getText().toString();
            email = txtAideEmail.getText().toString();
            address = txtAideAddress.getText().toString();
            // }
            website = txtAideWebsite.getText().toString();
            note = txtAideNote.getText().toString();

            fax = txtAideFax.getText().toString();

            if (name.equals("")) {
                txtAideCompName.setError("Please Enter Name Of Company");
                DialogManager.showAlert("Please Enter Name Of Company", context);
            }
         /*   if (mobile.equals("")) {
                txtAideOfficePhone.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", context);
            } */
            else if (mobile.length() != 0 && mobile.length() < 10) {
                txtAideOfficePhone.setError("Mobile number should be 10 digits");
                DialogManager.showAlert("Mobile number should be 10 digits", context);
            } else return true;
           /* else if (phone.equals("")) {
                txtHourOfficePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", context);
            } else if (phone.length() < 10) {
                txtHourOfficePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }
            else if (workphone.equals("")) {
                txtOtherPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", context);
            }else if (workphone.length() < 10) {
                txtOtherPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }*/

        } else if (screen.equals("Hospital")) {
            //  if (!fromDevice) {
            name = txtFNameHospital.getText().toString();
            mobile = txtHospitalOfficePhone.getText().toString();
            workphone = txtHospitalOtherPhone.getText().toString();
            address = txtHospitalAddress.getText().toString();
            //  }

            email = "";
            location = txtHospitalLocation.getText().toString();

            phone = "";

            fax = txtHospitalFax.getText().toString();

            website = txtHospitalWebsite.getText().toString();
            lastseen = txtHospitalLastSeen.getText().toString();
            locator = txtHospitalLocator.getText().toString();
            otherCategory = txtOtherCategoryHospital.getText().toString();
           /* int indexValuex = spinnerHospital.getSelectedItemPosition();
            String sources = preferences.getString(PrefConstants.SOURCE);
            if (indexValuex != 0) {
                speciality = HospitalType[indexValuex - 1];
            }*/
            speciality = txtHCategory.getText().toString();
            if (speciality.equals("Other")) {
                tilOtherCategoryHospital.setVisibility(View.VISIBLE);
            } else {
                tilOtherCategoryHospital.setVisibility(View.GONE);
            }

            practice_name = txtHospitalPracticeName.getText().toString();
            note = txtHospitalNote.getText().toString();
            if (name.equals("")) {
                txtFNameHospital.setError("Please Enter Name");
                DialogManager.showAlert("Please Enter Name", context);
            }

         /*   if (mobile.equals("")) {
                txtFinanceOfficePhone.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", context);
            } */
            else if (mobile.length() != 0 && mobile.length() < 10) {
                txtFinanceOfficePhone.setError("Office number should be 10 digits");
                DialogManager.showAlert("Office number should be 10 digits", context);
            } else return true;
            /*else if (phone.equals("")) {
                txtFinanceMobilePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", context);
            } else if (phone.length() < 10) {
                txtFinanceMobilePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }
            else if (workphone.equals("")) {
                txtFinanceOtherPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", context);
            }else if (workphone.length() < 10) {
                txtFinanceOtherPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }*/


        } else if (screen.equals("Finance")) {
            //  if (!fromDevice) {
            name = txtFName.getText().toString();
            email = txtFinanceEmail.getText().toString();
            mobile = txtFinanceOfficePhone.getText().toString();
            phone = txtFinanceMobilePhone.getText().toString();
            workphone = txtFinanceOtherPhone.getText().toString();
            address = txtFinanceAddress.getText().toString();
            //  }
            contactName = txtContactName.getText().toString();
            location = txtFinanceLocation.getText().toString();

            fax = txtFinanceFax.getText().toString();

            website = txtFinanceWebsite.getText().toString();
            lastseen = txtLastSeen.getText().toString();
            otherCategory = txtOtherCategory.getText().toString();
            int indexValuex = spinnerFinance.getSelectedItemPosition();
            String sources = preferences.getString(PrefConstants.SOURCE);
            if (sources.equals("Finance") || sources.equals("FinanceViewData") || sources.equals("FinanceData")) {
                /*if (indexValuex != 0) {
                    speciality = financeType[indexValuex - 1];
                }*/
                speciality = txtFCategory.getText().toString();
                if (speciality.equals("Other")) {
                    tilOtherCategory.setVisibility(View.VISIBLE);
                } else {
                    tilOtherCategory.setVisibility(View.GONE);
                    txtOtherCategory.setText("");
                }
            } else {
                if (indexValuex != 0) {
                    speciality = HospitalType[indexValuex - 1];
                }
            }

            practice_name = txtFinancePracticeName.getText().toString();
            note = txtFinanceNote.getText().toString();
            if (name.equals("")) {
                txtFName.setError("Please Enter Name");
                DialogManager.showAlert("Please Enter Name", context);
            } else if (!email.equals("") && !email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtFinanceEmail.setError("Please enter valid email");
                DialogManager.showAlert("Please enter valid email", context);
            }

         /*   if (mobile.equals("")) {
                txtFinanceOfficePhone.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", context);
            } */
            else if (mobile.length() != 0 && mobile.length() < 10) {
                txtFinanceOfficePhone.setError("Mobile number should be 10 digits");
                DialogManager.showAlert("Mobile number should be 10 digits", context);
            } else return true;
            /*else if (phone.equals("")) {
                txtFinanceMobilePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", context);
            } else if (phone.length() < 10) {
                txtFinanceMobilePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }
            else if (workphone.equals("")) {
                txtFinanceOtherPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", context);
            }else if (workphone.length() < 10) {
                txtFinanceOtherPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", context);
            }*/


        } else if (screen.equals("Insurance")) {
            // if (!fromDevice) {
            name = txtInsuaranceName.getText().toString();
            phone = txtInsuarancePhone.getText().toString();
            address = txtAddress.getText().toString();
            email = txtInsuaranceEmail.getText().toString();
            aentEmail = txtAentEmail.getText().toString();
            aentPhone = txtAentPhone.getText().toString();
            // }
            fax = txtInsuaranceFax.getText().toString();

            website = txtWebsite.getText().toString();
            note = txtInsuaranceNote.getText().toString();
            member = txtId.getText().toString();
            group = txtGroup.getText().toString();
            subscriber = txtSubscribe.getText().toString();
           /* int indexValuex = spinnerInsuarance.getSelectedItemPosition();
            if (indexValuex != 0) {
                type = insuaranceType[indexValuex - 1];
            }*/
            type = txtInsuType.getText().toString();
            agent = txtAgent.getText().toString();
            otherInsurance = txtOtherInsurance.getText().toString();
            if (name.equals("")) {
                txtInsuaranceName.setError("Please Enter Name of Insurance Company");
                DialogManager.showAlert("Please Enter Name of Insurance Company", context);

            } else if (!email.equals("") && !email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtInsuaranceEmail.setError("Please enter valid email");
                DialogManager.showAlert("Please enter valid email", context);
            } else if (type.equals("")) {
                txtInsuType.setError("Please Select Type of Insurance");
                DialogManager.showAlert("Please Select Type of Insurance", context);
            } else if (mobile.length() != 0 && mobile.length() < 10) {
                txtInsuarancePhone.setError("Mobile number should be 10 digits");
                DialogManager.showAlert("Mobile number should be 10 digits", context);
            } else return true;
        }
            /* (phone.equals("")) {
                txtInsuarancePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", context);
            } else if (phone.length() < 10) {
            txtInsuarancePhone.setError("Mobile number should be 10 digits");
            showAlert("Mobile number should be 10 digits", context);
        }*/


        return false;
    }

    private void dispatchTakePictureIntent(int resultCameraImage, String profile) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {

                photoFile = createImageFile(profile);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
               /* Uri photoURI = FileProvider.getUriForFile(this,
                        "com.infidigi.fotobuddies.fileprovider",
                        photoFile);*/
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile.getAbsolutePath());
                startActivityForResult(takePictureIntent, resultCameraImage);
            }
        }
    }

    private File createImageFile(String profile) throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if (profile.equals("Profile")) {
            imagepath = image.getAbsolutePath();
        } else if (profile.equals("Card")) {
            cardPath = image.getAbsolutePath();
        }
        return image;
    }

    public void postCommonDialog() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = rootview.findViewById(R.id.imgProfile);
        ImageView profileCard = rootview.findViewById(R.id.imgCard);
        if (requestCode == REQUEST_CARD && null != data) {
            rlCard.setVisibility(View.GONE);
            imgCard.setVisibility(View.GONE);
            flFront.setVisibility(View.VISIBLE);
            cardPath = "";
            //  photoCard = null;
        } else if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                int nh = (int) (selectedImage.getHeight() * (512.0 / selectedImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
                imgProfile.setImageBitmap(scaled);
//                ProfileMap = selectedImage;
                ProfileMap = scaled;
                imgEdit.setVisibility(View.VISIBLE);
                //  storeImage(ProfileMap, "Profile");
                //  profileImage.setImageBitmap(selectedImage);
//                imageLoaderProfile.displayImage(String.valueOf(imageUri), imgProfile, displayImageOptionsProfile);
                //   storeImage(selectedImage,"Profile");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE) {

            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), imageUriProfile);
                String imageurl = getRealPathFromURI(imageUriProfile);
                Bitmap selectedImage = imageOreintationValidator(thumbnail, imageurl);
                int nh = (int) (selectedImage.getHeight() * (512.0 / selectedImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
                imgProfile.setImageBitmap(scaled);
                ProfileMap = scaled;
                imgEdit.setVisibility(View.VISIBLE);
                //new comment
                // storeImage(scaled, "Profile");

            /*    imageLoaderProfile.displayImage(String.valueOf(imageUriProfile), imgProfile, displayImageOptionsProfile);

                // profileImage.setImageBitmap(bitmap);
                //  storeImage(selectedImage,"Profile");*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_SELECT_PHOTO_CARD && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                int nh = (int) (selectedImage.getHeight() * (512.0 / selectedImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
                imgCard.setImageBitmap(scaled);
                imgEditCard.setVisibility(View.VISIBLE);
                rlCard.setVisibility(View.VISIBLE);
                imgCard.setVisibility(View.VISIBLE);
                flFront.setVisibility(View.GONE);
                CardMap = scaled;
                //new comment
                // storeImage(scaled, "Card");

              /*  int nh = (int) (selectedImage.getHeight() * (512.0 / selectedImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
                imgCard.setImageBitmap(scaled);
                rlCard.setVisibility(View.VISIBLE);
                imgCard.setVisibility(View.VISIBLE);
                 flFront.setVisibility(View.GONE);
                CardMap = scaled;
                storeImage(scaled, "Card");
//                imageLoaderCard.displayImage(String.valueOf(imageUri), imgCard, displayImageOptionsCard);
                // profileCard.setImageBitmap(selectedImage);
                rlCard.setVisibility(View.VISIBLE);
                imgCard.setVisibility(View.VISIBLE);
                 flFront.setVisibility(View.GONE);*/
//                isOnActivityResult = true;
//                cardImgPath = String.valueOf(imageUri);
                // storeImage(selectedImage,"Card");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE_CARD) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), imageUriCard);

                String imageurl = getRealPathFromURI(imageUriCard);
                Bitmap selectedImage = imageOreintationValidator(thumbnail, imageurl);
                //new
                // profileCard.setImageBitmap(selectedImage);
                imgCard.setImageBitmap(selectedImage);
                imgEditCard.setVisibility(View.VISIBLE);
                //  imageLoaderCard.displayImage(String.valueOf(imageUriCard), imgCard, displayImageOptionsCard);

                rlCard.setVisibility(View.VISIBLE);
                imgCard.setVisibility(View.VISIBLE);
                flFront.setVisibility(View.GONE);
                CardMap = selectedImage;
                //new
                isOnActivityResult = true;
                cardImgPath = String.valueOf(imageUriCard);
                // storeImage(selectedImage,"Card");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_RELATION && data != null) {
            relation = data.getStringExtra("Relation");
            txtRelation.setText(relation);
            if (relation.equals("Other")) {
                tilOtherRelation.setVisibility(View.VISIBLE);
                txtOtherRelation.setVisibility(View.VISIBLE);
            } else {
                tilOtherRelation.setVisibility(View.GONE);
                txtOtherRelation.setVisibility(View.GONE);
            }
        } else if (requestCode == RESULT_PRIORITY && data != null) {
            priority = data.getStringExtra("Priority");
            txtPriority.setText(priority);

        } else if (requestCode == RESULT_SPECIALTY && data != null) {
            if (source.equalsIgnoreCase("PhysicianData") || source.equalsIgnoreCase("Physician")) {
                speciality = data.getStringExtra("Physician");
            } else {
                speciality = data.getStringExtra("Specialty");
            }

            txtSpecialty.setText(speciality);
            if (speciality.equals("Other")) {
                //tilOtherRelation.setVisibility(View.VISIBLE);
                tilOtherCategoryDoctor.setVisibility(View.VISIBLE);

            } else {
                //tilOtherRelation.setVisibility(View.GONE);
                tilOtherCategoryDoctor.setVisibility(View.GONE);
                txtOtherCategoryDoctor.setText("");
            }
        } else if (requestCode == RESULT_SPECIALTY_NETWORK && data != null) {
            if (source.equalsIgnoreCase("PhysicianData") || source.equalsIgnoreCase("Physician")) {
                network = data.getStringExtra("PhysicianNetwork");
            } else {
                network = data.getStringExtra("SpecialtyNetwork");
            }

            txtNetwork.setText(network);

        } else if (requestCode == RESULT_HOSPITAL_NETWORK && data != null) {
            location = data.getStringExtra("HospitalNetwork");
            txtHospitalLocation.setText(location);
        } else if (requestCode == RESULT_CATEGORY && data != null) {
            speciality = data.getStringExtra("Category");
            txtHCategory.setText(speciality);
            if (speciality.equals("Other")) {
                tilOtherCategoryHospital.setVisibility(View.VISIBLE);
            } else {
                tilOtherCategoryHospital.setVisibility(View.GONE);
                txtOtherCategoryHospital.setText("");
            }
        } else if (requestCode == RESULT_FINANCECAT && data != null) {
            speciality = data.getStringExtra("Category");
            txtFCategory.setText(speciality);
            if (speciality.equals("Other")) {
                tilOtherCategory.setVisibility(View.VISIBLE);
            } else {
                tilOtherCategory.setVisibility(View.GONE);
                txtOtherCategory.setText("");
            }
        } else if (requestCode == RESULT_INSURANCE && data != null) {
            type = data.getStringExtra("Category");
            txtInsuType.setText(type);
            if (type.equals("Other")) {
                tilOtherInsurance.setVisibility(View.VISIBLE);
            } else {
                tilOtherInsurance.setVisibility(View.GONE);
                txtOtherInsurance.setText("");
            }
        } else if (requestCode == RESULT_TYPE && data != null) {
            String type = data.getStringExtra("Relation");
            txtType = llAddPhone.findViewById(R.id.txtType);
            txtType.setText(type);

        }

    }

    private String getRealPathFromURI(Uri imageUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(imageUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private Bitmap imageOreintationValidator(Bitmap bitmap, String path) {

        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }

    public void callRelation(String relationship) {
        relation = relationship;
    }

    private void storeImage(Bitmap selectedImage, String profile) {

        FileOutputStream outStream = null;
        File file = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            if (selectedImage != null) {
                if (profile.equals("Profile")) {
                    imagepath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + imagepath);
                } else if (profile.equals("Card")) {
                    cardPath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + cardPath);
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void storeTempImage(Bitmap selectedImage, String profile) {
        FileOutputStream outStream1 = null;
        File files = new File(Environment.getExternalStorageDirectory() + "/MYLO/temp/");
        if (!files.exists()) {
            files.mkdirs();
        }

        try {
            if (selectedImage != null) {
                if (profile.equals("Profile")) {
                    imagepath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream1 = new FileOutputStream(Environment.getExternalStorageDirectory() + "/MYLO/temp/" + imagepath);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                    byte[] byteArray = stream.toByteArray();
                    outStream1.write(byteArray);
                    outStream1.close();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void storeProfileImage(Bitmap selectedImage, String profile) {

        FileOutputStream outStream = null;
        FileOutputStream outStream1 = null;
        File file = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
        File files = new File(Environment.getExternalStorageDirectory() + "/MYLO/Master/");
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        if (!files.exists()) {
            files.mkdirs();
        }

        try {
            if (selectedImage != null) {
                if (profile.equals("Profile")) {
                    imagepath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + imagepath);
                    outStream1 = new FileOutputStream(Environment.getExternalStorageDirectory() + "/MYLO/Master/" + imagepath);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                    byte[] byteArray = stream.toByteArray();
                    outStream1.write(byteArray);
                    outStream1.close();
                } else if (profile.equals("Card")) {
                    cardPath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + cardPath);
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void deleteEmergency(final Emergency item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = MyConnectionsQuery.deleteRecord(item.getId());
                if (flag == true) {
                    //   Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    // getData();
                    //setListData();
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();
    }

    public void deleteHospital(final Hospital item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = HospitalHealthQuery.deleteRecord(item.getId());
                if (flag == true) {
                    //     Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();

    }

    public void deletePharmacy(final Pharmacy item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = PharmacyQuery.deleteRecord(item.getId());
                if (flag == true) {
                    // Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();

    }

    public void deleteFinance(final Finance item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = FinanceQuery.deleteRecord(item.getId());
                if (flag == true) {
                    //  Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();

    }

    public void deleteInsurance(final Insurance item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = InsuranceQuery.deleteRecord(item.getId());
                if (flag == true) {
                    // Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();

    }

    public void backFunction(String source) {

        switch (source) {
            case "Connection":
                getValues("Connection");
                if (name.equals("") && address.equals("") &&
                        email.equals("") &&
                        relation.equals("")&& otherRelation.equals("")&&
                        imagepath.equals("")&&cardPath.equals("")&&
                        has_card.equals("NO")&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "Emergency":
                getValues("Emergency");

                if (name.equals("") && address.equals("") &&
                        email.equals("") && prior==5 &&
                        relation.equals("")&& otherRelation.equals("")&&note.equals("")&&
                        imagepath.equals("")&&cardPath.equals("")&&
                        has_card.equals("NO")&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "EmergencyUpdate":
                getValues("Emergency");
                if (name.equals(rel.getName()) && address.equals(rel.getAddress()) &&
                        email.equals(rel.getEmail()) && prior==rel.getIsPrimary() &&
                        relation.equals(rel.getRelationType())&& otherRelation.equals(rel.getOtherRelation())&&note.equals(rel.getNote())&&
                        imagepath.equals(rel.getPhoto())&&cardPath.equals(rel.getPhotoCard())&&
                        has_card.equals(rel.getHas_card())&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "Physician":
                getValues("Physician");

                if (name.equals("") && address.equals("") &&
                        website.equals("") && lastseen.equals("") &&
                        locator.equals("")&&speciality.equals("")&&
                        practice_name.equals("")&&network.equals("")&&
                        otherDoctor.equals("")&&affil.equals("")&&
                        note.equals("")&&affil.equals("")&&
                        imagepath.equals("")&&cardPath.equals("")&&
                        has_card.equals("NO")&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "Speciality":
                getValues("Physician");

                if (name.equals("") && address.equals("") &&
                        website.equals("") && lastseen.equals("") &&
                        locator.equals("")&&speciality.equals("")&&
                        practice_name.equals("")&&network.equals("")&&
                        otherDoctor.equals("")&&affil.equals("")&&
                        note.equals("")&&affil.equals("")&&
                        imagepath.equals("")&&cardPath.equals("")&&
                        has_card.equals("NO")&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "SpecialistData":
                getValues("Physician");
                if (name.equals(specialist.getName()) && address.equals(specialist.getAddress()) &&
                        website.equals(specialist.getWebsite()) && lastseen.equals(specialist.getLastseen()) &&
                        locator.equals(specialist.getLocator())&&speciality.equals(specialist.getType())&&
                        practice_name.equals(specialist.getPracticeName())&&network.equals(specialist.getNetwork())&&
                        otherDoctor.equals(specialist.getOtherType())&&affil.equals(specialist.getHospAffiliation())&&
                        note.equals(specialist.getNote())&&
                        imagepath.equals(specialist.getPhoto())&&cardPath.equals(specialist.getPhotoCard())&&
                        has_card.equals(specialist.getHas_card())&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "PhysicianData":
                getValues("Physician");
                if (name.equals(specialist.getName()) && address.equals(specialist.getAddress()) &&
                        website.equals(specialist.getWebsite()) && lastseen.equals(specialist.getLastseen()) &&
                        locator.equals(specialist.getLocator())&&speciality.equals(specialist.getType())&&
                        practice_name.equals(specialist.getPracticeName())&&network.equals(specialist.getNetwork())&&
                        otherDoctor.equals(specialist.getOtherType())&&affil.equals(specialist.getHospAffiliation())&&
                        note.equals(specialist.getNote())&&
                        imagepath.equals(specialist.getPhoto())&&cardPath.equals(specialist.getPhotoCard())&&
                        has_card.equals(specialist.getHas_card())&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "Pharmacy":
                getValues("Pharmacy");

                if (name.equals("") && address.equals("") &&
                        website.equals("") && locator.equals("")&&
                        note.equals("")&&
                        imagepath.equals("")&&cardPath.equals("")&&
                        has_card.equals("NO")&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }

                break;
            case "PharmacyData":
                getValues("Pharmacy");
                if (name.equals(pharmacy.getName()) && address.equals(pharmacy.getAddress()) &&
                        website.equals(pharmacy.getWebsite()) && locator.equals(pharmacy.getLocator())&&
                        note.equals(pharmacy.getNote())&&
                        imagepath.equals(pharmacy.getPhoto())&&cardPath.equals(pharmacy.getPhotoCard())&&
                        has_card.equals(pharmacy.getHas_card())&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "Hospital":
                getValues("Hospital");
                if (name.equals("") && address.equals("") &&
                        website.equals("") && lastseen.equals("") &&
                        locator.equals("")&&speciality.equals("")&&
                        practice_name.equals("")&&location.equals("")&&
                        otherCategory.equals("")&& note.equals("")&&
                        imagepath.equals("")&&cardPath.equals("")&&
                        has_card.equals("NO")&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "HospitalData":
                getValues("Hospital");
                if (name.equals(hospital.getName()) && address.equals(hospital.getAddress()) &&
                        website.equals(hospital.getWebsite()) && lastseen.equals(hospital.getLastseen()) &&
                        locator.equals(hospital.getLocator())&&speciality.equals(hospital.getCategory())&&
                        practice_name.equals(hospital.getPracticeName())&&location.equals(hospital.getLocation())&&
                        otherCategory.equals(hospital.getOtherCategory())&& note.equals(hospital.getNote())&&
                        imagepath.equals(hospital.getPhoto())&&cardPath.equals(hospital.getPhotoCard())&&
                        has_card.equals(hospital.getHas_card())&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "Finance":
                getValues("Finance");
                if (name.equals("") && address.equals("") &&
                        website.equals("") && lastseen.equals("") &&
                        email.equals("")&&speciality.equals("")&&
                        contactName.equals("")&&
                        otherCategory.equals("")&& note.equals("")&&
                        imagepath.equals("")&&cardPath.equals("")&&
                        has_card.equals("NO")&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "FinanceData":
                getValues("Finance");
                if (name.equals(finance.getName()) && address.equals(finance.getAddress()) &&
                        website.equals(finance.getWebsite()) && lastseen.equals(finance.getLastseen()) &&
                        email.equals(finance.getEmail())&&speciality.equals(finance.getCategory())&&
                        contactName.equals(finance.getContactName())&&
                        otherCategory.equals(finance.getOtherCategory())&& note.equals(finance.getNote())&&
                        imagepath.equals(finance.getPhoto())&&cardPath.equals(finance.getPhotoCard())&&
                        has_card.equals(finance.getHas_card())&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "Insurance":
                getValues("Insurance");

                if (name.equals("") &&
                        website.equals("") && email.equals("") &&
                        aentEmail.equals("")&&aentPhone.equals("")&&
                        agent.equals("")&&member.equals("")&&subscriber.equals("")&&
                        otherInsurance.equals("")&&type.equals("")&&
                        note.equals("")&&group.equals("")&&
                        imagepath.equals("")&&cardPath.equals("")&&
                        has_card.equals("NO")&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;

            case "InsuranceData":
                getValues("Insurance");
                if (name.equals(insurance.getName()) &&
                        website.equals(insurance.getWebsite()) && email.equals(insurance.getEmail()) &&
                        aentEmail.equals(insurance.getAgent_email())&&aentPhone.equals(insurance.getAgentPhone())&&
                        agent.equals(insurance.getAgent())&&member.equals(insurance.getMember())&&subscriber.equals(insurance.getSubscriber())&&
                        otherInsurance.equals(insurance.getOtherInsurance())&&type.equals(insurance.getType())&&
                        note.equals(insurance.getNote())&&group.equals(insurance.getGroup())&&
                        imagepath.equals(insurance.getPhoto())&&cardPath.equals(insurance.getPhotoCard())&&
                        has_card.equals(insurance.getHas_card())&&backflap==false) {
                    getActivity().finish();
                } else {
                    showSaveAlert();
                }
                break;
        }


    }

    private void showSaveAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Save");
        alert.setMessage("Do you want to save information?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                txtsave.performClick();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        alert.show();
    }

    private void getValues(String screen) {
        for (int i = 0; i < phonelist.size(); i++) {
            if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                phonelist.remove(phonelist.get(i));
            }
        }

        if (!screen.equals("Connection")) {
            storeImage(ProfileMap, "Profile");
            storeImage(CardMap, "Card");
        }
          name = txtName.getText().toString().trim();
          email = txtEmail.getText().toString().trim();
          address = txtAddress.getText().toString().trim();


            if (screen.equals("Connection")) {

                relation = txtRelation.getText().toString();
                otherRelation = txtOtherRelation.getText().toString();

            } else if (screen.equals("Emergency")) {

                relation = txtRelation.getText().toString();
                otherRelation = txtOtherRelation.getText().toString();
                note = txtEmergencyNote.getText().toString().trim();

                if (priority.equals("Primary Emergency Contact")) {
                    prior = 0;
                } else if (priority.equals("Primary Health Care Proxy Agent")) {
                    prior = 1;
                } else if (priority.equals("Secondary Emergency Contact")) {
                    prior = 2;
                } else if (priority.equals("Secondary Health Care Proxy Agent")) {
                    prior = 3;
                } else if (priority.equals("Primary Emergency Contact and Health Care Proxy Agent")) {

                    prior = 4;
                } else {
                    prior = 5;
                }
            }
               else if (screen.equals("Physician")) {
                //  if (!fromDevice) {
                name = txtDoctorName.getText().toString();
                address = txtDoctorAddress.getText().toString();
                //  }

                fax = txtDoctorFax.getText().toString();

                website = txtDoctorWebsite.getText().toString();
                lastseen = txtDoctorLastSeen.getText().toString();
                locator = txtDoctorLocator.getText().toString();

                speciality = txtSpecialty.getText().toString();
                if (speciality.equals("Other")) {
                    tilOtherCategoryDoctor.setVisibility(View.VISIBLE);
                } else {
                    tilOtherCategoryDoctor.setVisibility(View.GONE);
                }
                otherDoctor = txtOtherCategoryDoctor.getText().toString();
                practice_name = txtPracticeName.getText().toString();
                network = txtNetwork.getText().toString();
                affil = txtAffiliation.getText().toString();
                note = txtDoctorNote.getText().toString();

            }else if (screen.equals("Pharmacy")) {
                name = txtPharmacyName.getText().toString();
                address = txtPharmacyAddress.getText().toString();
                website = txtPharmacyWebsite.getText().toString();
                locator = txtPharmacyLocator.getText().toString();
                note = txtPharmacyNote.getText().toString();
            }  else if (screen.equals("Hospital")) {

                name = txtFNameHospital.getText().toString();
                mobile = txtHospitalOfficePhone.getText().toString();
                workphone = txtHospitalOtherPhone.getText().toString();
                address = txtHospitalAddress.getText().toString();
                email = "";
                location = txtHospitalLocation.getText().toString();
                phone = "";
                fax = txtHospitalFax.getText().toString();
                website = txtHospitalWebsite.getText().toString();
                lastseen = txtHospitalLastSeen.getText().toString();
                locator = txtHospitalLocator.getText().toString();
                otherCategory = txtOtherCategoryHospital.getText().toString();
                speciality = txtHCategory.getText().toString();
                if (speciality.equals("Other")) {
                    tilOtherCategoryHospital.setVisibility(View.VISIBLE);
                } else {
                    tilOtherCategoryHospital.setVisibility(View.GONE);
                }

                practice_name = txtHospitalPracticeName.getText().toString();
                note = txtHospitalNote.getText().toString();

            } else if (screen.equals("Finance")) {

                name = txtFName.getText().toString();
                email = txtFinanceEmail.getText().toString();
                address = txtFinanceAddress.getText().toString();
                contactName = txtContactName.getText().toString();
                website = txtFinanceWebsite.getText().toString();
                lastseen = txtLastSeen.getText().toString();
                otherCategory = txtOtherCategory.getText().toString();
                int indexValuex = spinnerFinance.getSelectedItemPosition();
                String sources = preferences.getString(PrefConstants.SOURCE);
                if (sources.equals("Finance") || sources.equals("FinanceViewData") || sources.equals("FinanceData")) {

                    speciality = txtFCategory.getText().toString();
                    if (speciality.equals("Other")) {
                        tilOtherCategory.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherCategory.setVisibility(View.GONE);
                        txtOtherCategory.setText("");
                    }
                } else {
                    if (indexValuex != 0) {
                        speciality = HospitalType[indexValuex - 1];
                    }
                }
                note = txtFinanceNote.getText().toString();

            } else if (screen.equals("Insurance")) {

                name = txtInsuaranceName.getText().toString();
                phone = txtInsuarancePhone.getText().toString();
                address = txtAddress.getText().toString();
                email = txtInsuaranceEmail.getText().toString();
                aentEmail = txtAentEmail.getText().toString();
                aentPhone = txtAentPhone.getText().toString();
                fax = txtInsuaranceFax.getText().toString();
                website = txtWebsite.getText().toString();
                note = txtInsuaranceNote.getText().toString();
                member = txtId.getText().toString();
                group = txtGroup.getText().toString();
                subscriber = txtSubscribe.getText().toString();
                type = txtInsuType.getText().toString();
                agent = txtAgent.getText().toString();
                otherInsurance = txtOtherInsurance.getText().toString();

            }

    }
}
