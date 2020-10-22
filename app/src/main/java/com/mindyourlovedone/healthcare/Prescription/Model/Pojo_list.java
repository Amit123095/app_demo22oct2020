package com.mindyourlovedone.healthcare.Prescription.Model;

import com.mindyourlovedone.healthcare.model.Dosage;
import com.mindyourlovedone.healthcare.model.PrescribeImage;

import java.io.Serializable;
import java.util.ArrayList;

public class Pojo_list implements Serializable {
    
    
 String date = "";
 String medicine = "";
 String malady ="";
    String rx = "";
    String frequency = "";
    int preid;
    String doseage = "";
    int unique;
    String doctor = "";
    String note = "";
    String RX = "";
    String Pre = "";
    int id;
    String pharmacy = "";
    ArrayList<PrescribeImage> prescriptionImageList = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getMalady() {
        return malady;
    }

    public void setMalady(String malady) {
        this.malady = malady;
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

    public String getDoseage() {
        return doseage;
    }

    public void setDoseage(String doseage) {
        this.doseage = doseage;
    }

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public ArrayList<PrescribeImage> getPrescriptionImageList() {
        return prescriptionImageList;
    }

    public void setPrescriptionImageList(ArrayList<PrescribeImage> prescriptionImageList) {
        this.prescriptionImageList = prescriptionImageList;
    }

    int userid;

 

    
}
