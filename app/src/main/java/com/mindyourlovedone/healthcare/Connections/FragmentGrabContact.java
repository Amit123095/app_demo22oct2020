package com.mindyourlovedone.healthcare.Connections;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.model.Contact;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by varsha on 8/28/2017.
 */

/**
 * Class: FragmentGrabContact
 * Screen: Contacts list Screen
 * A class that manages an conatct numbers from device to add as contact
 * extends Fragment
 * implements OnclickListener for onclick event on views
 */
public class FragmentGrabContact extends Fragment implements View.OnClickListener {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    View rootview;
    ListView lvContact;
    EditText etSearch;
    ContactAdapter contactAdapter;
    ImageView imgRefresh;
    ArrayList<Contact> offcontactList;
    DBHelper dbHelper;
    RelativeLayout rlSearch;
    TextView txtTitle, txtsave;
    ImageView imgBack;

    /**
     * @param inflater           LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view. This value may be null.
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_grab_contact, null);
        //Initialize database, get primary data and set data
        initComponent();
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();

        //Fetc contact records from database
        getOfflineContacts();

        //Set contacts in list from database
        setOfflineContacts();
        if (offcontactList.size() == 0) {
            showContacts();
        }
        return rootview;
    }
    /**
     * Function: Initialize database, Preferences
     */
    private void initComponent() {
        dbHelper = new DBHelper(getActivity(), "MASTER");
        ContactTableQuery s = new ContactTableQuery(getActivity(), dbHelper);
    }

    /**
     * Function: Fetc contact records from database
     */
    private void setOfflineContacts() {
        if (offcontactList.size() != 0) {
            rlSearch.setVisibility(View.VISIBLE);
            contactAdapter = new ContactAdapter(getActivity(), offcontactList);
            lvContact.setAdapter(contactAdapter);
        }
    }
    /**
     * Function: Initialize database, Preferences
     */
    private void getOfflineContacts() {
        offcontactList = new ArrayList<>();
        offcontactList = ContactTableQuery.fetchAllContactDetails();
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        imgRefresh.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtsave = getActivity().findViewById(R.id.txtsave);
        txtsave.setVisibility(View.GONE);
        txtTitle.setText("Select From Contacts");
        lvContact = rootview.findViewById(R.id.lvContact);
        etSearch = rootview.findViewById(R.id.etSearch);
        imgRefresh = getActivity().findViewById(R.id.imgRefresh);
        rlSearch = rootview.findViewById(R.id.rlSearch);
        // imgRefresh.setVisibility(View.GONE);
        rlSearch.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                getActivity().finish();
//                ((GrabConnectionActivity) getActivity()).finish();
            }
        });
        switch (GrabConnectionActivity.source) {
            case "Connection":
                rlSearch.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                break;

            case "Pharmacy":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "PharmacyData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "PharmacyDataView":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Proxy":
                rlSearch.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "ProxyUpdate":
                rlSearch.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "ProxyUpdateView":
                rlSearch.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "Emergency":
                rlSearch.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                break;

            case "EmergencyUpdate":
                rlSearch.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                break;

            case "EmergencyView":
                rlSearch.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                break;

            case "Speciality":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Physician":
                rlSearch.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                break;

            case "PhysicianData":
                rlSearch.setBackgroundResource(R.color.colorEmerMainGreen);
                break;

            case "PhysicianViewData":
                rlSearch.setBackgroundResource(R.color.colorEmerMainGreen);
                break;
            case "SpecialistData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "SpecialistViewData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Insurance":
                rlSearch.setBackgroundResource(R.color.colorInsuaranceSkyBlue);
                break;

            case "InsuranceData":
                rlSearch.setBackgroundResource(R.color.colorInsuaranceSkyBlue);
                break;

            case "InsuranceViewData":
                rlSearch.setBackgroundResource(R.color.colorInsuaranceSkyBlue);
                break;

            case "Aides":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "AidesData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "AidesViewData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Finance":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Hospital":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "PrescriptionInfo":
                rlSearch.setBackgroundResource(R.color.colorPrescriptionGray);
                break;

            case "HospitalData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;
            case "HospitalViewData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "FinanceData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "FinanceViewData":
                rlSearch.setBackgroundResource(R.color.colorSpecialityYellow);
                break;
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    /**
     * Function: Fetc contacts details and display
     */
    private void showContacts() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            getData();

        }
    }

    /**
     * Function: Fetch all  contacts
     */
    private void getData() {
        new LoadContacts().execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.READ_CONTACTS)
                            == PackageManager.PERMISSION_GRANTED) {
                        showContacts();
                    }
                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgRefresh) {
            showContacts();
        }
    }

    /**
     * Function: Save contacts from device to apps local database table
     * @param contactList
     */
    private void saveToTable(ArrayList<Contact> contactList) {
        ContactTableQuery.deleteContactData();
        for (int i = 0; i < contactList.size(); i++) {
            Contact contect = contactList.get(i);
            boolean flag = ContactTableQuery.insertContactData(contect.getId(), contect.getName(), contect.getPhone(), contect.getEmail(), contect.getImage(), contect.getAddress(), contect.getHomePhone(), contect.getWorkPhone());
        }
        getOfflineContacts();
        setOfflineContacts();
        contactAdapter.getFilter().filter(etSearch.getText().toString());
    }

    /**
     * Class: LoadContacts
     * Fetc device saved contacts and save into app database and display in list
     */
    class LoadContacts extends AsyncTask<Object, Object, ArrayList> {
        ProgressDialog pd;
        ArrayList contactList;

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Refreshing Contacts \nIt will take some time, Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected ArrayList doInBackground(Object... voids) {
            // Get Contact list from Phone
            contactList = new ArrayList<>();

            final String[] PROJECTION = new String[]{
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
            };

            ContentResolver cr = getActivity().getContentResolver();
            Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                try {
                    final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    final int idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);

                    String name, number = "", home = "", work = "", id;
                    while (cursor.moveToNext()) {
                        byte[] photo = new byte[0];
                        Bitmap profile = null;
                        id = cursor.getString(idIndex);
                        name = cursor.getString(nameIndex);

                        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                        number = "";
                        home = "";
                        work = "";
                        while (phones.moveToNext()) {
                            //  String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            switch (type) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    // do something with the Home number here...
                                    home = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                                    home = home.replaceAll("\\s", "");
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                                    // number = "(212)1234565";
                                    number = number.replaceAll("\\s", "");
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    // do something with the Work number here...
                                    work = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                                    work = work.replaceAll("\\s", "");
                                    break;
                            }
                        }
                        phones.close();

                        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
                        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                        Cursor curs = null;
                        try {
                            curs = getActivity().getContentResolver().query(photoUri,
                                    new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
                        } catch (Exception e) {
                            return null;
                        }
                        if (curs == null) {
                            return null;
                        }
                        try {
                            if (curs.moveToFirst()) {
                                photo = curs.getBlob(0);
                            } else {

                            }
                        } finally {
                            curs.close();
                        }

                        String email = "";
                        Cursor emailCur = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCur.moveToNext()) {
                            email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));//   emailList.add(email); // Here you will get list of email
                        }
                        emailCur.close();

                        String address = "";
                        Cursor addressCur = cr.query(
                                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (addressCur.moveToNext()) {
                            address = addressCur.getString(addressCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));

                        }
                        addressCur.close();


                        Contact objContact = new Contact();
                        objContact.setName(name);
                        objContact.setPhone(number);
                        objContact.setWorkPhone(work);
                        objContact.setHomePhone(home);
                        objContact.setAddress(address);
                        //  if (photo!=null||photo.length!=0) {
                        objContact.setImage(photo);
                        objContact.setEmail(email);
                        objContact.setId(id);

                        contactList.add(objContact);
                    }
                } finally {
                    cursor.close();
                }
            }
            return contactList;
        }

        @Override
        protected void onPostExecute(ArrayList contactList) {
            super.onPostExecute(contactList);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (null != contactList && contactList.size() != 0) {
                Collections.sort(contactList, new Comparator<Contact>() {

                    @Override
                    public int compare(Contact lhs, Contact rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });

            } else {
                Toast.makeText(getActivity(), "No Contact Found!!!", Toast.LENGTH_SHORT).show();
            }
            saveToTable(contactList);

        }
    }
}
