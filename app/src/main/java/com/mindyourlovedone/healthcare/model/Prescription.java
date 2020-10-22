package com.mindyourlovedone.healthcare.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by welcome on 9/18/2017.
 */

public class Prescription implements Serializable {

    String rx = "";
    String frequency = "";
    int preid;
    String medicine = "";
    String dose = "";
    int unique;
    String doctor = "";
    String dates = "";
    String note = "";
    String purpose = "";
    String RX = "";
    String Pre = "";
    int id;
    int userid;
    String doseForm = "";
    ArrayList<Dosage> dosageList = new ArrayList<>();
    ArrayList<PrescribeImage> prescriptionImageList = new ArrayList<>();

    String doseage = "";
    String malady ="";
    String pharmacy = "";

    public String getDoseage() {
        return doseage;
    }

    public void setDoseage(String doseage) {
        this.doseage = doseage;
    }

    public String getMalady() {
        return malady;
    }

    public void setMalady(String malady) {
        this.malady = malady;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }



    public String getRx() {
        return rx;
    }

    public void setRx(String rx) {
        this.rx = rx;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getPreid() {
        return preid;
    }

    public void setPreid(int preid) {
        this.preid = preid;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public String getRX() {
        return RX;
    }

    public void setRX(String RX) {
        this.RX = RX;
    }

    public String getPre() {
        return Pre;
    }

    public void setPre(String pre) {
        Pre = pre;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDoseForm() {
        return doseForm;
    }

    public void setDoseForm(String doseForm) {
        this.doseForm = doseForm;
    }

    public ArrayList<Dosage> getDosageList() {
        return dosageList;
    }

    public void setDosageList(ArrayList<Dosage> dosageList) {
        this.dosageList = dosageList;
    }

    public ArrayList<PrescribeImage> getPrescriptionImageList() {
        return prescriptionImageList;
    }

    public void setPrescriptionImageList(ArrayList<PrescribeImage> prescriptionImageList) {
        this.prescriptionImageList = prescriptionImageList;
    }
}
