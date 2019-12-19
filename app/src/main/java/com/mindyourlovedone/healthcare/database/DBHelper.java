package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by varsha on 7/27/2017.
 * Edited by shradha 28 Jan.
 * Final edit made by Nikita on 8-4-2019
 */

public class DBHelper extends SQLiteOpenHelper {
    // public static final String DATABASE_NAME = "MYLO.db";
    static final int DATABASE_VERSION = 4;//old version is less than 3

    public DBHelper(Context context, String DATABASE_NAME) {
        super(context, Environment.getExternalStorageDirectory() + "/MYLO/" + DATABASE_NAME + "/" + "MASTER.db", null, DATABASE_VERSION);
    }
    public DBHelper(Context context, String DATABASE_NAME, int i) {
        super(context,Environment.getExternalStorageDirectory() + "/MYLO/" + DATABASE_NAME + "/" + "MASTER.db", null, DATABASE_VERSION);
    }
    public DBHelper(Context context) {
        super(context, "MASTER.db", null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Contact Table
        String createContactTableQuery = ContactTableQuery.createContactTable();
        db.execSQL(createContactTableQuery);

        //PersonalInfoQuery Table
        db.execSQL(PersonalInfoQuery.createPersonalInfoTable());

        //MyConnectionsQuery Table
        db.execSQL(MyConnectionsQuery.createMyConnectionsTable());

        //MedInfoQuery Table
        db.execSQL(MedInfoQuery.createMedInfoTable());

        //Allergy Table
        db.execSQL(AllergyQuery.createAllergyTable());

        //Implants Table
        db.execSQL(MedicalImplantsQuery.createVaccineTable());
        db.execSQL(MedicalConditionQuery.createImplantsTable());


        //Hsopital Table
        db.execSQL(HospitalQuery.createHospitalTable());

        //History Table
        db.execSQL(HistoryQuery.createHistoryTable());

        //Doctor Table
        db.execSQL(SpecialistQuery.createDoctorTable());

        //Event Note Table
        db.execSQL(EventNoteQuery.createNoteTable());

        //Vital Table
        db.execSQL(VitalQuery.createVitalTable());

        //subscription table  -Nikita#Sub
        db.execSQL(SubscriptionQuery.createSubscriptionTable());

        //Prescription Table
        db.execSQL(PrescribeImageQuery.createImageTable());
        db.execSQL(DosageQuery.createDosageTable());
        db.execSQL(PrescriptionQuery.createPrescriptionTable());
        db.execSQL(PharmacyQuery.createPharmacyTable());
        db.execSQL(AideQuery.createAideTable());
        db.execSQL(FinanceQuery.createFinanceTable());
        db.execSQL(InsuranceQuery.createInsuranceTable());
        db.execSQL(CardQuery.createCardTable());
        db.execSQL(DocumentQuery.createDocumentTable());
        db.execSQL(AppointmentQuery.createAppointmentTable());
        db.execSQL(HospitalHealthQuery.createHospitalHealthTable());
        db.execSQL(DateQuery.createDosageTable());
        db.execSQL(PetQuery.createPetTable());
        db.execSQL(FormQuery.createDocumentTable());
        db.execSQL(VaccineQuery.createVaccineTable());
        db.execSQL(LivingQuery.createLivingTable());
        db.execSQL(ImplantQuery.createVaccineTable());
        db.execSQL(ImageQuery.createTable());
        db.execSQL(ContactDataQuery.createContactDataTable());
        db.execSQL(PrescriptionUpload.createDocumentTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//nikita
        if(oldVersion <4){//Nikita#Sub
            if (!isTableExists("Subscription", db)) {
                db.execSQL(SubscriptionQuery.createSubscriptionTable()); //Nikita#Sub
            }
        }
        if (oldVersion < 3) {
            //New Tables
            if (!isTableExists("ContactNumber", db)) {
                db.execSQL(ContactDataQuery.createContactDataTable());
                fillMultiContacts(db, "MyConnections", "check");
                fillMultiContacts(db, "FinanceLegal", "Finance");
                fillMultiContacts(db, "HospitalHealth", "Hospital");
                fillMultiContacts(db, "Insurance", "Insurance");
                fillMultiContacts(db, "Pharmacy", "Pharmacy");
                fillMultiContacts(db, "Specialist", "check");
            }
            if (!isTableExists("VitalSigns", db)) {
                db.execSQL(VitalQuery.createVitalTable());
            }
            if (!isTableExists("PrescriptionUpload", db)) {
                db.execSQL(PrescriptionUpload.createDocumentTable());
            }

            //Documents Table Note
            if (!columnExistsInTable(db, "Documents", "Note")) {
                db.execSQL("ALTER TABLE Documents ADD COLUMN Note TEXT");
            }

            //Finance Table asCard
            if (!columnExistsInTable(db, "FinanceLegal", "Has_Card")) {
                db.execSQL("ALTER TABLE FinanceLegal ADD COLUMN Has_Card VARCHAR(10)");
            }

            //Pharmacy Table asCard
            if (!columnExistsInTable(db, "Pharmacy", "Has_Card")) {
                db.execSQL("ALTER TABLE Pharmacy ADD COLUMN Has_Card VARCHAR(10)");
            }

            //HospitalHealth Table asCard
            if (!columnExistsInTable(db, "HospitalHealth", "Has_Card")) {
                db.execSQL("ALTER TABLE HospitalHealth ADD COLUMN Has_Card VARCHAR(10)");
            }

            //Specialist Table asCard
            if (!columnExistsInTable(db, "Specialist", "Has_Card")) {
                db.execSQL("ALTER TABLE Specialist ADD COLUMN Has_Card VARCHAR(10)");
            }

            //Insurance Table asCard, Aent Email, anet Pone
            if (!columnExistsInTable(db, "Insurance", "Has_Card")) {
                db.execSQL("ALTER TABLE Insurance ADD COLUMN Has_Card VARCHAR(10)");
            }
            if (!columnExistsInTable(db, "Insurance", "AgentEmail")) {
                db.execSQL("ALTER TABLE Insurance ADD COLUMN AgentEmail VARCHAR(50)");
            }
            if (!columnExistsInTable(db, "Insurance", "AgentNumber")) {
                db.execSQL("ALTER TABLE Insurance ADD COLUMN AgentNumber VARCHAR(20)");
            }

            //Implants Table location,details, note
            if (!columnExistsInTable(db, "ImplantsInfo", "Location")) {
                db.execSQL("ALTER TABLE ImplantsInfo ADD COLUMN Location VARCHAR(100)");
            }
            if (!columnExistsInTable(db, "ImplantsInfo", "Details")) {
                db.execSQL("ALTER TABLE ImplantsInfo ADD COLUMN Details VARCHAR(100)");
            }
            if (!columnExistsInTable(db, "ImplantsInfo", "Note")) {
                db.execSQL("ALTER TABLE ImplantsInfo ADD COLUMN Note TEXT");
            }

            //Insurance Form date
            if (!columnExistsInTable(db, "InsuranceForms", "Date")) {
                db.execSQL("ALTER TABLE InsuranceForms ADD COLUMN Date TEXT");
            }

            //Medical Profile
            if (!columnExistsInTable(db, "MedInfo", "SurgicalHistoryNote")) {
                db.execSQL("ALTER TABLE MedInfo ADD COLUMN SurgicalHistoryNote TEXT");
            }
            if (!columnExistsInTable(db, "MedInfo", "ImplantsNote")) {
                db.execSQL("ALTER TABLE MedInfo ADD COLUMN ImplantsNote TEXT");
            }
            if (!columnExistsInTable(db, "MedInfo", "ImmunizationNote")) {
                db.execSQL("ALTER TABLE MedInfo ADD COLUMN ImmunizationNote TEXT");
            }

            //MyCOnnections
            if (!columnExistsInTable(db, "MyConnections", "Has_Card")) {
                db.execSQL("ALTER TABLE MyConnections ADD COLUMN Has_Card VARCHAR(10)");
            }
            if (!columnExistsInTable(db, "MyConnections", "People")) {
                db.execSQL("ALTER TABLE MyConnections ADD COLUMN People VARCHAR(50)");
            }

            //Pet
            if (!columnExistsInTable(db, "PetInfo", "GuardAddress")) {
                db.execSQL("ALTER TABLE PetInfo ADD COLUMN GuardAddress TEXT");
            }
            if (!columnExistsInTable(db, "PetInfo", "GuardPhone")) {
                db.execSQL("ALTER TABLE PetInfo ADD COLUMN GuardPhone TEXT");
            }
            if (!columnExistsInTable(db, "PetInfo", "VeterianPhone")) {
                db.execSQL("ALTER TABLE PetInfo ADD COLUMN VeterianPhone TEXT");
            }
            if (!columnExistsInTable(db, "PetInfo", "VeterianAddress")) {
                db.execSQL("ALTER TABLE PetInfo ADD COLUMN VeterianAddress TEXT");
            }
//varsha added appointnment and insurance card
            if (!columnExistsInTable(db, "Appointment", "Note")) {
                db.execSQL("ALTER TABLE Appointment ADD COLUMN Note TEXT");
            }
            if (!columnExistsInTable(db, "InsuranceCard", "OtherInsurance")) {
                db.execSQL("ALTER TABLE InsuranceCard ADD COLUMN OtherInsurance VARCHAR(50)");
            }
        }

//        if (oldVersion != 1 ) {//need to change this new version will be 3 after
//            //Contact Table
//            db.execSQL(ContactTableQuery.dropTable());
//
//            //PersonalInfoQuery Table
//            db.execSQL(PersonalInfoQuery.dropTable());
//
//            //MyConnectionsQuery Table
//            db.execSQL(MyConnectionsQuery.dropTable());
//
//            //MedInfoQuery Table
//            db.execSQL(MedInfoQuery.dropTable());
//
//            //Allergy Table
//            db.execSQL(AllergyQuery.dropTable());
//
//            //Implants Table
//            db.execSQL(MedicalImplantsQuery.dropTable());
//            db.execSQL(MedicalConditionQuery.dropTable());
//
//            //Hospital Table
//            db.execSQL(HospitalQuery.dropTable());
//
//            //History Table
//            db.execSQL(HistoryQuery.dropTable());
//
//            //Doctor Table
//            db.execSQL(SpecialistQuery.dropTable());
//
//            //Event Note
//            db.execSQL(EventNoteQuery.dropTable());
//
//            //Vital Table
//            db.execSQL(VitalQuery.dropTable());
//
//            //Prescription
//            db.execSQL(PrescribeImageQuery.dropTable());
//            db.execSQL(PrescriptionQuery.dropTable());
//            db.execSQL(DosageQuery.dropTable());
//            db.execSQL(PharmacyQuery.dropTable());
//            db.execSQL(AideQuery.dropTable());
//            db.execSQL(FinanceQuery.dropTable());
//            db.execSQL(InsuranceQuery.dropTable());
//            db.execSQL(CardQuery.dropTable());
//            db.execSQL(DocumentQuery.dropTable());
//            db.execSQL(AppointmentQuery.dropTable());
//            db.execSQL(HospitalHealthQuery.dropTable());
//            db.execSQL(DateQuery.dropTable());
//            db.execSQL(PetQuery.dropTable());
//            db.execSQL(FormQuery.dropTable());
//            db.execSQL(VaccineQuery.dropTable());
//            db.execSQL(LivingQuery.dropTable());
//            db.execSQL(ImplantQuery.dropTable());
//            db.execSQL(ImageQuery.dropTable());
//            db.execSQL(ContactDataQuery.dropTable());
//            onCreate(db);
//        }

    }

    public boolean isTableExists(String tableName, SQLiteDatabase db) {//nikita

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public static boolean columnExistsInTable(SQLiteDatabase db, String table, String columnToCheck) {//nikita
        Cursor cursor = null;
        try {
            //query a row. don't acquire db lock
            cursor = db.rawQuery("SELECT * FROM " + table + " LIMIT 0", null);

            // getColumnIndex()  will return the index of the column
            //in the table if it exists, otherwise it will return -1
            if (cursor.getColumnIndex(columnToCheck) != -1) {
                //great, the column exists
                return true;
            } else {
                //sorry, the column does not exist
                return false;
            }

        } catch (Exception Exp) {
            //Something went wrong with SQLite.
            //If the table exists and your query was good,
            //the problem is likely that the column doesn't exist in the table.
            return false;
        } finally {
            //close the cursor
            if (cursor != null) cursor.close();
        }
    }

    public static boolean fillMultiContacts(SQLiteDatabase db, String table, String from) {//nikita
        Cursor cursor = null;
        Cursor cursorProfile = null;
        try {
            String email = "";
            int userid = 0, fromtableid = 0;

            cursorProfile = db.rawQuery("SELECT * FROM MyConnections", null);
            if (cursorProfile.moveToFirst()) {
                userid = cursorProfile.getInt(cursorProfile.getColumnIndex("UserId"));
                email = cursorProfile.getString(cursorProfile.getColumnIndex("Email"));
            }

            cursor = db.rawQuery("SELECT * FROM " + table, null);

            if (cursor.moveToFirst()) {
                do {
                    if (table.equalsIgnoreCase("Specialist")) {
                        int isPhysician = cursor.getInt(cursor.getColumnIndex("IsPhysician"));
                        if (isPhysician == 1) {
                            from = "Primary";
                        } else {
                            from = "Doctor";
                        }
                    }

                    if (table.equalsIgnoreCase("MyConnections")) {
                        int flag = cursor.getInt(cursor.getColumnIndex("Flag"));
                        if (flag == 1) {
                            from = "Personal Profile";
                        } else {
                            from = "Emergency";
                        }
                    }

                    if (from.equalsIgnoreCase("Personal Profile")) {
                        fromtableid = -1;
                    } else {
                        fromtableid = cursor.getInt(cursor.getColumnIndex("Id"));
                    }

                    if (cursor.getColumnIndex("Mobile") != -1
                            && !cursor.getString(cursor.getColumnIndex("Mobile")).isEmpty()) {
                        //great, the column exists
                        ContentValues cv = new ContentValues();
                        cv.put("idFromTable", fromtableid);
                        cv.put("UserId", userid);
                        cv.put("value", cursor.getString(cursor.getColumnIndex("Mobile")));
                        cv.put("userEmail", email);
                        cv.put("contactType", "Mobile");
                        cv.put("fromTable", from);
                        db.insert("ContactNumber", null, cv);
                    }
                    if (cursor.getColumnIndex("HomePhone") != -1
                            && !cursor.getString(cursor.getColumnIndex("HomePhone")).isEmpty()) {
                        //great, the column exists
                        ContentValues cv = new ContentValues();
                        cv.put("idFromTable", fromtableid);
                        cv.put("UserId", userid);
                        cv.put("value", cursor.getString(cursor.getColumnIndex("HomePhone")));
                        cv.put("userEmail", email);
                        cv.put("contactType", "Home");
                        cv.put("fromTable", from);
                        db.insert("ContactNumber", null, cv);
                    }
                    if (cursor.getColumnIndex("WorkPhone") != -1
                            && !cursor.getString(cursor.getColumnIndex("WorkPhone")).isEmpty()) {
                        //great, the column exists
                        ContentValues cv = new ContentValues();
                        cv.put("idFromTable", fromtableid);
                        cv.put("UserId", userid);
                        cv.put("value", cursor.getString(cursor.getColumnIndex("WorkPhone")));
                        cv.put("userEmail", email);
                        cv.put("contactType", "Work");
                        cv.put("fromTable", from);
                        db.insert("ContactNumber", null, cv);
                    }
                    if (cursor.getColumnIndex("Faxno") != -1
                            && !cursor.getString(cursor.getColumnIndex("Faxno")).isEmpty()) {
                        //great, the column exists
                        ContentValues cv = new ContentValues();
                        cv.put("idFromTable", fromtableid);
                        cv.put("UserId", userid);
                        cv.put("value", cursor.getString(cursor.getColumnIndex("Faxno")));
                        cv.put("userEmail", email);
                        cv.put("contactType", "Fax");
                        cv.put("fromTable", from);
                        db.insert("ContactNumber", null, cv);
                    }
                    if (cursor.getColumnIndex("OtherPhone") != -1
                            && !cursor.getString(cursor.getColumnIndex("OtherPhone")).isEmpty()) {
                        //great, the column exists
                        ContentValues cv = new ContentValues();
                        cv.put("idFromTable", fromtableid);
                        cv.put("UserId", userid);
                        cv.put("value", cursor.getString(cursor.getColumnIndex("OtherPhone")));
                        cv.put("userEmail", email);
                        cv.put("contactType", "Mobile");
                        cv.put("fromTable", from);
                        db.insert("ContactNumber", null, cv);
                    }
                    if (cursor.getColumnIndex("OfficePhone") != -1
                            && !cursor.getString(cursor.getColumnIndex("OfficePhone")).isEmpty()) {
                        //great, the column exists
                        ContentValues cv = new ContentValues();
                        cv.put("idFromTable", fromtableid);
                        cv.put("UserId", userid);
                        cv.put("value", cursor.getString(cursor.getColumnIndex("OfficePhone")));
                        cv.put("userEmail", email);
                        cv.put("contactType", "Office");
                        cv.put("fromTable", from);
                        db.insert("ContactNumber", null, cv);
                    }
                    if (cursor.getColumnIndex("AfterHourPhone") != -1
                            && !cursor.getString(cursor.getColumnIndex("AfterHourPhone")).isEmpty()) {
                        //great, the column exists
                        ContentValues cv = new ContentValues();
                        cv.put("idFromTable", fromtableid);
                        cv.put("UserId", userid);
                        cv.put("value", cursor.getString(cursor.getColumnIndex("AfterHourPhone")));
                        cv.put("userEmail", email);
                        cv.put("contactType", "Mobile");
                        cv.put("fromTable", from);
                        db.insert("ContactNumber", null, cv);
                    }
                } while (cursor.moveToNext());
            }

        } catch (Exception Exp) {
            //Something went wrong with SQLite.
            return false;
        } finally {
            //close the cursor
            if (cursor != null) cursor.close();
            if (cursorProfile != null) cursorProfile.close();
        }
        return true;
    }
}
