package com.mindyourlovedone.healthcare.model;

import java.io.Serializable;

/**
 * Created by varsha on 8/28/2017.
 */

public class Emergency implements Serializable {
    int id;
    String email = "";
    String mobile = "";
    String OtherRelation = "";
    String workPhone = "";
    String note = "";
    int connectionFlag;
    int isPrimary;
    String name = "";
    String phone = "";
    String photo;
    String photoCard;
    String relationType = "";
    String address = "";
    int image;

    public String getOtherRelation() {
        return OtherRelation;
    }

    public void setOtherRelation(String otherRelation) {
        OtherRelation = otherRelation;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getConnectionFlag() {
        return connectionFlag;
    }

    public void setConnectionFlag(int connectionFlag) {
        this.connectionFlag = connectionFlag;
    }

    public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoCard() {
        return photoCard;
    }

    public void setPhotoCard(String photoCard) {
        this.photoCard = photoCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}